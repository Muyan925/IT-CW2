package com.example.easystudy.pojo;

public class Ntop {
    public int rId;
    public int nId;
    public int pId;

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "ntop{" +
                "rId=" + rId +
                ", nId=" + nId +
                ", pId=" + pId +
                '}';
    }
}
