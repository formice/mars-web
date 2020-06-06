package com.formice.mars.web.dao;

import com.formice.mars.web.model.dto.ToolInputAndOutputPageDto;
import com.formice.mars.web.model.entity.ToolInputAndOutput;

import java.util.List;

public interface ToolInputAndOutputDao {
    int deleteByPrimaryKey(Long id);

    int insert(ToolInputAndOutput record);

    int insertSelective(ToolInputAndOutput record);

    ToolInputAndOutput selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ToolInputAndOutput record);

    int updateByPrimaryKey(ToolInputAndOutput record);

    List<ToolInputAndOutput> queryEntityWithPage(ToolInputAndOutputPageDto dto);

    Integer queryEntityWithPageCount(ToolInputAndOutputPageDto dto);

    List<ToolInputAndOutput> queryList(ToolInputAndOutput entity);
}