package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.Record;
import com.forum.entity.query.RecordQuery;

/**
 * @Description: 点赞记录RecordService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface RecordService {

	/**
	 * 根据条件查询列表
 	 */
	List<Record> findListByParam(RecordQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(RecordQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<Record> findListByPage(RecordQuery query);

	/**
	 * 新增
 	 */
	Integer add(Record bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<Record> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<Record> ListBean);

	/**
	 * 根据OpId查询
 	 */
	Record getRecordByOpId(Integer opId);

	/**
	 * 根据OpId更新
 	 */
	Integer updateRecordByOpId( Record bean, Integer opId);

	/**
	 * 根据OpId删除
 	 */
	Integer deleteRecordByOpId(Integer opId);

	/**
	 * 根据ObjectIdAndUserIdAndOpType查询
 	 */
	Record getRecordByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType);

	/**
	 * 根据ObjectIdAndUserIdAndOpType更新
 	 */
	Integer updateRecordByObjectIdAndUserIdAndOpType( Record bean, String objectId, String userId, Integer opType);

	/**
	 * 根据ObjectIdAndUserIdAndOpType删除
 	 */
	Integer deleteRecordByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType);

}