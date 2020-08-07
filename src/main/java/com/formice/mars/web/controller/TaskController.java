package com.formice.mars.web.controller;


import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.PageResponse;
import com.formice.mars.web.common.SessionBag;
import com.formice.mars.web.model.dto.TaskPageDto;
import com.formice.mars.web.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/task")
@Log4j2
public class TaskController {
    @Autowired
    private TaskService taskService;

    /*@RequestMapping(method = RequestMethod.POST,value = "/create",produces = "application/json;charset=UTF-8")
    public Response create(@RequestBody JSONObject jsonParam){
        System.out.println(jsonParam.toJSONString());
        //flowService.addFlow(jsonParam.toJSONString());
        return Response.createBySuccess();
    }*/

    @RequestMapping(method = RequestMethod.POST,value = "/pagelist")
    public PageResponse getBasePageList(TaskPageDto dto){
        log.info(dto.getPageNum()+","+dto.getPageSize());
        dto.setUserId(SessionBag.get(Constant.CURRENT_USER_ID,Long.class));
        return taskService.getPageList(dto);
    }


}
