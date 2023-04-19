package com.forum.controller.api;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.controller.base.ABaseController;
import com.forum.entity.config.WebConfig;
import com.forum.entity.vo.ResponseVO;
import com.forum.enums.ResponseCodeEnum;
import com.forum.exception.BusinessException;
import com.forum.service.SysSettingService;
import com.forum.utils.StringTools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("innerApiController")
@RequestMapping("/innerApi")
public class InnerApiController extends ABaseController {

    @Resource
    private WebConfig webConfig;

    @Resource
    private SysSettingService sysSettingService;

    @RequestMapping("/refresSysSetting")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO refresSysSetting(@VerifyParam(required = true) String appKey,
                                       @VerifyParam(required = true) Long timestamp,
                                       @VerifyParam(required = true) String sign) throws BusinessException {
        if (!webConfig.getInnerApiAppKey().equals(appKey)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (System.currentTimeMillis() - timestamp > 1000 * 10) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String mySign = StringTools.encodeMd5(appKey + timestamp + webConfig.getInnerApiSecret());
        if (!mySign.equals(sign)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        sysSettingService.refreshCache();
        return getSuccessResponseVO(null);
    }
}
