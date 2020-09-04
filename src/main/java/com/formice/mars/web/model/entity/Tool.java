package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tool extends BaseEntity{
    private Long id;

    private Long userId;

    private String name;

    private String desc;

    private String dockerImageUrl;

    private Integer cate;

    private Integer type;

    private Integer containerType;

    private String containerAddr;

    private String website;

    private String help;
}