package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.Article;
import com.forum.entity.query.ArticleQuery;

/**
 * @Description: 文章信息ArticleService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface ArticleService {

	/**
	 * 根据条件查询列表
 	 */
	List<Article> findListByParam(ArticleQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(ArticleQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<Article> findListByPage(ArticleQuery query);

	/**
	 * 新增
 	 */
	Integer add(Article bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<Article> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<Article> ListBean);

	/**
	 * 根据ArticleId查询
 	 */
	Article getArticleByArticleId(String articleId);

	/**
	 * 根据ArticleId更新
 	 */
	Integer updateArticleByArticleId( Article bean, String articleId);

	/**
	 * 根据ArticleId删除
 	 */
	Integer deleteArticleByArticleId(String articleId);

}