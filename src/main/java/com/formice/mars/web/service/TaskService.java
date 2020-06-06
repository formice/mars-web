package com.formice.mars.web.service;


import com.alibaba.fastjson.JSON;
import com.formice.mars.web.dao.FlowNodeDao;
import com.formice.mars.web.dao.TaskDao;
import com.formice.mars.web.dao.TaskRunDao;
import com.formice.mars.web.model.dto.TaskRunDto;
import com.formice.mars.web.model.entity.FlowNode;
import com.formice.mars.web.model.entity.Task;
import com.formice.mars.web.model.entity.TaskRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
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
           taskRunDao.insertSelective(new TaskRun(t.getId(),taskRunDto.getFlowId(),i.getToolId(),i.getType(),i.getId(),i.getValue()));
       });

       start(taskRunDto.getFlowId(),t.getId());
    }

    public void start(Long flowId,Long taskId) {
        List<FlowNode> nodes =  flowNodeDao.queryList(new FlowNode(flowId));
        System.out.println("开始启动任务...");
        nodes.forEach(n ->{
            String command = null;
            try {
                command = toolService.buildRunCommand(flowId,taskId,n.getBusiId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(command.replaceAll("&nbsp;"," "));
        });
        System.out.println("任务结束...");
    }

}
