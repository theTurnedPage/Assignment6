package com.coderscampus.assignment6;

import java.time.YearMonth;

public class SalesData {
	
	private String model;
	private YearMonth year;
	private Double sales;
	
	
	public SalesData(String model, YearMonth year, Double sales) {
		super();
		this.model = model;
		this.year = year;
		this.sales = sales;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public YearMonth getYear() {
		return year;
	}

	public void setYear(YearMonth year) {
		this.year = year;
	}


	public Double getSales() {
		return sales;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}
	
	

}
