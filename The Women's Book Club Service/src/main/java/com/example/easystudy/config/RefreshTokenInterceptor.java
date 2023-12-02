package com.example.easystudy.config;

import cn.hutool.json.JSONUtil;
import com.example.easystudy.Util.JwtTokenUtil;
import com.example.easystudy.result.Result;
import com.example.easystudy.service.UserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    UserService userService;
    public RefreshTokenInterceptor(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        response.setCharacterEncoding ("UTF-8");
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("Method:OPTIONS");
            return true;
        }
//        验证是否携带请求token同时验证是否为人工生成token
        System.out.println("12345678");
        if( ObjectUtils.isEmpty(token)&& JwtTokenUtil.verify(token))
        {
            return true;
        }
        String userId = JwtTokenUtil.getUserId(token);
        if (ObjectUtils.isEmpty(userId))
        {
            return true;
        }
        //判断token是否过期
        String rToken = userService.getToken(userId);
        if (ObjectUtils.isEmpty(rToken))
        {
            return true;
        }
//        刷新token时间
        System.out.println("1234567");
        Boolean aBoolean = userService.setExpires(userId);
        System.out.println("aBoolean:  " +aBoolean);
        return true;
    }
}
