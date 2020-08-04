package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolTemplate extends BaseEntity{
    private Long id;

    private Long toolId;

    private String content;

    public ToolTemplate(Long toolId) {
        this.toolId = toolId;
    }

    public ToolTemplate(Long toolId, String content) {
        this.toolId = toolId;
        this.content = content;
    }
}