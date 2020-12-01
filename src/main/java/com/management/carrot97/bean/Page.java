package com.management.carrot97.bean;

public class Page {
    private Integer pageNumber;

    private Integer pageSize;

    private Integer numberStart;

    public Page(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.numberStart = (pageNumber - 1) * pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getNumberStart() {
        return numberStart;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", numberStart=" + numberStart +
                '}';
    }
}
