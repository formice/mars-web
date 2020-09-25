package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlowNodeParam extends BaseEntity{
    private Long id;

    private Long flowId;

    private Long nodeId;

    private Long toolId;

    private Integer busiType;

    private Long busiId;

    private String value;

    private Long relaNodeId;

    private Long relaToolId;

    private Integer relaBusiType;

    private Long relaBusiId;

    /*public FlowNodeParam(Long flowId,Long nodeId, Long toolId,Integer busiType, Long busiId) {
        this.flowId = flowId;
        this.nodeId = nodeId;
        this.toolId = toolId;
        this.busiType = busiType;
        this.busiId = busiId;
    }*/

    public FlowNodeParam(Long flowId,Long nodeId, Long toolId, Integer busiType, Long busiId, Long relaNodeId ,Long relaToolId,Integer relaBusiType,Long relaBusiId) {
        this.flowId = flowId;
        this.nodeId = nodeId;
        this.toolId = toolId;
        this.busiType = busiType;
        this.busiId = busiId;
        this.relaNodeId = relaNodeId;
        this.relaToolId = relaToolId;
        this.relaBusiType = relaBusiType;
        this.relaBusiId = relaBusiId;
    }

    public FlowNodeParam(Long flowId, Long nodeId, Long toolId,Integer busiType, Long busiId) {
        this.flowId = flowId;
        this.nodeId = nodeId;
        this.toolId = toolId;
        this.busiType = busiType;
        this.busiId = busiId;
    }

    public FlowNodeParam(Long flowId, Long toolId, Long busiId, String value) {
        this.flowId = flowId;
        this.toolId = toolId;
        this.busiId = busiId;
        this.value = value;
    }
}