package com.coderscampus.assignment6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.List;

public class FileServiceImpl implements FileService {
	
	//Maybe this technically could be in FileService
	//public List<String> fileNames = List.of(model3File.toString(), modelSFile.toString(), modelXFile.toString());

	@Override
	public void readFile(String fileName) {
		
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        	
            reader.lines()
                  .skip(1) //Skip the header row showing "Date" and "Sales"
                  .map(line -> line.split(","))
                  .forEach(parts -> {
                      String date = parts[0];
                      String sales = parts[1];
                      System.out.println(date + " -> " + sales);
                  });            
        } 
        
        catch (IOException e) {
            System.out.println(":-( Error reading " + fileName + ": " + e.getMessage());
        }
    }
	@Override
	public void displayFile() {
		
        fileNames.forEach(fileName -> {
            System.out.println(fileName + " Yearly Sales Report " + "\n-------------------------------");
            readFile(fileName);
            System.out.println();
        });		
		
	}
}	