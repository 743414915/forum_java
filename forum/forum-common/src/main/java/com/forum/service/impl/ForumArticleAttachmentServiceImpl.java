package com.forum.service.impl;

import com.forum.entity.po.ForumArticleAttachment;
import com.forum.entity.query.ForumArticleAttachmentQuery;
import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;
import com.forum.mappers.ForumArticleAttachmentMapper;
import com.forum.service.ForumArticleAttachmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

;

/**
 * @Description: 文件信息ForumArticleAttachmentServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("forumArticleAttachmentService")
public class ForumArticleAttachmentServiceImpl implements ForumArticleAttachmentService {

	@Resource
	private ForumArticleAttachmentMapper<ForumArticleAttachment, ForumArticleAttachmentQuery> forumArticleAttachmentMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<ForumArticleAttachment> findListByParam(ForumArticleAttachmentQuery query) {
		return this.forumArticleAttachmentMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(ForumArticleAttachmentQuery query) {
		return this.forumArticleAttachmentMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<ForumArticleAttachment> findListByPage(ForumArticleAttachmentQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ForumArticleAttachment> list = this.findListByParam(query);
		PaginationResultVO<ForumArticleAttachment> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(ForumArticleAttachment bean) {
		return this.forumArticleAttachmentMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<ForumArticleAttachment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.forumArticleAttachmentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<ForumArticleAttachment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.forumArticleAttachmentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileId查询
 	 */
	public ForumArticleAttachment getForumArticleAttachmentByFileId(String fileId) {
		return this.forumArticleAttachmentMapper.selectByFileId(fileId);
	}

	/**
	 * 根据FileId更新
 	 */
	public Integer updateForumArticleAttachmentByFileId(ForumArticleAttachment bean, String fileId) {
		return this.forumArticleAttachmentMapper.updateByFileId(bean, fileId);
	}

	/**
	 * 根据FileId删除
 	 */
	public Integer deleteForumArticleAttachmentByFileId(String fileId) {
		return this.forumArticleAttachmentMapper.deleteByFileId(fileId);
	}

}