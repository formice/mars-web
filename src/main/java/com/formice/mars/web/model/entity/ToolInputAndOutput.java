package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolInputAndOutput extends BaseEntity{
    private Long id;

    private String name;

    private Long toolId;

    private Integer cate;

    private Integer type;

    private Integer minNum;

    private Integer maxNum;

    private Integer fileFormat;

    private Integer fileSplitSymbol;

    private String desc;

    private String prefix;

    private Integer prefixSplitSymbol;

    private Integer isMust;

    public ToolInputAndOutput(Long toolId, Integer cate) {
        this.toolId = toolId;
        this.cate = cate;
    }
}