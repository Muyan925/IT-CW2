package com.example.easystudy.config;

import com.example.easystudy.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * 静态资源放行
 * @author 16088
 */
@Configuration
//@EnableWebMvc
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Resource
    UserService userService;
    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    /**
     * 静态资源放行
     * @param
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + System.getProperty("user.dir") + "/img/");
        registry.addResourceHandler("/note/**").addResourceLocations("file:" + System.getProperty("user.dir") + "/note/");
        registry.addResourceHandler("/link/**").addResourceLocations("file:" + System.getProperty("user.dir") + "/md-img/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RefreshTokenInterceptor(userService)).addPathPatterns("/**").order(0);
        registry.addInterceptor(new LoginInterceptor(userService)).addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/img/**",
                        "/link/**",
                        "/note/**",
                        "/register/**",
                        "/user/getCaptcha",
                        "/users/send/**");
    }
}