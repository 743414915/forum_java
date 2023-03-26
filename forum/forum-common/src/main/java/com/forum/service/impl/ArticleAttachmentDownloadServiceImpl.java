package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.ArticleAttachmentDownloadMapper;
import com.forum.service.ArticleAttachmentDownloadService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.ArticleAttachmentDownload;
import com.forum.entity.query.ArticleAttachmentDownloadQuery;

import javax.annotation.Resource;

/**
 * @Description: 用户附件下载ArticleAttachmentDownloadServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("articleAttachmentDownloadService")
public class ArticleAttachmentDownloadServiceImpl implements ArticleAttachmentDownloadService {

	@Resource
	private ArticleAttachmentDownloadMapper<ArticleAttachmentDownload, ArticleAttachmentDownloadQuery> articleAttachmentDownloadMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<ArticleAttachmentDownload> findListByParam(ArticleAttachmentDownloadQuery query) {
		return this.articleAttachmentDownloadMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(ArticleAttachmentDownloadQuery query) {
		return this.articleAttachmentDownloadMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<ArticleAttachmentDownload> findListByPage(ArticleAttachmentDownloadQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ArticleAttachmentDownload> list = this.findListByParam(query);
		PaginationResultVO<ArticleAttachmentDownload> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(ArticleAttachmentDownload bean) {
		return this.articleAttachmentDownloadMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<ArticleAttachmentDownload> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.articleAttachmentDownloadMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<ArticleAttachmentDownload> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.articleAttachmentDownloadMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileIdAndUserId查询
 	 */
	public ArticleAttachmentDownload getArticleAttachmentDownloadByFileIdAndUserId(String fileId, String userId) {
		return this.articleAttachmentDownloadMapper.selectByFileIdAndUserId(fileId, userId);
	}

	/**
	 * 根据FileIdAndUserId更新
 	 */
	public Integer updateArticleAttachmentDownloadByFileIdAndUserId(ArticleAttachmentDownload bean, String fileId, String userId) {
		return this.articleAttachmentDownloadMapper.updateByFileIdAndUserId(bean, fileId, userId);
	}

	/**
	 * 根据FileIdAndUserId删除
 	 */
	public Integer deleteArticleAttachmentDownloadByFileIdAndUserId(String fileId, String userId) {
		return this.articleAttachmentDownloadMapper.deleteByFileIdAndUserId(fileId, userId);
	}

}