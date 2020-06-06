package com.formice.mars.web.model.dto;

import lombok.Data;

@Data
public class FlowPageDto extends PageDto{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
