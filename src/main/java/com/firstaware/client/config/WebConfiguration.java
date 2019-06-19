package com.firstaware.client.config;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }


    @Bean
    public FilterRegistrationBean<RequestDumperFilter> requestDumperFilter() {
        FilterRegistrationBean<RequestDumperFilter> requestDumperFilter = new FilterRegistrationBean<>();
        requestDumperFilter.setFilter(new RequestDumperFilter());
        requestDumperFilter.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER - 1);
        return requestDumperFilter;
    }


}
