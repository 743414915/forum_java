package com.forum;

import com.forum.exception.BusinessException;
import com.forum.service.SysSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("initRun")
public class InitRun implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(InitRun.class);

    @Resource
    private SysSettingService sysSettingService;

    @Override
    public void run(ApplicationArguments args) throws BusinessException {
        // 刷新系统缓存设置
        sysSettingService.refreshCache();
        logger.info("服务启动成功");
    }
}
