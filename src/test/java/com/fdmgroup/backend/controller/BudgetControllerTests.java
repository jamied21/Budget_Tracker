package com.fdmgroup.backend.controller;

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
import com.finance.backend.service.BudgetServiceImp;

@WebMvcTest
class BudgetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BudgetServiceImp budgetService;

	private Budget budget;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	@BeforeEach
	void setUp() {

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
		given(budgetService.saveBudget(ArgumentMatchers.any(Budget.class)))
				.willAnswer(invocation -> invocation.getArgument(0));
		;

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/budgets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(budget)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.budgetName", is(budget.getBudgetName())))
				.andExpect(jsonPath("$.budgetAmount", is(budget.getBudgetAmount())));
		
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

	@AfterEach
	void tearDown() {

		budget = null;
	}

}