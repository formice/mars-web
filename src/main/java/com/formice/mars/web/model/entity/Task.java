package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Task extends SnowflakeIdEntity{
    private Long id;

    private Long flowId;

    private Long userId;

    private String name;

    private Integer status;

    private Integer process;

    private Date startTime;

    private Date endTime;

    public Task(Long id,Integer status,Date startTime,Date endTime,Long createBy){
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        super.setCreateBy(createBy);
    }

    public Task(Long id,Integer process){
        this.id = id;
        this.process = process;
    }

    public Task(Long flowId,Long userId,String name,Long createBy) {
        this.flowId = flowId;
        this.userId = userId;
        this.name = name;
        super.setCreateBy(createBy);
    }

    public Task(String name) {
        this.name = name;
    }


}