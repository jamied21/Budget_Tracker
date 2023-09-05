package com.finance.backend.service;

import java.util.List;

import com.finance.backend.model.Expense;

public interface ExpenseService {

	Expense saveExpense(Expense expense);

	Expense findExpenseById(Integer id);

	List<Expense> findAllExpenses();

	boolean deleteExpenseByid(Integer id);

	boolean updateExpenseById(Integer id, Expense expense);

	List<Expense> findExpensesByIncomeId(Integer incomeId);

}
