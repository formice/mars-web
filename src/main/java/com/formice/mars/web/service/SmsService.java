package com.formice.mars.web.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.formice.mars.web.dao.SmsDao;
import com.formice.mars.web.model.entity.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class SmsService {
    @Autowired
    private SmsDao smsDao;

    public void sendCode(String mobile){
        sendCode(mobile,"ABC商城","SMS_197885668",String.valueOf((new Random()).nextInt(9999)));
    }

    public void sendCode(String mobile,String signName,String templateCode,String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G4g2NakPVnzmbyqX5oz", "7yvXNgVNpypKZiQF5ip4r1t3jiwwP4");

        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName",signName);//ABC商城
        request.putQueryParameter("TemplateCode",templateCode);//SMS_197885668
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            if(response.getData() != null && response.getData().contains("\"Code\":\"OK\"")) {
                saveSms(mobile,templateCode,code);
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    private void saveSms(String mobile,String templateCode,String code){
        // 发送成功之后也插入到数据库
        Sms sms = new Sms();
        sms.setContent(templateCode);
        Date curDate = new Date();
        if(code!=null){
            sms.setCode(code);
            sms.setExpireTime(curDate.getTime()+1*60*1000);
            sms.setExpireDis(1*60*1000L);
        }
        sms.setMobile(mobile);

        sms.setCreateTime(curDate);
        sms.setCreateBy(-1l);
        smsDao.saveSms(sms);
    }
}
