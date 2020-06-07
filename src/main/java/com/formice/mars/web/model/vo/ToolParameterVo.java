package com.formice.mars.web.model.vo;

import lombok.Data;

@Data
public class ToolParameterVo {
    private Long id;

    private String name;

    private Long toolId;

    private String type;

    private String defaultValue;

    private String desc;

    private String prefix;

    private String prefixSplitSymbol;

    private String isUseQuotationMarks;

    private String isMust;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefixSplitSymbol() {
        return prefixSplitSymbol;
    }

    public void setPrefixSplitSymbol(String prefixSplitSymbol) {
        this.prefixSplitSymbol = prefixSplitSymbol;
    }

    public String getIsUseQuotationMarks() {
        return isUseQuotationMarks;
    }

    public void setIsUseQuotationMarks(String isUseQuotationMarks) {
        this.isUseQuotationMarks = isUseQuotationMarks;
    }

    public String getIsMust() {
        return isMust;
    }

    public void setIsMust(String isMust) {
        this.isMust = isMust;
    }
}