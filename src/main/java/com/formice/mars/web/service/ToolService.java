package com.formice.mars.web.service;


import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.PageResponse;
import com.formice.mars.web.dao.*;
import com.formice.mars.web.model.dto.ToolDto;
import com.formice.mars.web.model.dto.ToolInputAndOutputPageDto;
import com.formice.mars.web.model.dto.ToolPageDto;
import com.formice.mars.web.model.dto.ToolParameterPageDto;
import com.formice.mars.web.model.entity.*;
import com.formice.mars.web.model.vo.InputAndOutputVo;
import com.formice.mars.web.model.vo.ToolPageListVo;
import com.formice.mars.web.model.vo.ToolParameterVo;
import com.formice.mars.web.tool.DateUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ToolService {
    @Autowired
    private ToolDao toolDao;
    @Autowired
    private ToolInputAndOutputDao toolInputAndOutputDao;
    @Autowired
    private ToolParameterDao toolParameterDao;
    @Autowired
    private DicService dicService;
    @Autowired
    private TaskRunDao taskRunDao;
    @Autowired
    private ToolTemplateDao toolTemplateDao;
    @Autowired
    private FlowNodeParamDao flowNodeParamDao;
    @Autowired
    private PanService panService;
    @Autowired
    private TaskService taskService;


    public Long addTool(Tool tool){
        toolDao.insertSelective(tool);
        return tool.getId();
    }

    public Tool getTool(Long id){
        return toolDao.selectByPrimaryKey(id);
    }


    public void addToolInputAndOutput(ToolInputAndOutput toolInputAndOutput){
        toolInputAndOutputDao.insertSelective(toolInputAndOutput);
    }

    public void addToolParameter(ToolParameter toolParameter){
        toolParameterDao.insertSelective(toolParameter);
    }

    public void addToolTemplate(ToolTemplate toolTemplate){
        toolTemplateDao.insertSelective(toolTemplate);
    }

    /*public List<ToolInputAndOutput> listInputAndOutput(Long toolId, Integer cate){
        ToolInputAndOutput entity = new ToolInputAndOutput();
        entity.setToolId(toolId);
        entity.setCate(cate);
        return toolInputAndOutputDao.selectByEntity(entity);
    }*/

    public PageResponse getInputAndOutputPageList(ToolInputAndOutputPageDto toolInputAndOutputPageDto){
        /*ToolInputAndOutput entity = new ToolInputAndOutput();
        entity.setToolId(toolInputAndOutputPageDto.getToolId());
        entity.setCate(toolInputAndOutputPageDto.getCate());*/

        //PageHelper.startPage(pageNum, pageSize);
        List<InputAndOutputVo> result = Lists.newArrayList();
        List<ToolInputAndOutput> data = toolInputAndOutputDao.queryEntityWithPage(toolInputAndOutputPageDto);
        data.forEach(d->{
            InputAndOutputVo  v = new InputAndOutputVo();
            v.setId(d.getId());
            v.setName(d.getName());
            v.setCate(dicService.queryById(new Long(d.getCate())).getName());
            v.setType(dicService.queryById(new Long(d.getType())).getName());
            v.setMinNum(d.getMinNum());
            v.setMaxNum(d.getMaxNum());
            v.setPrefix(d.getPrefix());
            if(d.getPrefixSplitSymbol() != null) {
                v.setPrefixSplitSymbol(dicService.queryById(new Long(d.getPrefixSplitSymbol())).getName());
            }
            String [] arr = d.getFileFormat().split(",");
            List<String> l = Lists.newArrayList();
            for(String s: arr){
                l.add(dicService.queryById(new Long(s)).getValue());
            }
            v.setFileFormat(Joiner.on(",").join(l));
            //v.setFileFormat(dicService.queryById(new Long(d.getFileFormat())).getName());
            //v.setFileSplitSymbol(dicService.queryById(new Long(d.getFileSplitSymbol())).getName());
            v.setDesc(d.getDesc());

            v.setIsMust(d.getIsMust() == 0 ? "否" : "是");
            result.add(v);
        });
        Integer count = toolInputAndOutputDao.queryEntityWithPageCount(toolInputAndOutputPageDto);

        return PageResponse.createBySuccess(result,count);
    }

    public PageResponse getParameterPageList(ToolParameterPageDto toolParameterPageDto){
        List<ToolParameter> data = toolParameterDao.queryEntityWithPage(toolParameterPageDto);
        Integer count = toolParameterDao.queryEntityWithPageCount(toolParameterPageDto);
        List<ToolParameterVo> result = Lists.newArrayList();

        data.forEach(d->{
            ToolParameterVo v = new ToolParameterVo();
            v.setId(d.getId());
            v.setToolId(d.getToolId());
            v.setName(d.getName());
            v.setType(dicService.queryById(new Long(d.getType())).getName());
            v.setDefaultValue(d.getDefaultValue());
            v.setPrefix(d.getPrefix());
            if(d.getPrefixSplitSymbol() != null) {
                v.setPrefixSplitSymbol(dicService.queryById(new Long(d.getPrefixSplitSymbol())).getName());
            }
            v.setDesc(d.getDesc());
            v.setIsMust(d.getIsMust() == 0 ? "否" : "是");
            v.setIsUseQuotationMarks(d.getIsUseQuotationMarks() == 0? "否" : "是");
            result.add(v);
        });
        return PageResponse.createBySuccess(result,count);
    }

    public PageResponse getBasePageList(ToolPageDto toolPageDto){
        List<ToolPageListVo> vos = Lists.newArrayList();
        List<Tool> data = toolDao.queryEntityWithPage(toolPageDto);
        data.forEach(t ->{
            ToolPageListVo tv = new ToolPageListVo();
            tv.setId(t.getId());
            tv.setUserId(t.getUserId());
            tv.setDesc(t.getDesc());
            tv.setDockerImageUrl(t.getDockerImageUrl());
            tv.setName(t.getName());
            if(t.getCate() != null) {
                tv.setCate(dicService.queryById(new Long(t.getCate())).getName());
            }
            tv.setType(dicService.queryById(new Long(t.getType())).getName());
            tv.setHelp(t.getHelp());
            tv.setWebsite(t.getWebsite());
            tv.setCreateTime(DateUtil.dateToString(t.getCreateTime(),DateUtil.DATE_FORMAT_SECOND));
            vos.add(tv);
        });
        Integer count = toolDao.queryEntityWithPageCount(toolPageDto);
        return PageResponse.createBySuccess(vos,count);
    }

    public List<Tool> getList(ToolDto toolDto){
        return toolDao.queryList(toolDto);
    }

    public List<ToolParameter> getParameters(Long toolId){
        return toolParameterDao.queryList(new ToolParameter(toolId));
    }

    public List<ToolInputAndOutput> getInputOrOutputList(Long toolId,Integer cate){
        return toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,cate));
    }

    //# 建索引
    //bwa index -a bwtsw -p hg19 hg19.fa 1>hg19.bwa_index.log 2>&1
    //-p 输出文件的前缀，例如对hg19.fa建索引，那么输出文件前缀就写hg19
    //-a 选择构建索引的算法
    //taskId1/toolId1/input/{1.fastq,2.fastq,3.fastq}
    //taskId1/toolId1/output/{4.amb,5.ann,6.bwt,7.pac}

    //taskId1/toolId2/input/{4.amb,5.ann}
    //taskId1/toolId2/output/{toolId2.amb,toolId2.ann,toolId2.bwt,toolId2.pac}
    @Deprecated
    public String buildRunCommand(Long toolId){
        StringBuffer sb = new StringBuffer("bwa index");
        //参数
        List<ToolParameter> parameters = toolParameterDao.queryList(new ToolParameter(toolId));
        parameters.forEach(p -> {
            Dic d = dicService.queryByCode(p.getPrefixSplitSymbol());
            sb.append(Constant.ITEM_DEFAULT_SPLIT_SYMBOL);
            sb.append(p.getPrefix()+d.getValue()+"参数值");
        });
        //输入
        List<ToolInputAndOutput> inputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,16));
        inputs.forEach(i -> {
            Dic d = dicService.queryByCode(i.getPrefixSplitSymbol()+"");
            sb.append(Constant.ITEM_DEFAULT_SPLIT_SYMBOL);
            sb.append(i.getPrefix()+d.getValue()+"输入值");
        });
        //输出
        List<ToolInputAndOutput> outputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,17));
        outputs.forEach(o -> {
            Dic d = dicService.queryByCode(o.getPrefixSplitSymbol()+"");
            sb.append(Constant.ITEM_DEFAULT_SPLIT_SYMBOL);
            sb.append(o.getPrefix()+d.getValue()+"输出值");
        });
        return sb.toString();
    }


    //# 建索引
    //bwa index -a bwtsw -p hg19 hg19.fa 1>hg19.bwa_index.log 2>&1
    //-p 输出文件的前缀，例如对hg19.fa建索引，那么输出文件前缀就写hg19
    //-a 选择构建索引的算法
    /*
    bwa mem -t ${param.thread}  ${input.ref}  ${input.input_file1}  ${input.input_file2}  | samtools view -Sb  ->  ${output.output}
    samtools sort -@ ${param.thread} -m ${param.memory} -O  ${param.format} -o ${output.output} ${input.bam} -T ${param.prefix}

    bwa mem ${param.thread}  ${input.ref}  ${input.input_file1}  ${input.input_file2}  | samtools view -Sb  ->  ${output.output}
    samtools sort ${param.thread} ${param.memory} ${param.format} ${output.output} ${input.bam} ${param.prefix}

    bwa mem -t 3   /data/flow1/input/ref.fa   /data/flow1/input/read_1.fastq.gz   /data/flow1/input/read_2.fastq.gz  | samtools view -Sb  ->   /data/flow1/output/sample.bam
    samtools sort -@ 2 -m 5 -O bam -o /data/flow1/output/sample.sorted.bam  /data/flow1/output/sample.bam -T PREFIX.bam
    */
    public String buildRunCommand(FlowNode node,String path,Long taskId) throws Exception {
        String result = null;
        //输入
        Map<String,String> inputMap = new HashMap<String,String>();
        List<ToolInputAndOutput> inputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(node.getToolId(),16));
        inputs.forEach(i -> {
            /*TaskRun t = taskRunDao.queryEntity(new TaskRun(taskId, flowId, toolId, 16, i.getId()));
            if(t != null) {
                Dic d = dicService.queryByCode(i.getPrefixSplitSymbol() + "");

                String file = path+t.getValue();
                if(t.getIsRemote() == 1){
                    file = panService.download(t.getValue(),path);
                }
                //inputMap.put(i.getName(),i.getPrefix() + d.getValue() + file);
                //去掉前缀
                inputMap.put(i.getName(),file);
            }*/
            FlowNodeParam nodep = flowNodeParamDao.queryEntity(new FlowNodeParam(node.getFlowId(),node.getId(),node.getToolId(),16, i.getId()));
            String inputFileName = taskService.getToolOuputFileName(nodep.getFlowId(),taskId,nodep.getRelaNodeId(),nodep.getRelaToolId(),nodep.getRelaBusiId());
            inputMap.put(i.getName(),path+inputFileName);
        });
        //输出
        Map<String,String> outputMap = new HashMap<String,String>();
        List<ToolInputAndOutput> outputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(node.getToolId(),17));
        outputs.forEach(o -> {
            /*TaskRun t = taskRunDao.queryEntity(new TaskRun(taskId, flowId, toolId, 17, o.getId()));
            if(t != null) {
                Dic d = dicService.queryByCode(o.getPrefixSplitSymbol() + "");
                //outputMap.put(o.getName(),o.getPrefix() + d.getValue() + t.getValue());
                //去掉前缀
                outputMap.put(o.getName(),path+t.getValue());
            }*/
            String outputFileName = taskService.getToolOuputFileName(node.getFlowId(),taskId,node.getId(),node.getToolId(),o.getId());
            outputMap.put(o.getName(),path+outputFileName);
        });

        //参数
        Map<String,String> paramMap = new HashMap<String,String>();
        List<ToolParameter> parameters = toolParameterDao.queryList(new ToolParameter(node.getToolId()));
        parameters.forEach(p -> {
            Dic d = dicService.queryByCode(p.getPrefixSplitSymbol());
            //System.out.println("taskId:" + taskId+",flowId="+flowId+",toolId:"+toolId+",pId:"+p.getId());
            //TaskRun t = taskRunDao.queryEntity(new TaskRun(taskId, flowId, toolId, 18, p.getId()));
            FlowNodeParam t = flowNodeParamDao.queryEntity(new FlowNodeParam(node.getFlowId(),node.getId(),node.getToolId(),18,p.getId()));
            //paramMap.put(p.getName(),p.getPrefix()+d.getValue()+t.getValue());
            //去掉前缀
            paramMap.put(p.getName(),t.getValue());
        });


        List<ToolTemplate> ts =  toolTemplateDao.queryList(new ToolTemplate(node.getToolId()));
        if(!CollectionUtils.isEmpty(ts)) {
            //new一个模板资源加载器
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            /* 使用Beetl默认的配置。
                 * Beetl可以使用配置文件的方式去配置，但由于此处是直接上手的例子，
                 * 我们不去管配置的问题，只需要基本的默认配置就可以了。
                 */
            Configuration config = Configuration.defaultConfiguration();
            //Beetl的核心GroupTemplate
            GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, config);
            //我们自定义的模板，其中${title}就Beetl默认的占位符
            String testTemplate=ts.get(0).getContent();
            Template template = groupTemplate.getTemplate(testTemplate);
            //template.binding(templateMap);
            template.binding("input",inputMap);
            template.binding("output",outputMap);
            template.binding("param",paramMap);
            //渲染字符串
            result = template.render();
            //System.out.println("111："+result.replaceAll("&nbsp;"," "));
            //sb.append(str);
        }
        return result;
    }


    /*public String buildRunCommand(Long flowId,Long taskId,Long toolId) throws Exception {
        String result = null;
        //输入
        Map<String,String> inputMap = new HashMap<String,String>();
        List<ToolInputAndOutput> inputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,16));
        inputs.forEach(i -> {
            TaskRun t = taskRunDao.queryEntity(new TaskRun(taskId, flowId, toolId, 16, i.getId()));
            if(t != null) {
                Dic d = dicService.queryByCode(i.getPrefixSplitSymbol() + "");

                String file = t.getValue();
                if(t.getIsRemote() == 1){
                    file = panService.download(t.getValue(),path);
                }
                //inputMap.put(i.getName(),i.getPrefix() + d.getValue() + file);
                //去掉前缀
                inputMap.put(i.getName(),file);
            }
        });
        //输出
        Map<String,String> outputMap = new HashMap<String,String>();
        List<ToolInputAndOutput> outputs = toolInputAndOutputDao.queryList(new ToolInputAndOutput(toolId,17));
        outputs.forEach(o -> {
            TaskRun t = taskRunDao.queryEntity(new TaskRun(taskId, flowId, toolId, 17, o.getId()));
            if(t != null) {
                Dic d = dicService.queryByCode(o.getPrefixSplitSymbol() + "");
                //outputMap.put(o.getName(),o.getPrefix() + d.getValue() + t.getValue());
                //去掉前缀
                outputMap.put(o.getName(),t.getValue());
            }
        });

        //参数
        Map<String,String> paramMap = new HashMap<String,String>();
        List<ToolParameter> parameters = toolParameterDao.queryList(new ToolParameter(toolId));
        parameters.forEach(p -> {
            Dic d = dicService.queryByCode(p.getPrefixSplitSymbol());
            //System.out.println("taskId:" + taskId+",flowId="+flowId+",toolId:"+toolId+",pId:"+p.getId());
            //TaskRun t = taskRunDao.queryEntity(new TaskRun(taskId, flowId, toolId, 18, p.getId()));
            FlowNodeParam t = flowNodeParamDao.queryEntity(new FlowNodeParam(flowId,toolId,p.getId()));
            //paramMap.put(p.getName(),p.getPrefix()+d.getValue()+t.getValue());
            //去掉前缀
            paramMap.put(p.getName(),t.getValue());
        });


        List<ToolTemplate> ts =  toolTemplateDao.queryList(new ToolTemplate(toolId));
        if(!CollectionUtils.isEmpty(ts)) {
            //new一个模板资源加载器
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            *//* 使用Beetl默认的配置。
             * Beetl可以使用配置文件的方式去配置，但由于此处是直接上手的例子，
             * 我们不去管配置的问题，只需要基本的默认配置就可以了。
             *//*
            Configuration config = Configuration.defaultConfiguration();
            //Beetl的核心GroupTemplate
            GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, config);
            //我们自定义的模板，其中${title}就Beetl默认的占位符
            String testTemplate=ts.get(0).getContent();
            Template template = groupTemplate.getTemplate(testTemplate);
            //template.binding(templateMap);
            template.binding("input",inputMap);
            template.binding("output",outputMap);
            template.binding("param",paramMap);
            //渲染字符串
            result = template.render();
            //System.out.println("111："+result.replaceAll("&nbsp;"," "));
            //sb.append(str);
        }
        return result;
    }*/

    public void main(String [] args) throws IOException {
        /*//new一个模板资源加载器
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        *//* 使用Beetl默认的配置。
         * Beetl可以使用配置文件的方式去配置，但由于此处是直接上手的例子，
         * 我们不去管配置的问题，只需要基本的默认配置就可以了。
         *//*
        Configuration config = Configuration.defaultConfiguration();
        //Beetl的核心GroupTemplate
        GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, config);
        //我们自定义的模板，其中${title}就Beetl默认的占位符
        String testTemplate="<html>\n" +
                "<head>\n" +
                "\t<title>${title}</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<h1>${name}</h1>\n" +
                "</body>\n" +
                "</html>";
        Template template = groupTemplate.getTemplate(testTemplate);
        template.binding("title","This is a test template Email.");
        template.binding("name", "beetl");
        //渲染字符串
        String str = template.render();
        System.out.println(str);*/
    }

}
