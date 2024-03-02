package com.finance.backend.controller;

import java.util.HashMap;
import java.util.List;
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

import com.finance.backend.model.Budget;
import com.finance.backend.service.BudgetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/budgets")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {

	private BudgetService budgetService;

	public BudgetController(BudgetService budgetService) {

		this.budgetService = budgetService;
	}
	@Operation(summary = "Creates a new Budget")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Budget resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Budget resource has invalid field(s).") })
	@PostMapping
	public ResponseEntity<?> saveBudget(@Valid @RequestBody Budget budget, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.budgetService.saveBudget(budget), HttpStatus.CREATED);

	}
	@Operation(summary = "Updates new Budget")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Budget resource successfully updated and returned.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Budget found for that id.") })

	@PutMapping("/{id}")
	public ResponseEntity<?> updateBudgetByID(@PathVariable Integer id, @RequestBody Budget budget) {

		if (budgetService.updateBudgetById(id, budget)) {

			return ResponseEntity.ok(budget);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}
	@Operation(summary = "Deletes an Budget resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Budget resource successfully deleted.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Budget found for that id.") })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBudgetByID(@PathVariable Integer id) {

		if (this.budgetService.deleteBudgetById(id)) {

			return new ResponseEntity<>(HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@Operation(summary = "Retrieves an Budget resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Budget resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Budget found for that id.") })
	@GetMapping("/{id}")
	public ResponseEntity<?> findBudgetByID(@PathVariable Integer id) {
		Budget budgetInDb = this.budgetService.findBudgetById(id);

		if (budgetInDb == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(budgetInDb, HttpStatus.OK);
	}
	@Operation(summary = "Retrieves Budget resource(s) from the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Budget resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping
	public ResponseEntity<?> findAllBudget() {
		return new ResponseEntity<>(this.budgetService.findAllBudgets(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieves an Budget resource from the database with the income id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Budget resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Budget found for that income id.") })

	@GetMapping("/incomes/{incomeId}")
	public ResponseEntity<?> findBudgetsByIncomeId(@PathVariable Integer incomeId) {
		List<Budget> budgets = budgetService.findByIncomeId(incomeId);

		if (budgets.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(budgets, HttpStatus.OK);
	}

}
