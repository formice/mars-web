package com.formice.mars.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.formice.mars.web.common.Response;
import com.formice.mars.web.service.PanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/pan")
@Log4j2
public class PanController {
    @Autowired
    private PanService panService;

    @RequestMapping(method = RequestMethod.GET,value = "/file")
    public Response  getRootDir(String folder){
        log.info("folder:"+folder);
        return Response.createBySuccess(panService.getFile(folder));
    }

    @RequestMapping(method = RequestMethod.POST,value = "/create/folder")
    public Response  createFolder(String folder){
        log.info("folder:"+folder);
        panService.createFolder(folder);
        return Response.createBySuccess();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/file/del",produces = "application/json;charset=UTF-8")
    public Response  delFile(@RequestBody JSONArray jsonParam){
        log.info("json:"+jsonParam.toJSONString());
        List<String> list = JSON.parseArray(jsonParam.toJSONString(), String.class);
        list.forEach(l ->{
            panService.delFile(l);
        });

        return Response.createBySuccess();
    }
}
