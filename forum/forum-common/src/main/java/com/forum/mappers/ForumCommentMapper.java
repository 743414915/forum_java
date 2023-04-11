package com.forum.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 评论Mapper
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumCommentMapper<T, P> extends BaseMapper {
    /**
     * 根据CommentId查询
     */
    T selectByCommentId(@Param("commentId") Integer commentId);

    /**
     * 根据CommentId更新
     */
    Integer updateByCommentId(@Param("bean") T t, @Param("commentId") Integer commentId);

    /**
     * 根据CommentId删除
     */
    Integer deleteByCommentId(@Param("commentId") Integer commentId);

    void updateCommentGoodCount(@Param("changeCount") Integer changeCount, @Param("commentId") Integer commentId0);

    void updateTopTypeByArticleId(@Param("articleId") String articleId);
}