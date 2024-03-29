package com.formice.mars.web.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolDto{
    private Integer cate;
    private Integer type;
    private String name;
    private Integer limit;
    private Long userId;
}
