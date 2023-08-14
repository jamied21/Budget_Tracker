package com.fdmgroup.frontend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.finance.backend.Repository.CustomerRepository;
import com.finance.backend.model.Customer;
import com.finance.backend.service.CustomerServiceImp;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTests {

	@InjectMocks
	private CustomerServiceImp MockCustomerService;

	@Mock
	private CustomerRepository MockCustomerRepository;

	private Customer customer;

	@BeforeEach
	void setUp() {

		customer = new Customer("JD98", "password");
		customer.setId(1);

	}

	@Test
	@DisplayName("Save Customer")
	void arrangeCustomerObject_actCustomer_assertCheckCustomerSaveInDB() {
		
		//arrange
		when(MockCustomerRepository.save(customer)).thenReturn(customer);
		//act
		MockCustomerService.SaveCustomer(customer);
		
		//assert
		verify(MockCustomerRepository,times(1)).save(customer);

	}

	@Test
	@DisplayName("Find Customer By ID")
	void arrangeCustomerObject_actfindCustomer_assertCheckCustomerIsCorrect() {
		Optional<Customer> optionalCustomer = Optional.of(customer);
		Integer id = 1;
		// arrange
		when(MockCustomerRepository.findById(id)).thenReturn(optionalCustomer);
		// act
		Customer result = MockCustomerService.findCustomerById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(customer.getId());
		assertThat(result.getUserName()).isEqualTo(customer.getUserName());
		assertThat(result.getPassword()).isEqualTo(customer.getPassword());

		verify(MockCustomerRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Customer By ID True")
	void arrangeCustomerObject_actDeleteCustomer_assertCheckCustomerIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(MockCustomerRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = MockCustomerService.deleteCustomerByid(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(MockCustomerRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Customer By ID False")
	void arrangeCustomerObject_actDeleteCustomer_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(MockCustomerRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = MockCustomerService.deleteCustomerByid(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(MockCustomerRepository, times(0)).deleteById(id);

	}

	@AfterEach
	void tearDown() throws Exception {

		customer = null;
	}

}
