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
import com.forum.enums.ArticleStatusEnum;
import com.forum.enums.OperRecordOpTypeEnum;
import com.forum.enums.PageSize;
import com.forum.enums.ResponseCodeEnum;
import com.forum.exception.BusinessException;
import com.forum.service.ForumCommentService;
import com.forum.service.LikeRecordService;
import com.forum.utils.SysCacheUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
        commentQuery.setOrderBy("top_type," + orderBy);
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
    public ResponseVO loadComment(HttpSession session, @VerifyParam(required = true) Integer commentId) throws BusinessException {
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
}
