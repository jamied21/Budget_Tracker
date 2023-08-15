package com.finance.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "incomes")
public class Income {
	@Id
	@Column(name = "income_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer incomeYear;

	@NotBlank(message = "Income month is required.")
	private String incomeMonth;

	@Column(name = "salary")
	@Digits(integer = 3, fraction = 2)
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

	public Integer getincomeYear() {
		return incomeYear;
	}

	public void setincomeYear(Integer incomeYear) {
		this.incomeYear = incomeYear;
	}

	public String getincomeMonth() {
		return incomeMonth;
	}

	public void setincomeMonth(String incomeMonth) {
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
