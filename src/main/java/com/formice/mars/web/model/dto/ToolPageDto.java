package com.formice.mars.web.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolPageDto extends PageDto{
    private String name;
    private Integer type;
    private Long userId;
}
