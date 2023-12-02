package com.example.easystudy.Util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    public static final String token_prefix = "redis:token:";
    public Boolean setToken(String token,String userId)
    {
        //初始token
        String tokenKey = token_prefix+userId;
        //采用MD5算法进行加密
        String md5TokenKey = DigestUtils.md5DigestAsHex(tokenKey.getBytes());
        System.out.println(stringRedisTemplate);
        return  stringRedisTemplate.opsForValue()
                .setIfAbsent(md5TokenKey, token, 30, TimeUnit.MINUTES);
    }

    public String getToken(String userId)
    {
        String tokenKey = token_prefix+userId;
        String md5TokenKey = DigestUtils.md5DigestAsHex(tokenKey.getBytes());
        return  stringRedisTemplate.opsForValue().get(md5TokenKey);
    }
}
