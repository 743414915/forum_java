package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.Setting;
import com.forum.entity.query.SettingQuery;

/**
 * @Description: 系统设置信息SettingService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface SettingService {

	/**
	 * 根据条件查询列表
 	 */
	List<Setting> findListByParam(SettingQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(SettingQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<Setting> findListByPage(SettingQuery query);

	/**
	 * 新增
 	 */
	Integer add(Setting bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<Setting> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<Setting> ListBean);

	/**
	 * 根据Code查询
 	 */
	Setting getSettingByCode(String code);

	/**
	 * 根据Code更新
 	 */
	Integer updateSettingByCode( Setting bean, String code);

	/**
	 * 根据Code删除
 	 */
	Integer deleteSettingByCode(String code);

}