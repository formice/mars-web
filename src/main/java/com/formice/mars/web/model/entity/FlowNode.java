package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlowNode extends BaseEntity{
    private Long id;

    private String uuid;

    private Long flowId;

    private Long toolId;

    private String alias;

    public FlowNode(Long flowId) {
        this.flowId = flowId;
    }

    public FlowNode(Long flowId,String uuid,Long toolId) {
        this.uuid = uuid;
        this.flowId = flowId;
        this.toolId = toolId;
    }

    public FlowNode(String uuid,Long flowId, Long toolId,String alias) {
        this.uuid = uuid;
        this.flowId = flowId;
        this.toolId = toolId;
        this.alias = alias;
    }
}