/**
  * Copyright 2020 bejson.com 
  */
package com.formice.mars.web.model.dto;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-05-08 19:9:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class FlowJsonDto {
    private String name;
    private String desc;
    private List<Line> lines;
    private List<Node> nodes;
    private List<FlowNodeParamDto> params;
    private List<FlowNodeParamDto> relas;

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<FlowNodeParamDto> getParams() {
        return params;
    }

    public void setParams(List<FlowNodeParamDto> params) {
        this.params = params;
    }*/
}


