package com.forum.service;


import com.forum.entity.po.ForumArticleAttachment;
import com.forum.entity.query.ForumArticleAttachmentQuery;
import com.forum.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @Description: 文件信息ForumArticleAttachmentService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumArticleAttachmentService {

	/**
	 * 根据条件查询列表
 	 */
	List<ForumArticleAttachment> findListByParam(ForumArticleAttachmentQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(ForumArticleAttachmentQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<ForumArticleAttachment> findListByPage(ForumArticleAttachmentQuery query);

	/**
	 * 新增
 	 */
	Integer add(ForumArticleAttachment bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<ForumArticleAttachment> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<ForumArticleAttachment> ListBean);

	/**
	 * 根据FileId查询
 	 */
	ForumArticleAttachment getForumArticleAttachmentByFileId(String fileId);

	/**
	 * 根据FileId更新
 	 */
	Integer updateForumArticleAttachmentByFileId( ForumArticleAttachment bean, String fileId);

	/**
	 * 根据FileId删除
 	 */
	Integer deleteForumArticleAttachmentByFileId(String fileId);

}