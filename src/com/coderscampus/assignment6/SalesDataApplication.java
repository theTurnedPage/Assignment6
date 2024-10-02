package com.coderscampus.assignment6;

import java.io.IOException;
import java.text.ParseException;

public class SalesDataApplication {

	public static void main(String[] args) throws ParseException {
	
		runApp();
	}
	
	private static void runApp() throws ParseException {
		
//--------------------------input--------------------------	
		FileServiceImpl captureData = new FileServiceImpl();
		
		captureData.readFile(FileServiceImpl.model3File.toString());
		captureData.readFile(FileServiceImpl.modelXFile.toString());
		captureData.readFile(FileServiceImpl.modelSFile.toString());
//--------------------------output--------------------------
		
		System.out.println("\nModel 3 Yearly Sales Report\n--------------------------");
		FileServiceImpl.retrieveYearlyReport(FileServiceImpl.model3Map);
		FileServiceImpl.getMinMax(FileServiceImpl.model3Map);
		System.out.println("\nModel X Yearly Sales Report\n--------------------------");
		FileServiceImpl.retrieveYearlyReport(FileServiceImpl.modelSMap);
		FileServiceImpl.getMinMax(FileServiceImpl.modelXMap);	
		System.out.println("\nModel S Yearly Sales Report\n--------------------------");
		FileServiceImpl.retrieveYearlyReport(FileServiceImpl.modelXMap);
		FileServiceImpl.getMinMax(FileServiceImpl.modelSMap);		
	}

}
