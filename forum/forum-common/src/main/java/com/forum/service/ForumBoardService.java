package com.forum.service;


import com.forum.entity.po.ForumBoard;
import com.forum.entity.query.ForumBoardQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.exception.BusinessException;

import java.util.List;

/**
 * @Description: 文章板块信息ForumBoardService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumBoardService {

    /**
     * 根据条件查询列表
     */
    List<ForumBoard> findListByParam(ForumBoardQuery query);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(ForumBoardQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<ForumBoard> findListByPage(ForumBoardQuery query);

    /**
     * 新增
     */
    Integer add(ForumBoard bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<ForumBoard> ListBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<ForumBoard> ListBean);

    /**
     * 根据BoardId查询
     */
    ForumBoard getForumBoardByBoardId(Integer boardId);

    /**
     * 根据BoardId更新
     */
    Integer updateForumBoardByBoardId(ForumBoard bean, Integer boardId);

    /**
     * 根据BoardId删除
     */
    Integer deleteForumBoardByBoardId(Integer boardId);

    List<ForumBoard> getBoardTree(Integer postType);

    void saveForumBoard(ForumBoard forumBoard) throws BusinessException;

    void changeBoardSort(String boardIds);
}