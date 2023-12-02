package com.example.easystudy.pojo;

import java.util.List;

/**
 * 用户实体类
 * @author BB4U
 */
public class User {
    /**
     * 用户id
     */
    public int uId;

    /**
     * 用户账号
     */
    public String userId;

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
     * 用户密码
     */
    public String userPassword;

    /**
     * 用户性别
     */
    public String userGender;

    /**
     * 用户头像
     */
    public String userImage;

    /**
     * 用户笔记
     */
    public List<Note> notes;

    /**
     * 用户计划
     */
    public List<Plan> plans;

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userImage='" + userImage + '\'' +
                ", notes=" + notes +
                ", plans=" + plans +
                '}';
    }
}
