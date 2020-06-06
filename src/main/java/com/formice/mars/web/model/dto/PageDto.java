package com.formice.mars.web.model.dto;

import lombok.Data;

@Data
public class PageDto {
    protected int pageNum;
    protected int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum-1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
