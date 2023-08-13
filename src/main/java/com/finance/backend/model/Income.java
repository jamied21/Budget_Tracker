package com.finance.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Income {
	@Id
	@SequenceGenerator(name = "INCOME_ID_GEN", sequenceName = "income_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INCOME_ID_GEN")
	private Integer id;

	private Integer year; // Could look into date time

	private String month; // Subject to change the datatype

	private BigDecimal income;

	public Income(Integer year, String month, BigDecimal income) {

		this.year = year;
		this.month = month;
		this.income = income;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

}
