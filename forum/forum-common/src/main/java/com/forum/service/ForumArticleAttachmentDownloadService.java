package com.forum.service;


import com.forum.entity.po.ForumArticleAttachmentDownload;
import com.forum.entity.query.ForumArticleAttachmentDownloadQuery;
import com.forum.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @Description: 用户附件下载ForumArticleAttachmentDownloadService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumArticleAttachmentDownloadService {

	/**
	 * 根据条件查询列表
 	 */
	List<ForumArticleAttachmentDownload> findListByParam(ForumArticleAttachmentDownloadQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(ForumArticleAttachmentDownloadQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<ForumArticleAttachmentDownload> findListByPage(ForumArticleAttachmentDownloadQuery query);

	/**
	 * 新增
 	 */
	Integer add(ForumArticleAttachmentDownload bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<ForumArticleAttachmentDownload> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<ForumArticleAttachmentDownload> ListBean);

	/**
	 * 根据FileIdAndUserId查询
 	 */
	ForumArticleAttachmentDownload getForumArticleAttachmentDownloadByFileIdAndUserId(String fileId, String userId);

	/**
	 * 根据FileIdAndUserId更新
 	 */
	Integer updateForumArticleAttachmentDownloadByFileIdAndUserId( ForumArticleAttachmentDownload bean, String fileId, String userId);

	/**
	 * 根据FileIdAndUserId删除
 	 */
	Integer deleteForumArticleAttachmentDownloadByFileIdAndUserId(String fileId, String userId);

}