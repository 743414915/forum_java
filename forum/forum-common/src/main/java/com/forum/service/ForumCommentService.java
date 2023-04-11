package com.forum.service;


import com.forum.entity.po.ForumComment;
import com.forum.entity.query.ForumCommentQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.exception.BusinessException;

import java.util.List;

/**
 * @Description: 评论ForumCommentService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumCommentService {

    /**
     * 根据条件查询列表
     */
    List<ForumComment> findListByParam(ForumCommentQuery query);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(ForumCommentQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<ForumComment> findListByPage(ForumCommentQuery query);

    /**
     * 新增
     */
    Integer add(ForumComment bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<ForumComment> ListBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<ForumComment> ListBean);

    /**
     * 根据CommentId查询
     */
    ForumComment getForumCommentByCommentId(Integer commentId);

    /**
     * 根据CommentId更新
     */
    Integer updateForumCommentByCommentId(ForumComment bean, Integer commentId);

    /**
     * 根据CommentId删除
     */
    Integer deleteForumCommentByCommentId(Integer commentId);

    void changeTopType(String userId, Integer commentId, Integer topType) throws BusinessException;

}