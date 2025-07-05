package com.gec.obwiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
           .authorizeRequests()
               .anyRequest().permitAll() // 允许所有请求访问
               .and()
           .csrf().disable(); // 禁用 CSRF 保护（开发环境中可禁用）

        return http.build();
    }
}