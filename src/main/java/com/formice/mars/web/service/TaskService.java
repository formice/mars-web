package com.formice.mars.web.service;


import com.alibaba.fastjson.JSON;
import com.formice.mars.web.common.PageResponse;
import com.formice.mars.web.dao.FlowNodeDao;
import com.formice.mars.web.dao.TaskDao;
import com.formice.mars.web.dao.TaskRunDao;
import com.formice.mars.web.model.dto.TaskPageDto;
import com.formice.mars.web.model.dto.TaskRunDto;
import com.formice.mars.web.model.entity.FlowNode;
import com.formice.mars.web.model.entity.Task;
import com.formice.mars.web.model.entity.TaskRun;
import com.formice.mars.web.tool.ShellUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Log4j2
public class TaskService {
    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskRunDao taskRunDao;

    @Autowired
    private FlowNodeDao flowNodeDao;

    @Autowired
    private ToolService toolService;

   public void create(String json){
        TaskRunDto taskRunDto = JSON.parseObject(json, TaskRunDto.class);
        //生成task
        Task t = new Task(taskRunDto.getName());
        taskDao.insertSelective(t);

        /*//生成输入
        taskRunDto.getInput().forEach(i ->{
            taskRunDao.insertSelective(new TaskRun(t.getId(),taskRunDto.getFlowId(),i.getToolId(),16,i.getId(),i.getValue()));
        });

        //生成输出
       taskRunDto.getOutput().forEach(i ->{
           taskRunDao.insertSelective(new TaskRun(t.getId(),taskRunDto.getFlowId(),i.getToolId(),17,i.getId(),i.getValue()));
       });*/

       //生成运行时设置
       taskRunDto.getRun().forEach(i ->{
           taskRunDao.insertSelective(new TaskRun(t.getId(),taskRunDto.getFlowId(),i.getToolId(),i.getType(),i.getId(),i.getValue(),i.getIsRemote()));
       });

       new Thread(()-> {
           //运行工作流
           try {
               start(taskRunDto.getFlowId(),t.getId());
           } catch (Exception e) {
               e.printStackTrace();
               //任务执行异常，状态置：失败，结束时间：当前时间
               taskDao.updateByPrimaryKeySelective(new Task(t.getId(),22,null,new Date()));
           }
       }
       ).start();


    }

    public void start(Long flowId,Long taskId) throws Exception {
        List<FlowNode> nodes =  flowNodeDao.queryList(new FlowNode(flowId));
        log.info("开始运行任务...");
        //开始运行任务，状态置：运行中，开始时间：当前时间
        taskDao.updateByPrimaryKeySelective(new Task(taskId,20,new Date(),null));

        //Stream.iterate(0, i -> i + 1).limit(nodes.size()).forEach(i -> {
        for(int i = 0; i < nodes.size(); i++){
            FlowNode n = nodes.get(i);
            String command = toolService.buildRunCommand(flowId, taskId, n.getBusiId());
            String c = command.replaceAll("&nbsp;"," ");
            log.info("开始运行工具："+c);
            ShellUtils.runShell(c);
            log.info("工具运行完成...");
            //更新task的process
            int process = (i+1)*100/nodes.size();
            taskDao.updateByPrimaryKeySelective(new Task(taskId,process));
        };
        /*nodes.forEach(n ->{
            String command = null;
            try {
                command = toolService.buildRunCommand(flowId,taskId,n.getBusiId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String c = command.replaceAll("&nbsp;"," ");
            log.info("开始运行工具："+c);
            ShellUtils.runShell(c);
            log.info("工具运行完成...");
        });*/
        log.info("任务结束...");
        //任务结束运行，状态置：成功，结束时间：当前时间
        taskDao.updateByPrimaryKeySelective(new Task(taskId,18,null,new Date()));
    }

    public PageResponse getPageList(TaskPageDto dto){
        List<Task> data = taskDao.queryEntityWithPage(dto);
        Integer count = taskDao.queryEntityWithPageCount(dto);
        return PageResponse.createBySuccess(data,count);
    }

}
