package com.example.easystudy.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.easystudy.pojo.UserDto;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    //设置30分钟过期时间
    private static final long EXPIRE_DATE=30 * 60 * 1000;
    //token秘钥
    private static final String TOKEN_SECRET = "wangyu2002myc070701wy2001";
    private static final String Claim_userId = "Claim_userId";
//    private static final String Claim_userPassword = "Claim_userPassword";
    public static String createToken(UserDto userDto)
    {
        String token = "";
        try {

            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            //  设置密钥算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带userId,userPassword信息
            token = JWT.create().withHeader(header).withClaim(Claim_userId, userDto.getUserId())
                    .withExpiresAt(date).sign(algorithm);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return token;
    }
    public static  boolean verify(String token)
    {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            return true;
        }catch (Exception e)
        {
//            e.printStackTrace();
            return  false;
        }
    }
    public static String getUserId(String token)
    {
        try {

            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("Claim_userId").asString();
        }catch (Exception e)
        {
//            e.printStackTrace();
            return null;
        }
    }
}
