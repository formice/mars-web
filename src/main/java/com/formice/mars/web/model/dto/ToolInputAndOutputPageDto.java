package com.formice.mars.web.model.dto;

import lombok.Data;

@Data
public class ToolInputAndOutputPageDto extends PageDto{
    private Long toolId;
    private Integer cate;

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public Integer getCate() {
        return cate;
    }

    public void setCate(Integer cate) {
        this.cate = cate;
    }

    @Override
    public String toString() {
        return "ToolInputAndOutputPageDto{" +
                "toolId=" + toolId +
                ", cate=" + cate +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
