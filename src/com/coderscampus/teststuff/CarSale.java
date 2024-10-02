package com.coderscampus.teststuff;

	import java.util.List;
	import java.util.Map;
	import java.util.stream.Collectors;

	class CarSale {
	    public CarSale(int i, double d, String string) {
			// TODO Auto-generated constructor stub
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public double getSaleAmount() {
			return saleAmount;
		}

		public void setSaleAmount(double saleAmount) {
			this.saleAmount = saleAmount;
		}

		public String getModelName() {
			return modelName;
		}

		public void setModelName(String modelName) {
			this.modelName = modelName;
		}

		private int year;
	    private double saleAmount;
	    private String modelName;
	    
	    

	    // Constructor, getters, and setters (if needed) go here

	    // Assuming you have a list of CarSale objects
	    public static Map<Integer, Double> sumSalesByYear(List<CarSale> carSales) {
	        return carSales.stream()
	                .collect(Collectors.groupingBy(
	                        CarSale::getYear,
	                        Collectors.summingDouble(CarSale::getSaleAmount)
	                ));
	    }

	    public static void main3() {
	        // Example usage:
	        List<CarSale> carSales = List.of(
	                new CarSale(2020, 25000.0, "Toyota Camry"),
	                new CarSale(2021, 28000.0, "Honda Civic"),
	                new CarSale(2020, 30000.0, "Ford Mustang")
	                // Add more car sales records here
	        );

	        Map<Integer, Double> salesByYear = sumSalesByYear(carSales);
	        salesByYear.forEach((year, totalSales) -> {
	            System.out.println("Year " + year + ": Total sales = $" + totalSales);
	        });
	    }
	}