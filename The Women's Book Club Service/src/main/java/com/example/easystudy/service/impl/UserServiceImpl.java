package com.example.easystudy.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.easystudy.Util.SendMessage;
import com.example.easystudy.result.Result;
import com.example.easystudy.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Value("${accessKeyId}")
    private String accessKeyId;
    @Value("${accessKeySecret}")
    private String accessKeySecret;
    @Value("${signName}")
    private String signName;
    @Value("${templateCode}")
    private String templateCode;
    private String salt = "asdfghjkl1234567890";
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    public static final String token_prefix = "redis:token:";
    @Override
    public Result sendCode(String phoneNumber) {
        Random random = new Random();
        String code = String.valueOf(random.nextInt(1000, 9999));
        if (stringRedisTemplate.opsForValue().get(DigestUtils.md5DigestAsHex((phoneNumber + salt).getBytes())) != null) {
            return new Result(false, "验证码已发送，请一分钟后再次发送");
        }
        try {
            Client client = SendMessage.createClient(accessKeyId, accessKeySecret);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setPhoneNumbers(phoneNumber)
                    .setTemplateParam("{\"code\":\"" + code + "\"}");
            RuntimeOptions runtime = new RuntimeOptions();
            SendSmsResponse resp = client.sendSmsWithOptions(sendSmsRequest, runtime);
            if ("OK".equals(resp.getBody().getCode())) {
                stringRedisTemplate.opsForValue().set(DigestUtils.md5DigestAsHex(phoneNumber.getBytes()), String.valueOf(code), 5, TimeUnit.MINUTES);
                stringRedisTemplate.opsForValue().set(DigestUtils.md5DigestAsHex((phoneNumber + salt).getBytes()), String.valueOf(code), 1, TimeUnit.MINUTES);
                return new Result(true, "验证码发送成功，请注意查收");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result(false, "验证码发送失败，请稍后重试");
    }

    @Override
    public Boolean setToken(String token, String userId) {
        //初始token
        String tokenKey = token_prefix+userId;
        //采用MD5算法进行加密
        String md5TokenKey = DigestUtils.md5DigestAsHex(tokenKey.getBytes());
        System.out.println(stringRedisTemplate);
        return  stringRedisTemplate.opsForValue()
                .setIfAbsent(md5TokenKey, token, 30, TimeUnit.MINUTES);
    }

    @Override
    public String getToken(String userId) {
        String tokenKey = token_prefix+userId;
        String md5TokenKey = DigestUtils.md5DigestAsHex(tokenKey.getBytes());
        return  stringRedisTemplate.opsForValue().get(md5TokenKey);
    }
    public Boolean setExpires(String userId)
    {
        String tokenKey = token_prefix+userId;
        String md5TokenKey = DigestUtils.md5DigestAsHex(tokenKey.getBytes());
        return stringRedisTemplate.expire(md5TokenKey, 30, TimeUnit.MINUTES);
    }
}
