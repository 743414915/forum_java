package com.forum.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebConfig extends AppConfig {
    @Value("${spring.mail.username}")
    private String sendUserName;

    @Value("${admin.emails}")
    private String adminEmail;

    @Value("${inner.api.appKey}")
    private String innerApiAppKey;

    @Value("${inner.api.appSecret}")
    private String innerApiSecret;

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public String getInnerApiAppKey() {
        return innerApiAppKey;
    }

    public String getInnerApiSecret() {
        return innerApiSecret;
    }
}
