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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.backend.model.Income;
import com.finance.backend.service.IncomeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/incomes")
@CrossOrigin(origins = "http://localhost:3000")
public class IncomeController {

	private IncomeService incomeService;

	public IncomeController(IncomeService incomeService) {

		this.incomeService = incomeService;
	}
	
	@Operation(summary = "Creates a new Income")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Income resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Income resource has invalid field(s).") })

	@PostMapping
	public ResponseEntity<?> saveIncome(@Valid @RequestBody Income income, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.incomeService.saveIncome(income), HttpStatus.CREATED);

	}
	
	@Operation(summary = "Updates new Income")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Income resource successfully updated and returned.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Income found for that id.") })

	@PutMapping("/{id}")
	public ResponseEntity<?> updateIncomeByID(@PathVariable Integer id, @RequestBody Income income) {

		if (incomeService.updateIncomeById(id, income)) {

			return ResponseEntity.ok(income);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}
	
	@Operation(summary = "Deletes an Income resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Income resource successfully deleted.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Income found for that id.") })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIncomeByID(@PathVariable Integer id) {

		if (this.incomeService.deleteIncomeById(id)) {

			return new ResponseEntity<>(HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Retrieves an Income resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Income resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Income found for that id.") })

	@GetMapping("/{id}")
	public ResponseEntity<?> findIncomeByID(@PathVariable Integer id) {
		Income incomeInDb = this.incomeService.findIncomeById(id);

		if (incomeInDb == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(incomeInDb, HttpStatus.OK);
	}

	@Operation(summary = "Retrieves Income resource(s) from the database. ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Income resource(s) successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })


	@GetMapping
	public ResponseEntity<?> findAllIncome() {
		return new ResponseEntity<>(this.incomeService.findAllIncomes(), HttpStatus.OK);
	}

}
