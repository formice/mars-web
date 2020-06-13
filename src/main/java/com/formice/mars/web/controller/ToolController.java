package com.formice.mars.web.controller;


import com.formice.mars.web.common.PageResponse;
import com.formice.mars.web.common.Response;
import com.formice.mars.web.model.dto.ToolDto;
import com.formice.mars.web.model.dto.ToolInputAndOutputPageDto;
import com.formice.mars.web.model.dto.ToolPageDto;
import com.formice.mars.web.model.dto.ToolParameterPageDto;
import com.formice.mars.web.model.entity.Tool;
import com.formice.mars.web.model.entity.ToolInputAndOutput;
import com.formice.mars.web.model.entity.ToolParameter;
import com.formice.mars.web.model.entity.ToolTemplate;
import com.formice.mars.web.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/tool")
public class ToolController {
    @Autowired
    private ToolService toolService;

    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public Response add(Tool tool){
        return Response.createBySuccess(toolService.addTool(tool));
    }

    @RequestMapping(method = RequestMethod.POST,value = "/addInputAndOutput")
    public Response addInputAndOutput(ToolInputAndOutput toolInputAndOutput){
        System.out.println(toolInputAndOutput);
        toolService.addToolInputAndOutput(toolInputAndOutput);
        return Response.createBySuccess();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/addParameter")
    public Response addToolParameter(ToolParameter toolParameter){
        System.out.println(toolParameter);
        toolService.addToolParameter(toolParameter);
        return Response.createBySuccess();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/template/add")
    public Response addToolParameter(ToolTemplate toolTemplate){
        System.out.println(toolTemplate);
        toolService.addToolTemplate(toolTemplate);

        return Response.createBySuccess();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/inputAndOutput/pagelist")
    public PageResponse getInputAndOutputPageList(ToolInputAndOutputPageDto toolInputAndOutputPageDto){
        System.out.println(toolInputAndOutputPageDto);
        return toolService.getInputAndOutputPageList(toolInputAndOutputPageDto);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/parameter/pagelist")
    public PageResponse getParameterPageList(ToolParameterPageDto toolParameterPageDto){
        System.out.println(toolParameterPageDto);
        return toolService.getParameterPageList(toolParameterPageDto);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/base/pagelist")
    public PageResponse getBasePageList(ToolPageDto toolPageDto){
        System.out.println(toolPageDto);
        return toolService.getBasePageList(toolPageDto);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/buildRunCommand")
    public String getBuildRunCommand(Long taskId,Long flowId,Long toolId) throws Exception {
        return toolService.buildRunCommand(flowId,taskId,toolId);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/list")
    public Response getList(ToolDto toolDto){
        System.out.println(toolDto);
        return Response.createBySuccess(toolService.getList(toolDto));
    }

    @RequestMapping(method = RequestMethod.POST,value = "/parameter")
    public Response getParameters(Long toolId) throws Exception {
        return Response.createBySuccess(toolService.getParameters(toolId));
    }

    /**
     @RequestMapping(method = RequestMethod.GET,value = "/delete/{id}")
     public void delete(@PathVariable("id")int id){
     userService.delete(id);
     }

     @RequestMapping(method = RequestMethod.POST,value = "/insert")
     public void insert(User user){
     userService.insert(user);
     }
     @RequestMapping(method = RequestMethod.POST,value = "/update/{id}")
     public void update(@RequestParam User user){
     userService.update(user);
     }

     @RequestMapping(method = RequestMethod.GET,value = "/{id}/select")
     public User select(@PathVariable("id")int id){
     return userService.selectById(id);
     }

     @RequestMapping(method = RequestMethod.GET,value = "/selectAll/{pageNum}/{pageSize}")
     public List<User> selectAll(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
     return userService.selectAll(pageNum,pageSize);
     }
     */
}
