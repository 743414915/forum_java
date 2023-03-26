package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.CodeMapper;
import com.forum.service.CodeService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.Code;
import com.forum.entity.query.CodeQuery;

import javax.annotation.Resource;

/**
 * @Description: 邮箱验证码CodeServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("codeService")
public class CodeServiceImpl implements CodeService {

	@Resource
	private CodeMapper<Code, CodeQuery> codeMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<Code> findListByParam(CodeQuery query) {
		return this.codeMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(CodeQuery query) {
		return this.codeMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<Code> findListByPage(CodeQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<Code> list = this.findListByParam(query);
		PaginationResultVO<Code> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(Code bean) {
		return this.codeMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<Code> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.codeMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<Code> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.codeMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据EmailAndCode查询
 	 */
	public Code getCodeByEmailAndCode(String email, String code) {
		return this.codeMapper.selectByEmailAndCode(email, code);
	}

	/**
	 * 根据EmailAndCode更新
 	 */
	public Integer updateCodeByEmailAndCode(Code bean, String email, String code) {
		return this.codeMapper.updateByEmailAndCode(bean, email, code);
	}

	/**
	 * 根据EmailAndCode删除
 	 */
	public Integer deleteCodeByEmailAndCode(String email, String code) {
		return this.codeMapper.deleteByEmailAndCode(email, code);
	}

}