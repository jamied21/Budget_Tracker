package com.finance.backend.service;

import java.util.List;

import com.finance.backend.model.Budget;

public interface BudgetService {

	Budget saveBudget(Budget budget);

	Budget findBudgetById(Integer id);

	List<Budget> findAllBudgets();

	boolean updateBudgetById(Integer id, Budget budget);

	boolean deleteBudgetById(Integer id);

	List<Budget> findByIncomeId(Integer incomeId);

}
