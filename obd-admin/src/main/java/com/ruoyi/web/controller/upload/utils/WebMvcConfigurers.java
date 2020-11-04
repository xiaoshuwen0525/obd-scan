package com.ruoyi.web.controller.upload.utils;



import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
class WebMvcConfigurers implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/obdImg/**").addResourceLocations("classpath:/static/obdImg/");

        registry.addResourceHandler("/static/obdImg/**").addResourceLocations("file:E:/static/obdImg/");
    }
}