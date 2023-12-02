package com.example.easystudy.pojo;

import java.util.List;

/**
 * 笔记实体
 * @author BB4U
 */
public class Note {
    /**
     * 笔记主键
     */
    public int nId;

    /**
     * 笔记名称
     */
    public String noteName;

    /**
     * 笔记类型
     */
    public String typeName;

    /**
     * 笔记重要性
     */
    public String notePress;

    /**
     * 笔记状态
     */
    public int noteStatus;

    /**
     * 用户id
     */
    public int uId;

    /**
     * 类型id
     */
    public int tId;

    /**
     * 笔记文件位置
     */
    public String fileLocation;

    /**
     * 计划
     */
    public List<Plan> plans;

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNotePress() {
        return notePress;
    }

    public void setNotePress(String notePress) {
        this.notePress = notePress;
    }

    public int getNoteStatus() {
        return noteStatus;
    }

    public void setNoteStatus(int noteStatus) {
        this.noteStatus = noteStatus;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Note{" +
                "nId=" + nId +
                ", noteName='" + noteName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", notePress='" + notePress + '\'' +
                ", noteStatus=" + noteStatus +
                ", uId=" + uId +
                ", tId=" + tId +
                ", fileLocation='" + fileLocation + '\'' +
                ", plans=" + plans +
                '}';
    }
}
