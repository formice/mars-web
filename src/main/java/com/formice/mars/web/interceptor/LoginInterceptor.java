package com.formice.mars.web.interceptor;

import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.SessionBag;
import com.formice.mars.web.model.entity.UserTicket;
import com.formice.mars.web.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserTicketService userTicketService;


    //请求处理之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        //System.out.println("请求者ip:"+ RequestUtils.getIpAddr(request)+" url:"+request.getRequestURI()+"?"+RequestUtils.getQueryUrl(request));
        String ticket =  request.getHeader("ticket");
        System.out.println("ticket:"+ticket);

        UserTicket ut = userTicketService.queryUserTicketByTicket(ticket);
        boolean isEffectiveTicket = userTicketService.validateTicket(ut,ticket);
        //System.out.println(isEffectiveTicket);
        if (isEffectiveTicket) {
            //设置当前userId
            SessionBag.set(Constant.CURRENT_USER_ID, ut.getUserId());
            return true;
        } else {
            response.sendRedirect("/uc/login/redirect");
            return false;
        }
    }

}

