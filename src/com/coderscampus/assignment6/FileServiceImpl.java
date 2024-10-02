package com.coderscampus.assignment6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileServiceImpl implements FileService {
		
	public static Map<String, Double> model3Map = new HashMap<String, Double>();
	public static Map<String, Double> modelSMap = new HashMap<String, Double>();
	public static Map<String, Double> modelXMap = new HashMap<String, Double>();
	public static Map<String, Double> allSalesMap = new HashMap<String, Double>();
	
	public static SalesData teslaData = new SalesData(null, 0, Double.NaN);
	//public static List<SalesData> teslaList = new ArrayList<>();
	
	public List<Map<String, Double>> mapNames = List.of(model3Map, modelSMap, modelXMap);
	
//	public static void testMethod() throws ParseException {
//	}
	
	@Override
	public void readFile(String fileName) {						
		
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        	
            reader.lines()
                  .skip(1) //Skip the header row showing "Date" and "Sales"
                  .map(line -> line.split(","))
                  .forEach(parts -> {
                      String dateString = parts[0]; //two columns, 1 for date one for sale
                      String salesString = parts[1];
                      allSalesMap.put(dateString, Double.parseDouble(salesString));
                      //store the months separate for comparisons later                      
                      Double salesDouble = Double.parseDouble(salesString); //change to a double, use for map
         
					  fillMaps(fileName, dateString, salesDouble);//put values into corresponding map
                      //fillList(fileName, theYear, salesDouble); //put values into corresponding list
                      //System.out.println(date + " -> " + sales);                      
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
	//create SalesData objects, add to list(s) got carried away with Maps and decided to use them instead
	//public static void fillList(String fileName, int year, Double sales){
		
		//if (fileNames.contains(fileName)) {
					
//	        if (fileName.toString().equalsIgnoreCase(model3File.toString())) {
//	        	try {
//					teslaData = new SalesData("Model 3", year, sales);
//					teslaList.add(teslaData);
//					
//				} catch (Exception e) {
//					System.out.println("Exception!");
//				}
//	        }
//	        
//	        else if (fileName.toString().equalsIgnoreCase(modelSFile.toString())) {
//	        	try {
//					teslaData = new SalesData("Model S", year, sales);	
//					teslaList.add(teslaData);
//				} catch (Exception e) {
//					System.out.println("Exception!");
//				}
//	        }
//	        
//	        else if (fileName.toString().equalsIgnoreCase(modelXFile.toString())) {
//	        	try {
//					teslaData = new SalesData("Model X", year, sales);	
//					teslaList.add(teslaData);
//				} catch (Exception e) {
//					System.out.println("Exception!");
//					System.out.println(e);
//				}
//	        }	        
		//}		                                        
	//}	
	
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
	      	  //System.out.println(theKey);
	      	String theOutput = convertStringFormat(theKey);
	      	System.out.print(theOutput);
	      } 
	      
	      else {
	          System.out.println("No matching entry found for value \"" + aValue + "\"");
	      }        	      	    	    	    	
	}
    
    public static void retrieveYearlyReport(Map<String, Double> dataMap) {

    	Integer[] yearArray = {16, 17, 18, 19, 20};//
    	Integer totalSales = 0;
    	int i = 0;
    	
    	if (dataMap.equals(model3Map)) {
    	   i = 1;//skip 2016 for Model3
    	}
    	    		
    	for (i = 0; i < yearArray.length - 1; i++) {
        	
    		totalSales = sumValues(dataMap, yearArray[i].toString());
        	System.out.println(yearArray[i] + " -> " + totalSales +"\n");        			
        }
    }    	    	                    	    
    
    public static Integer sumValues(Map<String, Double> dataMap, String theYear) {    	    	    	    	    	    	
    	
    	//System.out.println("test!" + dataMap.entrySet().toString());
    	
    	List<Integer> yearlySales = dataMap.entrySet().stream()
    			.filter(entry -> entry.getKey().contains(theYear))
    			.map(cars -> cars.getValue().intValue())    			
    			.collect(Collectors.toList());
    	
    	Integer sumValue = yearlySales.stream()
    	        .mapToInt(Integer::intValue)
    	        .sum();
    	    	    	
    	//System.out.println("\n*----Y: "+ yearlySales +"----*\n");
    	//System.out.println("\n*----S: "+ sumValue +"----*\n");

    	    
    	return sumValue;
    }
    
    public static int stringDateToYear(String dateString) throws ParseException {
    	
    	//ignore the months, only get the year
    	String part2 = dateString.substring(dateString.length() - 2);
    	//add a 20 to the beginning
    	String bothParts = "20" + part2;
    	
    	try {
    		//parse the String to an int and return
    	    int parsedInt = Integer.parseInt(bothParts);
    	    //System.out.println("Parsed integer value: " + parsedInt);
    	    return parsedInt;
    	} catch (NumberFormatException e) {
    	    System.err.println("Error parsing the String to an int: " + e.getMessage());
    	}
		return 0;		
    }
    
    public static String shortenString(String dateString) {
    	
    	//Remove part of String for yyyy-MM format for the best and worst month printout
    	//It is possible you meant for MM to be numbers instead, so I provided both
    	String shortenedString = (String) dateString.subSequence(0, 2).toString().toUpperCase();
    	
    	return shortenedString;
    }
    
	 public static String convertStringFormat(String inputString) {

		 try {	        
	            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM-yy");
	            Date date = inputFormat.parse(inputString);

	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(date);

	            int year = calendar.get(Calendar.YEAR);
	            int month = calendar.get(Calendar.MONTH);

	            //Get the two-letter month abbreviation
	            String[] monthAbbreviations = new DateFormatSymbols().getShortMonths();
	            String monthAbbreviation = monthAbbreviations[month];
	            String fixedMonth = shortenString(monthAbbreviation);

	            //Convert to yyyy-mmm format, %4d format specifier
	            return String.format("%4d", year) + "-" + fixedMonth;
	        } catch (ParseException e) {
	            //Handle parsing errors (invalid inputString's format)
	            e.printStackTrace();
	            return "Invalid inputString.";
	        }
	    }    

}