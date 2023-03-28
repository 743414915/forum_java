package com.forum.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 系统设置信息Mapper
 * @auther: chong
 * @date: 2023/03/27
 */
public interface SysSettingMapper<T, P> extends BaseMapper {
	/**
	 * 根据Code查询
 	 */
	T selectByCode(@Param("code") String code);

	/**
	 * 根据Code更新
 	 */
	Integer updateByCode(@Param("bean") T t, @Param("code") String code);

	/**
	 * 根据Code删除
 	 */
	Integer deleteByCode(@Param("code") String code);

}