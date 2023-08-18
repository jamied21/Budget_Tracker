package com.finance.backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.finance.backend.model.Customer;
import com.finance.backend.service.CustomerServiceImp;

@WebMvcTest(CustomerController.class)
class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerServiceImp customerService;

	private Customer customer;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	@BeforeEach
	void setUp() {

		customer = new Customer("JD98", "password");
		customer.setId(1);

	}

	@Test
	@DisplayName("Delete customer - positive")
	public void givenCustomerId_whenDeleteCustomerById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(customerService.deleteCustomerByid(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/customers/1")).andDo(print()).andExpect(status().isOk());

		verify(customerService, times(1)).deleteCustomerByid(id);
	}

	@Test
	@DisplayName("Delete customer - negative")
	public void givenNonExistentCustomerId_whenDeleteCustomerById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(customerService.deleteCustomerByid(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/customers/2")).andDo(print()).andExpect(status().isNotFound());

		verify(customerService, times(1)).deleteCustomerByid(id);
	}

	@Test
	@DisplayName("saveCustomer-Positive")
	public void givenCustomerObject_whenSaveCustomer_thenReturnSavedCustomer() throws Exception {
		// given
		given(customerService.SaveCustomer(ArgumentMatchers.any(Customer.class))).willReturn(customer);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customer)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.userName", is(customer.getUserName())));
		
		// @formatter:on

		verify(customerService, times(1)).SaveCustomer(ArgumentMatchers.any(Customer.class));
	}

	@Test
	@DisplayName("saveCustomer-Negative")
	public void givenCustomerObject_whenSaveCustomer_thenReturnError() throws Exception {
		// given
		customer.setUserName(null);
		given(customerService.SaveCustomer(ArgumentMatchers.any(Customer.class))).willReturn(customer);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customer)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(customerService, times(0)).SaveCustomer(ArgumentMatchers.any(Customer.class));
	}

	@AfterEach
	void tearDown() {

		customer = null;
	}

}
