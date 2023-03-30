package com.forum.service;


import com.forum.entity.po.EmailCode;
import com.forum.entity.query.EmailCodeQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.exception.BusinessException;

import java.util.List;

/**
 * @Description: 邮箱验证码EmailCodeService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface EmailCodeService {

    /**
     * 根据条件查询列表
     */
    List<EmailCode> findListByParam(EmailCodeQuery query);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(EmailCodeQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<EmailCode> findListByPage(EmailCodeQuery query);

    /**
     * 新增
     */
    Integer add(EmailCode bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<EmailCode> ListBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<EmailCode> ListBean);

    /**
     * 根据EmailAndCode查询
     */
    EmailCode getEmailCodeByEmailAndCode(String email, String code);

    /**
     * 根据EmailAndCode更新
     */
    Integer updateEmailCodeByEmailAndCode(EmailCode bean, String email, String code);

    /**
     * 根据EmailAndCode删除
     */
    Integer deleteEmailCodeByEmailAndCode(String email, String code);

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @param type  验证码类型 0是注册
     */
    void SendEmailCode(String email, Integer type) throws BusinessException;

    void checkCode(String email, String emailCode) throws BusinessException;
}