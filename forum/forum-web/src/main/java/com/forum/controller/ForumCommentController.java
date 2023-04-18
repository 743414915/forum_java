package com.forum.controller;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.constants.Constants;
import com.forum.controller.base.ABaseController;
import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.po.ForumComment;
import com.forum.entity.po.LikeRecord;
import com.forum.entity.query.ForumCommentQuery;
import com.forum.entity.vo.ResponseVO;
import com.forum.enums.*;
import com.forum.exception.BusinessException;
import com.forum.service.ForumCommentService;
import com.forum.service.LikeRecordService;
import com.forum.utils.StringTools;
import com.forum.utils.SysCacheUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class ForumCommentController extends ABaseController {
    @Resource
    private ForumCommentService forumCommentService;

    @Resource
    private LikeRecordService likeRecordService;

    @RequestMapping("/loadComment")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO loadComment(HttpSession session, @VerifyParam(required = true) String articleId, Integer pageNo, Integer orderType) throws BusinessException {
        final String ORDER_TYPE0 = "good_count desc,comment_id asc";
        final String ORDER_TYPE1 = "comment_id desc";
        if (!SysCacheUtils.getSysSetting().getCommentSetting().getCommentOpen()) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        ForumCommentQuery commentQuery = new ForumCommentQuery();
        commentQuery.setArticleId(articleId);
        String orderBy = orderType == null || orderType.equals(Constants.ZERO) ? ORDER_TYPE0 : ORDER_TYPE1;
        commentQuery.setOrderBy("top_type desc," + orderBy);
        commentQuery.setPageNo(pageNo);

        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (null != userDto) {
            commentQuery.setQueryLikeType(true);
            commentQuery.setCurrentUserId(userDto.getUserId());
        } else {
            commentQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        }
        commentQuery.setPageSize(PageSize.SIZE50.getSize());
        commentQuery.setPCommentId(Constants.ZERO);
        commentQuery.setLoadChildren(true);
        return getSuccessResponseVO(forumCommentService.findListByPage(commentQuery));
    }

    @RequestMapping("/doLike")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO doLike(HttpSession session, @VerifyParam(required = true) Integer commentId) throws BusinessException {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        String objectId = String.valueOf(commentId);
        likeRecordService.doLike(objectId, userDto.getUserId(), userDto.getNickName(), OperRecordOpTypeEnum.COMMENT_LIKE);

        LikeRecord likeRecord =
                likeRecordService.getLikeRecordByObjectIdAndUserIdAndOpType(objectId, userDto.getUserId(), OperRecordOpTypeEnum.COMMENT_LIKE.getType());

        ForumComment comment = forumCommentService.getForumCommentByCommentId(commentId);

        comment.setLikeType(likeRecord == null ? 0 : 1);
        return getSuccessResponseVO(comment);
    }

    @RequestMapping("/changeTopType")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO changeTopType(HttpSession session,
                                    @VerifyParam(required = true) Integer commentId,
                                    @VerifyParam(required = true) Integer topType) throws BusinessException {
        forumCommentService.changeTopType(getUserInfoFromSession(session).getUserId(), commentId, topType);

        return getSuccessResponseVO(null);
    }

    @RequestMapping("/postComment")
    @GlobalInterceptor(checkParams = true, checkLogin = true)
    public ResponseVO postComment(HttpSession session,
                                  @VerifyParam(required = true) String articleId,
                                  @VerifyParam(required = true) Integer pCommentId,
                                  @VerifyParam(min = 1, max = 800) String content,
                                  MultipartFile image,
                                  String replyUserId) throws BusinessException {
        if (!SysCacheUtils.getSysSetting().getCommentSetting().getCommentOpen()) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (image == null && StringTools.isEmpty(content)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        SessionWebUserDto userDto = getUserInfoFromSession(session);
        content = StringTools.ecpapeHtml(content);
        ForumComment comment = new ForumComment();
        comment.setUserId(userDto.getUserId());
        comment.setNickName(userDto.getNickName());
        comment.setUserIpAddress(userDto.getProvince());
        comment.setPCommentId(pCommentId);
        comment.setArticleId(articleId);
        comment.setContent(content);
        comment.setReplyUserId(replyUserId);
        comment.setTopType(CommentTopTypeEnum.NO_TOP.getType());

        forumCommentService.postComment(comment, image);

        if (pCommentId != 0) {
            ForumCommentQuery forumCommentQuery = new ForumCommentQuery();
            forumCommentQuery.setArticleId(articleId);
            forumCommentQuery.setPCommentId(pCommentId);
            forumCommentQuery.setOrderBy("comment_id asc");
            List<ForumComment> children = forumCommentService.findListByParam(forumCommentQuery);
            return getSuccessResponseVO(children);
        }
        return getSuccessResponseVO(comment);
    }
}
