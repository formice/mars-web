package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlowLine extends BaseEntity{
    private Long id;

    private Long flowId;

    private Long fromId;

    private Long toId;

    public FlowLine(Long flowId, Long fromId, Long toId) {
        this.flowId = flowId;
        this.fromId = fromId;
        this.toId = toId;
    }
}