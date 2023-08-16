package com.finance.backend.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Budget {
	@Id
	@SequenceGenerator(name = "BUDGET_ID_GEN", sequenceName = "budget_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUDGET_ID_GEN")
	private Integer id;

	@NotBlank(message = "Please type in a budget name")
	@Size(min = 2, max = 255, message = "Budget Name must be between 2 to 255 characters long.")
	private String budgetName; // For example transport, food , leisure

	@Digits(integer = 3, fraction = 2)
	@DecimalMin(value = "0.1", inclusive = false, message = "Please insert a valid Amount > 0.0")
	private BigDecimal budgetAmount;

	@OneToMany(mappedBy = "budget")
	private List<Expense> expense;

	public Budget() {

	}

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

	@Override
	public String toString() {
		return "Budget [id=" + id + ", budgetName=" + budgetName + ", budgetAmount=" + budgetAmount + ", expense="
				+ expense + "]";
	}

}
