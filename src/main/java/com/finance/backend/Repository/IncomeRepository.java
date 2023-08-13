package com.finance.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.backend.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

}
