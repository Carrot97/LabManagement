package com.management.carrot97.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("user/login");
        registry.addViewController("/index").setViewName("user/login");
        registry.addViewController("/login").setViewName("user/login");
//        registry.addViewController("/main").setViewName("activity");
//        registry.addViewController("/base").setViewName("commons/base");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginHandlerInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/index", "/login");
//    }
}
