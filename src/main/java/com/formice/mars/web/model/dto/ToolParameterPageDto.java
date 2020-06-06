package com.formice.mars.web.model.dto;

import lombok.Data;

@Data
public class ToolParameterPageDto extends PageDto{
    private Long toolId;

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }



    @Override
    public String toString() {
        return "ToolInputAndOutputPageDto{" +
                "toolId=" + toolId +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
