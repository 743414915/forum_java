package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.SettingMapper;
import com.forum.service.SettingService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.Setting;
import com.forum.entity.query.SettingQuery;

import javax.annotation.Resource;

/**
 * @Description: 系统设置信息SettingServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("settingService")
public class SettingServiceImpl implements SettingService {

	@Resource
	private SettingMapper<Setting, SettingQuery> settingMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<Setting> findListByParam(SettingQuery query) {
		return this.settingMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(SettingQuery query) {
		return this.settingMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<Setting> findListByPage(SettingQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<Setting> list = this.findListByParam(query);
		PaginationResultVO<Setting> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(Setting bean) {
		return this.settingMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<Setting> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.settingMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<Setting> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.settingMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Code查询
 	 */
	public Setting getSettingByCode(String code) {
		return this.settingMapper.selectByCode(code);
	}

	/**
	 * 根据Code更新
 	 */
	public Integer updateSettingByCode(Setting bean, String code) {
		return this.settingMapper.updateByCode(bean, code);
	}

	/**
	 * 根据Code删除
 	 */
	public Integer deleteSettingByCode(String code) {
		return this.settingMapper.deleteByCode(code);
	}

}