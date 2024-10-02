package com.coderscampus.teststuff;

import java.util.Date;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;

public class TestClass {

	 public static String convertToFullYearFormat(String input) {
	        try {
	            SimpleDateFormat inputFormat = new SimpleDateFormat("yy-MMM");
	            Date date = inputFormat.parse(input);

	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(date);

	            int year = calendar.get(Calendar.YEAR);
	            int month = calendar.get(Calendar.MONTH);

	            //Get the three-letter month abbreviation
	            String[] monthAbbreviations = new DateFormatSymbols().getShortMonths();
	            String monthAbbreviation = monthAbbreviations[month];

	            //Convert to yyyy-mmm format, %4d format specifier
	            return String.format("%4d", year) + "-" + monthAbbreviation;
	        } catch (ParseException e) {
	            //Handle parsing errors (invalid input format)
	            e.printStackTrace();
	            return "Invalid input format. Please provide a valid yy-mmm string.";
	        }
	    }

	    public static void main2() {
	        String inputDate = "23-Sep"; //Example input
	        String fullYearFormat = convertToFullYearFormat(inputDate);
	        System.out.println("Converted date: " + fullYearFormat);
	    }
	    
	    public static void main(String[] args) {
	    	
	    	main2();
	    	//CarSale.main3();
	    }
}
