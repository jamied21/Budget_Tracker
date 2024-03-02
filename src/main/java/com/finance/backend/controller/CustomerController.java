package com.finance.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.backend.model.Customer;
import com.finance.backend.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {

		this.customerService = customerService;
	}
	
	@Operation(summary = "Creates a new Customer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Customer resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Customer resource has invalid field(s).") })
	@PostMapping
	public ResponseEntity<?> saveCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.customerService.SaveCustomer(customer), HttpStatus.CREATED);

	}

	@Operation(summary = "Deletes a Customer resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Customer resource successfully deleted.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Customer found for that id.") })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eleteCustomerByID(@PathVariable Integer id) {

		if (this.customerService.deleteCustomerByid(id)) {

			return new ResponseEntity<>(HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Retrieves a Customer resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Customer resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Customer found for that id.") })
	@GetMapping("/{id}")
	public ResponseEntity<?> findCustomerByID(@PathVariable Integer id) {
		Customer customerInDb = this.customerService.findCustomerById(id);

		if (customerInDb == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(customerInDb, HttpStatus.OK);
	}

}
