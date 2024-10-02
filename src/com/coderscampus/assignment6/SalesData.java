package com.coderscampus.assignment6;

public class SalesData {
	
	private String model;
	private int year;
	private Double sales;
	
	
	public SalesData(String model, int year, Double sales) {
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


	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}


	public Double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}
	
	

}
