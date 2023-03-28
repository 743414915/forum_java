package com.forum.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 文件信息Mapper
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumArticleAttachmentMapper<T, P> extends BaseMapper {
	/**
	 * 根据FileId查询
 	 */
	T selectByFileId(@Param("fileId") String fileId);

	/**
	 * 根据FileId更新
 	 */
	Integer updateByFileId(@Param("bean") T t, @Param("fileId") String fileId);

	/**
	 * 根据FileId删除
 	 */
	Integer deleteByFileId(@Param("fileId") String fileId);

}