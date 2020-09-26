package com.formice.mars.web.service;


import com.alibaba.fastjson.JSON;
import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.PageResponse;
import com.formice.mars.web.common.SessionBag;
import com.formice.mars.web.dao.*;
import com.formice.mars.web.model.dto.FlowJsonDto;
import com.formice.mars.web.model.dto.FlowPageDto;
import com.formice.mars.web.model.entity.*;
import com.formice.mars.web.model.vo.FlowPageListVo;
import com.formice.mars.web.tool.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class FlowService {
    @Autowired
    private FlowDao flowDao;
    @Autowired
    private FlowNodeDao flowNodeDao;
    @Autowired
    private FlowLineDao flowLineDao;
    @Autowired
    private ToolDao toolDao;
    @Autowired
    private ToolInputAndOutputDao toolInputAndOutputDao;

    @Autowired
    private ToolParameterDao toolParameterDao;
    @Autowired
    private FlowNodeParamDao flowNodeParamDao;
    @Autowired
    private DicService dicService;



    public void addFlow(String flowJson){
        FlowJsonDto flowJsonDto = JSON.parseObject(flowJson, FlowJsonDto.class);
        Flow f = new Flow(flowJsonDto.getName(),flowJsonDto.getDesc());
        f.setUserId(SessionBag.get(Constant.CURRENT_USER_ID,Long.class));
        //f.setCreateBy(SessionBag.get(Constant.CURRENT_USER_ID,Long.class));
        flowDao.insertSelective(f);
        flowJsonDto.getNodes().forEach(n ->{
            FlowNode node = new FlowNode(n.getNodeId().split("-")[2],f.getId(),Long.valueOf(n.getNodeId().split("-")[1]),n.getAlias());
            flowNodeDao.insertSelective(node);
        });
        flowJsonDto.getLines().forEach(l ->{
            FlowLine line = new FlowLine(f.getId(),Long.valueOf(l.getFromId().split("-")[1]),Long.valueOf(l.getToId().split("-")[1]));
            flowLineDao.insertSelective(line);
        });

        flowJsonDto.getParams().forEach(p ->{
            //uuid 转 nodeId
            FlowNode node = flowNodeDao.queryNodeByUuid(new FlowNode(f.getId(),p.getUuid(),p.getToolId()));
            FlowNodeParam param = new FlowNodeParam(f.getId(),node.getId(),p.getToolId(),18,p.getId(),p.getValue());
            flowNodeParamDao.insertSelective(param);
        });
        flowJsonDto.getRelas().forEach(r ->{
            //uuid 转 nodeId
            FlowNode node = flowNodeDao.queryNodeByUuid(new FlowNode(f.getId(),r.getUuid(),r.getToolId()));
            //relaUuid 转 relaNodeId
            FlowNode relaNode = flowNodeDao.queryNodeByUuid(new FlowNode(f.getId(),r.getRelaUuid(),r.getRelaToolId()));
            if(relaNode != null){
                FlowNodeParam param = new FlowNodeParam(f.getId(),node.getId(),r.getToolId(),16,r.getBusiId(),relaNode.getId(),r.getRelaToolId(),17,r.getRelaBusiId());
                flowNodeParamDao.insertSelective(param);
            }

        });
    }

    public PageResponse getPageList(FlowPageDto flow){
        Long userId = SessionBag.get(Constant.CURRENT_USER_ID,Long.class);
        System.out.println("当前userId:"+userId);
        flow.setUserId(userId);
        List<Flow> data = flowDao.queryEntityWithPage(flow);
        List<FlowPageListVo> list = Lists.newArrayList();
        data.forEach(d->{
            list.add(new FlowPageListVo(String.valueOf(d.getId()),
                    String.valueOf(d.getUserId()),
                    d.getName(),
                    d.getDesc(),
                    dicService.queryById(new Long(d.getCate())).getName(),
                    DateUtil.dateToString(d.getCreateTime(),DateUtil.DATE_FORMAT_SECOND)
                    ));
        });
        Integer count = flowDao.queryEntityWithPageCount(flow);
        return PageResponse.createBySuccess(list,count);
    }

    public List<ToolInputAndOutput> getInputItem(Long flowId,Integer cate){
        FlowNode inputTool = null;
        if(cate == 16) {
            inputTool = flowNodeDao.queryList(new FlowNode(flowId)).get(0);
        }else if(cate == 17){
            List<FlowNode> nodes = flowNodeDao.queryList(new FlowNode(flowId));
            inputTool = nodes.get(nodes.size()-1);
        }
        Tool t = toolDao.selectByPrimaryKey(inputTool.getBusiId());

        System.out.println(t.getId());

        return toolInputAndOutputDao.queryList(new ToolInputAndOutput(t.getId(),cate));
        /*lists.forEach(l -> {
            //输入项名称
            l.getName();
            //文件分隔符
            l.getFileSplitSymbol();
            //文件格式
            l.getFileFormat();
            //输入项 文件的最小个数
            l.getMinNum();
            //输入项 文件的最大个数
            l.getMaxNum();
        });*/

    }

    public Map<Long,List<ToolParameter>> getFlowToolParam(Long flowId){
        Map<Long,List<ToolParameter>> result = new HashMap<>();
        List<FlowNode> nodes = flowNodeDao.queryList(new FlowNode(flowId));
        nodes.forEach(n -> {
            result.put(n.getBusiId(),toolParameterDao.queryList(new ToolParameter(n.getBusiId())));
        });
        return result;
    }


    /*public Map<Long,Map<String,List<Object>>> getToolConfig(Long flowId){
        Map<Long,Map<String,List<Object>>> result = new HashMap<>();
        List<FlowNode> nodes = flowNodeDao.queryList(new FlowNode(flowId));
        nodes.forEach(n -> {
            //result.put(n.getBusiId(),toolParameterDao.queryList(new ToolParameter(n.getBusiId())));
            Long toolId = n.getBusiId();
            Map<String,List<Object>> map = Maps.newHashMap();
            List<ToolInputAndOutput> inputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,16));
            List<ToolInputAndOutput> outputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,17));
            List<ToolParameter> params = toolParameterDao.queryList(new ToolParameter(toolId));
            map.put("inputs",inputs);
            map.put("outputs",outputs);
            map.put("params",params);
            result.put(toolId,map)
        });
        return result;
    }*/

    /*public Map<Long,Map<String,Object>> getToolConfig(Long flowId){
        Map<Long,Map<String,Object>> result = new HashMap<>();
        List<FlowNode> nodes = flowNodeDao.queryList(new FlowNode(flowId));
        nodes.forEach(n -> {
            //result.put(n.getBusiId(),toolParameterDao.queryList(new ToolParameter(n.getBusiId())));
            Long toolId = n.getBusiId();
            Map<String,Object> map = Maps.newHashMap();
            List<ToolInputAndOutput> inputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,16));
            List<ToolInputAndOutput> outputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,17));
            List<ToolParameter> params = toolParameterDao.queryList(new ToolParameter(toolId));
            map.put("inputs",inputs);
            map.put("outputs",outputs);

            map.put("params",params);
            result.put(toolId,map);
        });
        return result;
    }*/
    public Map<String,Map<String,Object>> getToolConfig(Long flowId){
        Map<String,Map<String,Object>> result = new LinkedHashMap<>();
        List<FlowNode> nodes = flowNodeDao.queryList(new FlowNode(flowId));
        nodes.forEach(n -> {
            //result.put(n.getBusiId(),toolParameterDao.queryList(new ToolParameter(n.getBusiId())));
            Long toolId = n.getBusiId();
            Tool t = toolDao.selectByPrimaryKey(toolId);
            String toolName = t.getName();
            Map<String,Object> map = Maps.newLinkedHashMap();
            List<ToolInputAndOutput> inputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,16));
            List<ToolInputAndOutput> outputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,17));
            List<ToolParameter> params = toolParameterDao.queryList(new ToolParameter(toolId));
            map.put("inputs",inputs);
            map.put("outputs",outputs);

            map.put("params",params);
            result.put(toolId+"@*-*@"+toolName,map);
        });
        return result;
    }

    public List<FlowNode> getFlowInputs(Long flowId){
        List<FlowNode> nodes = flowNodeDao.queryList(new FlowNode(flowId));
        //过滤出import_data 节点，作为输入项
        return nodes.stream().filter(n -> n.getBusiId() == 295).collect(Collectors.toList());
    }



}
