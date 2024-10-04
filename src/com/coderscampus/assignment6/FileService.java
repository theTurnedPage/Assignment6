package com.coderscampus.assignment6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
		
	//SalesData object is not really being used, initially it was but then the maps were super useful
	public static SalesData teslaData = new SalesData("", YearMonth.now(), Double.NaN);
	
	
	public static List<SalesData> listX  = new ArrayList<>();
	public static List<SalesData> listS  = new ArrayList<>();
	public static List<SalesData> list3  = new ArrayList<>();
	
	public void readFile(String fileName) {						
		
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        	
            reader.lines()
                  .skip(1) //Skip the header row showing "Date" and "Sales"
                  .map(line -> line.split(","))
                  .forEach(parts -> {
                      String dateString = parts[0]; //two columns, 1 for date one for sale
                      String salesString = parts[1];                      
                      YearMonth yearMonth = convertDate(dateString);                
                      Double salesDouble = Double.parseDouble(salesString); //change to a double, use for map         
					  createObjects(fileName, yearMonth, salesDouble);//put values into corresponding map                    
                  });            
        }         
        catch (IOException e) {
            System.out.println(":-( Error reading " + fileName + ": " + e.getMessage());
        }        
    }

	public static void createObjects(String fileName, YearMonth dateString, Double salesDouble){
		
		//create object via args received through parameters
		SalesData newData = new SalesData("TBD", dateString, salesDouble);  
		
        if (fileName.equals("model3.csv")) {
        	try {        		 
        		 newData.setModel("Model 3"); //Set the model per met conditions
        		 list3.add(newData);
			} catch (Exception e) {
				System.out.println("Exception!");
			}
        }        
        else if (fileName.equals("modelS.csv")) {
        	try {
        		newData.setModel("Model S");
        		listS.add(newData);
			} catch (Exception e) {
				System.out.println("Exception!");
			}
        }        
        else if (fileName.equals("modelX.csv")) {
        	try {
        		 newData.setModel("Model X");
        		 listX.add(newData);
			} catch (Exception e) {
				System.out.println("Exception!");
			}
        }					
	}	
    //removed retrieveKey method
    public static void retrieveYearlyReport(List<SalesData> theData) throws ParseException {    	
    	//removed fixed array, and instead pulled the years of sale using streams into Lists
    	double totalSales = 1;    	
    	int i = 0;  

    	List<YearMonth> saleYears = theData.stream().distinct().map(s -> s.getYear()).toList();
    	List<String> shortenSaleYears = new ArrayList<>();
    	//create new list, then populate it with return value of shortened years
    	for (int j = 0; j < saleYears.size(); j++) {
    		shortenSaleYears.add(shortenDateString(saleYears.get(j)));
    	}    	    	    		 	    	    
    			
    	//use distinct to avoid duplicate years, shorten the List, and sort it
    	List<String> newSaleYears = shortenSaleYears.stream().distinct().sorted().toList();    	
 
    	for (i = 0; i < newSaleYears.size(); i++) {
        	totalSales = sumValues(theData, newSaleYears.get(i));
        	System.out.println(newSaleYears.get(i) + " -> " + totalSales +"\n"); 
    	}
    	
    	getMinMax(theData);
    }    
    public static void getMinMax(List<SalesData> theData) throws ParseException {    	
        //Streams to find the highest and lowest values with double data type    	
        Double maxNumber = theData.stream()
        		.map(SalesData::getSales)
        		.max(Double::compareTo)
        		.orElse(Double.NaN);               		
        		
        Double minNumber = theData.stream()
        		.map(SalesData::getSales)
        		.min(Double::compareTo)
        		.orElse(Double.NaN);                     
        
        String teslaModel = minMaxModel(theData); 

        YearMonth minDate = theData.stream()
        		.filter(s -> s.getSales().equals(minNumber))
        		.map(SalesData::getYear)
        		.findFirst().orElse(null);
        		        		        
        YearMonth maxDate = theData.stream()
        		.filter(s -> s.getSales().equals(maxNumber))
        		.map(SalesData::getYear)
        		.findFirst().orElse(null);
        		        		               
        System.out.print("\nThe best month for " + teslaModel + " was: ");
        System.out.print(maxDate + " -> " +  maxNumber); //use Optional, Streams, to get the corresponding key
        System.out.print("\nThe worst month for " + teslaModel + " was: ");
        System.out.print(minDate + " -> " +  minNumber);                      
    }	    
    public static String minMaxModel(List<SalesData> theData) {
    	//conditions for whichever Tesla Model is being referenced
    	if (theData.equals(list3)) {
    		
    		return "Model 3";
    	}    	
    	else if (theData.equals(listS)) {
    		
    		return "Model S";
    	}    	
    	else if (theData.equals(listX)) {
    		
    		return "Model X";
    	}    	
    	return null;
    } 
    public static double sumValues(List<SalesData> theData, String stringYear) {    	    	    	    	    	    	    
    	
        List<Double> collectedNumbers = theData.stream()
        		.filter(aYear -> aYear.getYear().toString().contains(stringYear))
        		.map(SalesData::getSales)
        		.collect(Collectors.toList());
     	
    	Double theSums = collectedNumbers.stream()
    			.mapToDouble(d -> d.doubleValue())
    			.sum();

		return theSums;
    }            
    public static String shortenDateString(YearMonth yearMonth) {
    	//remove 3 parts of the string, leaving just the year
    	String dateString ="";
    	dateString = yearMonth.toString();
        if (dateString.length() >= 5) {
            return dateString.substring(0, dateString.length() - 3);
        } else {//if string is already shortened
            return dateString;
        }
    }    
    //much simpler, less gymnastics than the previous methods.
    private static YearMonth convertDate(String originalDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM-yy");
        YearMonth yearMonth = YearMonth.parse(originalDate, inputFormatter);
        return yearMonth;
    }		
}