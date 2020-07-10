package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Task {
    private Long id;

    private String name;

    private Integer status;

    private Integer process;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Integer isDeleted;

    public Task(Long id,Integer status,Date startTime,Date endTime){
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Task(Long id,Integer process){
        this.id = id;
        this.process = process;
    }

    public Task(String name) {
        this.name = name;
    }


}