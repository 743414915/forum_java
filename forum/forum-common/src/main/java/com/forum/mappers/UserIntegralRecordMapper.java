package com.forum.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 用户积分记录表Mapper
 * @auther: chong
 * @date: 2023/03/27
 */
public interface UserIntegralRecordMapper<T, P> extends BaseMapper {
	/**
	 * 根据RecordId查询
 	 */
	T selectByRecordId(@Param("recordId") Integer recordId);

	/**
	 * 根据RecordId更新
 	 */
	Integer updateByRecordId(@Param("bean") T t, @Param("recordId") Integer recordId);

	/**
	 * 根据RecordId删除
 	 */
	Integer deleteByRecordId(@Param("recordId") Integer recordId);

}