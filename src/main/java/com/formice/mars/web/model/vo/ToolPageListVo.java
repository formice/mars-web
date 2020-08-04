package com.formice.mars.web.model.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToolPageListVo {
    private Long id;

    private Long userId;

    private String name;

    private String cate;

    private String type;

    private String website;

    private String help;

    private String createTime;
}
