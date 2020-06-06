package com.formice.mars.web.dao;

import com.formice.mars.web.model.dto.ToolDto;
import com.formice.mars.web.model.dto.ToolPageDto;
import com.formice.mars.web.model.entity.Tool;

import java.util.List;

public interface ToolDao {
    int deleteByPrimaryKey(Long id);

    int insert(Tool record);

    int insertSelective(Tool record);

    Tool selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Tool record);

    int updateByPrimaryKey(Tool record);

    List<Tool> queryEntityWithPage(ToolPageDto dto);

    Integer queryEntityWithPageCount(ToolPageDto dto);

    List<Tool> queryList(ToolDto toolDto);
}