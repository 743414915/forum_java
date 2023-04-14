package com.forum.service;


import com.forum.entity.po.ForumArticle;
import com.forum.entity.po.ForumArticleAttachment;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: 文章信息ForumArticleService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface ForumArticleService {

    /**
     * 根据条件查询列表
     */
    List<ForumArticle> findListByParam(ForumArticleQuery query);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(ForumArticleQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<ForumArticle> findListByPage(ForumArticleQuery query);

    /**
     * 新增
     */
    Integer add(ForumArticle bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<ForumArticle> ListBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<ForumArticle> ListBean);

    /**
     * 根据ArticleId查询
     */
    ForumArticle getForumArticleByArticleId(String articleId);

    /**
     * 根据ArticleId更新
     */
    Integer updateForumArticleByArticleId(ForumArticle bean, String articleId);

    /**
     * 根据ArticleId删除
     */
    Integer deleteForumArticleByArticleId(String articleId);

    ForumArticle readArticle(String articleId) throws BusinessException;

    void postArticle(Boolean isAdmin, ForumArticle article, ForumArticleAttachment articleAttachment, MultipartFile cover, MultipartFile attachment) throws BusinessException;
}