package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.Sms;

import java.util.List;
import java.util.Map;

public interface SmsDao {
	public int saveSms(Sms sms);
	
	public List<Sms> querySmsByMobileAndCode(Map<String,Object> params);
}
