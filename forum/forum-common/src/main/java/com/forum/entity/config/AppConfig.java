package com.forum.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${project.folder}")
    private String projectFolder;

    @Value("${isDev}")
    private boolean isDev;


    @Value("${inner.api.appKey}")
    private String innerApiAppKey;

    @Value("${inner.api.appSecret}")
    private String innerApiSecret;

    public String getProjectFolder() {
        return projectFolder;
    }

    public boolean isDev() {
        return isDev;
    }

    public String getInnerApiAppKey() {
        return innerApiAppKey;
    }

    public String getInnerApiSecret() {
        return innerApiSecret;
    }
}
