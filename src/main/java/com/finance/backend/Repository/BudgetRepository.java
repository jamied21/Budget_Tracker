package com.finance.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.backend.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

	List<Budget> findByIncomeId(Integer incomeId);

}
