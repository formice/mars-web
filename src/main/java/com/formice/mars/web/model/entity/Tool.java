package com.formice.mars.web.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Tool {
    private Long id;

    private String name;

    private Integer cate;

    private Integer type;

    private Integer containerType;

    private String containerAddr;

    private String website;

    private String help;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Integer isDeleted;

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

    public Integer getContainerType() {
        return containerType;
    }

    public void setContainerType(Integer containerType) {
        this.containerType = containerType;
    }

    public String getContainerAddr() {
        return containerAddr;
    }

    public void setContainerAddr(String containerAddr) {
        this.containerAddr = containerAddr;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
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