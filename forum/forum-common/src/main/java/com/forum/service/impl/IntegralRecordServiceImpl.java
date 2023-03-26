package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.IntegralRecordMapper;
import com.forum.service.IntegralRecordService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.IntegralRecord;
import com.forum.entity.query.IntegralRecordQuery;

import javax.annotation.Resource;

/**
 * @Description: 用户积分记录表IntegralRecordServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("integralRecordService")
public class IntegralRecordServiceImpl implements IntegralRecordService {

	@Resource
	private IntegralRecordMapper<IntegralRecord, IntegralRecordQuery> integralRecordMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<IntegralRecord> findListByParam(IntegralRecordQuery query) {
		return this.integralRecordMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(IntegralRecordQuery query) {
		return this.integralRecordMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<IntegralRecord> findListByPage(IntegralRecordQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<IntegralRecord> list = this.findListByParam(query);
		PaginationResultVO<IntegralRecord> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(IntegralRecord bean) {
		return this.integralRecordMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<IntegralRecord> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.integralRecordMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<IntegralRecord> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.integralRecordMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据RecordId查询
 	 */
	public IntegralRecord getIntegralRecordByRecordId(Integer recordId) {
		return this.integralRecordMapper.selectByRecordId(recordId);
	}

	/**
	 * 根据RecordId更新
 	 */
	public Integer updateIntegralRecordByRecordId(IntegralRecord bean, Integer recordId) {
		return this.integralRecordMapper.updateByRecordId(bean, recordId);
	}

	/**
	 * 根据RecordId删除
 	 */
	public Integer deleteIntegralRecordByRecordId(Integer recordId) {
		return this.integralRecordMapper.deleteByRecordId(recordId);
	}

}