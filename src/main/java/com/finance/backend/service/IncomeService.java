package com.finance.backend.service;

import java.util.List;

import com.finance.backend.model.Income;

public interface IncomeService {

	Income saveIncome(Income income);

	Income findIncomeById(Integer id);

	List<Income> findAllIncomes();

	boolean deleteIncomeById(Integer id);

	boolean updateIncomeById(Integer id, Income income);

}
