package com.formice.mars.web.dao;

import com.formice.mars.web.model.entity.PortalContactUs;

public interface PortalContactUsDao {

    int insertSelective(PortalContactUs record);

    PortalContactUs selectByPrimaryKey(Long id);
}