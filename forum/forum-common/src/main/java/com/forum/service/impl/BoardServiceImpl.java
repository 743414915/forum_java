package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.BoardMapper;
import com.forum.service.BoardService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.Board;
import com.forum.entity.query.BoardQuery;

import javax.annotation.Resource;

/**
 * @Description: 文章板块信息BoardServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Resource
	private BoardMapper<Board, BoardQuery> boardMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<Board> findListByParam(BoardQuery query) {
		return this.boardMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(BoardQuery query) {
		return this.boardMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<Board> findListByPage(BoardQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<Board> list = this.findListByParam(query);
		PaginationResultVO<Board> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(Board bean) {
		return this.boardMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<Board> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.boardMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<Board> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.boardMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据BoardId查询
 	 */
	public Board getBoardByBoardId(Integer boardId) {
		return this.boardMapper.selectByBoardId(boardId);
	}

	/**
	 * 根据BoardId更新
 	 */
	public Integer updateBoardByBoardId(Board bean, Integer boardId) {
		return this.boardMapper.updateByBoardId(bean, boardId);
	}

	/**
	 * 根据BoardId删除
 	 */
	public Integer deleteBoardByBoardId(Integer boardId) {
		return this.boardMapper.deleteByBoardId(boardId);
	}

}