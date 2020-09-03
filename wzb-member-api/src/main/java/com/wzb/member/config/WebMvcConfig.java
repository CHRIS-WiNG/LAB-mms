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
 * @Description: 实现 WebMvcConfigurer 接口而不是继承 WebMvcConfigurationSupport 是因为
 * 继承 WebMvcConfigurationSupport 就不能开启 WebMvcAutoConfiguration 自动配置类，静态资源无法自动映射
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationFilter)
                // 拦截所有请求
                .addPathPatterns("/**")
                // 登录请求排除，不被拦截
                .excludePathPatterns("/user/login");
    }
}
