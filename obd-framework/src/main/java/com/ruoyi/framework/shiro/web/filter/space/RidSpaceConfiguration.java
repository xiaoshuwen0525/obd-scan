package com.ruoyi.framework.shiro.web.filter.space;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

/**
 * @Date: 2020-4-26 17:30
 * @Author: wcq
 */
@Configuration
public class RidSpaceConfiguration implements WebMvcConfigurer {
    /**
     * 去除参数头尾空格过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean spaceFilter() {
        FilterRegistrationBean fitler = new FilterRegistrationBean();
        fitler.setFilter(new SpaceFilter());
        fitler.addUrlPatterns("/order/*");
        fitler.addUrlPatterns("/batch/*");
        fitler.setName("SpaceFilter");
        fitler.setDispatcherTypes(DispatcherType.REQUEST);
        return fitler;
    }

}
