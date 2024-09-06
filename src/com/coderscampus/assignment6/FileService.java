package com.coderscampus.assignment6;

import java.io.File;
import java.util.List;

public interface FileService {

	//create abstract method(s) for implementation later
	public void readFile(String filename);	
	public void displayFile();
	
	File model3File = new File ("model3.csv");
	File modelSFile = new File ("modelS.csv");
	File modelXFile = new File ("modelX.csv");
	
	public List<String> fileNames = List.of(model3File.toString(), modelSFile.toString(), modelXFile.toString());
			
}
