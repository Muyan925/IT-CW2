package com.example.easystudy.config;

import cn.hutool.json.JSONUtil;
import com.example.easystudy.Util.JwtTokenUtil;
import com.example.easystudy.Util.TokenUtil;
import com.example.easystudy.Util.UserThreadLocal;

import com.example.easystudy.result.Result;
import com.example.easystudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor implements HandlerInterceptor {

    UserService userService;
    public LoginInterceptor(UserService userService)
    {
        this.userService = userService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Result result = new Result();
        result.setMsg("token验证失败或token失效");
        result.setFlag(false);
        System.out.println("LoginInterceptor运行中");
        String token = request.getHeader("token");
        response.setCharacterEncoding ("UTF-8");
        //拦截器取到请求先进行判断，如果是OPTIONS请求，则放行
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("Method:OPTIONS");
            return true;
        }
//        验证是否携带请求token同时验证是否为人工生成token
        if( ObjectUtils.isEmpty(token)&& JwtTokenUtil.verify(token))
        {
            response.getWriter().print(JSONUtil.parse(result));

            return false;
        }
        String userId = JwtTokenUtil.getUserId(token);
        if (ObjectUtils.isEmpty(userId))
        {
            response.getWriter().print(JSONUtil.parse(result));
            return false;
        }
        String rToken = userService.getToken(userId);
        if (ObjectUtils.isEmpty(rToken))
        {
            response.getWriter().print(JSONUtil.parse(result));
            return false;
        }
//        UserThreadLocal.
        return rToken.equals(token);

    }
}
