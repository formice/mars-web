package com.formice.mars.web.controller;


import com.formice.mars.web.common.Response;
import com.formice.mars.web.service.SmsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/sms")
@Log4j2
public class SmsController {
    @Autowired
    private SmsService smsService;

    @RequestMapping(method = RequestMethod.POST,value = "/send/code")
    public Response sendCode(String mobile){
        smsService.sendCode(mobile);
        return  Response.createBySuccess();
    }


}
