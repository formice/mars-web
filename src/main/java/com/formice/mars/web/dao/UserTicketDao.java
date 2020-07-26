package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.UserTicket;

import java.util.Map;

public interface UserTicketDao {
	public int saveUserTicket(UserTicket userTicket);
	
	public UserTicket queryUserTicketByTicket(Map<String,Object> params);
	
	public int delUserTicketByUserIdAndTicket(Map<String,Object> params);
}
