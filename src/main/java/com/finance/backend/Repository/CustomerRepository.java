package com.finance.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.backend.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
