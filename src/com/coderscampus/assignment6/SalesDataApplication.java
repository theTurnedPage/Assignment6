package com.coderscampus.assignment6;

import java.io.IOException;
import java.text.ParseException;

public class SalesDataApplication {

	public static void main(String[] args) throws ParseException {
	
		runApp();
	}
	
	private static void runApp() throws ParseException {
		
		FileServiceImpl captureData = new FileServiceImpl();
		
		captureData.readFile(captureData.model3File.toString());
		captureData.readFile(captureData.modelXFile.toString());
		captureData.readFile(captureData.modelSFile.toString());
		//System.out.println("\n-----begin min max report-----\n");													
		
		//System.out.println("\n"+teslaList.get(50).getModel());
		//System.out.println("\n------begin yearly report------\n");
				
		System.out.println("\nModel 3 Yearly Sales Report\n--------------------------");
		captureData.retrieveYearlyReport(captureData.model3Map);
		captureData.getMinMax(captureData.model3Map);
		System.out.println("\nModel X Yearly Sales Report\n--------------------------");
		captureData.retrieveYearlyReport(captureData.modelSMap);
		captureData.getMinMax(captureData.modelXMap);	
		System.out.println("\nModel S Yearly Sales Report\n--------------------------");
		captureData.retrieveYearlyReport(captureData.modelXMap);
		captureData.getMinMax(captureData.modelSMap);
	}

}
