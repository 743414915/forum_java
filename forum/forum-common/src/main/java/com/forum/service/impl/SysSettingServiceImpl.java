package com.forum.service.impl;

import com.forum.entity.po.SysSetting;
import com.forum.entity.query.SimplePage;
import com.forum.entity.query.SysSettingQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;
import com.forum.mappers.SysSettingMapper;
import com.forum.service.SysSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

;

/**
 * @Description: 系统设置信息SysSettingServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("sysSettingService")
public class SysSettingServiceImpl implements SysSettingService {

	@Resource
	private SysSettingMapper<SysSetting, SysSettingQuery> sysSettingMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<SysSetting> findListByParam(SysSettingQuery query) {
		return this.sysSettingMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(SysSettingQuery query) {
		return this.sysSettingMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<SysSetting> findListByPage(SysSettingQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<SysSetting> list = this.findListByParam(query);
		PaginationResultVO<SysSetting> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(SysSetting bean) {
		return this.sysSettingMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<SysSetting> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.sysSettingMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<SysSetting> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.sysSettingMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Code查询
 	 */
	public SysSetting getSysSettingByCode(String code) {
		return this.sysSettingMapper.selectByCode(code);
	}

	/**
	 * 根据Code更新
 	 */
	public Integer updateSysSettingByCode(SysSetting bean, String code) {
		return this.sysSettingMapper.updateByCode(bean, code);
	}

	/**
	 * 根据Code删除
 	 */
	public Integer deleteSysSettingByCode(String code) {
		return this.sysSettingMapper.deleteByCode(code);
	}

}