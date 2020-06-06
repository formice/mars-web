package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.TaskRun;

public interface TaskRunDao {
    int deleteByPrimaryKey(Long id);

    int insert(TaskRun record);

    int insertSelective(TaskRun record);

    TaskRun selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskRun record);

    int updateByPrimaryKey(TaskRun record);

    TaskRun queryEntity(TaskRun record);
}