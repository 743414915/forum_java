package com.forum.controller;

import com.forum.controller.base.ABaseController;
import com.forum.entity.dto.SysSetting4CommentDto;
import com.forum.entity.dto.SysSettingDto;
import com.forum.entity.vo.ResponseVO;
import com.forum.exception.BusinessException;
import com.forum.service.SysSettingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("sysSettingController")
@RequestMapping("/setting")
public class SysSettingController extends ABaseController {

    @Resource
    private SysSettingService sysSettingService;

    @RequestMapping("/getSetting")
    private ResponseVO getSetting() throws BusinessException {
        SysSettingDto sysSettingDto = sysSettingService.refreshCache();
        boolean commentOpen = true;
        SysSetting4CommentDto sysSetting4CommentDto = new SysSetting4CommentDto();
        if (sysSettingDto != null) {
            commentOpen = sysSettingDto.getCommentSetting().getCommentOpen();
        }
        sysSetting4CommentDto.setCommentOpen(commentOpen);
        return getSuccessResponseVO(sysSetting4CommentDto);
    }
}
