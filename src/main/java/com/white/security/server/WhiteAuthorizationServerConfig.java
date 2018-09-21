package com.white.security.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 认证服务器配置
 *
 * @Author: White
 * @Date: 2018/9/20
 */
@Configuration
@EnableAuthorizationServer
public class WhiteAuthorizationServerConfig {
}
