package com.formice.mars.web.model.dto;

import lombok.Data;

@Data
public class TaskRunItemDto {
    private Long id;
    private Long nodeId;
    private Long toolId;
    private Integer type;
    private String value;
    private Integer isRemote;
}
