package com.buyersfirst.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.buyersfirst.core.services.RequestInterceptor;

@Configuration
public class InterceptorConfig {

    @Bean
    public MappedInterceptor myInterceptor()
    {
        return new MappedInterceptor(null, new RequestInterceptor());
    }
}
