package com.forum.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 文章板块信息Mapper
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumBoardMapper<T, P> extends BaseMapper {
	/**
	 * 根据BoardId查询
 	 */
	T selectByBoardId(@Param("boardId") Integer boardId);

	/**
	 * 根据BoardId更新
 	 */
	Integer updateByBoardId(@Param("bean") T t, @Param("boardId") Integer boardId);

	/**
	 * 根据BoardId删除
 	 */
	Integer deleteByBoardId(@Param("boardId") Integer boardId);

}