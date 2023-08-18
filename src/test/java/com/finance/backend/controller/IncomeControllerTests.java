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
import com.finance.backend.model.Income;
import com.finance.backend.service.IncomeServiceImp;

@WebMvcTest(IncomeController.class)
class IncomeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IncomeServiceImp incomeService;

	private Income income;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	@BeforeEach
	void setUp() {

		income = new Income(2022, "August", BigDecimal.valueOf(90.99));
		income.setId(1);

	}

	@Test
	@DisplayName("FindAllIncomes")
	public void givenNothing_whenFindAllIncome_thenReturnAllSavedIncome() throws Exception {
		List<Income> incomes = List.of(income);
		when(incomeService.findAllIncomes()).thenReturn(incomes);

		mockMvc.perform(get("/api/v1/incomes")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].incomeMonth", is(income.getIncomeMonth())));

		verify(incomeService, times(1)).findAllIncomes();

	}

	@Test
	@DisplayName("Delete income - positive")
	public void givenIncomeId_whenDeleteIncomeById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(incomeService.deleteIncomeById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/incomes/1")).andDo(print()).andExpect(status().isOk());

		verify(incomeService, times(1)).deleteIncomeById(id);
	}

	@Test
	@DisplayName("Delete income - negative")
	public void givenNonExistentIncomeId_whenDeleteIncomeById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(incomeService.deleteIncomeById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/incomes/2")).andDo(print()).andExpect(status().isNotFound());

		verify(incomeService, times(1)).deleteIncomeById(id);
	}

	@Test
	public void testUpdateIncomeById() throws Exception {
	    when(incomeService.updateIncomeById(eq(1), any(Income.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/incomes/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(income)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateIncomeByIdNotFound() throws Exception {
	    when(incomeService.updateIncomeById(1, income)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/incomes/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(income)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveIncome-Positive")
	public void givenIncomeObject_whenSaveIncome_thenReturnSavedIncome() throws Exception {
		// given
		given(incomeService.saveIncome(ArgumentMatchers.any(Income.class))).willReturn(income);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/incomes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(income)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.incomeMonth", is(income.getIncomeMonth())));
		
		// @formatter:on

		verify(incomeService, times(1)).saveIncome(ArgumentMatchers.any(Income.class));
	}

	@Test
	@DisplayName("saveIncome-Negative")
	public void givenIncomeObject_whenSaveIncome_thenReturnError() throws Exception {
		// given
		income.setSalary(null);
		given(incomeService.saveIncome(ArgumentMatchers.any(Income.class))).willReturn(income);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/incomes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(income)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(incomeService, times(0)).saveIncome(ArgumentMatchers.any(Income.class));
	}

	@AfterEach
	void tearDown() {

		income = null;
	}

}
