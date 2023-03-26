package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.ArticleMapper;
import com.forum.service.ArticleService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.Article;
import com.forum.entity.query.ArticleQuery;

import javax.annotation.Resource;

/**
 * @Description: 文章信息ArticleServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Resource
	private ArticleMapper<Article, ArticleQuery> articleMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<Article> findListByParam(ArticleQuery query) {
		return this.articleMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(ArticleQuery query) {
		return this.articleMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<Article> findListByPage(ArticleQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<Article> list = this.findListByParam(query);
		PaginationResultVO<Article> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(Article bean) {
		return this.articleMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<Article> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.articleMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<Article> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.articleMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据ArticleId查询
 	 */
	public Article getArticleByArticleId(String articleId) {
		return this.articleMapper.selectByArticleId(articleId);
	}

	/**
	 * 根据ArticleId更新
 	 */
	public Integer updateArticleByArticleId(Article bean, String articleId) {
		return this.articleMapper.updateByArticleId(bean, articleId);
	}

	/**
	 * 根据ArticleId删除
 	 */
	public Integer deleteArticleByArticleId(String articleId) {
		return this.articleMapper.deleteByArticleId(articleId);
	}

}