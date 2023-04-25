package com.forum.service.impl;

import com.forum.constants.Constants;
import com.forum.entity.dto.FileUploadDto;
import com.forum.entity.po.ForumArticle;
import com.forum.entity.po.ForumComment;
import com.forum.entity.po.UserInfo;
import com.forum.entity.po.UserMessage;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.query.ForumCommentQuery;
import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.*;
import com.forum.exception.BusinessException;
import com.forum.mappers.ForumArticleMapper;
import com.forum.mappers.ForumCommentMapper;
import com.forum.service.ForumCommentService;
import com.forum.service.UserInfoService;
import com.forum.service.UserMessageService;
import com.forum.utils.FileUtils;
import com.forum.utils.StringTools;
import com.forum.utils.SysCacheUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

;

/**
 * @Description: 评论ForumCommentServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("forumCommentService")
public class ForumCommentServiceImpl implements ForumCommentService {

    @Resource
    private ForumCommentMapper<ForumComment, ForumCommentQuery> forumCommentMapper;

    @Resource
    private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserMessageService userMessageService;

    @Resource
    private FileUtils fileUtils;

    @Resource
    @Lazy
    private ForumCommentService forumCommentService;

    /**
     * 根据条件查询列表
     */
    public List<ForumComment> findListByParam(ForumCommentQuery query) {
        //获取一级评论
        List<ForumComment> list = this.forumCommentMapper.selectList(query);
        //获取二级评论
        if (query.getLoadChildren() != null && query.getLoadChildren()) {
            ForumCommentQuery subQuery = new ForumCommentQuery();
            subQuery.setQueryLikeType(query.getQueryLikeType());
            subQuery.setCurrentUserId(query.getCurrentUserId());
            subQuery.setArticleId(query.getArticleId());
            subQuery.setStatus(query.getStatus());

            List<Integer> pCommentIdList = list.stream().map(ForumComment::getCommentId).distinct().collect(Collectors.toList());
            subQuery.setpCommentIdList(pCommentIdList);

            List<ForumComment> subCommentList = this.forumCommentMapper.selectList(subQuery);

            Map<Integer, List<ForumComment>> tempMap = subCommentList.stream().collect(Collectors.groupingBy(ForumComment::getPCommentId));
            list.forEach(item -> {
                item.setChildren(tempMap.get(item.getCommentId()));
            });
        }
        return list;
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(ForumCommentQuery query) {
        return this.forumCommentMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<ForumComment> findListByPage(ForumCommentQuery query) {
        Integer count = this.findCountByParam(query);
        int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<ForumComment> list = this.findListByParam(query);
        PaginationResultVO<ForumComment> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(ForumComment bean) {
        return this.forumCommentMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<ForumComment> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.forumCommentMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<ForumComment> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.forumCommentMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据CommentId查询
     */
    public ForumComment getForumCommentByCommentId(Integer commentId) {
        return this.forumCommentMapper.selectByCommentId(commentId);
    }

    /**
     * 根据CommentId更新
     */
    public Integer updateForumCommentByCommentId(ForumComment bean, Integer commentId) {
        return this.forumCommentMapper.updateByCommentId(bean, commentId);
    }

    /**
     * 根据CommentId删除
     */
    public Integer deleteForumCommentByCommentId(Integer commentId) {
        return this.forumCommentMapper.deleteByCommentId(commentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeTopType(String userId, Integer commentId, Integer topType) throws BusinessException {
        CommentTopTypeEnum topTypeEnum = CommentTopTypeEnum.getByType(topType);
        if (null == topTypeEnum) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        ForumComment forumComment = forumCommentMapper.selectByCommentId(commentId);
        if (null == forumComment) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        ForumArticle forumArticle = forumArticleMapper.selectByArticleId(forumComment.getArticleId());
        if (null == forumArticle) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        if (!forumArticle.getUserId().equals(userId) || forumComment.getPCommentId() != 0) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (forumComment.getTopType().equals(topType)) {
            return;
        }
        // 同一个帖子下面的评论只能置顶一个，所以要先先清除其他的置顶
        if (CommentTopTypeEnum.TOP.getType().equals(topType)) {
            forumCommentMapper.updateTopTypeByArticleId(forumArticle.getArticleId());
        }
        ForumComment updateInfo = new ForumComment();
        updateInfo.setTopType(topType);
        forumCommentMapper.updateByCommentId(updateInfo, commentId);
    }

    @Override
    public void postComment(ForumComment comment, MultipartFile image) throws BusinessException {
        ForumArticle forumArticle = forumArticleMapper.selectByArticleId(comment.getArticleId());
        if (null == forumArticle || !ArticleStatusEnum.AUDIT.getStatus().equals(forumArticle.getStatus())) {
            throw new BusinessException("评论的文章不存在");
        }
        ForumComment pComment = null;
        if (comment.getPCommentId() != 0) {
            pComment = forumCommentMapper.selectByCommentId(comment.getPCommentId());
            if (pComment == null) {
                throw new BusinessException("回复的评论不存在");
            }
        }
        // 判断回复的用户是否存在
        if (!StringTools.isEmpty(comment.getReplyUserId())) {
            UserInfo userInfo = userInfoService.getUserInfoByUserId(comment.getReplyUserId());
            if (userInfo == null) {
                throw new BusinessException("回复的用户不存在");
            }
            comment.setReplyNickName(userInfo.getNickName());
        }
        comment.setPostTime(new Date());
        if (image != null) {
            // 带图片
            FileUploadDto fileUploadDto = fileUtils.uploadFile2Local(image, Constants.FILE_FOLDER_IMAGE, FileUploadTypeEnum.COMMENT_IMAGE);
            comment.setImgPath(fileUploadDto.getLocalPath());
        }

        Boolean needAudit = SysCacheUtils.getSysSetting().getAuditSetting().getCommentAudit();
        comment.setStatus(needAudit ? CommentStatusEnum.NO_AUDIT.getStatus() : CommentStatusEnum.AUDIT.getStatus());

        this.forumCommentMapper.insert(comment);

        if (needAudit) {
            return;
        }
        updateCommentInfo(comment, forumArticle, pComment);
    }

    public void updateCommentInfo(ForumComment comment, ForumArticle forumArticle, ForumComment pComment) throws BusinessException {
        Integer commentIntegral = SysCacheUtils.getSysSetting().getCommentSetting().getCommentIntegral();
        if (commentIntegral > 0) {
            this.userInfoService.updateUserIntegral(comment.getUserId(), UserIntegralOperTypeEnum.POST_COMMENT, UserIntegralChangeTypeEnum.ADD.getChangeType(), commentIntegral);
        }
        if (comment.getPCommentId() == 0) {
            this.forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.COMMENT_COUNT.getType(), Constants.ONE, comment.getArticleId());
        }
        // 记录消息
        UserMessage userMessage = new UserMessage();
        userMessage.setMessageType(MessageTypeEnum.COMMENT.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setArticleId(comment.getArticleId());
        userMessage.setCommentId(comment.getCommentId());
        userMessage.setSendUserId(comment.getUserId());
        userMessage.setSendNickName(comment.getNickName());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        userMessage.setArticleTitle(forumArticle.getTitle());
        userMessage.setMessageContent(comment.getContent());

        if (comment.getPCommentId() == 0) {
            // 一级评论
            userMessage.setReceivedUserId(forumArticle.getUserId());
        } else if (comment.getPCommentId() != 0 && StringTools.isEmpty(comment.getReplyUserId())) {
//            二级评论回复给一级评论
            userMessage.setReceivedUserId(pComment.getUserId());
        } else if (comment.getPCommentId() != 0 && !StringTools.isEmpty(comment.getReplyUserId())) {
//            二级评论回复二级评论
            userMessage.setReceivedUserId(comment.getReplyUserId());
        }
        //发布人和接收人不是一个人
        if (!comment.getUserId().equals(userMessage.getReceivedUserId())) {
            userMessageService.add(userMessage);
        }
    }

    @Override
    public void delComment(String commentIds) throws BusinessException {
        String[] commentIdArray = commentIds.split(",");
        for (String commentIdStr : commentIdArray) {
            Integer commentId = Integer.parseInt(commentIdStr);
            forumCommentService.delCommentSingle(commentId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delCommentSingle(Integer commentId) throws BusinessException {

        ForumComment comment = forumCommentMapper.selectByCommentId(commentId);
        if (null == comment || CommentStatusEnum.DEL.getStatus().equals(comment.getStatus())) {
            return;
        }
        ForumComment forumComment = new ForumComment();
        forumComment.setStatus(CommentStatusEnum.DEL.getStatus());
        forumCommentMapper.updateByCommentId(forumComment, commentId);

        // 删除已经审核的文章，更新文章数量
        if (CommentStatusEnum.AUDIT.getStatus().equals(comment.getStatus())) {
            if (comment.getPCommentId() == 0) {
                forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.COMMENT_COUNT.getType(), Constants.NEGATIVE_ONE, comment.getArticleId());
            }
            Integer integral = SysCacheUtils.getSysSetting().getPostSetting().getPostIntegral();
            userInfoService.updateUserIntegral(comment.getUserId(), UserIntegralOperTypeEnum.DEL_COMMENT, UserIntegralChangeTypeEnum.REDUCE.getChangeType(), integral);
        }
        UserMessage userMessage = new UserMessage();
        userMessage.setReceivedUserId(comment.getUserId());
        userMessage.setMessageType(MessageTypeEnum.SYS.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        userMessage.setMessageContent("评论【" + comment.getContent() + "】已被管理员删除");
        userMessageService.add(userMessage);
    }

    @Override
    public void auditComment(String commentIds) throws BusinessException {
        String[] commentIdArray = commentIds.split(",");
        for (String commentIdStr : commentIdArray) {
            Integer commentId = Integer.parseInt(commentIdStr);
            forumCommentService.auditCommentSingle(commentId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditCommentSingle(Integer commentId) throws BusinessException {
        ForumComment comment = forumCommentMapper.selectByCommentId(commentId);
        if (!CommentStatusEnum.NO_AUDIT.getStatus().equals(comment.getStatus())) {
            return;
        }
        ForumComment forumComment = new ForumComment();
        forumComment.setStatus(CommentStatusEnum.AUDIT.getStatus());
        forumCommentMapper.updateByCommentId(forumComment, commentId);
        ForumArticle forumArticle = forumArticleMapper.selectByArticleId(comment.getArticleId());
        ForumComment pComment = null;
        if (comment.getPCommentId() != 0 && StringTools.isEmpty(comment.getReplyUserId())) {
            pComment = forumCommentMapper.selectByCommentId(comment.getPCommentId());
        }
        updateCommentInfo(comment, forumArticle, pComment);
    }
}