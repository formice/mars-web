package com.formice.mars.web.service;

import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.Response;
import com.formice.mars.web.common.SessionBag;
import com.formice.mars.web.dao.CustomerDao;
import com.formice.mars.web.dao.SmsDao;
import com.formice.mars.web.model.entity.Customer;
import com.formice.mars.web.model.entity.Sms;
import com.formice.mars.web.model.enums.ResponseCode;
import com.formice.mars.web.tool.Encode;
import com.formice.mars.web.tool.UUIDGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ucService")
@Log4j2
public class UcService {
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private SmsDao smsDao;

	@Autowired
	private UserTicketService userTicketService;
	
	//第一信息
	//用户名
	private static final String ACCOUNT="18986127077";  
	//（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
	private static final String PWD="83F6FCB86A5617AE3E81148EEC51";
	//HTTP请求连接
	private static final String URL="http://sms.1xinxi.cn/asmx/smsservice.aspx?";
	//短信签名
	private static final String SIGN="吾良品";

	public Customer getCustomer(String mobile) {
		return customerDao.queryCustomerByMobile(mobile);
	}

	public Response quickLogin(HttpServletRequest request, String mobile, String code){
		Response resp = new Response();
		String result = "";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		params.put("code", code);
		List<Sms> smsList = smsDao.querySmsByMobileAndCode(params);
		if (CollectionUtils.isEmpty(smsList)) {
			// 验证码输入错误
			return  new Response(ResponseCode.CODE_VERFI_CODE_INPUT_WRONG.getCode());
		}

		if (new Date().getTime() > smsList.get(0).getExpireTime()) {// 过期
			// 验证码过期
			return new Response(ResponseCode.CODE_VERFI_CODE_OUT_OF_DATE.getCode());
		}

		Customer user = getCustomer(mobile);
		if (user != null) {// 用户已经存在，直接登录
			SessionBag.createSession(request, Constant.USER_KEY, user);
			resp = Response.createBySuccess(userTicketService.createTicket(user.getId()));
			log.info("用户：" + mobile + "登录成功," + " from IP:" + request.getRemoteAddr());
		} else {// 用户还未注册
			//注册
			Customer entity = new Customer();
			entity.setMobile(mobile);
			//entity.setPwd(Encode.MD5(pwd));
			entity.setCreateTime(new Date());
			int n = customerDao.saveCustomer(entity);
			// 如果用户注册成功，就:(1)将当前user加入到session中,登陆该用户
			if (n == 1) {
				log.info("用户：" + mobile + "登录并注册成功," + " from IP:" + request.getRemoteAddr());
				// 用户注册成功后，直接登录系统
				SessionBag.createSession(request, Constant.USER_KEY, entity);
				resp = Response.createBySuccess(userTicketService.createTicket(user.getId()));
			}
		}
		return resp;
	}

	public Response login(HttpServletRequest request, String mobile, String pwd) {
		Response resp = new Response();
		String result = "";
		Customer user = getCustomer(mobile);
		if (user != null) {// 用户已经注册
			if (user.getPwd().equals(Encode.MD5(pwd))) {// 如果密码输入正确
				SessionBag.createSession(request, Constant.USER_KEY, user);
				resp = Response.createBySuccess();
				log.info(user.getName() + "登陆了");
			} else {
				resp = new Response(ResponseCode.CODE_ACCOUNT_PWD_NOT_MATCH.getCode());
				log.info(user.getName() + "密码输入错误");
			}
		} else {// 用户还未注册
			//result = "user_no_register";// 该用户还没有注册
			resp = new Response(ResponseCode.CODE_ACCOUNT_NOT_EXIST.getCode());
			log.info(mobile + "用户还没有注册");
		}
		return resp;
	}

