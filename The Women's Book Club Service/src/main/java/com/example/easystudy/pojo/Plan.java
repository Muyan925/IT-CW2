package com.example.easystudy.pojo;

import java.util.List;

/**
 * 计划实体类
 * @author BB4U
 */
public class Plan {
    /**
     * 计划id
     */
    public int pId;

    /**
     * 计划名称
     */
    public String planName;

    /**
     * 计划简介
     */
    public String planInfo;

    /**
     * 计划重要性
     */
    public String planStress;

    /**
     * 计划状态
     */
    public int planStatus;

    /**
     * 开始时间
     */
    public String startTime;

    /**
     * 结束时间
     */
    public String endTime;

    /**
     * 用户id
     */
    public int uId;

    /**
     * 笔记列表
     */
    public List<Note> notes;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(String planInfo) {
        this.planInfo = planInfo;
    }

    public String getPlanStress() {
        return planStress;
    }

    public void setPlanStress(String planStress) {
        this.planStress = planStress;
    }

    public int getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(int planStatus) {
        this.planStatus = planStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "pId=" + pId +
                ", planName='" + planName + '\'' +
                ", planInfo='" + planInfo + '\'' +
                ", planStress='" + planStress + '\'' +
                ", planStatus=" + planStatus +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", uId=" + uId +
                ", notes=" + notes +
                '}';
    }
}
