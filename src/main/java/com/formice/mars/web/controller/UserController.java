package com.formice.mars.web.controller;


import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.Response;
import com.formice.mars.web.common.SessionBag;
import com.formice.mars.web.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET,value = "/info")
    public Response getUser(){
        Long userId = SessionBag.get(Constant.CURRENT_USER_ID,Long.class);
        return  Response.createBySuccess(userService.getUser(userId));
    }


}
