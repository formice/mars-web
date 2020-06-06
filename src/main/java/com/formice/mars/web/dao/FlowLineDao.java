package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.FlowLine;

public interface FlowLineDao {
    int deleteByPrimaryKey(Long id);

    int insert(FlowLine record);

    int insertSelective(FlowLine record);

    FlowLine selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlowLine record);

    int updateByPrimaryKey(FlowLine record);
}