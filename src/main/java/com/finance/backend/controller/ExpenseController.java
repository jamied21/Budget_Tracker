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

import com.finance.backend.model.Expense;
import com.finance.backend.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/expenses")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseController {

	private ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {

		this.expenseService = expenseService;
	}

	@Operation(summary = "Creates a new Expense")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Expense resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Expense resource has invalid field(s).") })
	@PostMapping
	public ResponseEntity<?> saveExpense(@Valid @RequestBody Expense expense, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.expenseService.saveExpense(expense), HttpStatus.CREATED);

	}

	@Operation(summary = "Updates new Expense")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Expense resource successfully updated and returned.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Expense found for that id.") })

	@PutMapping("/{id}")
	public ResponseEntity<?> updateExpenseByID(@PathVariable Integer id, @RequestBody Expense expense) {

		if (expenseService.updateExpenseById(id, expense)) {

			return ResponseEntity.ok(expense);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@Operation(summary = "Deletes an Expense resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Expense resource successfully deleted.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Expense found for that id.") })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExpenseByID(@PathVariable Integer id) {

		if (this.expenseService.deleteExpenseByid(id)) {

			return new ResponseEntity<>(HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Retrieves a Expense resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Expense resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Expense found for that id.") })
	@GetMapping("/{id}")
	public ResponseEntity<?> findExpenseByID(@PathVariable Integer id) {
		Expense expenseInDb = this.expenseService.findExpenseById(id);

		if (expenseInDb == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(expenseInDb, HttpStatus.OK);
	}

	@Operation(summary = "Retrieves a Expense resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Expense resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Expense found for that id.") })
	@GetMapping
	public ResponseEntity<?> findAllExpense() {
		return new ResponseEntity<>(this.expenseService.findAllExpenses(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieves a Expense resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Expense resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Expense found for that id.") })
	@GetMapping("/incomes/{incomeId}")
	public ResponseEntity<?> fidnExpensesByIncomeId(@PathVariable Integer incomeId) {
		List<Expense> expenses = this.expenseService.findExpensesByIncomeId(incomeId);

		if (expenses.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(expenses, HttpStatus.OK);

	}
}
