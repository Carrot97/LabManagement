package com.management.carrot97.config;

import com.management.carrot97.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("user/login");
        registry.addViewController("/login").setViewName("user/login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(/*静态资源*/"/assets/**","/webjars/**",
                        /*登录页面*/"/", "/login", "/user/login",
                        /*注册页面*/"/user/register",
                        /*最近活动页面*/"/activity/recent",
                        /*全部活动页面*/"/activity/all");
    }

}
