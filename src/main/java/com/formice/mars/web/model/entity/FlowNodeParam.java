package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlowNodeParam extends BaseEntity{
    private Long id;

    private Long flowId;

    private Long toolId;

    private Long busiId;

    private String value;

    public FlowNodeParam(Long flowId, Long toolId, Long busiId) {
        this.flowId = flowId;
        this.toolId = toolId;
        this.busiId = busiId;
    }

    public FlowNodeParam(Long flowId, Long toolId, Long busiId, String value) {
        this.flowId = flowId;
        this.toolId = toolId;
        this.busiId = busiId;
        this.value = value;
    }
}