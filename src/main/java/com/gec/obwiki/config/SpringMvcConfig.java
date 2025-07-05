package com.gec.obwiki.config;

import com.gec.obwiki.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/test/**",
                        "/api/redis/**",
                        "/api/user/login",
                        "/api/category/all",
                        "/api/ebook/list",
                        "/api/doc/all/**",
                        "/api/doc/vote/**",
                       "/api/content/findContent/**",
                        "/api/ebookSnapshot/**",
                        "/api/getBackendData",
                        "/api/user/save"
                );
    }
}