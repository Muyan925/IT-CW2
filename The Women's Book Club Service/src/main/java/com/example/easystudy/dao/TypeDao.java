package com.example.easystudy.dao;

import com.example.easystudy.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类型Dao
 * @author BB4U
 */
@Mapper
public interface TypeDao {
    /**
     * 获取用户创建类型
     * @param uId
     * @return
     */
    List<Type> selectTypeListByUid(@Param("uId") int uId);

    /**
     * 更新计划信息
     * @param tId
     * @param typeName
     * @return
     */
    int updateTypeByTid(@Param("tId") int tId,@Param("typeName") String typeName);

    /**
     * 增加类型
     * @param type
     * @return
     */
    int addType(Type type);

    /**
     * 删除类型
     * @param tId
     * @return
     */
    int deleteTypeByTid(@Param("tId") int tId);

    /**
     * 获取类型（通过id）
     * @param tId
     * @return
     */
    Type SelectTypeByTid(@Param("tId") int tId);
}
