package com.formice.mars.web.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserTicket {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private String ticket;
	private Long expireDis;
	private Long expireTime;
	
	private Date createTime;
	private Long createBy;
	private Date updateTime;
	private Long updateBy;
	private Integer isDeleted;
}
