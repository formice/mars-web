package com.formice.mars.web.controller;


import com.formice.mars.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /*@RequestMapping(method = RequestMethod.POST,value = "/create",produces = "application/json;charset=UTF-8")
    public Response create(@RequestBody JSONObject jsonParam){
        System.out.println(jsonParam.toJSONString());
        //flowService.addFlow(jsonParam.toJSONString());
        return Response.createBySuccess();
    }*/


}
