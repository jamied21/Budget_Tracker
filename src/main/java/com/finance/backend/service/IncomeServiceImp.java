package com.finance.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finance.backend.Repository.IncomeRepository;
import com.finance.backend.model.Income;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class IncomeServiceImp implements IncomeService {

	private IncomeRepository incomeRepository;

	public IncomeServiceImp(IncomeRepository incomeRepository) {

		this.incomeRepository = incomeRepository;
	}

	@Override
	public Income SaveIncome(Income income) {

		return this.incomeRepository.save(income);
	}

	@Override
	public Income findIncomeById(Integer id) {

		return this.incomeRepository.findById(id).orElse(null);
	}

	@Override
	public List<Income> findAllIncomes() {

		return this.incomeRepository.findAll();
	}

	@Override
	public boolean deleteIncomeByid(Integer id) {
		if (this.incomeRepository.existsById(id)) {
			this.incomeRepository.deleteById(id);
			return true;

		}
		return false;
	}

	@Override
	public boolean updateIncomeById(Integer id, Income income) {
		if (this.incomeRepository.existsById(id)) {
			this.incomeRepository.save(income);
			return true;
		}

		return false;
	}

}
