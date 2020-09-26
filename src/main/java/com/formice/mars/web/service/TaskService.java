package com.formice.mars.web.service;


import com.alibaba.fastjson.JSON;
import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.PageResponse;
import com.formice.mars.web.common.SessionBag;
import com.formice.mars.web.dao.*;
import com.formice.mars.web.model.dto.TaskPageDto;
import com.formice.mars.web.model.dto.TaskRunDto;
import com.formice.mars.web.model.entity.*;
import com.formice.mars.web.model.vo.TaskPageListVo;
import com.formice.mars.web.tool.ShellUtils;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    private ToolInputAndOutputDao toolInputAndOutputDao;

    @Autowired
    private PanService panService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private DicService dicService;

   public void create(String json){
        TaskRunDto taskRunDto = JSON.parseObject(json, TaskRunDto.class);
        Long userId = SessionBag.get(Constant.CURRENT_USER_ID,Long.class);
        //生成task
        Task t = new Task(taskRunDto.getFlowId(),userId,taskRunDto.getName(),userId);
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
           List<ToolInputAndOutput> list = toolInputAndOutputDao.queryList(new ToolInputAndOutput(i.getToolId(),16));
           taskRunDao.insertSelective(new TaskRun(t.getId(),taskRunDto.getFlowId(),i.getNodeId(),i.getToolId(),16,list.get(0).getId(),i.getValue(),i.getIsRemote()));
       });

       new Thread(()-> {
           //运行工作流
           try {
               start(taskRunDto.getFlowId(),t.getId(),userId);
           } catch (Exception e) {
               e.printStackTrace();
               //任务执行异常，状态置：失败，结束时间：当前时间
               taskDao.updateByPrimaryKeySelective(new Task(t.getId(),22,null,new Date(),userId));
           }
       }
       ).start();


    }

    public String getTaskPath(Long flowId,Long taskId){
       //return Constant.TASK_ROOT_PATH+flowId+ File.separator+taskId+File.separator;
        return Constant.TASK_ROOT_PATH+"T_"+flowId+"_"+taskId+File.separator;
    }

    public void start(Long flowId,Long taskId,Long userId) throws Exception {
        List<FlowNode> nodes =  flowNodeDao.queryList(new FlowNode(flowId));
        log.info("开始运行任务...");
        //开始运行任务，状态置：运行中，开始时间：当前时间
        taskDao.updateByPrimaryKeySelective(new Task(taskId,20,new Date(),null,userId));

        //1.初始化task运行的目录
        String path = getTaskPath(flowId,taskId);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //2.过滤出import_data工具，将oss文件下载到本地运行目录
        List<FlowNode> edNodes = nodes.stream().filter(n ->
                Constant.EXPORT_DATA_TOOL.equals(n.getBusiId())).collect(Collectors.toList());
        edNodes.forEach(n ->{
            TaskRun t = taskRunDao.queryEntity(
                    new TaskRun(taskId, flowId,n.getId(), Constant.EXPORT_DATA_TOOL, 16, Constant.EXPORT_DATA_TOOL_INPUT_ITEM_ID));
            if(t != null) {
                panService.download(t.getValue(),path,
                        getToolOuputFileName(flowId,taskId,n.getId(),Constant.EXPORT_DATA_TOOL,Constant.EXPORT_DATA_TOOL_OUTPUT_ITEM_ID/*,t.getValue()*/));
            }
        });
        //3.过滤出其他命令行执行工具，逐个运行
        List<FlowNode> runNodes = nodes.stream().filter(n -> !Constant.EXPORT_DATA_TOOL.equals(n.getBusiId())).collect(Collectors.toList());
        /*runNodes.forEach(n ->{
            TaskRun t = taskRunDao.queryEntity(new TaskRun(taskId, flowId, Constant.EXPORT_DATA_TOOL, 16, Constant.EXPORT_DATA_TOOL_INPUT_ITEM_ID));
            if(t != null) {
                panService.download(t.getValue(),path);
            }
        });*/

        //Stream.iterate(0, i -> i + 1).limit(nodes.size()).forEach(i -> {
        for(int i = 0; i < runNodes.size(); i++){
            FlowNode n = runNodes.get(i);
            //构建工具执行命令
            String command = toolService.buildRunCommand(n,path,taskId);
            command = command.replaceAll("&nbsp;"," ");
            //构建docker命令
            //"docker run -v  /opt/docker/share:/tmp ncbi-blast:2.10.1 "
            Tool t = toolService.getTool(n.getBusiId());
            StringBuffer sb = new StringBuffer();
            sb.append(" docker run -v ");
            sb.append(path);
            sb.append(":");
            sb.append(path);
            //sb.append(" --log-driver syslog ");
            String imageId = t.getDockerImageUrl().split("/")[t.getDockerImageUrl().split("/").length-1];
            sb.append(" "+imageId+" ");
            sb.append(command);
            //sb.append(" > "+path+"run.log 2>&1 ");
            //下载docker镜像
            log.info("开始下载镜像："+t.getDockerImageUrl());
            ShellUtils.runShell("docker pull "+t.getDockerImageUrl());
            log.info("镜像下载完成："+t.getDockerImageUrl());
            //通过docker运行工具
            log.info("开始运行工具："+sb.toString());
            ShellUtils.runShell(sb.toString());
            log.info("工具运行完成...");
            //更新task的process
            //int process = (i+1)*100/runNodes.size();
            int process = (i+1)*100/(runNodes.size()+1);
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


        //3.执行完后，上传结果文件到oss
        FlowNode n = runNodes.get(runNodes.size()-1);
        List<ToolInputAndOutput> outputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(n.getBusiId(),17));
        Customer c = customerDao.queryCustomerById(userId);
        outputs.forEach(o ->{
            //TaskRun t = taskRunDao.queryEntity(new TaskRun(taskId, flowId, n.getBusiId(), 17, o.getId()));
            //String ossFolder = c.getName()+File.separator+"result"+File.separator+flowId+File.separator+taskId+File.separator;
            String ossFolder = getTaskResultPath(userId,taskId);
            panService.createFolder(ossFolder);
            String localFolder = getTaskPath(flowId,taskId);
            //String localFolder = "/opt/webapps/upload/";
            String outputFileName = getToolOuputFileName(n.getFlowId(),taskId,n.getId(),n.getBusiId(),o.getId());
            //String fileName = "SRR3226034_2.fastq.gz";
            //ShellUtils.runShell("chmod 777 "+localFolder+fileName);
            log.info("开始上传文件:"+outputFileName+","+localFolder+"-->"+ossFolder);
            panService.upload(outputFileName,localFolder,ossFolder);
            log.info("结束上传文件:"+outputFileName+","+localFolder+"-->"+ossFolder);
        });

        int process = 100;
        taskDao.updateByPrimaryKeySelective(new Task(taskId,process));

        log.info("任务执行结束...");
        //2.任务结束运行，状态置：成功，结束时间：当前时间
        taskDao.updateByPrimaryKeySelective(new Task(taskId,18,null,new Date(),userId));

    }

    public PageResponse getPageList(TaskPageDto dto){
        List<Task> data = taskDao.queryEntityWithPage(dto);
        Integer count = taskDao.queryEntityWithPageCount(dto);
        List<TaskPageListVo> list = Lists.newArrayList();
        data.forEach(d->{
            list.add(new TaskPageListVo(String.valueOf(d.getId()),
                    String.valueOf(d.getFlowId()),
                    String.valueOf(d.getUserId()),
                    d.getName(),
                    d.getStatus(),
                    d.getProcess(),
                    d.getStartTime(),
                    d.getEndTime()
            ));
        });
        return PageResponse.createBySuccess(list,count);
    }

    //public String

    public String getTaskResultPath(Long userId,Long taskId){
       Task t = taskDao.selectByPrimaryKey(taskId);
       System.out.println("dd:"+taskId+","+t);
       Customer c = customerDao.queryCustomerById(userId);
       //return c.getName()+File.separator+"result"+File.separator+t.getFlowId()+File.separator+taskId+File.separator;
        return c.getName()+File.separator+"task"+File.separator+"T_"+t.getFlowId()+"_"+taskId+File.separator;
    }

    public String getToolOuputFileName(Long flowId,Long taskId,Long nodeId,Long toolId,Long outputId){
       String prefix = flowId+"_"+nodeId+"_"+toolId+"_17_"+outputId;
       String suffix = null;
        ToolInputAndOutput output = toolInputAndOutputDao.selectByPrimaryKey(outputId);
        //如果文件格式是“任意”，文件的后缀采用输入文件的后缀
        //if(Constant.FORMAT_ANYWAY_ID.equals(output.getFileFormat())){
        if(Constant.EXPORT_DATA_TOOL.equals(toolId)){
            TaskRun t = taskRunDao.queryEntity(
                    new TaskRun(taskId, flowId,nodeId, Constant.EXPORT_DATA_TOOL, 16, Constant.EXPORT_DATA_TOOL_INPUT_ITEM_ID));
            String inputFile = t.getValue();
            String fileName = inputFile.split(File.separator)[inputFile.split(File.separator).length - 1];
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            /*if(inputFile != null) {
                String fileName = inputFile.split(File.separator)[inputFile.split(File.separator).length - 1];
                suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            }*/
        }else {
            Dic d = dicService.queryByCode(output.getFileFormat() + "");
            suffix = d.getValue();
        }

        StringBuffer sb = new StringBuffer(prefix);
        if(!StringUtils.isEmpty(suffix)){
            sb.append("."+suffix);
        }
       return sb.toString();
    }

}
