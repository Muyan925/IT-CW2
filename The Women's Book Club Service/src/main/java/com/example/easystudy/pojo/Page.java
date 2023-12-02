package com.example.easystudy.pojo;

import javax.xml.crypto.Data;

/**
 * 分页类
 * @author SMH
 */
public class Page {
    private int total;
    private int pageSize;
    private int beginPage;
    private Object records;
    private int pages;

    public Page() {
    }

    public Page(int pageSize, int beginPage) {
        this.pageSize = pageSize;
        this.beginPage = beginPage;
    }

    public Page(int total, int pageSize, int beginPage) {
        this.total = total;
        this.pageSize = pageSize;
        this.beginPage = beginPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public Object getRecords() {
        return records;
    }

    public void setRecords(Object records) {
        this.records = records;
    }

    public int getPages() {
        if (this.pages == 0) {
            return (int) Math.ceil(this.total * 1.0 / this.pageSize);
        }
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
