package com.finance.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.backend.Repository.ExpenseRepository;
import com.finance.backend.model.Expense;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ExpenseServiceImp implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public Expense SaveExpense(Expense expense) {

		return this.expenseRepository.save(expense);
	}

	@Override
	public Expense findExpenseById(Integer id) {

		return this.expenseRepository.findById(id).orElse(null);
	}

	@Override
	public List<Expense> findAllExpenses() {

		return this.expenseRepository.findAll();
	}

	@Override
	public boolean deleteExpenseByid(Integer id) {
		if (this.expenseRepository.existsById(id)) {
			this.expenseRepository.deleteById(id);

			return true;
		}
		return false;
	}

}
