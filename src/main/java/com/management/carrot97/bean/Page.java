package com.management.carrot97.bean;

import com.management.carrot97.constant.NumberConstants;

public class Page {
    private Integer pageNumber;

    private Integer pageSize;

    public Page(Integer pageNumber) {
        this.pageNumber = pageNumber;
        this.pageSize = NumberConstants.PAGESIZE;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
