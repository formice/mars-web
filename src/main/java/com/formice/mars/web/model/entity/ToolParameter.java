package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolParameter extends BaseEntity{
    private Long id;

    private String name;

    private Long toolId;

    private Integer type;

    private String defaultValue;

    private String desc;

    private String prefix;

    private String prefixSplitSymbol;

    private Integer isUseQuotationMarks;

    private Integer isMust;

    public ToolParameter(Long toolId) {
        this.toolId = toolId;
    }

}