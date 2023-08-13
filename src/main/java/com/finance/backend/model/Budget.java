package com.finance.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Budget {
	@Id
	@SequenceGenerator(name = "BUDGET_ID_GEN", sequenceName = "budget_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUDGET_ID_GEN")
	private Integer id;

	private String budgetName; // For example transport, food , leisure

	private BigDecimal budgetAmount;

	public Budget(String budgetName, BigDecimal budgetAmount) {

		this.budgetName = budgetName;
		this.budgetAmount = budgetAmount;
	}

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

}
