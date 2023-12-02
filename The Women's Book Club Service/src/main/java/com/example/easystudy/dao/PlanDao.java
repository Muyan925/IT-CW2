package com.example.easystudy.dao;

import com.example.easystudy.pojo.Page;
import com.example.easystudy.pojo.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计划Dao
 * @author BB4U
 */
@Mapper
public interface PlanDao {
    /**
     * 获取完成计划数
     * @param uId
     * @return
     */
    int selectFNumber(@Param("uId") int uId);

    /**
     * 获取未完成计划数
     * @param uId
     * @return
     */
    int selectUNumber(@Param("uId") int uId);

    /**
     * 获取用户所有计划
     * @param uId
     * @param beginPage
     * @param pageSize
     * @return
     */
    List<Plan> GetAllPlansByUid(@Param("uId") int uId, @Param("beginPage") int beginPage, @Param("pageSize") int pageSize);

    /**
     * 获取用户计划（分页用）
     * @param uId
     * @return
     */
    int GetPlanCountByUid(@Param("uId") int uId);

    /**
     * 新增计划
     * @param plan
     * @return
     */
    int AddPlans(Plan plan);

    /**
     * 显示计划基础信息
     * @param pId
     * @return
     */
    Plan ShowPlanBasicInfoByPid(@Param("pId") int pId);

    /**
     * 修改计划基础信息
     * @param plan
     * @return
     */
    int UpdatePlanBasicInfo(Plan plan);

    /**
     * 获取未完成的计划
     * @param uId
     * @return
     */
    List<Plan> GetUnfinishedPlansByUid(@Param("uId") int uId);

    /**
     * 搜索计划
     * @param uId
     * @param planName
     * @param pageBegin
     * @param pageSize
     * @return
     */
    List<Plan> SelectPlansByKeyword(@Param("uId") int uId,@Param("planName") String planName,@Param("pageBegin") int pageBegin, @Param("pageSize") int pageSize);

    /**
     * 搜索计划（分页用0
     * @param uId
     * @param planName
     * @return
     */
    int SelectPlanCountByKeyword(@Param("uId") int uId,@Param("planName") String planName);

    /**
     * 单个删除计划
     * @param pId
     * @return
     */
    int DeleteSinglePlanByPid(@Param("pId") int pId);

    /**
     * 筛选计划
     * @param planStress
     * @param planStatus
     * @param uId
     * @param beginPage
     * @param pageSize
     * @return
     */
    List<Plan> FilterPlans(@Param("planStress") String planStress, @Param("planStatus") int planStatus, @Param("uId") int uId, @Param("beginPage") int beginPage, @Param("pageSize") int pageSize);

    /**
     * 筛选计划（分页用）
     * @param plan
     * @return
     */
    int FilterPlanCount(Plan plan);
}
