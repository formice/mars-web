package com.formice.mars.web.dao;

import com.formice.mars.web.model.dto.ToolParameterPageDto;
import com.formice.mars.web.model.entity.ToolParameter;

import java.util.List;

public interface ToolParameterDao {
    int deleteByPrimaryKey(Long id);

    int insert(ToolParameter record);

    int insertSelective(ToolParameter record);

    ToolParameter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ToolParameter record);

    int updateByPrimaryKey(ToolParameter record);

    List<ToolParameter> queryEntityWithPage(ToolParameterPageDto dto);

    Integer queryEntityWithPageCount(ToolParameterPageDto dto);

    List<ToolParameter> queryList(ToolParameter entity);
}