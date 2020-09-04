package com.formice.mars.web.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowPageListVo{
    private String id;

    private String userId;

    private String name;

    private String desc;

    private String cate;

    private String createTime;

}