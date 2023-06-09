package com.forum;

import com.forum.exception.BusinessException;
import com.forum.service.SysSettingService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InitRun implements ApplicationRunner {

    @Resource
    private SysSettingService sysSettingService;

    @Override
    public void run(ApplicationArguments args) throws BusinessException {
        sysSettingService.refreshCache();
    }
}
