package com.formice.mars.web.tool;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestUtils {
	/**
	 * 获取请求参数 不管是post 还是get都可以获取到
	 */
	public static String getQueryUrl(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		// 得到所有的参数名
		List<String> list = new ArrayList<String>();

		Set<String> keyList = params.keySet();

		for (String str : keyList) {
			list.add(str);
		}

		// 参数名ASCII码从小到大排序（字典序）
		//StringUtils.getSort(list);

		for (int i = 0; i < list.size(); i++) {
			// 参数的值
			String[] value = params.get(list.get(i));

			// 如果参数的值为空不参与签名
			if (value != null && value.length>0) {
				sb.append(list.get(i));
				sb.append('=');
				sb.append(value[0]);
				sb.append('&');
			}else{
				sb.append(list.get(i));
				sb.append('=');
				sb.append("");
				sb.append('&');
			}
		}

		return sb.toString();
	}
	
    public static String getIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    } 
}
