package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortalContactUs extends BaseEntity{
    private Long id;

    private String name;

    private String mobile;

    private String content;
}