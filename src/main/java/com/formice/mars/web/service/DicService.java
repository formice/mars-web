package com.formice.mars.web.service;


import com.formice.mars.web.dao.DicDao;
import com.formice.mars.web.model.entity.Dic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DicService {
    @Autowired
    private DicDao dicDao;

    public List<Dic> queryList(Dic dic){
        return dicDao.queryList(dic);
    }

    public Dic queryById(Long id){
        return dicDao.selectByPrimaryKey(id);
    }

    public Dic queryByCode(String code){
        return dicDao.queryByCode(code);
    }
}
