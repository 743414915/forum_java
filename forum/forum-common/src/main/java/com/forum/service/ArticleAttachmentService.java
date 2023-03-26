package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.ArticleAttachment;
import com.forum.entity.query.ArticleAttachmentQuery;

/**
 * @Description: 文件信息ArticleAttachmentService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface ArticleAttachmentService {

	/**
	 * 根据条件查询列表
 	 */
	List<ArticleAttachment> findListByParam(ArticleAttachmentQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(ArticleAttachmentQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<ArticleAttachment> findListByPage(ArticleAttachmentQuery query);

	/**
	 * 新增
 	 */
	Integer add(ArticleAttachment bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<ArticleAttachment> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<ArticleAttachment> ListBean);

	/**
	 * 根据FileId查询
 	 */
	ArticleAttachment getArticleAttachmentByFileId(String fileId);

	/**
	 * 根据FileId更新
 	 */
	Integer updateArticleAttachmentByFileId( ArticleAttachment bean, String fileId);

	/**
	 * 根据FileId删除
 	 */
	Integer deleteArticleAttachmentByFileId(String fileId);

}