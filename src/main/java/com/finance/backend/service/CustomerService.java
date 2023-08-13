package com.finance.backend.service;

import com.finance.backend.model.Customer;

public interface CustomerService {

	Customer SaveCustomer(Customer customer);

	Customer findCustomerById(Integer id);

	boolean deleteCustomerByid(Integer id);

}
