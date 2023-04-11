package com.forum.service.impl;

import com.forum.entity.po.ForumArticle;
import com.forum.entity.po.ForumComment;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.query.ForumCommentQuery;
import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.CommentTopTypeEnum;
import com.forum.enums.PageSize;
import com.forum.enums.ResponseCodeEnum;
import com.forum.exception.BusinessException;
import com.forum.mappers.ForumArticleMapper;
import com.forum.mappers.ForumCommentMapper;
import com.forum.service.ForumCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
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

            Map<Integer, List<ForumComment>> tempMap = subCommentList.stream().collect(Collectors.groupingBy(ForumComment::getCommentId));
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

        if (!forumArticle.getUserId().equals(userId) || forumComment.getCommentId() != 0) {
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
}