package com.coderscampus.assignment6;

import java.text.ParseException;

public class SalesDataApplication {

	public static void main(String[] args) throws ParseException {
	
		//--------------------------input--------------------------	
				FileService captureData = new FileService();
				
				captureData.readFile("model3.csv");
				captureData.readFile("modelS.csv");
				captureData.readFile("modelX.csv");
		//--------------------------output--------------------------
				
				System.out.println("\n\nModel 3 Yearly Sales Report\n--------------------------");
				FileService.retrieveYearlyReport(FileService.list3);
				//FileService.getMinMax(FileService.list3);
				System.out.println("\n\nModel X Yearly Sales Report\n--------------------------");
				FileService.retrieveYearlyReport(FileService.listX);
				//FileService.getMinMax(FileService.listX);	
				System.out.println("\n\nModel S Yearly Sales Report\n--------------------------");
				FileService.retrieveYearlyReport(FileService.listS);
				//FileService.getMinMax(FileService.listS);		
	}
}