package com.formice.mars.web.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPageListVo{
    private String id;

    private String flowId;

    private String userId;

    private String name;

    private Integer status;

    private Integer process;

    private Date startTime;

    private Date endTime;
}