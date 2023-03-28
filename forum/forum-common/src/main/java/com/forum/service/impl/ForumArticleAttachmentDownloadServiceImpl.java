package com.forum.service.impl;

import com.forum.entity.po.ForumArticleAttachmentDownload;
import com.forum.entity.query.ForumArticleAttachmentDownloadQuery;
import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;
import com.forum.mappers.ForumArticleAttachmentDownloadMapper;
import com.forum.service.ForumArticleAttachmentDownloadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

;

/**
 * @Description: 用户附件下载ForumArticleAttachmentDownloadServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("forumArticleAttachmentDownloadService")
public class ForumArticleAttachmentDownloadServiceImpl implements ForumArticleAttachmentDownloadService {

	@Resource
	private ForumArticleAttachmentDownloadMapper<ForumArticleAttachmentDownload, ForumArticleAttachmentDownloadQuery> forumArticleAttachmentDownloadMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<ForumArticleAttachmentDownload> findListByParam(ForumArticleAttachmentDownloadQuery query) {
		return this.forumArticleAttachmentDownloadMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(ForumArticleAttachmentDownloadQuery query) {
		return this.forumArticleAttachmentDownloadMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<ForumArticleAttachmentDownload> findListByPage(ForumArticleAttachmentDownloadQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ForumArticleAttachmentDownload> list = this.findListByParam(query);
		PaginationResultVO<ForumArticleAttachmentDownload> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(ForumArticleAttachmentDownload bean) {
		return this.forumArticleAttachmentDownloadMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<ForumArticleAttachmentDownload> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.forumArticleAttachmentDownloadMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<ForumArticleAttachmentDownload> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.forumArticleAttachmentDownloadMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileIdAndUserId查询
 	 */
	public ForumArticleAttachmentDownload getForumArticleAttachmentDownloadByFileIdAndUserId(String fileId, String userId) {
		return this.forumArticleAttachmentDownloadMapper.selectByFileIdAndUserId(fileId, userId);
	}

	/**
	 * 根据FileIdAndUserId更新
 	 */
	public Integer updateForumArticleAttachmentDownloadByFileIdAndUserId(ForumArticleAttachmentDownload bean, String fileId, String userId) {
		return this.forumArticleAttachmentDownloadMapper.updateByFileIdAndUserId(bean, fileId, userId);
	}

	/**
	 * 根据FileIdAndUserId删除
 	 */
	public Integer deleteForumArticleAttachmentDownloadByFileIdAndUserId(String fileId, String userId) {
		return this.forumArticleAttachmentDownloadMapper.deleteByFileIdAndUserId(fileId, userId);
	}

}