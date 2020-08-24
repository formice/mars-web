package com.formice.mars.web.service;

import com.formice.mars.web.dao.CustomerDao;
import com.formice.mars.web.model.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private CustomerDao customerDao;

    public Customer getUser(Long userId){
        return customerDao.queryCustomerById(userId);
    }


}
