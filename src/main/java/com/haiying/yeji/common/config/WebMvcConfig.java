package com.haiying.yeji.common.config;


import com.haiying.yeji.common.result.ResponseResultInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //配置ResponseResultInterceptor
        registry.addInterceptor(new ResponseResultInterceptor()).addPathPatterns("/**");
        //配置登录拦截器
        List<String> excludeList = new ArrayList<>();
        excludeList.add("/checkUser/login");
        excludeList.add("/login");
        excludeList.add("/login");
        excludeList.add("/back");
        excludeList.add("/*.js");
        excludeList.add("/*.css");
        excludeList.add("/*.svg");

//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(excludeList);
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
