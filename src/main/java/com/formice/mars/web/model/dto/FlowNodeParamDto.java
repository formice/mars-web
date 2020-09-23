package com.formice.mars.web.model.dto;

import lombok.Data;

@Data
public class FlowNodeParamDto {
    private Long id;

    private String value;

    private String uuid;
    private Long toolId;
    private Long busiId;
    private String relaUuid;
    private Long relaToolId;
    private Long relaBusiId;
}
