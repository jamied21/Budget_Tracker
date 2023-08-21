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
import com.finance.backend.model.Expense;
import com.finance.backend.service.ExpenseServiceImp;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExpenseServiceImp expenseService;

	private Expense expense;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	@BeforeEach
	void setUp() {

		expense = new Expense("Food", BigDecimal.valueOf(40.0));
		expense.setId(1);

	}

	@Test
	@DisplayName("FindAllExpenses")
	public void givenNothing_whenFindAllExpense_thenReturnAllSavedExpense() throws Exception {
		List<Expense> expenses = List.of(expense);
		when(expenseService.findAllExpenses()).thenReturn(expenses);

		mockMvc.perform(get("/api/v1/expenses")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].expenseName", is(expense.getExpenseName())));

		verify(expenseService, times(1)).findAllExpenses();

	}

	@Test
	@DisplayName("Delete expense - positive")
	public void givenExpenseId_whenDeleteExpenseById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(expenseService.deleteExpenseByid(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/expenses/1")).andDo(print()).andExpect(status().isOk());

		verify(expenseService, times(1)).deleteExpenseByid(id);
	}

	@Test
	@DisplayName("Delete expense - negative")
	public void givenNonExistentExpenseId_whenDeleteExpenseById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(expenseService.deleteExpenseByid(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/expenses/2")).andDo(print()).andExpect(status().isNotFound());

		verify(expenseService, times(1)).deleteExpenseByid(id);
	}

	@Test
	@DisplayName("findExpenseByID--positive")
	void givenExpenseId_whenFindExpenseById_thenReturnExpenseObjectFromDB() throws Exception {
		// arrange
		given(expenseService.findExpenseById(1)).willReturn(expense);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/expenses/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.expenseName", is(expense.getExpenseName())));

		// @Formatter: on

		verify(expenseService, times(1)).findExpenseById(1);

	}

	@Test
	@DisplayName("findExpenseByID--negative")
	void givenExpenseId_whenFindExpenseById_thenReturnError() throws Exception {
		// arrange
		given(expenseService.findExpenseById(2)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/expenses/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(expenseService, times(1)).findExpenseById(2);

	}

	@Test
	public void testUpdateExpenseById() throws Exception {
	    when(expenseService.updateExpenseById(eq(1), any(Expense.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/expenses/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(expense)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateExpenseByIdNotFound() throws Exception {
	    when(expenseService.updateExpenseById(1, expense)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/expenses/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(expense)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveExpense-Positive")
	public void givenExpenseObject_whenSaveExpense_thenReturnSavedExpense() throws Exception {
		// given
		given(expenseService.saveExpense(ArgumentMatchers.any(Expense.class))).willReturn(expense);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/expenses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expense)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.expenseName", is(expense.getExpenseName())));
		
		// @formatter:on

		verify(expenseService, times(1)).saveExpense(ArgumentMatchers.any(Expense.class));
	}

	@Test
	@DisplayName("saveExpense-Negative")
	public void givenExpenseObject_whenSaveExpense_thenReturnError() throws Exception {
		// given
		expense.setAmount(null);
		given(expenseService.saveExpense(ArgumentMatchers.any(Expense.class))).willReturn(expense);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/expenses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expense)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(expenseService, times(0)).saveExpense(ArgumentMatchers.any(Expense.class));
	}

	@AfterEach
	void tearDown() {

		expense = null;
	}

}
