package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.IntegralRecord;
import com.forum.entity.query.IntegralRecordQuery;

/**
 * @Description: 用户积分记录表IntegralRecordService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface IntegralRecordService {

	/**
	 * 根据条件查询列表
 	 */
	List<IntegralRecord> findListByParam(IntegralRecordQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(IntegralRecordQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<IntegralRecord> findListByPage(IntegralRecordQuery query);

	/**
	 * 新增
 	 */
	Integer add(IntegralRecord bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<IntegralRecord> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<IntegralRecord> ListBean);

	/**
	 * 根据RecordId查询
 	 */
	IntegralRecord getIntegralRecordByRecordId(Integer recordId);

	/**
	 * 根据RecordId更新
 	 */
	Integer updateIntegralRecordByRecordId( IntegralRecord bean, Integer recordId);

	/**
	 * 根据RecordId删除
 	 */
	Integer deleteIntegralRecordByRecordId(Integer recordId);

}