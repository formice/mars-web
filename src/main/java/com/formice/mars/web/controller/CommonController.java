package com.formice.mars.web.controller;


import com.formice.mars.web.common.Response;
import com.formice.mars.web.model.entity.Dic;
import com.formice.mars.web.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private DicService dicService;

    @RequestMapping(method = RequestMethod.POST,value = "/dic/group")
    public Response getFlowToolConfig(String group){
        return Response.createBySuccess(dicService.queryList(new Dic(group)));
    }


}
