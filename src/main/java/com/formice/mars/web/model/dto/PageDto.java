package com.formice.mars.web.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageDto extends BaseDto{
    protected int pageNum;
    protected int pageSize;
    protected int start;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /*public void setStart() {
        this.start = (pageNum-1)*pageSize;
    }*/

    public int getStart() {
        return (pageNum-1)*pageSize;
    }
}
