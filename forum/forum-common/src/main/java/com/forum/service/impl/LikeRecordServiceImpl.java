package com.forum.service.impl;

import com.forum.constants.Constants;
import com.forum.entity.po.ForumArticle;
import com.forum.entity.po.LikeRecord;
import com.forum.entity.po.UserMessage;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.query.LikeRecordQuery;
import com.forum.entity.query.SimplePage;
import com.forum.entity.query.UserMessageQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.*;
import com.forum.exception.BusinessException;
import com.forum.mappers.ForumArticleMapper;
import com.forum.mappers.LikeRecordMapper;
import com.forum.mappers.UserMessageMapper;
import com.forum.service.LikeRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

;

/**
 * @Description: 点赞记录LikeRecordServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("likeRecordService")
public class LikeRecordServiceImpl implements LikeRecordService {

    @Resource
    private LikeRecordMapper<LikeRecord, LikeRecordQuery> likeRecordMapper;

    @Resource
    private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;

    @Resource
    private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;

    /**
     * 根据条件查询列表
     */
    public List<LikeRecord> findListByParam(LikeRecordQuery query) {
        return this.likeRecordMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(LikeRecordQuery query) {
        return this.likeRecordMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<LikeRecord> findListByPage(LikeRecordQuery query) {
        Integer count = this.findCountByParam(query);
        int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<LikeRecord> list = this.findListByParam(query);
        PaginationResultVO<LikeRecord> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(LikeRecord bean) {
        return this.likeRecordMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<LikeRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.likeRecordMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<LikeRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.likeRecordMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据OpId查询
     */
    public LikeRecord getLikeRecordByOpId(Integer opId) {
        return this.likeRecordMapper.selectByOpId(opId);
    }

    /**
     * 根据OpId更新
     */
    public Integer updateLikeRecordByOpId(LikeRecord bean, Integer opId) {
        return this.likeRecordMapper.updateByOpId(bean, opId);
    }

    /**
     * 根据OpId删除
     */
    public Integer deleteLikeRecordByOpId(Integer opId) {
        return this.likeRecordMapper.deleteByOpId(opId);
    }

    /**
     * 根据ObjectIdAndUserIdAndOpType查询
     */
    public LikeRecord getLikeRecordByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType) {
        return this.likeRecordMapper.selectByObjectIdAndUserIdAndOpType(objectId, userId, opType);
    }

    /**
     * 根据ObjectIdAndUserIdAndOpType更新
     */
    public Integer updateLikeRecordByObjectIdAndUserIdAndOpType(LikeRecord bean, String objectId, String userId, Integer opType) {
        return this.likeRecordMapper.updateByObjectIdAndUserIdAndOpType(bean, objectId, userId, opType);
    }

    /**
     * 根据ObjectIdAndUserIdAndOpType删除
     */
    public Integer deleteLikeRecordByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType) {
        return this.likeRecordMapper.deleteByObjectIdAndUserIdAndOpType(objectId, userId, opType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doLike(String objectId, String userId, String nickName, OperRecordOpTypeEnum opTypeEnum) throws BusinessException {
        UserMessage userMessage = new UserMessage();
        userMessage.setCreateTime(new Date());

        switch (opTypeEnum) {
            case ARTICLE_LIKE:
                ForumArticle forumArticle = forumArticleMapper.selectByArticleId(objectId);
                if (forumArticle == null) {
                    throw new BusinessException("文章不存在");
                }

                articleLike(forumArticle, objectId, userId, opTypeEnum);

                userMessage.setArticleId(objectId);
                userMessage.setArticleTitle(forumArticle.getTitle());
                userMessage.setMessageType(MessageTypeEnum.ARTICLE_LIKE.getType());
                userMessage.setCommentId(Constants.ZERO);
                userMessage.setReceivedUserId(forumArticle.getUserId());
                break;
            case COMMENT_LIKE:
                break;
        }
        userMessage.setSendUserId(userId);
        userMessage.setSendNickName(nickName);
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        if (!userId.equals(userMessage.getReceivedUserId())) {
            UserMessage dbInfo = userMessageMapper.selectByArticleIdAndCommentIdAndSendUserIdAndMessageType(userMessage.getArticleId(),
                    userMessage.getCommentId(), userMessage.getSendUserId(), userMessage.getMessageType());
            if (dbInfo == null) {
                userMessageMapper.insert(userMessage);
            }
        }
    }

    public void articleLike(ForumArticle forumArticle, String objId, String userId, OperRecordOpTypeEnum opTypeEnum) throws BusinessException {
        LikeRecord record = this.likeRecordMapper.selectByObjectIdAndUserIdAndOpType(objId, userId, opTypeEnum.getType());
        if (record != null) {
            this.likeRecordMapper.deleteByObjectIdAndUserIdAndOpType(objId, userId, opTypeEnum.getType());
            forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.GOOD_COUNT.getType(), Constants.NEGATIVE_ONE, objId);
        } else {
            LikeRecord likeRecord = new LikeRecord();
            likeRecord.setObjectId(objId);
            likeRecord.setUserId(userId);
            likeRecord.setOpType(opTypeEnum.getType());
            likeRecord.setCreateTime(new Date());
            likeRecord.setAuthorUserId(forumArticle.getUserId());
            this.likeRecordMapper.insert(likeRecord);

            forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.GOOD_COUNT.getType(), Constants.ONE, objId);
        }
    }
}