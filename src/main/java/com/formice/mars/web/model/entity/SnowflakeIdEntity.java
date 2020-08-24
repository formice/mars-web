package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SnowflakeIdEntity extends BaseEntity {
	private static final long serialVersionUID = 3745200467736051071L;

	private Long snowflakeId;

}
