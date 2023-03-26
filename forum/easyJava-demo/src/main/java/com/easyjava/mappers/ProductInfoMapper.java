package com.easyjava.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商品信息Mapper
 * @auther: chong
 * @date: 2023/03/26
 */
public interface ProductInfoMapper<T, P> extends BaseMapper {
	/**
	 * 根据Id查询
 	 */
	T selectById(@Param("id") Integer id);

	/**
	 * 根据Id更新
 	 */
	Integer updateById(@Param("bean") T t, @Param("id") Integer id);

	/**
	 * 根据Id删除
 	 */
	Integer deleteById(@Param("id") Integer id);

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

	/**
	 * 根据SkuTypeAndColorType查询
 	 */
	T selectBySkuTypeAndColorType(@Param("skuType") Integer skuType, @Param("colorType") Integer colorType);

	/**
	 * 根据SkuTypeAndColorType更新
 	 */
	Integer updateBySkuTypeAndColorType(@Param("bean") T t, @Param("skuType") Integer skuType, @Param("colorType") Integer colorType);

	/**
	 * 根据SkuTypeAndColorType删除
 	 */
	Integer deleteBySkuTypeAndColorType(@Param("skuType") Integer skuType, @Param("colorType") Integer colorType);

}