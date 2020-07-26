package com.formice.mars.web;

import com.formice.mars.web.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加对用户未登录的拦截器，并添加排除项
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/js/**", "/dist/images/**")//排除样式、脚本、图片等资源文件
                .excludePathPatterns("/login")
                .excludePathPatterns("/sms/send/code")
                .excludePathPatterns("/uc/**");

        //.excludePathPatterns("/", "/index");
    }
}