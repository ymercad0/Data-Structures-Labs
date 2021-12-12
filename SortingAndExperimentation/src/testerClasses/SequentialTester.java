package testerClasses;
import java.io.File;
import java.io.FileNotFoundException;

import strategiesClasses.OrderedFD;
import strategiesClasses.SequentialFD;

public class SequentialTester {

	public static void main(String[] args) throws FileNotFoundException {
	    GenericFDTester<Integer> tester = 
	    		new GenericFDTester<>(new SequentialFD<Integer>());
	    tester.run(new IntegerDataReader(new File("inputData", "integerData.txt")));

	    GenericFDTester<String> stester = 
	    		new GenericFDTester<>(new SequentialFD<String>());
	    stester.run(new StringDataReader(new File("inputData", "stringData.txt")));
	}

}
