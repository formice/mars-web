package com.formice.mars.web.dao;


import com.formice.mars.web.model.entity.Customer;

import java.util.List;

public interface CustomerDao {
	public Customer queryCustomerById(Long id);
	
	public Customer queryCustomerByMobile(String mobile);
	
	public List<Customer> queryCustomerByIds(List<Long> ids);
	
	public int updateCustomerIcon(Customer customerRpc);
	
	public int saveCustomer(Customer customer);
}
