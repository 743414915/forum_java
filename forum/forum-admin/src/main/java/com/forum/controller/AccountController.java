package com.forum.controller;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.constants.Constants;
import com.forum.controller.base.ABaseController;
import com.forum.entity.config.AdminConfig;
import com.forum.entity.dto.*;
import com.forum.entity.vo.ResponseVO;
import com.forum.enums.VerifyRegexEnum;
import com.forum.exception.BusinessException;
import com.forum.service.EmailCodeService;
import com.forum.service.UserInfoService;
import com.forum.utils.StringTools;
import com.forum.utils.SysCacheUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController extends ABaseController {

    @Resource
    private AdminConfig adminConfig;

    /**
     * 获取验证码
     *
     * @param response response
     * @param session  session
     * @param type     验证码类型
     */
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session, Integer type) throws IOException {
        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10);

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = vCode.getCode();

        // 登陆
        session.setAttribute(Constants.CHECK_CODE_KEY, code);
        vCode.write(response.getOutputStream());
    }

    /**
     * 登录
     *
     * @param session   session
     * @param account   账号
     * @param password  密码
     * @param checkCode 验证码
     * @return ResponseVO
     * @throws BusinessException
     */
    @RequestMapping("/login")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO login(HttpSession session,
                            @VerifyParam(required = true) String account,
                            @VerifyParam(required = true) String password,
                            @VerifyParam(required = true) String checkCode) throws BusinessException {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }
            if (!adminConfig.getAdminAccount().equals(account) || !StringTools.encodeMd5(adminConfig.getAdminPassword()).equals(password)) {
                throw new BusinessException("账号或者密码错误");
            }
            SessionAdminUserDto sessionAdminUserDto = new SessionAdminUserDto();
            sessionAdminUserDto.setAccount(account);
            session.setAttribute(Constants.SESSION_KEY, sessionAdminUserDto);
            return getSuccessResponseVO(sessionAdminUserDto);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    /**
     * 退出登录
     *
     * @param session session
     * @return ResponseVO
     */
    @RequestMapping("/logout")
    public ResponseVO logout(HttpSession session) {
        session.invalidate();
        return getSuccessResponseVO(null);
    }

}
