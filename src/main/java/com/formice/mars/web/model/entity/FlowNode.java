package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FlowNode {
    private Long id;

    private Long flowId;

    private Long busiId;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Integer isDeleted;

    public FlowNode(Long flowId) {
        this.flowId = flowId;
    }

    public FlowNode(Long flowId, Long busiId) {
        this.flowId = flowId;
        this.busiId = busiId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public Long getBusiId() {
        return busiId;
    }

    public void setBusiId(Long busiId) {
        this.busiId = busiId;
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