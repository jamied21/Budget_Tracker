package com.fdmgroup.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.finance.backend.Repository.ExpenseRepository;
import com.finance.backend.model.Expense;
import com.finance.backend.service.ExpenseServiceImp;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTests {

	@InjectMocks
	private ExpenseServiceImp MockExpenseService;

	@Mock
	private ExpenseRepository MockExpenseRepository;
	@Mock
	private Expense expense;

	@BeforeEach
	void setUp() {

		expense = new Expense("Travel", BigDecimal.valueOf(35.0));
		expense.setId(1);

	}

	@Test
	@DisplayName("Save Expense")
	void arrangeExpenseObject_actExpense_assertCheckExpenseSaveInDB() {
		
		//arrange
		when(MockExpenseRepository.save(expense)).thenReturn(expense);
		//act
		MockExpenseService.saveExpense(expense);
		
		//assert
		verify(MockExpenseRepository,times(1)).save(expense);

	}

	@Test
	@DisplayName("Find Expense By ID")
	void arrangeExpenseObject_actfindExpense_assertCheckExpenseIsCorrect() {
		Optional<Expense> optionalExpense = Optional.of(expense);
		Integer id = 1;
		// arrange
		when(MockExpenseRepository.findById(id)).thenReturn(optionalExpense);
		// act
		Expense result = MockExpenseService.findExpenseById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(expense.getId());
		assertThat(result.getExpenseName()).isEqualTo(expense.getExpenseName());
		assertThat(result.getAmount()).isEqualTo(expense.getAmount());

		verify(MockExpenseRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Expense By ID True")
	void arrangeExpenseObject_actDeleteExpense_assertCheckExpenseIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(MockExpenseRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = MockExpenseService.deleteExpenseByid(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(MockExpenseRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Expense By ID False")
	void arrangeExpenseObject_actDeleteExpense_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(MockExpenseRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = MockExpenseService.deleteExpenseByid(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(MockExpenseRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Expenses")
	void arrangeExpenseList_actFindExpense_assertGetExpenseList() {

		List<Expense> expenses = new ArrayList<>();
		expenses.add(expense);
		// arrange
		when(MockExpenseRepository.findAll()).thenReturn(expenses);

		// act

		List<Expense> result = MockExpenseService.findAllExpenses();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getExpenseName()).isEqualTo(expense.getExpenseName());
		verify(MockExpenseRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Expense By ID True")
	void arrangeExpenseObject_actUpdateExpense_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(MockExpenseRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = MockExpenseService.updateExpenseById(id, expense);

		// assert
		assertThat(result).isEqualTo(true);
		verify(MockExpenseRepository, times(1)).save(expense);

	}

	@Test
	@DisplayName("Update Expense By ID False")
	void arrangeExpenseObject_actUpdateExpense_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(MockExpenseRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = MockExpenseService.updateExpenseById(id, expense);

		// assert
		assertThat(result).isEqualTo(false);
		verify(MockExpenseRepository, times(0)).save(expense);

	}

	@Test
	@DisplayName("Find Expense By Income ID")
	void arrangeExpenseObject_actfindExpemse_assertCheckExpenseIsCorrect() {
		List<Expense> expenses = new ArrayList<>();
		expenses.add(expense);

		/*
		 * Income income = new Income(2023, "August", BigDecimal.valueOf(20000.0));
		 * income.setId(2); budget.setIncome(income);
		 */

		Integer incomeId = 1;
		// arrange
		when(MockExpenseRepository.findByIncomeId(incomeId)).thenReturn(expenses);
		// act
		List<Expense> result = MockExpenseService.findExpensesByIncomeId(incomeId);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getExpenseName()).isEqualTo(expense.getExpenseName());
		verify(MockExpenseRepository, times(1)).findByIncomeId(incomeId);
	}

	@AfterEach
	void tearDown() throws Exception {

		expense = null;
	}

}
