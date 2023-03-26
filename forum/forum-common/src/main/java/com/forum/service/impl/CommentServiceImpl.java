package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.CommentMapper;
import com.forum.service.CommentService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.Comment;
import com.forum.entity.query.CommentQuery;

import javax.annotation.Resource;

/**
 * @Description: 评论CommentServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper<Comment, CommentQuery> commentMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<Comment> findListByParam(CommentQuery query) {
		return this.commentMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(CommentQuery query) {
		return this.commentMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<Comment> findListByPage(CommentQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<Comment> list = this.findListByParam(query);
		PaginationResultVO<Comment> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(Comment bean) {
		return this.commentMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<Comment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.commentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<Comment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.commentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据CommentId查询
 	 */
	public Comment getCommentByCommentId(Integer commentId) {
		return this.commentMapper.selectByCommentId(commentId);
	}

	/**
	 * 根据CommentId更新
 	 */
	public Integer updateCommentByCommentId(Comment bean, Integer commentId) {
		return this.commentMapper.updateByCommentId(bean, commentId);
	}

	/**
	 * 根据CommentId删除
 	 */
	public Integer deleteCommentByCommentId(Integer commentId) {
		return this.commentMapper.deleteByCommentId(commentId);
	}

}