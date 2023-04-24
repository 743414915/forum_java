package com.forum.controller;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.controller.base.ABaseController;
import com.forum.entity.config.AdminConfig;
import com.forum.entity.dto.*;
import com.forum.entity.vo.ResponseVO;
import com.forum.exception.BusinessException;
import com.forum.service.SysSettingService;
import com.forum.utils.JsonUtils;
import com.forum.utils.OKHttpUtils;
import com.forum.utils.StringTools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("sysSettingController")
@RequestMapping("/setting")
public class SysSettingController extends ABaseController {

    @Resource
    private SysSettingService sysSettingService;

    @Resource
    private AdminConfig adminConfig;

    @RequestMapping("/getSetting")
    public ResponseVO getSetting() throws BusinessException {
        return getSuccessResponseVO(sysSettingService.refreshCache());
    }

    @RequestMapping("/saveSetting")
    public ResponseVO saveSetting(SysSetting4AuditDto auditDto,
                                   SysSetting4CommentDto commentDto,
                                   SysSetting4EmailDto emailDto,
                                   SysSetting4LikeDto likeDto,
                                   SysSetting4PostDto postDto,
                                   SysSetting4RegisterDto registerDto) throws BusinessException {
        SysSettingDto sysSettingDto = new SysSettingDto();
        sysSettingDto.setAuditSetting(auditDto);
        sysSettingDto.setCommentSetting(commentDto);
        sysSettingDto.setEmailSetting(emailDto);
        sysSettingDto.setLikeSetting(likeDto);
        sysSettingDto.setPostSetting(postDto);
        sysSettingDto.setRegisterSetting(registerDto);
        sysSettingService.saveSetting(sysSettingDto);
        sendWebRequest();
        return getSuccessResponseVO(null);
    }

    public void sendWebRequest() throws BusinessException {
        String appKey = adminConfig.getInnerApiAppKey();
        String appSecret = adminConfig.getInnerApiSecret();
        Long timeStamp = System.currentTimeMillis();
        String sign = StringTools.encodeMd5(appKey + timeStamp + appSecret);
        String url = adminConfig.getWebApiUrl() + "?appKey=" + appKey + "&timestamp=" + timeStamp + "&sign=" + sign;
        String responseJson = OKHttpUtils.getRequest(url);
        ResponseVO responseVO = JsonUtils.convertJson2Obj(responseJson, ResponseVO.class);
        if (!STATUS_SUCCESS.equals(responseVO.getStatus())) {
            throw new BusinessException("刷新访客段缓存失败");
        }
    }
}
