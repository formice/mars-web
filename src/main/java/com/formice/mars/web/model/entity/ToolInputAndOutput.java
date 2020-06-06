package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ToolInputAndOutput {
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

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Integer isDeleted;

    public ToolInputAndOutput(Long toolId, Integer cate) {
        this.toolId = toolId;
        this.cate = cate;
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

    public Integer getCate() {
        return cate;
    }

    public void setCate(Integer cate) {
        this.cate = cate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(Integer fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Integer getFileSplitSymbol() {
        return fileSplitSymbol;
    }

    public void setFileSplitSymbol(Integer fileSplitSymbol) {
        this.fileSplitSymbol = fileSplitSymbol;
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

    public Integer getPrefixSplitSymbol() {
        return prefixSplitSymbol;
    }

    public void setPrefixSplitSymbol(Integer prefixSplitSymbol) {
        this.prefixSplitSymbol = prefixSplitSymbol;
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