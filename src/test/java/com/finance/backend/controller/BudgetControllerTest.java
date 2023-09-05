package com.finance.backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.backend.model.Budget;
import com.finance.backend.model.Income;
import com.finance.backend.service.BudgetServiceImp;

@WebMvcTest(BudgetController.class)
class BudgetControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BudgetServiceImp budgetService;

	private Budget budget;

	private Income income;
	// private Expense expense1;
	// private Expense expense2;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	@BeforeEach
	void setUp() {
//		expense1 = new Expense("Beer", BigDecimal.valueOf(40.0));
//		expense1 = new Expense("Train", BigDecimal.valueOf(10.0));
//		List<Expense> expenses = new ArrayList<>();
//		expenses.add(expense1);
//		expenses.add(expense2);
		budget = new Budget("Food", BigDecimal.valueOf(40.0));
		budget.setId(1);

	}

	@Test
	@DisplayName("FindAllBudgets")
	public void givenNothing_whenFindAllBudget_thenReturnAllSavedBudget() throws Exception {
		List<Budget> budgets = List.of(budget);
		when(budgetService.findAllBudgets()).thenReturn(budgets);

		mockMvc.perform(get("/api/v1/budgets")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].budgetName", is(budget.getBudgetName())));

		verify(budgetService, times(1)).findAllBudgets();

	}

	@Test
	@DisplayName("Delete budget - positive")
	public void givenBudgetId_whenDeleteBudgetById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(budgetService.deleteBudgetById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/budgets/1")).andDo(print()).andExpect(status().isOk());

		verify(budgetService, times(1)).deleteBudgetById(id);
	}

	@Test
	@DisplayName("Delete budget - negative")
	public void givenNonExistentBudgetId_whenDeleteBudgetById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(budgetService.deleteBudgetById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/budgets/2")).andDo(print()).andExpect(status().isNotFound());

		verify(budgetService, times(1)).deleteBudgetById(id);

	}

	@Test
	@DisplayName("findBudgetByID--positive")
	void givenBudgetId_whenFindBudgetById_thenReturnBudgetObjectFromDB() throws Exception {
		// arrange
		given(budgetService.findBudgetById(1)).willReturn(budget);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/budgets/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.budgetName", is(budget.getBudgetName())));

		// @Formatter: on

		verify(budgetService, times(1)).findBudgetById(1);

	}

	@Test
	@DisplayName("findBudgetByID--negative")
	void givenBudgetId_whenFindBudgetById_thenReturnError() throws Exception {
		// arrange
		given(budgetService.findBudgetById(2)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/budgets/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(budgetService, times(1)).findBudgetById(2);

	}

	@Test
	public void testUpdateBudgetById() throws Exception {
	    when(budgetService.updateBudgetById(eq(1), any(Budget.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/budgets/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(budget)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateBudgetByIdNotFound() throws Exception {
	    when(budgetService.updateBudgetById(1, budget)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/budgets/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(budget)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveBudget-Positive")
	public void givenBudgetObject_whenSaveBudget_thenReturnSavedBudget() throws Exception {
		// given
		given(budgetService.saveBudget(ArgumentMatchers.any(Budget.class))).willReturn(budget);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/budgets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(budget)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.budgetName", is(budget.getBudgetName())));
		
		// @formatter:on

		verify(budgetService, times(1)).saveBudget(ArgumentMatchers.any(Budget.class));
	}

	@Test
	@DisplayName("saveBudget-Negative")
	public void givenBudgetObject_whenSaveBudget_thenReturnError() throws Exception {
		// given
		budget.setBudgetAmount(null);
		given(budgetService.saveBudget(ArgumentMatchers.any(Budget.class))).willReturn(budget);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/budgets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(budget)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(budgetService, times(0)).saveBudget(ArgumentMatchers.any(Budget.class));
	}

	@Test
	@DisplayName("Find Budgets by Income Id")
	public void givenIncomeId_whenFindBudgets_thenReturnAllBudgets() throws Exception {

		Income income = new Income(2023, "August", BigDecimal.valueOf(20000.0));
		income.setId(2);
		budget.setIncome(income);

		List<Budget> budgets = List.of(budget);

		Integer incomeId = 2;
		when(budgetService.findByIncomeId(incomeId)).thenReturn(budgets);

		mockMvc.perform(get("/api/v1/budgets/incomes/{incomeId}", incomeId)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].budgetName", is(budget.getBudgetName())));

		verify(budgetService, times(1)).findByIncomeId(incomeId);

	}

	@AfterEach
	void tearDown() {

		budget = null;
		income = null;
	}
}
