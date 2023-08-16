package com.finance.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.backend.Repository.CustomerRepository;
import com.finance.backend.model.Customer;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CustomerServiceImp implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public CustomerServiceImp(CustomerRepository customerRepository) {

		this.customerRepository = customerRepository;
	}

	@Override
	public Customer SaveCustomer(Customer customer) {

		return this.customerRepository.save(customer);
	}

	@Override
	public Customer findCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return this.customerRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteCustomerByid(Integer id) {

		if (this.customerRepository.existsById(id)) {
			this.customerRepository.deleteById(id);
			return true;
		}

		return false;

	}

}
