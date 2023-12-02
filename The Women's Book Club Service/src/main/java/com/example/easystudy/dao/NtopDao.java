package com.example.easystudy.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关系Dao
 * @author BB4U
 */
@Mapper
public interface NtopDao {
    /**
     * 根据planid删除相应的记录
     * @param pId
     * @return
     */
    int deleteNtopByPid(@Param("pId") int pId);

    /**
     * 从计划中移除笔记
     * @param pId
     * @param nId
     * @return
     */
    int  RemoveNotesFromPlan(@Param("pId") int pId,@Param("nId") int nId);

    /**
     * 根据笔记id删除计划
     * @param nId
     * @return
     */
    int  deleteNtopByNid(@Param("nId") int nId);

    /**
     * 往计划中添加笔记
     * @param nId
     * @param pId
     * @return
     */
    int  AddNotesToPlan(@Param("nId") int nId,@Param("pId") int pId);

    /**
     * 根据pid查询所属笔记
     * @param pId
     * @return
     */
    List<Integer> ShowNidByPid(@Param("pId") int pId);

}
