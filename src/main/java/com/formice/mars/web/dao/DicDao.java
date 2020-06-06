package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.Dic;

import java.util.List;

public interface DicDao {
    int deleteByPrimaryKey(Long id);

    int insert(Dic record);

    int insertSelective(Dic record);

    Dic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dic record);

    int updateByPrimaryKey(Dic record);

    List<Dic> queryList(Dic entity);

    Dic queryByCode(String code);
}