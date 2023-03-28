package com.forum.service;


import com.forum.entity.po.UserIntegralRecord;
import com.forum.entity.query.UserIntegralRecordQuery;
import com.forum.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @Description: 用户积分记录表UserIntegralRecordService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface UserIntegralRecordService {

	/**
	 * 根据条件查询列表
 	 */
	List<UserIntegralRecord> findListByParam(UserIntegralRecordQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(UserIntegralRecordQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<UserIntegralRecord> findListByPage(UserIntegralRecordQuery query);

	/**
	 * 新增
 	 */
	Integer add(UserIntegralRecord bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<UserIntegralRecord> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<UserIntegralRecord> ListBean);

	/**
	 * 根据RecordId查询
 	 */
	UserIntegralRecord getUserIntegralRecordByRecordId(Integer recordId);

	/**
	 * 根据RecordId更新
 	 */
	Integer updateUserIntegralRecordByRecordId( UserIntegralRecord bean, Integer recordId);

	/**
	 * 根据RecordId删除
 	 */
	Integer deleteUserIntegralRecordByRecordId(Integer recordId);

}