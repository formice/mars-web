package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Flow extends BaseEntity{
    private Long id;

    private Long userId;

    private String name;

    private String desc;

    public Flow(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Flow(Long userId, String name, String desc) {
        this.userId = userId;
        this.name = name;
        this.desc = desc;
    }
}