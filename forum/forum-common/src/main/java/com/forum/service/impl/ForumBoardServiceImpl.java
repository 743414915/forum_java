package com.forum.service.impl;

import com.forum.entity.po.ForumBoard;
import com.forum.entity.query.ForumBoardQuery;
import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;
import com.forum.mappers.ForumBoardMapper;
import com.forum.service.ForumBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

;

/**
 * @Description: 文章板块信息ForumBoardServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("forumBoardService")
public class ForumBoardServiceImpl implements ForumBoardService {

    @Resource
    private ForumBoardMapper<ForumBoard, ForumBoardQuery> forumBoardMapper;

    /**
     * 根据条件查询列表
     */
    public List<ForumBoard> findListByParam(ForumBoardQuery query) {
        return this.forumBoardMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(ForumBoardQuery query) {
        return this.forumBoardMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<ForumBoard> findListByPage(ForumBoardQuery query) {
        Integer count = this.findCountByParam(query);
        int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<ForumBoard> list = this.findListByParam(query);
        PaginationResultVO<ForumBoard> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(ForumBoard bean) {
        return this.forumBoardMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<ForumBoard> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.forumBoardMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<ForumBoard> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.forumBoardMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据BoardId查询
     */
    public ForumBoard getForumBoardByBoardId(Integer boardId) {
        return this.forumBoardMapper.selectByBoardId(boardId);
    }

    /**
     * 根据BoardId更新
     */
    public Integer updateForumBoardByBoardId(ForumBoard bean, Integer boardId) {
        return this.forumBoardMapper.updateByBoardId(bean, boardId);
    }

    /**
     * 根据BoardId删除
     */
    public Integer deleteForumBoardByBoardId(Integer boardId) {
        return this.forumBoardMapper.deleteByBoardId(boardId);
    }

    @Override
    public List<ForumBoard> getBoardTree(Integer postType) {
        ForumBoardQuery boardQuery = new ForumBoardQuery();
        boardQuery.setOrderBy("sort asc");
        boardQuery.setPostType(postType);
        List<ForumBoard> forumBoardList = this.forumBoardMapper.selectList(boardQuery);

        return convertLine2Tree(forumBoardList, 0);
    }

    private List<ForumBoard> convertLine2Tree(List<ForumBoard> dataList, Integer pid) {
        List<ForumBoard> children = new ArrayList<>();
        for (ForumBoard m : dataList) {
            if (m.getPBoardId().equals(pid)) {
                m.setChildren(convertLine2Tree(dataList, m.getBoardId()));
                children.add(m);
            }
        }
        return children;
    }
}