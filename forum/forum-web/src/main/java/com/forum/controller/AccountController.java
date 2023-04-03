package com.forum.controller;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.constants.Constants;
import com.forum.controller.base.ABaseController;
import com.forum.entity.dto.CreateImageCode;
import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.dto.SysSetting4CommentDto;
import com.forum.entity.dto.SysSettingDto;
import com.forum.entity.po.SysSetting;
import com.forum.entity.vo.ResponseVO;
import com.forum.enums.ResponseCodeEnum;
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
    private EmailCodeService emailCodeService;

    @Resource
    private UserInfoService userInfoService;

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

        // 登陆注册
        if (type == null || type == 0) {
            session.setAttribute(Constants.CHECK_CODE_KEY, code);
        } else {
            // 获取邮箱
            session.setAttribute(Constants.CHECK_CODE_KEY_EMAIL, code);
        }
        vCode.write(response.getOutputStream());
    }

    /**
     * 发送邮箱验证码
     *
     * @param session   session
     * @param email     邮箱
     * @param checkCode 验证码
     * @param type      验证码类型
     * @return ResponseVO
     */
    @RequestMapping("/sendEmailCode")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO sendEmailCode(HttpSession session,
                                    @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL, max = 150) String email,
                                    @VerifyParam(required = true) String checkCode,
                                    @VerifyParam(required = true) Integer type) throws BusinessException {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY_EMAIL))) {
                throw new BusinessException("图片验证码错误");
            }
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY_EMAIL);
        }

        try {
            emailCodeService.SendEmailCode(email, type);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return getSuccessResponseVO(null);
    }

    /**
     * 注册
     *
     * @param session   session
     * @param checkCode 验证码
     * @return ResponseVO
     * @throws BusinessException
     */
    @RequestMapping("/register")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO register(HttpSession session,
                               @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL, max = 150) String email,
                               @VerifyParam(required = true) String emailCode,
                               @VerifyParam(required = true) String nickName,
                               @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, max = 18, min = 8) String password,
                               @VerifyParam(required = true) String checkCode) throws BusinessException {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }
            userInfoService.register(email, emailCode, nickName, password, checkCode);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    /**
     * 登录
     *
     * @param session   session
     * @param email     邮箱
     * @param password  密码
     * @param checkCode 验证码
     * @return ResponseVO
     * @throws BusinessException
     */
    @RequestMapping("/login")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO login(HttpSession session,
                            HttpServletRequest request,
                            @VerifyParam(required = true) String email,
                            @VerifyParam(required = true) String password,
                            @VerifyParam(required = true) String checkCode) throws BusinessException {
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }
            SessionWebUserDto sessionWebUserDto = userInfoService.login(email, password, getIpAddr(request));
            session.setAttribute(Constants.SESSION_KEY, sessionWebUserDto);
            return getSuccessResponseVO(sessionWebUserDto);
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

    /**
     * 获取用户信息
     *
     * @param session session
     * @return ResponseVO
     */
    @RequestMapping("/getUserInfo")
    public ResponseVO getUserInfo(HttpSession session) {
        return getSuccessResponseVO(getUserInfoFromSession(session));
    }

    /**
     * 获取系统设置
     *
     * @return ResponseVO
     */
    @RequestMapping("/getSysSetting")
    public ResponseVO getSysSetting() {
        SysSettingDto sysSettingDto = SysCacheUtils.getSysSetting();
        SysSetting4CommentDto commentDto = sysSettingDto.getCommentSetting();

        Map<String, Object> result = new HashMap<>();
        result.put("commentOpen", commentDto.getCommentOpen());

        return getSuccessResponseVO(result);
    }

    @RequestMapping("/resetPwd")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO resetPwd(HttpSession session,
                               @VerifyParam(required = true) String email,
                               @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD, max = 18, min = 8) String password,
                               @VerifyParam(required = true) String emailCode,
                               @VerifyParam(required = true) String checkCode) throws BusinessException {


        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }

            userInfoService.resetPwd(email, password, checkCode);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

}
