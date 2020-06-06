package com.formice.mars.web.dao;

import com.formice.mars.web.model.dto.FlowPageDto;
import com.formice.mars.web.model.entity.Flow;

import java.util.List;

public interface FlowDao {
    int deleteByPrimaryKey(Long id);

    int insert(Flow record);

    int insertSelective(Flow record);

    Flow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Flow record);

    int updateByPrimaryKey(Flow record);

    List<Flow> queryEntityWithPage(FlowPageDto entity);

    Integer queryEntityWithPageCount(FlowPageDto entity);
}