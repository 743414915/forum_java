package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.InfoMapper;
import com.forum.service.InfoService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.Info;
import com.forum.entity.query.InfoQuery;

import javax.annotation.Resource;

/**
 * @Description: 用户信息InfoServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("infoService")
public class InfoServiceImpl implements InfoService {

	@Resource
	private InfoMapper<Info, InfoQuery> infoMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<Info> findListByParam(InfoQuery query) {
		return this.infoMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(InfoQuery query) {
		return this.infoMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<Info> findListByPage(InfoQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<Info> list = this.findListByParam(query);
		PaginationResultVO<Info> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(Info bean) {
		return this.infoMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<Info> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.infoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<Info> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.infoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据UserId查询
 	 */
	public Info getInfoByUserId(String userId) {
		return this.infoMapper.selectByUserId(userId);
	}

	/**
	 * 根据UserId更新
 	 */
	public Integer updateInfoByUserId(Info bean, String userId) {
		return this.infoMapper.updateByUserId(bean, userId);
	}

	/**
	 * 根据UserId删除
 	 */
	public Integer deleteInfoByUserId(String userId) {
		return this.infoMapper.deleteByUserId(userId);
	}

	/**
	 * 根据Email查询
 	 */
	public Info getInfoByEmail(String email) {
		return this.infoMapper.selectByEmail(email);
	}

	/**
	 * 根据Email更新
 	 */
	public Integer updateInfoByEmail(Info bean, String email) {
		return this.infoMapper.updateByEmail(bean, email);
	}

	/**
	 * 根据Email删除
 	 */
	public Integer deleteInfoByEmail(String email) {
		return this.infoMapper.deleteByEmail(email);
	}

	/**
	 * 根据NickName查询
 	 */
	public Info getInfoByNickName(String nickName) {
		return this.infoMapper.selectByNickName(nickName);
	}

	/**
	 * 根据NickName更新
 	 */
	public Integer updateInfoByNickName(Info bean, String nickName) {
		return this.infoMapper.updateByNickName(bean, nickName);
	}

	/**
	 * 根据NickName删除
 	 */
	public Integer deleteInfoByNickName(String nickName) {
		return this.infoMapper.deleteByNickName(nickName);
	}

}