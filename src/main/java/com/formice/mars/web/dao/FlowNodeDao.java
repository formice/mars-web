package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.Flow;
import com.formice.mars.web.model.entity.FlowNode;

import java.util.List;

public interface FlowNodeDao {
    int deleteByPrimaryKey(Long id);

    int insert(FlowNode record);

    int insertSelective(FlowNode record);

    FlowNode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlowNode record);

    int updateByPrimaryKey(FlowNode record);

    List<FlowNode> queryList(FlowNode record);

    FlowNode queryNodeByUuid(FlowNode record);
}