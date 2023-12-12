package com.atguigu.ssyx.common.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 添加用户登录拦截器到项目中
 */
@Configuration
public class LoginMvcConfigAdapter extends WebMvcConfigurationSupport {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor(redisTemplate)).
                addPathPatterns("/api/**").
                excludePathPatterns("/api/user/weixin/wxLogin/*", "/api/**/inner/**");
        super.addInterceptors(registry);
    }
}
