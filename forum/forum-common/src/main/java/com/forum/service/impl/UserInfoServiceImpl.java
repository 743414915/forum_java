package com.forum.service.impl;

import com.forum.constants.Constants;
import com.forum.entity.config.WebConfig;
import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.po.UserInfo;
import com.forum.entity.po.UserIntegralRecord;
import com.forum.entity.po.UserMessage;
import com.forum.entity.query.SimplePage;
import com.forum.entity.query.UserInfoQuery;
import com.forum.entity.query.UserIntegralRecordQuery;
import com.forum.entity.query.UserMessageQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.*;
import com.forum.exception.BusinessException;
import com.forum.mappers.UserInfoMapper;
import com.forum.mappers.UserIntegralRecordMapper;
import com.forum.mappers.UserMessageMapper;
import com.forum.service.EmailCodeService;
import com.forum.service.UserInfoService;
import com.forum.utils.JsonUtils;
import com.forum.utils.OKHttpUtils;
import com.forum.utils.StringTools;
import com.forum.utils.SysCacheUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * @Description: 用户信息UserInfoServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    @Resource
    private EmailCodeService emailCodeService;

    @Resource
    private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;

    @Resource
    private UserIntegralRecordMapper<UserIntegralRecord, UserIntegralRecordQuery> userIntegralRecordMapper;

    @Resource
    private WebConfig webConfig;

    /**
     * 根据条件查询列表
     */
    public List<UserInfo> findListByParam(UserInfoQuery query) {
        return this.userInfoMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(UserInfoQuery query) {
        return this.userInfoMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery query) {
        Integer count = this.findCountByParam(query);
        int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<UserInfo> list = this.findListByParam(query);
        PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(UserInfo bean) {
        return this.userInfoMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据UserId查询
     */
    public UserInfo getUserInfoByUserId(String userId) {
        return this.userInfoMapper.selectByUserId(userId);
    }

    /**
     * 根据UserId更新
     */
    public Integer updateUserInfoByUserId(UserInfo bean, String userId) {
        return this.userInfoMapper.updateByUserId(bean, userId);
    }

    /**
     * 根据UserId删除
     */
    public Integer deleteUserInfoByUserId(String userId) {
        return this.userInfoMapper.deleteByUserId(userId);
    }

    /**
     * 根据Email查询
     */
    public UserInfo getUserInfoByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }

    /**
     * 根据Email更新
     */
    public Integer updateUserInfoByEmail(UserInfo bean, String email) {
        return this.userInfoMapper.updateByEmail(bean, email);
    }

    /**
     * 根据Email删除
     */
    public Integer deleteUserInfoByEmail(String email) {
        return this.userInfoMapper.deleteByEmail(email);
    }

    /**
     * 根据NickName查询
     */
    public UserInfo getUserInfoByNickName(String nickName) {
        return this.userInfoMapper.selectByNickName(nickName);
    }

    /**
     * 根据NickName更新
     */
    public Integer updateUserInfoByNickName(UserInfo bean, String nickName) {
        return this.userInfoMapper.updateByNickName(bean, nickName);
    }

    /**
     * 根据NickName删除
     */
    public Integer deleteUserInfoByNickName(String nickName) {
        return this.userInfoMapper.deleteByNickName(nickName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String email, String emailCode, String nickName, String password, String checkCode) throws BusinessException {
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        if (null != userInfo) {
            throw new BusinessException("邮箱账号已经存在");
        }
        userInfo = this.userInfoMapper.selectByNickName(nickName);
        if (null != userInfo) {
            throw new BusinessException("昵称已存在");
        }

        emailCodeService.checkCode(email, emailCode);

        String userId = StringTools.getRandomNumber(Constants.LENGTH_10);

        UserInfo insertInfo = new UserInfo();
        insertInfo.setUserId(userId);
        insertInfo.setEmail(email);
        insertInfo.setNickName(nickName);
        insertInfo.setPassword(StringTools.encodeMd5(password));
        insertInfo.setJoinTime(new Date());
        insertInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        insertInfo.setTotalIntegral(Constants.ZERO);
        insertInfo.setCurrentIntegral(Constants.ZERO);
        this.userInfoMapper.insert(insertInfo);

        // 更新用户积分
        updateUserIntegral(userId, UserIntegralOperTypeEnum.REGISTER, UserIntegralChangeTypeEnum.ADD.getChangeType(), Constants.INTEGRAL);
        // 记录消息
        UserMessage userMessage = new UserMessage();
        userMessage.setReceivedUserId(userId);
        userMessage.setMessageType(MessageTypeEnum.SYS.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        userMessage.setMessageContent(SysCacheUtils.getSysSetting().getRegisterSetting().getRegisterWelcomInfo());
        userMessageMapper.insert(userMessage);
    }

    /**
     * 更新用户积分
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType, Integer integral) throws BusinessException {
        integral = changeType * integral;
        if (integral == 0) {
            return;
        }

        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if (UserIntegralChangeTypeEnum.REDUCE.getChangeType().equals(changeType) && userInfo.getCurrentIntegral() + integral < 0) {
            integral = changeType * userInfo.getCurrentIntegral();
        }

        UserIntegralRecord userIntegralRecord = new UserIntegralRecord();
        userIntegralRecord.setUserId(userId);
        userIntegralRecord.setOperType(operTypeEnum.getOperType());
        userIntegralRecord.setCreateTime(new Date());
        userIntegralRecord.setIntegral(integral);

        this.userIntegralRecordMapper.insert(userIntegralRecord);

        Integer count = this.userInfoMapper.updateIntegral(userId, integral);
        if (count == 0) {
            throw new BusinessException("更新用户积分失败");
        }
    }

    @Override
    public SessionWebUserDto login(String email, String password, String ip) throws BusinessException {
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);

        if (null == userInfo || !userInfo.getPassword().equals(password)) {
            throw new BusinessException("账号或者密码错误");
        }
        if (UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
            throw new BusinessException("账号被禁用");
        }
        String ipAddress = getIpAddress(ip);
        UserInfo updateInfo = new UserInfo();
        updateInfo.setLastLoginTime(new Date());
        updateInfo.setLastLoginIp(ip);
        updateInfo.setLastLoginIpAddress(ipAddress);

        this.userInfoMapper.updateByUserId(updateInfo, userInfo.getUserId());
        SessionWebUserDto sessionWebUserDto = new SessionWebUserDto();
        sessionWebUserDto.setNickName(userInfo.getNickName());
        sessionWebUserDto.setProvince(ipAddress);
        sessionWebUserDto.setUserId(userInfo.getUserId());

        if (!StringTools.isEmpty(webConfig.getAdminEmail()) && ArrayUtils.contains(webConfig.getAdminEmail().split(","), userInfo.getEmail())) {
            sessionWebUserDto.setAdmin(true);
        } else {
            sessionWebUserDto.setAdmin(false);
        }
        return sessionWebUserDto;
    }

    public String getIpAddress(String ip) {
        try {
            String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
            String responseJson = OKHttpUtils.getRequest(url);
            if (null == responseJson) {
                return Constants.NO_ADDRESS;
            }
            Map<String, String> addressInfo = JsonUtils.convertJson2Obj(responseJson, Map.class);

            // 省份信息
            return addressInfo.get("pro");
        } catch (Exception e) {
            logger.error("获取IP地址失败", e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Constants.NO_ADDRESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPwd(String email, String password, String emailCode) throws BusinessException {
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        if (null == userInfo) {
            throw new BusinessException("邮箱不存在");
        }
        emailCodeService.checkCode(email, emailCode);

        UserInfo updateInfo = new UserInfo();
        updateInfo.setPassword(StringTools.encodeMd5(password));
        this.userInfoMapper.updateByEmail(updateInfo, email);
    }
}