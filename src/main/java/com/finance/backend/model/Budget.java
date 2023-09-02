package com.finance.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Budget {
	@Id
	@Column(name = "budget_id")
	@SequenceGenerator(name = "BUDGET_ID_GEN", sequenceName = "budget_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUDGET_ID_GEN")
	private Integer id;

	@NotBlank(message = "Please type in a budget name")
	@Size(min = 2, max = 255, message = "Budget Name must be between 2 to 255 characters long.")
	private String budgetName; // For example transport, food , leisure

	@NotNull(message = "Budget Amount is required.")
	private BigDecimal budgetAmount;

	public Budget() {

	}

	public Budget(String budgetName, BigDecimal budgetAmount) {

		this.budgetName = budgetName;
		this.budgetAmount = budgetAmount;
		// this.expenses = expenses;
	}

	public BigDecimal amountLeft(Budget budget, Expense expense) {

		BigDecimal remainingBudget = budget.getBudgetAmount().subtract(expense.getAmount());

		if (remainingBudget.compareTo(BigDecimal.ZERO) < 0) {

			System.out.print("Error you are over budget");

		}
		return remainingBudget;

	};

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	public BigDecimal getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(BigDecimal budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	/*
	 * public List<Expense> getExpenses() { return expenses; }
	 * 
	 * public void setExpenses(List<Expense> expenses) { this.expenses = expenses; }
	 */

}
