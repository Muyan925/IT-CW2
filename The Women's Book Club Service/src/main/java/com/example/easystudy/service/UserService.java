package com.example.easystudy.service;

import com.example.easystudy.result.Result;

public interface UserService {
    Result sendCode(String phoneNumber);
    Boolean setToken(String token,String userId);
    String getToken(String userId);
    Boolean setExpires(String userId);
}