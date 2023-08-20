package com.finance.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Income {
	@Id
	@SequenceGenerator(name = "INCOME_ID_GEN", sequenceName = "income_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INCOME_ID_GEN")
	private Integer id;

	private Integer incomeYear;

	@NotBlank(message = "Income month is required.")
	@Size(min = 2, max = 255, message = "Income Month must be between 2 to 255 characters long.")
	private String incomeMonth;

	@Column(name = "salary")
	@NotNull(message = "Salary can't be blank")
	@DecimalMin(value = "0.1", inclusive = false, message = "Please insert a valid Salary > 0.0")
	private BigDecimal salary;

	public Income() {

	}

	public Income(Integer incomeYear, String incomeMonth, BigDecimal salary) {

		this.incomeYear = incomeYear;
		this.incomeMonth = incomeMonth;
		this.salary = salary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIncomeYear() {
		return incomeYear;
	}

	public void setIncomeYear(Integer incomeYear) {
		this.incomeYear = incomeYear;
	}

	public String getIncomeMonth() {
		return incomeMonth;
	}

	public void setIncomeMonth(String incomeMonth) {
		this.incomeMonth = incomeMonth;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Income [id=" + id + ", incomeYear=" + incomeYear + ", incomeMonth=" + incomeMonth + ", salary=" + salary
				+ "]";
	}

}
