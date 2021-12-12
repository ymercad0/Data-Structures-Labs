package testerClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IntegerDataReader implements DataReader<Integer> {

	private File inputFile; 

	public IntegerDataReader(File inputFile) { 
		this.inputFile = inputFile; 
	}

	@Override
	public ArrayList<Integer> readDataFromFile() throws FileNotFoundException {

		ArrayList<Integer> data = new ArrayList<>(); 

		Scanner input = new Scanner(inputFile); 
		while (input.hasNext()) 
			data.add(input.nextInt()); 

		input.close();

		return data;
	}
}
