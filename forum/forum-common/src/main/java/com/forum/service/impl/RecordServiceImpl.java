package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.RecordMapper;
import com.forum.service.RecordService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.Record;
import com.forum.entity.query.RecordQuery;

import javax.annotation.Resource;

/**
 * @Description: 点赞记录RecordServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("recordService")
public class RecordServiceImpl implements RecordService {

	@Resource
	private RecordMapper<Record, RecordQuery> recordMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<Record> findListByParam(RecordQuery query) {
		return this.recordMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(RecordQuery query) {
		return this.recordMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<Record> findListByPage(RecordQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<Record> list = this.findListByParam(query);
		PaginationResultVO<Record> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(Record bean) {
		return this.recordMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<Record> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.recordMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<Record> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.recordMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据OpId查询
 	 */
	public Record getRecordByOpId(Integer opId) {
		return this.recordMapper.selectByOpId(opId);
	}

	/**
	 * 根据OpId更新
 	 */
	public Integer updateRecordByOpId(Record bean, Integer opId) {
		return this.recordMapper.updateByOpId(bean, opId);
	}

	/**
	 * 根据OpId删除
 	 */
	public Integer deleteRecordByOpId(Integer opId) {
		return this.recordMapper.deleteByOpId(opId);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType查询
 	 */
	public Record getRecordByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType) {
		return this.recordMapper.selectByObjectIdAndUserIdAndOpType(objectId, userId, opType);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType更新
 	 */
	public Integer updateRecordByObjectIdAndUserIdAndOpType(Record bean, String objectId, String userId, Integer opType) {
		return this.recordMapper.updateByObjectIdAndUserIdAndOpType(bean, objectId, userId, opType);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType删除
 	 */
	public Integer deleteRecordByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType) {
		return this.recordMapper.deleteByObjectIdAndUserIdAndOpType(objectId, userId, opType);
	}

}