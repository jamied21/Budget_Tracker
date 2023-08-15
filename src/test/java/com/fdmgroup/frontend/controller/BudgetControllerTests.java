package com.fdmgroup.frontend.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.backend.model.Budget;
import com.finance.backend.service.BudgetServiceImp;

class BudgetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	@MockBean
	BudgetServiceImp budgetService;

	@MockBean
	Budget budget;

	@BeforeEach
	void setUp() {

		budget = new Budget("Food", BigDecimal.valueOf(40.0));
		budget.setId(1);

	}

	@AfterEach
	void tearDown() throws Exception {

		budget = null;
	}

}
