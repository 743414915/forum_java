package com.forum.controller;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.controller.base.ABaseController;
import com.forum.entity.dto.*;
import com.forum.entity.query.UserInfoQuery;
import com.forum.entity.vo.ResponseVO;
import com.forum.exception.BusinessException;
import com.forum.service.UserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("userInfoController")
@RequestMapping("/user")
public class UserInfoController extends ABaseController {

    @Resource
    private UserInfoService userInfoService;

    @RequestMapping("/loadUserList")
    private ResponseVO loadUserList(UserInfoQuery userInfoQuery) {
        userInfoQuery.setOrderBy("join_time desc");
        return getSuccessResponseVO(userInfoService.findListByPage(userInfoQuery));
    }

    @RequestMapping("/updateUserStatus")
    @GlobalInterceptor(checkParams = true)
    private ResponseVO updateUserStatus(@VerifyParam(required = true) Integer status,
                                        @VerifyParam(required = true) String userId) {
        userInfoService.updateUserStatus(status, userId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/sendMessage")
    @GlobalInterceptor(checkParams = true)
    private ResponseVO sendMessage(@VerifyParam(required = true) String userId,
                                   @VerifyParam(required = true) String message,
                                   @VerifyParam(required = true) Integer integral) throws BusinessException {
        userInfoService.sendMessage(userId, message, integral);
        return getSuccessResponseVO(null);
    }
}
