package com.formice.mars.web.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ToolParameter {
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

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Integer isDeleted;

    public ToolParameter(Long toolId) {
        this.toolId = toolId;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public Integer getIsUseQuotationMarks() {
        return isUseQuotationMarks;
    }

    public void setIsUseQuotationMarks(Integer isUseQuotationMarks) {
        this.isUseQuotationMarks = isUseQuotationMarks;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}