package com.formice.mars.web.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskRunDto {
    private Long flowId;
    private String name;

    private List<TaskRunItemDto> input;

    private List<TaskRunItemDto> output;

    private List<TaskRunItemDto> param;

    private List<TaskRunItemDto> run;

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskRunItemDto> getInput() {
        return input;
    }

    public void setInput(List<TaskRunItemDto> input) {
        this.input = input;
    }

    public List<TaskRunItemDto> getOutput() {
        return output;
    }

    public void setOutput(List<TaskRunItemDto> output) {
        this.output = output;
    }

    public List<TaskRunItemDto> getParam() {
        return param;
    }

    public void setParam(List<TaskRunItemDto> param) {
        this.param = param;
    }

    public List<TaskRunItemDto> getRun() {
        return run;
    }

    public void setRun(List<TaskRunItemDto> run) {
        this.run = run;
    }
}
