package com.example.easystudy.pojo;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    public String userId;
//    public String userPassword;
    public String userImage;
    public int uId;

    /**
     * 用户账号
     */

    /**
     * 用户昵称
     */
    public String userName;

    /**
     * 用户地址
     */
    public String userAddress;

    /**
     * 用户邮箱
     */
    public String userEmail;

    /**
     * 用户个人简介
     */
    public String userInfo;


    /**
     * 用户性别
     */
    public String userGender;

    /**
     * 用户头像
     */

    /**
     * 用户笔记
     */
    public List<Note> notes;

    /**
     * 用户计划
     */
    public List<Plan> plans;

    public UserDto(String userId)
    {
        this.userId = userId;
    }
    public UserDto()
    {

    }
}
