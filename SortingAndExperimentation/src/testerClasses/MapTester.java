package testerClasses;

import java.io.File;
import java.io.FileNotFoundException;

import strategiesClasses.MapFD;
import strategiesClasses.OrderedFD;

/**
 * 
 * @author pedroirivera-vega
 *
 */
public class MapTester {

	public static void main(String[] args) throws FileNotFoundException {
	    GenericFDTester<Integer> tester = 
	    		new GenericFDTester<>(new MapFD<Integer>());
	    tester.run(new IntegerDataReader(new File("inputData", "integerData.txt")));

	
	    GenericFDTester<String> stester = 
	    		new GenericFDTester<>(new MapFD<String>());
	    stester.run(new StringDataReader(new File("inputData", "stringData.txt")));
	}

}
