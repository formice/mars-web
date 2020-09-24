package com.formice.mars.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.formice.mars.web.common.PageResponse;
import com.formice.mars.web.common.Response;
import com.formice.mars.web.model.dto.FlowPageDto;
import com.formice.mars.web.model.entity.FlowNode;
import com.formice.mars.web.service.FlowService;
import com.formice.mars.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/flow")
public class FlowController {
    @Autowired
    private FlowService flowService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.POST,value = "/add",produces = "application/json;charset=UTF-8")
    public Response add(@RequestBody JSONObject jsonParam){
        System.out.println(jsonParam.toJSONString());
        flowService.addFlow(jsonParam.toJSONString());
        return Response.createBySuccess();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/pagelist")
    public PageResponse getBasePageList(FlowPageDto flow){
        System.out.println(flow);
        return flowService.getPageList(flow);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/tool/inputAndOutput")
    public Response getInputItem(Long flowId,Integer cate){
        return Response.createBySuccess(flowService.getInputItem(flowId,cate));
    }

    @RequestMapping(method = RequestMethod.POST,value = "/tool/param")
    public Response getFlowToolParams(Long flowId){
        return Response.createBySuccess(flowService.getFlowToolParam(flowId));
    }

    @RequestMapping(method = RequestMethod.POST,value = "/tool/config")
    public Response getFlowToolConfig(Long flowId){
        return Response.createBySuccess(flowService.getToolConfig(flowId));
    }

    @RequestMapping(method = RequestMethod.POST,value = "/run",produces = "application/json;charset=UTF-8")
    public Response run(@RequestBody JSONObject jsonParam){
        System.out.println(jsonParam.toJSONString());
        taskService.create(jsonParam.toJSONString());
        return Response.createBySuccess();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/{id}/inputs")
    public Response getFlowInputs(@PathVariable("id")Long id){
        return Response.createBySuccess(flowService.getFlowInputs(id));
    }
}
