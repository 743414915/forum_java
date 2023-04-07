package com.forum.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 文章信息Mapper
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumArticleMapper<T, P> extends BaseMapper {
    /**
     * 根据ArticleId查询
     */
    T selectByArticleId(@Param("articleId") String articleId);

    /**
     * 根据ArticleId更新
     */
    Integer updateByArticleId(@Param("bean") T t, @Param("articleId") String articleId);

    /**
     * 根据ArticleId删除
     */
    Integer deleteByArticleId(@Param("articleId") String articleId);

    void updateArticleCount(@Param("updateType") Integer updateType, @Param("changeCount") Integer changeCount, @Param("articleId") String articleId);
}