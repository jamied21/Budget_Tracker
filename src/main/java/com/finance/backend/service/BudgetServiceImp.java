package com.finance.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.backend.Repository.BudgetRepository;
import com.finance.backend.model.Budget;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class BudgetServiceImp implements BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	public BudgetServiceImp(BudgetRepository budgetRepository) {

		this.budgetRepository = budgetRepository;
	}

	@Override
	public Budget saveBudget(Budget budget) {

		return this.budgetRepository.save(budget);
	}

	@Override
	public Budget findBudgetById(Integer id) {

		return this.budgetRepository.findById(id).orElse(null);
	}

	@Override
	public List<Budget> findAllBudgets() {

		return this.budgetRepository.findAll();
	}

	@Override
	public boolean updateBudgetById(Integer id, Budget budget) {
		if (this.budgetRepository.existsById(id)) {
			this.budgetRepository.save(budget);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteBudgetById(Integer id) {
		if (this.budgetRepository.existsById(id)) {
			this.budgetRepository.deleteById(id);

			return true;

		}
		return false;
	}

	public List<Budget> findByIncomeId(Integer incomeId) {
		return this.budgetRepository.findByIncomeId(incomeId);

	}

}
