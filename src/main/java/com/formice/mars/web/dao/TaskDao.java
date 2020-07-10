package com.formice.mars.web.dao;

import com.formice.mars.web.model.dto.TaskPageDto;
import com.formice.mars.web.model.entity.Task;

import java.util.List;

public interface TaskDao {
    int deleteByPrimaryKey(Long id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    List<Task> queryEntityWithPage(TaskPageDto entity);

    Integer queryEntityWithPageCount(TaskPageDto entity);
}