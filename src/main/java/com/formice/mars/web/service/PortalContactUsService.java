package com.formice.mars.web.service;


import com.formice.mars.web.dao.PortalContactUsDao;
import com.formice.mars.web.model.entity.PortalContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PortalContactUsService {
    @Autowired
    private PortalContactUsDao portalContactUsDao;

    public void addContactUs(PortalContactUs entity){
        portalContactUsDao.insertSelective(entity);
    }
}
