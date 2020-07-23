package com.formice.mars.web.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.formice.mars.web.common.Response;
import com.formice.mars.web.service.UcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value="/uc")
public class UcController{
	@Autowired
	private UcService ucService;
	
	@RequestMapping(value="/mlogin")
	public ModelAndView mlogin(HttpServletRequest request){
		//request.setAttribute("from", request.getParameter("from"));
		ModelAndView mav=new ModelAndView("/mlogin");
		String qs = request.getQueryString();
		if(!StringUtils.isEmpty(qs) && qs.contains("from=")){
			qs = qs.replace("from=", "");
		}
		mav.addObject("from", qs);
		return mav;
	}
	
	@RequestMapping(value="/login")
	public @ResponseBody Response login(HttpServletRequest request,HttpServletResponse response,String account,String pwd){
		return ucService.login(request, account, pwd);
	}
	
	@RequestMapping(value="/sendSms")
	public @ResponseBody Boolean sendSms(String mobile){
		Integer intCount = (new Random()).nextInt(999999);// 最大值位999999
		if (intCount < 100000) {
			intCount += 100000; // 最小值位100001
		}
		String content = "您的验证码为" + intCount
				+ "，如非本人操作，请忽略本短信。";
		return ucService.sendTemplateSMS(mobile, content,intCount);
	}
	
	@RequestMapping(value="/mregister")
	public ModelAndView mregister(HttpServletRequest request){
		//request.setAttribute("from", request.getParameter("from"));
		ModelAndView mav=new ModelAndView("/mregister");
		String qs = request.getQueryString();
		if(!StringUtils.isEmpty(qs) && qs.contains("from=")){
			qs = qs.replace("from=", "");
		}
		mav.addObject("from", qs);
		return mav;
	}
	
	@RequestMapping(value="/register")
	public @ResponseBody Response register(HttpServletRequest request, String account, String pwd, String verifCode){
		return ucService.register(account, pwd,verifCode,request);
	}

}
