package com.example.easystudy.pojo;

/**
 * 类型实体类
 * @author BB4U
 */
public class Type {
    /**
     * 类型id
     */
    public int tId;

    /**
     * 用户id
     */
    public int uId;

    /**
     * 类型名称
     */
    public String typeName;

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Type{" +
                "tId=" + tId +
                ", uId=" + uId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
