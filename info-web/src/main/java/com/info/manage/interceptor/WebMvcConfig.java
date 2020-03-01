package com.info.manage.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xxy
 * @ClassName WebMvcConfig
 * @Description 启动项目时加载此类
 * @Date 2019/1/30 15:37
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor ( new WebHandlerInterceptor () ).addPathPatterns ( "/**" );
    }

}
