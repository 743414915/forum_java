package com.forum.controller;

import com.forum.constants.Constants;
import com.forum.controller.base.ABaseController;
import com.forum.entity.dto.CreateImageCode;
import com.forum.entity.vo.ResponseVO;
import com.forum.enums.ResponseCodeEnum;
import com.forum.exception.BusinessException;
import com.forum.service.EmailCodeService;
import com.forum.service.UserInfoService;
import com.forum.utils.StringTools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public ResponseVO sendEmailCode(HttpSession session, String email, String checkCode, Integer type) throws BusinessException {
        try {
            if (StringTools.isEmpty(email) || StringTools.isEmpty(checkCode) || type == null) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
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
    public ResponseVO register(HttpSession session, String email, String emailCode, String nickName, String password, String checkCode) throws BusinessException {
        try {
            if (StringTools.isEmpty(checkCode) || StringTools.isEmpty(email) || StringTools.isEmpty(emailCode) || StringTools.isEmpty(nickName) || StringTools.isEmpty(password)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }
            userInfoService.register(email, emailCode, nickName, password, checkCode);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }
}
