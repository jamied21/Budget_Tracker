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

import com.finance.backend.model.Income;
import com.finance.backend.service.IncomeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/incomes")
@CrossOrigin(origins = "http://localhost:3000")
public class IncomeController {

	private IncomeService incomeService;

	public IncomeController(IncomeService incomeService) {

		this.incomeService = incomeService;
	}

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

	@PutMapping("/{id}")
	public ResponseEntity<?> updateIncomeByID(@PathVariable Integer id, @RequestBody Income income) {

		if (incomeService.updateIncomeById(id, income)) {

			return ResponseEntity.ok(income);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIncomeByID(@PathVariable Integer id) {

		if (this.incomeService.deleteIncomeById(id)) {

			return new ResponseEntity<>(HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findIncomeByID(@PathVariable Integer id) {
		Income incomeInDb = this.incomeService.findIncomeById(id);

		if (incomeInDb == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(incomeInDb, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> findAllIncome() {
		return new ResponseEntity<>(this.incomeService.findAllIncomes(), HttpStatus.OK);
	}

}
