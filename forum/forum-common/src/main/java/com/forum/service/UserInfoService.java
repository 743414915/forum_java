package com.forum.service;


import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.po.UserInfo;
import com.forum.entity.query.UserInfoQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.UserIntegralOperTypeEnum;
import com.forum.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: 用户信息UserInfoService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface UserInfoService {

    /**
     * 根据条件查询列表
     */
    List<UserInfo> findListByParam(UserInfoQuery query);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(UserInfoQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<UserInfo> findListByPage(UserInfoQuery query);

    /**
     * 新增
     */
    Integer add(UserInfo bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserInfo> ListBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<UserInfo> ListBean);

    /**
     * 根据UserId查询
     */
    UserInfo getUserInfoByUserId(String userId);

    /**
     * 根据UserId更新
     */
    Integer updateUserInfoByUserId(UserInfo bean, String userId);

    /**
     * 根据UserId删除
     */
    Integer deleteUserInfoByUserId(String userId);

    /**
     * 根据Email查询
     */
    UserInfo getUserInfoByEmail(String email);

    /**
     * 根据Email更新
     */
    Integer updateUserInfoByEmail(UserInfo bean, String email);

    /**
     * 根据Email删除
     */
    Integer deleteUserInfoByEmail(String email);

    /**
     * 根据NickName查询
     */
    UserInfo getUserInfoByNickName(String nickName);

    /**
     * 根据NickName更新
     */
    Integer updateUserInfoByNickName(UserInfo bean, String nickName);

    /**
     * 根据NickName删除
     */
    Integer deleteUserInfoByNickName(String nickName);

    void register(String email, String emailCode, String nickName, String password, String checkCode) throws BusinessException;

    void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType, Integer integral) throws BusinessException;

    SessionWebUserDto login(String email, String password, String ip) throws BusinessException;

    void resetPwd(String email, String password, String emailCode) throws BusinessException;

    void updateUserInfo(UserInfo userInfo, MultipartFile avatar) throws BusinessException;

    void updateUserStatus(Integer status, String userId);

    void sendMessage(String userId, String message, Integer integral) throws BusinessException;
}