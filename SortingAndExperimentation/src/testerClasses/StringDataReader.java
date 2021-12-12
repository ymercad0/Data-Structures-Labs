package testerClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StringDataReader implements DataReader<String> {

	private File inputFile; 

	public StringDataReader(File inputFile) { 
		this.inputFile = inputFile; 
	}

	@Override
	public ArrayList<String> readDataFromFile() throws FileNotFoundException {

		ArrayList<String> data = new ArrayList<>(); 

		Scanner input = new Scanner(inputFile); 
		while (input.hasNext()) 
			data.add(input.nextLine()); 

		input.close();

		return data;	
	}
}
