package com.forum.service.impl;

import com.forum.entity.po.UserIntegralRecord;
import com.forum.entity.query.SimplePage;
import com.forum.entity.query.UserIntegralRecordQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;
import com.forum.mappers.UserIntegralRecordMapper;
import com.forum.service.UserIntegralRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

;

/**
 * @Description: 用户积分记录表UserIntegralRecordServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("userIntegralRecordService")
public class UserIntegralRecordServiceImpl implements UserIntegralRecordService {

	@Resource
	private UserIntegralRecordMapper<UserIntegralRecord, UserIntegralRecordQuery> userIntegralRecordMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<UserIntegralRecord> findListByParam(UserIntegralRecordQuery query) {
		return this.userIntegralRecordMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(UserIntegralRecordQuery query) {
		return this.userIntegralRecordMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<UserIntegralRecord> findListByPage(UserIntegralRecordQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<UserIntegralRecord> list = this.findListByParam(query);
		PaginationResultVO<UserIntegralRecord> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(UserIntegralRecord bean) {
		return this.userIntegralRecordMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<UserIntegralRecord> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userIntegralRecordMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<UserIntegralRecord> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userIntegralRecordMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据RecordId查询
 	 */
	public UserIntegralRecord getUserIntegralRecordByRecordId(Integer recordId) {
		return this.userIntegralRecordMapper.selectByRecordId(recordId);
	}

	/**
	 * 根据RecordId更新
 	 */
	public Integer updateUserIntegralRecordByRecordId(UserIntegralRecord bean, Integer recordId) {
		return this.userIntegralRecordMapper.updateByRecordId(bean, recordId);
	}

	/**
	 * 根据RecordId删除
 	 */
	public Integer deleteUserIntegralRecordByRecordId(Integer recordId) {
		return this.userIntegralRecordMapper.deleteByRecordId(recordId);
	}

}