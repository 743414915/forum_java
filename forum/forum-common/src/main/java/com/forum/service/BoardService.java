package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.Board;
import com.forum.entity.query.BoardQuery;

/**
 * @Description: 文章板块信息BoardService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface BoardService {

	/**
	 * 根据条件查询列表
 	 */
	List<Board> findListByParam(BoardQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(BoardQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<Board> findListByPage(BoardQuery query);

	/**
	 * 新增
 	 */
	Integer add(Board bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<Board> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<Board> ListBean);

	/**
	 * 根据BoardId查询
 	 */
	Board getBoardByBoardId(Integer boardId);

	/**
	 * 根据BoardId更新
 	 */
	Integer updateBoardByBoardId( Board bean, Integer boardId);

	/**
	 * 根据BoardId删除
 	 */
	Integer deleteBoardByBoardId(Integer boardId);

}