	public Response register(String mobile, String pwd, String verifCode, HttpServletRequest request) {
		Response resp = new Response();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		params.put("code", verifCode);
		List<Sms> smsList = smsDao.querySmsByMobileAndCode(params);
		if (CollectionUtils.isEmpty(smsList)) {
			// 验证码输入错误
			resp = new Response(ResponseCode.CODE_VERFI_CODE_INPUT_WRONG.getCode());
			return resp;
		}

		if (new Date().getTime() > smsList.get(0).getExpireTime()) {// 过期
			// 验证码过期
			resp = new Response(ResponseCode.CODE_VERFI_CODE_OUT_OF_DATE.getCode());
			return resp;
		}
		// 在保存用户前，看是否存在，可以防止重复提交
		Customer c = getCustomer(mobile);
		if (c == null) {
			Customer entity = new Customer();
			entity.setMobile(mobile);
			entity.setPwd(Encode.MD5(pwd));
			entity.setCreateTime(new Date());
			int n = customerDao.saveCustomer(entity);
			// 如果用户注册成功，就:(1)将当前user加入到session中,登陆该用户(2)初始化zx_user表，给该用户初始化一条用户正在读的书籍信息
			if (n == 1) {
				log.info("用户" + mobile + "注册成功," + " from IP:" + request.getRemoteAddr());
				// 用户注册成功后，直接登录系统
				SessionBag.createSession(request, Constant.USER_KEY, entity);
				resp = Response.createBySuccess();
			}
		} else {
			log.error("用户" + mobile + "已经注册,请勿重复注册," + " from IP:" + request.getRemoteAddr());
			resp = Response.createBySuccess();
		}

		return resp;
	}

	public Boolean sendTemplateSMS(String mobile, String content,Integer code) {
		Boolean isSuccess = true;
		try {
			// 第一信息
			StringBuffer sb = new StringBuffer();
			sb.append(getCommon());
			// 向StringBuffer追加手机号码
			sb.append("&mobile=" + mobile);
			// 向StringBuffer追加消息内容转URL标准码
			sb.append("&content=" + URLEncoder.encode(content, "UTF-8"));
			// 创建url对象
			log.info("发送短信请求sb:" + sb.toString());
			java.net.URL url = new java.net.URL(sb.toString());
			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");
			// 发送
			InputStream is = url.openStream();
			// 转换返回值
			String returnStr = convertStreamToString(is);
			if (returnStr != null) {
				String[] statusStr = returnStr.split(",");
				if (statusStr[0] != null
						&& "200"
								.equals(statusStr[0])) {
					// 发送成功之后也插入到数据库
					Sms sms = new Sms();
					sms.setContent(content);
					Date curDate = new Date();
					if(code!=null){
						sms.setCode(code.toString());
						sms.setExpireTime(curDate.getTime()+2*60*1000);
						sms.setExpireDis(2*60*1000L);
					}
					sms.setMobile(mobile);
					
					sms.setCreateTime(curDate);
					sms.setCreateBy(-1l);
					smsDao.saveSms(sms);
				} else {
					isSuccess = false;
				}
			} else {
				isSuccess = false;
			}
		} catch (Exception e) {
			isSuccess = false;
			log.info(e.getMessage());
		}
		return isSuccess;
	
	}
	
	public  String getCommon() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(URL);
			// 向StringBuffer追加用户名
			sb.append("name=" + ACCOUNT);
			// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
			sb.append("&pwd=" + PWD);
			// 追加发送时间，可为空，为空为及时发送
			sb.append("&stime=");
			// 加签名
			sb.append("&sign=" + URLEncoder.encode(SIGN, "UTF-8"));
			// type为固定值pt extno为扩展码，必须为数字 可为空
			sb.append("&type=pt&extno=");
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return sb.toString();
	}
	
	/**
	 * 转换返回值类型为UTF-8格式.
	 * @param is
	 * @return
	 */
	public  String convertStreamToString(InputStream is) {    
        StringBuilder sb1 = new StringBuilder();    
        byte[] bytes = new byte[4096];  
        int size = 0;  
        
        try {    
        	while ((size = is.read(bytes)) > 0) {  
                String str = new String(bytes, 0, size, "UTF-8");  
                sb1.append(str);  
            }  
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                is.close();    
            } catch (IOException e) {    
               e.printStackTrace();    
            }    
        }    
        return sb1.toString();    
    }
	
	
}
