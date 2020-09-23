package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlowNode extends BaseEntity{
    private Long id;

    private String uuid;

    private Long flowId;

    private Long busiId;

    private String alias;

    public FlowNode(Long flowId) {
        this.flowId = flowId;
    }

    public FlowNode(Long flowId,String uuid,Long busiId) {
        this.uuid = uuid;
        this.flowId = flowId;
        this.busiId = busiId;
    }

    public FlowNode(String uuid,Long flowId, Long busiId,String alias) {
        this.uuid = uuid;
        this.flowId = flowId;
        this.busiId = busiId;
        this.alias = alias;
    }
}