package com.formice.mars.web.service;

import com.formice.mars.web.dao.UserTicketDao;
import com.formice.mars.web.model.entity.BaseEntity;
import com.formice.mars.web.model.entity.UserTicket;
import com.formice.mars.web.tool.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserTicketService {
	
	@Autowired
	private UserTicketDao userTicketDao;
	
	
	private int saveUserTicket(UserTicket userTicket) {
		return userTicketDao.saveUserTicket(userTicket);
	}

	public UserTicket queryUserTicketByTicket(String ticket) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("ticket", ticket);
		return userTicketDao.queryUserTicketByTicket(params);
	}
	
	private int delUserTicketByUserIdAndTicket(Long userId,String ticket){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("ticket", ticket);
		return userTicketDao.delUserTicketByUserIdAndTicket(params);
	}
	
	public String createTicket(Long userId){
		UserTicket userTicket = new UserTicket();
		Date curDate = new Date();
		userTicket.setUserId(userId);

		//30分钟 过期
		userTicket.setExpireDis(30*60*1000L);
		userTicket.setExpireTime(curDate.getTime()+30*60*1000);

		String ticket = UUIDGenerator.getUUID();
		userTicket.setTicket(ticket);
		userTicket.setCreateBy(BaseEntity.CREATE_BY_SYS);
		userTicket.setCreateTime(curDate);
		int n = saveUserTicket(userTicket);
		if(n > 0){
			return ticket;
		}else{
			return null;
		}
	}
	
	public void destoryTicket(Long userId,String ticket){
		delUserTicketByUserIdAndTicket(userId, ticket);
	}
	
	public boolean validateTicket(UserTicket ut,String ticket){
		if (StringUtils.isEmpty(ticket)) {
			return false;
		}
		//UserTicket ticketEntity = queryUserTicketByTicket(ticket);
		
		if(ut == null){
			return false;
		}
		
		Date curDate = new Date();
		if(curDate.getTime() >= ut.getExpireTime()){
			return false;
		}
		/*
		if(!ticketEntity.getTerminalType().equals(terminalType)){
			isEffectiveTicket = false;
		}*/
		
		return true;
	}

	public int updateExpireTimeByUserIdAndTicket(UserTicket ut){
		return userTicketDao.updateExpireTimeByUserIdAndTicket(ut);
	}

}
