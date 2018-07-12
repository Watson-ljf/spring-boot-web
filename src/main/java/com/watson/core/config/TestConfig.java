package com.watson.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author: Watson
 * description: 本配置仅仅作为测试使用
 * date: 2018/6/2 22:03.
 */
@Component
// @ConfigurationProperties("env")
public class TestConfig {
    @Value("${env.allow-origin}")
    private String allowOrigin;

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }
}
