package com.finance.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/budgets")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {

	private BudgetService budgetService;

	public BudgetController(BudgetService budgetService) {

		this.budgetService = budgetService;
	}

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

	@PutMapping("/{id}")
	public ResponseEntity<?> updateBudgetByID(@PathVariable Integer id, @RequestBody Budget budget) {

		if (budgetService.updateBudgetById(id, budget)) {

			return ResponseEntity.ok(budget);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBudgetByID(@PathVariable Integer id) {

		if (this.budgetService.deleteBudgetById(id)) {

			return new ResponseEntity<>(HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findBudgetByID(@PathVariable Integer id) {
		Budget budgetInDb = this.budgetService.findBudgetById(id);

		if (budgetInDb == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(budgetInDb, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> findAllBudget() {
		return new ResponseEntity<>(this.budgetService.findAllBudgets(), HttpStatus.OK);
	}

}
