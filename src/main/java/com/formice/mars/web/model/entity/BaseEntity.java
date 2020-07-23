package com.formice.mars.web.model.entity;

import java.io.Serializable;

public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 3745200467736051071L;
	
	public static final Integer DELETE_YES = 1;
	public static final Integer DELETE_NO = 0;
	
	
	public static final Long CREATE_BY_SYS = -88L;//操作人是系统
	public static final Long CREATE_BY_PAY_NOFITY = -99L;//操作人是支付notify
	public static final Long CREATE_BY_QZ = -77L;//定时器任务更新

}
