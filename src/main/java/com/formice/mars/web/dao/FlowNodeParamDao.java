package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.FlowNodeParam;

public interface FlowNodeParamDao {
    int deleteByPrimaryKey(Long id);

    int insert(FlowNodeParam record);

    int insertSelective(FlowNodeParam record);

    FlowNodeParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlowNodeParam record);

    int updateByPrimaryKey(FlowNodeParam record);

    FlowNodeParam queryEntity(FlowNodeParam record);
}