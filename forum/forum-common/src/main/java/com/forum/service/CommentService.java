package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.Comment;
import com.forum.entity.query.CommentQuery;

/**
 * @Description: 评论CommentService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface CommentService {

	/**
	 * 根据条件查询列表
 	 */
	List<Comment> findListByParam(CommentQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(CommentQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<Comment> findListByPage(CommentQuery query);

	/**
	 * 新增
 	 */
	Integer add(Comment bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<Comment> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<Comment> ListBean);

	/**
	 * 根据CommentId查询
 	 */
	Comment getCommentByCommentId(Integer commentId);

	/**
	 * 根据CommentId更新
 	 */
	Integer updateCommentByCommentId( Comment bean, Integer commentId);

	/**
	 * 根据CommentId删除
 	 */
	Integer deleteCommentByCommentId(Integer commentId);

}