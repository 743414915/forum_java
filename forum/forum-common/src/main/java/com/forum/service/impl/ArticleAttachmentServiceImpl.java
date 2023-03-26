package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.ArticleAttachmentMapper;
import com.forum.service.ArticleAttachmentService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.ArticleAttachment;
import com.forum.entity.query.ArticleAttachmentQuery;

import javax.annotation.Resource;

/**
 * @Description: 文件信息ArticleAttachmentServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("articleAttachmentService")
public class ArticleAttachmentServiceImpl implements ArticleAttachmentService {

	@Resource
	private ArticleAttachmentMapper<ArticleAttachment, ArticleAttachmentQuery> articleAttachmentMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<ArticleAttachment> findListByParam(ArticleAttachmentQuery query) {
		return this.articleAttachmentMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(ArticleAttachmentQuery query) {
		return this.articleAttachmentMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<ArticleAttachment> findListByPage(ArticleAttachmentQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ArticleAttachment> list = this.findListByParam(query);
		PaginationResultVO<ArticleAttachment> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(ArticleAttachment bean) {
		return this.articleAttachmentMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<ArticleAttachment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.articleAttachmentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<ArticleAttachment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.articleAttachmentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileId查询
 	 */
	public ArticleAttachment getArticleAttachmentByFileId(String fileId) {
		return this.articleAttachmentMapper.selectByFileId(fileId);
	}

	/**
	 * 根据FileId更新
 	 */
	public Integer updateArticleAttachmentByFileId(ArticleAttachment bean, String fileId) {
		return this.articleAttachmentMapper.updateByFileId(bean, fileId);
	}

	/**
	 * 根据FileId删除
 	 */
	public Integer deleteArticleAttachmentByFileId(String fileId) {
		return this.articleAttachmentMapper.deleteByFileId(fileId);
	}

}