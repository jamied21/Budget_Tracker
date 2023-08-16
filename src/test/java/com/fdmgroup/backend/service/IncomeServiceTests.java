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

import com.finance.backend.Repository.IncomeRepository;
import com.finance.backend.model.Income;
import com.finance.backend.service.IncomeServiceImp;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTests {

	@InjectMocks
	private IncomeServiceImp MockIncomeService;

	@Mock
	private IncomeRepository MockIncomeRepository;
	@Mock
	private Income income;

	@BeforeEach
	void setUp() {

		income = new Income(2023, "August", BigDecimal.valueOf(20000.0));
		income.setId(1);

	}

	@Test
	@DisplayName("Save Income")
	void arrangeIncomeObject_actIncome_assertCheckIncomeSaveInDB() {
		
		//arrange
		when(MockIncomeRepository.save(income)).thenReturn(income);
		//act
		MockIncomeService.SaveIncome(income);
		
		//assert
		verify(MockIncomeRepository,times(1)).save(income);

	}

	@Test
	@DisplayName("Find Income By ID")
	void arrangeIncomeObject_actfindIncome_assertCheckIncomeIsCorrect() {
		Optional<Income> optionalIncome = Optional.of(income);
		Integer id = 1;
		// arrange
		when(MockIncomeRepository.findById(id)).thenReturn(optionalIncome);
		// act
		Income result = MockIncomeService.findIncomeById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(income.getId());
		assertThat(result.getincomeMonth()).isEqualTo(income.getincomeMonth());
		assertThat(result.getSalary()).isEqualTo(income.getSalary());

		verify(MockIncomeRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Income By ID True")
	void arrangeIncomeObject_actDeleteIncome_assertCheckIncomeIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(MockIncomeRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = MockIncomeService.deleteIncomeByid(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(MockIncomeRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Income By ID False")
	void arrangeIncomeObject_actDeleteIncome_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(MockIncomeRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = MockIncomeService.deleteIncomeByid(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(MockIncomeRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Incomes")
	void arrangeIncomeList_actFindIncome_assertGetIncomeList() {

		List<Income> incomes = new ArrayList<>();
		incomes.add(income);
		// arrange
		when(MockIncomeRepository.findAll()).thenReturn(incomes);

		// act

		List<Income> result = MockIncomeService.findAllIncomes();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getincomeMonth()).isEqualTo(income.getincomeMonth());
		verify(MockIncomeRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Income By ID True")
	void arrangeIncomeObject_actUpdateIncome_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(MockIncomeRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = MockIncomeService.updateIncomeById(id, income);

		// assert
		assertThat(result).isEqualTo(true);
		verify(MockIncomeRepository, times(1)).save(income);

	}

	@Test
	@DisplayName("Update Income By ID False")
	void arrangeIncomeObject_actUpdateIncome_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(MockIncomeRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = MockIncomeService.updateIncomeById(id, income);

		// assert
		assertThat(result).isEqualTo(false);
		verify(MockIncomeRepository, times(0)).save(income);

	}

	@AfterEach
	void tearDown() throws Exception {

		income = null;
	}

}
