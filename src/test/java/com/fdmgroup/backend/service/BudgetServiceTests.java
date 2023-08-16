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

import com.finance.backend.Repository.BudgetRepository;
import com.finance.backend.model.Budget;
import com.finance.backend.service.BudgetServiceImp;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTests {

	@InjectMocks
	private BudgetServiceImp MockBudgetService;

	@Mock
	private BudgetRepository MockBudgetRepository;
	@Mock
	private Budget budget;

	@BeforeEach
	void setUp() {

		budget = new Budget("Food", BigDecimal.valueOf(40.0));
		budget.setId(1);

	}

	@Test
	@DisplayName("Save Budget")
	void arrangeBudgetObject_actBudget_assertCheckBudgetSaveInDB() {
		
		//arrange
		when(MockBudgetRepository.save(budget)).thenReturn(budget);
		//act
		MockBudgetService.saveBudget(budget);
		
		//assert
		verify(MockBudgetRepository,times(1)).save(budget);

	}

	@Test
	@DisplayName("Find Budget By ID")
	void arrangeBudgetObject_actfindBudget_assertCheckBudgetIsCorrect() {
		Optional<Budget> optionalBudget = Optional.of(budget);
		Integer id = 1;
		// arrange
		when(MockBudgetRepository.findById(id)).thenReturn(optionalBudget);
		// act
		Budget result = MockBudgetService.findBudgetById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(budget.getId());
		assertThat(result.getBudgetName()).isEqualTo(budget.getBudgetName());
		assertThat(result.getBudgetAmount()).isEqualTo(budget.getBudgetAmount());

		verify(MockBudgetRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Budget By ID True")
	void arrangeBudgetObject_actDeleteBudget_assertCheckBudgetIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(MockBudgetRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = MockBudgetService.deleteBudgetById(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(MockBudgetRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Budget By ID False")
	void arrangeBudgetObject_actDeleteBudget_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(MockBudgetRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = MockBudgetService.deleteBudgetById(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(MockBudgetRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Budgets")
	void arrangeBudgetList_actFindBudget_assertGetBudgetList() {

		List<Budget> budgets = new ArrayList<>();
		budgets.add(budget);
		// arrange
		when(MockBudgetRepository.findAll()).thenReturn(budgets);

		// act

		List<Budget> result = MockBudgetService.findAllBudgets();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getBudgetName()).isEqualTo(budget.getBudgetName());
		verify(MockBudgetRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Budget By ID True")
	void arrangeBudgetObject_actUpdateBudget_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(MockBudgetRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = MockBudgetService.updateBudgetById(id, budget);

		// assert
		assertThat(result).isEqualTo(true);
		verify(MockBudgetRepository, times(1)).save(budget);

	}

	@Test
	@DisplayName("Update Budget By ID False")
	void arrangeBudgetObject_actUpdateBudget_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(MockBudgetRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = MockBudgetService.updateBudgetById(id, budget);

		// assert
		assertThat(result).isEqualTo(false);
		verify(MockBudgetRepository, times(0)).save(budget);

	}

	@AfterEach
	void tearDown() throws Exception {

		budget = null;
	}
}
