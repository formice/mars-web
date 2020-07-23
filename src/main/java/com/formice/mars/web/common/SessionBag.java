/**
 * <br> @ProjectName: 木兰
 * <br> @(#)mangnolia com.mangnolia.platform.sys.SessionBag.java
 * <br> 版权声明 木兰在线有限公司 版权所有 违者必究
 *
 * @Copyright:  Copyright (c) 2013
 * @Company:    木兰在线有限公司
 * @author:     formice
 * @Date:       May 14, 2013
 * @version:    1.0
 */
package com.formice.mars.web.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;

/**
 * <br>  
 * <b>类描述:提供功能：
 * (1)session操作功能
 * (2)将session中内容放进SESSION_MAP中供controller层以外的任何地方调用（用ThreadLocal提供一个存储线程内变量的地方. <p/>
	 客户端代码可以用静态方法存储和获取线程内变量,不需要依赖于HttpSession）
	 
 * <p/>
 * <pre></pre>
 * <br>时间: May 14, 2013
 * 
 * <br>————————————————————————————————————
 * <br>修改记录
 * <br>    修改者：
 * <br>    修改时间：
 * <br>    修改原因：
 * <br>————————————————————————————————————
 */
@SuppressWarnings("unchecked")
@Log4j2
public class SessionBag {
	/** * 保存变量的ThreadLocal，保持在同一线程中同步数据. */
	private static final ThreadLocal SESSION_MAP = new ThreadLocal();

	/** * 工具类的protected构造方法. */
	protected SessionBag() {
	}

	/**
	 * 获得线程中保存的属性.
	 * 
	 * @param attribute
	 *            属性名称
	 * @return 属性值
	 */
	public static Object get(String attribute) {
		Map map = (Map) SESSION_MAP.get();
		log.info(map.toString());
		return map.get(attribute);
	}

	/**
	 * 获得线程中保存的属性，使用指定类型进行转型.
	 * 
	 * @param attribute
	 *            属性名称
	 * @param clazz
	 *            类型
	 * @param <T>
	 *            自动转型
	 * @return 属性值
	 */
	public static <T> T get(String attribute, Class<T> clazz) {
		return (T) get(attribute);
	}

	/**
	 * 设置制定属性名的值.
	 * 
	 * @param attribute
	 *            属性名称
	 * @param value
	 *            属性值
	 */
	public static void set(String attribute, Object value) {
		Map map = (Map) SESSION_MAP.get();

		if (map == null) {
			map = new HashMap();
			SESSION_MAP.set(map);
		}
		map.put(attribute, value);
	}
	
	 public static void destory(){
		 SESSION_MAP.set(null);
		 log.info("destory sessionbag");
	 }
	
	/*...................................以下为session操作方法........................................*/
	
	/**
	 * 
	 * <br>功能说明：创建session
	 * <br>--------------------------------------
	 * <br>修     改：
	 * <br>修改者：
	 * <br>时    间:
	 * <br>--------------------------------------
	 * @param request
	 * @param name
	 * @param obj
	 */
	public static void createSession(HttpServletRequest request,String name,Object obj){
		//创建session
		HttpSession session=request.getSession();
		session.setAttribute(name,obj);//防止重名
		
		//将session中的信息放进sessionBag，方便controller层以外的地方使用
		SessionBag.set(name,obj);  
	}
	/**
	 * 
	 * <br>功能说明：更新session
	 * <br>--------------------------------------
	 * <br>修     改：
	 * <br>修改者：
	 * <br>时    间:
	 * <br>--------------------------------------
	 * @param request
	 * @param name
	 * @param obj
	 */
	public static void updateSession(HttpServletRequest request,String name,Object obj){
		createSession(request,name,obj);
	}
	
	/**
	 * 
	 * <br>功能说明：根据名称获取session
	 * <br>--------------------------------------
	 * <br>修     改：
	 * <br>修改者：
	 * <br>时    间:
	 * <br>--------------------------------------
	 * @param request
	 * @param name
	 * @return
	 */
	public static Object getSession(HttpServletRequest request,String name){ 
		return request.getSession().getAttribute(name);
	}
}
