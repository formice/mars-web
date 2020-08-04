package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlowNode extends BaseEntity{
    private Long id;

    private Long flowId;

    private Long busiId;

    public FlowNode(Long flowId) {
        this.flowId = flowId;
    }

    public FlowNode(Long flowId, Long busiId) {
        this.flowId = flowId;
        this.busiId = busiId;
    }
}