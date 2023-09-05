package com.finance.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.backend.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

	List<Expense> findByIncomeId(Integer incomeId);

}
