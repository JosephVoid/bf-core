package com.buyersfirst.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.buyersfirst.core.services.RequestInterceptor;
@EnableWebMvc
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor();
    }

    public @Override void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestInterceptor());
}
}
