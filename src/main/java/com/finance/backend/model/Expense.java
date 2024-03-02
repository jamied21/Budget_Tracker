package com.finance.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Expense {
	@Id
	@SequenceGenerator(name = "EXPENSE_ID_GEN", sequenceName = "expense_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_ID_GEN")
	private Integer id;

	@NotBlank(message = "Please type in a name for the expense")
	@Column(name = "expense_name")
	private String expenseName;

	@NotNull(message = "Expense amount is required")
	@DecimalMin(value = "0.1", inclusive = false, message = "Please insert a valid amount > 0.0")
	@Column(name = "amount")
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "FK_BUDGET_ID")
	private Budget budget;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinColumn(name = "FK_INCOME_ID")
	private Income income;

	public Expense() {

	}

	public Expense(String expenseName, BigDecimal amount) {

		this.expenseName = expenseName;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public Income getIncome() {
		return income;
	}

	public void setIncome(Income income) {
		this.income = income;
	}

}
