package com.finance.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

	private ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {

		this.expenseService = expenseService;
	}

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

	@PutMapping("/{id}")
	public ResponseEntity<?> updateExpenseByID(@PathVariable Integer id, @RequestBody Expense expense) {

		if (expenseService.updateExpenseById(id, expense)) {

			return ResponseEntity.ok(expense);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExpenseByID(@PathVariable Integer id) {

		if (this.expenseService.deleteExpenseByid(id)) {

			return new ResponseEntity<>(HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findExpenseByID(@PathVariable Integer id) {
		Expense expenseInDb = this.expenseService.findExpenseById(id);

		if (expenseInDb == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(expenseInDb, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> findAllExpense() {
		return new ResponseEntity<>(this.expenseService.findAllExpenses(), HttpStatus.OK);
	}

}