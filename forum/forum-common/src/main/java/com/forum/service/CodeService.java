package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.Code;
import com.forum.entity.query.CodeQuery;

/**
 * @Description: 邮箱验证码CodeService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface CodeService {

	/**
	 * 根据条件查询列表
 	 */
	List<Code> findListByParam(CodeQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(CodeQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<Code> findListByPage(CodeQuery query);

	/**
	 * 新增
 	 */
	Integer add(Code bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<Code> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<Code> ListBean);

	/**
	 * 根据EmailAndCode查询
 	 */
	Code getCodeByEmailAndCode(String email, String code);

	/**
	 * 根据EmailAndCode更新
 	 */
	Integer updateCodeByEmailAndCode( Code bean, String email, String code);

	/**
	 * 根据EmailAndCode删除
 	 */
	Integer deleteCodeByEmailAndCode(String email, String code);

}