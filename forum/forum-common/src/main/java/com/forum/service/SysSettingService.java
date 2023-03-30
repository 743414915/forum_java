package com.forum.service;


import com.forum.entity.po.SysSetting;
import com.forum.entity.query.SysSettingQuery;
import com.forum.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @Description: 系统设置信息SysSettingService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface SysSettingService {

	/**
	 * 根据条件查询列表
 	 */
	List<SysSetting> findListByParam(SysSettingQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(SysSettingQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<SysSetting> findListByPage(SysSettingQuery query);

	/**
	 * 新增
 	 */
	Integer add(SysSetting bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<SysSetting> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<SysSetting> ListBean);

	/**
	 * 根据Code查询
 	 */
	SysSetting getSysSettingByCode(String code);

	/**
	 * 根据Code更新
 	 */
	Integer updateSysSettingByCode( SysSetting bean, String code);

	/**
	 * 根据Code删除
 	 */
	Integer deleteSysSettingByCode(String code);

	/**
	 * 刷新缓存
	 */
	void refreshCache();

}