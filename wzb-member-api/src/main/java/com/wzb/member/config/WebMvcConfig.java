package com.wzb.member.config;

import com.wzb.member.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationFilter)
                // 拦截所有请求
                .addPathPatterns("/**")
                // 登录请求排除，不被拦截
                .excludePathPatterns("/user/login");
    }
}
