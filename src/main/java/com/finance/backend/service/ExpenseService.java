package com.finance.backend.service;

import java.util.List;

import com.finance.backend.model.Expense;

public interface ExpenseService {

	Expense SaveExpense(Expense expense);

	Expense findExpenseById(Integer id);

	List<Expense> findAllExpenses();

	boolean deleteExpenseByid(Integer id);

	boolean updateExpenseById(Integer id, Expense expense);

}
