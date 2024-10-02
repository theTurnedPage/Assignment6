package com.coderscampus.assignment6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileServiceImpl implements FileService {
		
	public static Map<String, Double> model3Map = new HashMap<String, Double>();
	public static Map<String, Double> modelSMap = new HashMap<String, Double>();
	public static Map<String, Double> modelXMap = new HashMap<String, Double>();
	//SalesData object is not really being used, initially it was but then the maps were super useful
	public static SalesData teslaData = new SalesData(null, 0, Double.NaN);
	
	public List<Map<String, Double>> mapNames = List.of(model3Map, modelSMap, modelXMap);
	
	@Override
	public void readFile(String fileName) {						
		
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        	
            reader.lines()
                  .skip(1) //Skip the header row showing "Date" and "Sales"
                  .map(line -> line.split(","))
                  .forEach(parts -> {
                      String dateString = parts[0]; //two columns, 1 for date one for sale
                      String salesString = parts[1];
                      String newDateString = convertDate(dateString);
                      
                      //store the months separate for comparisons later                      
                      Double salesDouble = Double.parseDouble(salesString); //change to a double, use for map
         
					  fillMaps(fileName, newDateString, salesDouble);//put values into corresponding map                    
                  });            
        } 
        
        catch (IOException e) {
            System.out.println(":-( Error reading " + fileName + ": " + e.getMessage());
        }        
    }
	//put entries into the 3 maps, conditioned with 3 parameters
	public static void fillMaps(String fileName, String dateString, Double salesDouble){
		
        if (fileName.toString().equalsIgnoreCase(model3File.toString())) {
        	try {
				model3Map.put(dateString, salesDouble);												
			} catch (Exception e) {
				System.out.println("Exception!");
			}
        }
        
        else if (fileName.toString().equalsIgnoreCase(modelSFile.toString())) {
        	try {
				modelSMap.put(dateString, salesDouble);				
			} catch (Exception e) {
				System.out.println("Exception!");
			}
        }
        
        else if (fileName.toString().equalsIgnoreCase(modelXFile.toString())) {
        	try {
				modelXMap.put(dateString, salesDouble);				
			} catch (Exception e) {
				System.out.println("Exception!");
			}
        }	 				
	}
	
    public static void getMinMax(Map<String, Double> dataMap) throws ParseException {    	

        //Streams to find the highest and lowest values with double data type
        double maxNumber = dataMap.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(Double.NaN); //Default value if the map is empty

        double minNumber = dataMap.values().stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(Double.NaN);                      
        
        String teslaModel = minMaxModel(dataMap);
        
        System.out.print("\nThe best month for " + teslaModel + " was ");
        retrieveKey(dataMap, maxNumber); //use Optional, Streams, to get the corresponding key
        System.out.print("\nThe worst month for " + teslaModel + " was ");
        retrieveKey(dataMap, minNumber);                               
    }	
    
    public static String minMaxModel(Map<String, Double> dataMap) {
    	//conditions for whichever Tesla Model is being referenced
    	if (dataMap.equals(model3Map)) {
    		
    		return "Model 3";
    	}
    	
    	else if (dataMap.equals(modelSMap)) {
    		
    		return "Model S";
    	}
    	
    	else if (dataMap.equals(modelXMap)) {
    		
    		return "Model X";
    	}
    	
    	return null;
    }
    
	//retrieve key from map
	public static void retrieveKey(Map<String, Double> dataMap, Double aValue) throws ParseException {
		
	  String theKey = "";    	        	
		
	  Optional<Map.Entry<String, Double>> matchingEntry = dataMap.entrySet().stream()
	          .filter(entry -> entry.getValue().equals(aValue))
	          .findFirst();
	
	      if (matchingEntry.isPresent()) {
	      	theKey = matchingEntry.get().getKey();
	      	String theOutput = theKey;
	      	System.out.print(theOutput);
	      } 
	      
	      else {
	          System.out.println("No matching entry found for value \"" + aValue + "\"");
	      }        	      	    	    	    	
	}
    
    public static void retrieveYearlyReport(Map<String, Double> dataMap) {
    	
    	//removed fixed array, and instead pulled the years of sale using streams into Lists
    	Integer totalSales = 0;    	
    	int i = 0;  
    	//take all years, which are in the keySet, insert into a list
    	List<String> saleYears = dataMap.keySet().stream()
    			.toList();
    	
    	List<String> shortenSaleYears = new ArrayList<>();
    	//create new list, then populate it with return value of shortened years
    	for (int j = 0; j < saleYears.size(); j++) {
    		shortenSaleYears.add(shortenDateString(saleYears.get(j)));
    	}
    	
    	//use distinct to avoid duplicate years, shorten the List, and sort it
    	List<String> newSaleYears = shortenSaleYears.stream().distinct().sorted().toList();
    	
    	if (dataMap.equals(model3Map)) {
     	   i = 1;//skip 2016 for Model3
     	}
    	//removing the fixed array made it so 2016 doesn't appear for Model 3
    	for (i = 0; i < newSaleYears.size(); i++) {
        	totalSales = sumValues(dataMap, newSaleYears.get(i).toString());
        	System.out.println(newSaleYears.get(i) + " -> " + totalSales +"\n"); 
    	}

    }
    
    public static String shortenDateString(String dateString) {
    	//remove 3 parts of the string, leaving just the year
        if (dateString.length() >= 5) {
            return dateString.substring(0, dateString.length() - 3);
        } else {//if string is too short
            return dateString;
        }
    }
    
    public static Integer sumValues(Map<String, Double> dataMap, String theYear) {    	    	    	    	    	    	
    	
    	//from dataMap, get all entries filtered by theYear parameter, then get the intValue of the Values in the dataMap into a list for use later    	
    	List<Integer> yearlySales = dataMap.entrySet().stream()
    			.filter(entry -> entry.getKey().contains(theYear))
    			.map(cars -> cars.getValue().intValue())    			
    			.collect(Collectors.toList());
    	//sum all the intValues in yearlySales, then return
    	Integer sumValue = yearlySales.stream()
    	        .mapToInt(Integer::intValue)
    	        .sum();
    	    
    	return sumValue;
    }
    
    //much simpler, less gymnastics than the previous methods.
    private static String convertDate(String originalDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM-yy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        //kept getting an exception, LocalDate seems to expect a complete date (including the day).
        //Using LocalDate apparently assumes the first day of the specified month, not matching the output
        //for the assignment
        YearMonth yearMonth = YearMonth.parse(originalDate, inputFormatter);
        return yearMonth.format(outputFormatter);
    }		
}