package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.ArticleAttachmentDownload;
import com.forum.entity.query.ArticleAttachmentDownloadQuery;

/**
 * @Description: 用户附件下载ArticleAttachmentDownloadService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface ArticleAttachmentDownloadService {

	/**
	 * 根据条件查询列表
 	 */
	List<ArticleAttachmentDownload> findListByParam(ArticleAttachmentDownloadQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(ArticleAttachmentDownloadQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<ArticleAttachmentDownload> findListByPage(ArticleAttachmentDownloadQuery query);

	/**
	 * 新增
 	 */
	Integer add(ArticleAttachmentDownload bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<ArticleAttachmentDownload> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<ArticleAttachmentDownload> ListBean);

	/**
	 * 根据FileIdAndUserId查询
 	 */
	ArticleAttachmentDownload getArticleAttachmentDownloadByFileIdAndUserId(String fileId, String userId);

	/**
	 * 根据FileIdAndUserId更新
 	 */
	Integer updateArticleAttachmentDownloadByFileIdAndUserId( ArticleAttachmentDownload bean, String fileId, String userId);

	/**
	 * 根据FileIdAndUserId删除
 	 */
	Integer deleteArticleAttachmentDownloadByFileIdAndUserId(String fileId, String userId);

}