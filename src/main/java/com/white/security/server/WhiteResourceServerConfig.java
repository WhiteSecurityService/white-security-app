package com.white.security.server;

import com.white.security.app.authentication.openId.OpenIdAuthenticationSecurityConfig;
import com.white.security.core.authentication.FormAuthenticationConfig;
import com.white.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.white.security.core.properties.SecurityConstants;
import com.white.security.core.properties.SecurityProperties;
import com.white.security.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 资源服务器配置
 *
 * @Author: White
 * @Date: 2018/9/20
 */
@Configuration
@EnableResourceServer
public class WhiteResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer whiteSocialSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 表单配置
        formAuthenticationConfig.configure(http);

        http.apply(validateCodeSecurityConfig)
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .apply(whiteSocialSecurityConfig)
                    .and()
                .apply(openIdAuthenticationSecurityConfig)
                    .and()
                .authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getSignInPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        //securityProperties.getBrowser().getSignOutUrl(), // 当配置了white.security.browser.signOutUrl属性，需要配置此项
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,
                        "/user/register").permitAll()
                .anyRequest()
                .authenticated() // 任何请求,登录后可以访问
                .and()
                .csrf().disable();
    }
}
