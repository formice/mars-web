package com.formice.mars.web.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PanFileDto {
    private String name;
    private String type;
    private String size;
    private String lastUpdateTime;
    private String path;

    public PanFileDto(String name,String path,String type){
        this.name = name;
        this.path = path;
        this.type = type;
    }
}
