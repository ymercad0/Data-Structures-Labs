package testerClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface DataReader<E> {
	ArrayList<E> readDataFromFile() throws FileNotFoundException; 
}
