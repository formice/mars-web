package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.ToolTemplate;

import java.util.List;

public interface ToolTemplateDao {
    int deleteByPrimaryKey(Long id);

    int insert(ToolTemplate record);

    int insertSelective(ToolTemplate record);

    ToolTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ToolTemplate record);

    int updateByPrimaryKey(ToolTemplate record);

    List<ToolTemplate> queryList(ToolTemplate record);
}