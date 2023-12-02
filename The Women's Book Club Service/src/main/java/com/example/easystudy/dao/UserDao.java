package com.example.easystudy.dao;


import com.example.easystudy.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Dao
 * @author BB4U
 */
@Mapper
public interface UserDao {
    /**
     * 获取所有用户信息
     * @return
     */
    List<User> selectAllUser();

    /**
     * 根据uid获取用户信息
     * @param userId
     * @return
     */
    String selectUser(@Param("userId") String userId);

    /**
     * 用户注册
     * @param user
     * @return
     */
    int registerUser(User user);

    /**
     * 查询用户基础信息
     * @param uId
     * @return
     */
    User selectUserinfo(@Param("uId") int uId);

    /**
     * 用户密码修改
     * @param uId
     * @param userPassword
     * @return
     */
    int updatePassword(@Param("uId") int uId,@Param("userPassword") String userPassword);

    /**
     * 用户信息更新
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 用户头像修改
     * @param uId
     * @param userImage
     * @return
     */
    int updateImage(@Param("uId") int uId,@Param("userImage") String userImage);

    /**
     * 获取用户上传笔记数
     * @param uId
     * @return
     */
    int GetUploadNotesNumByUid(@Param("uId") int uId);

    /**
     * 获取用户上传计划数
     * @param uId
     * @return
     */
    int GetCreatedPlansNumByUid(@Param("uId") int uId);

    /**
     * 根据账号查询用户
     * @param userId
     * @return
     */
    User SelectUserByUserId(@Param("userId") String userId);

}
