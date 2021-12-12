package testerClasses;

import java.util.ArrayList;
import java.util.Map;

import strategiesClasses.MapFD;
import strategiesClasses.SequentialFD;

public class MapTester1 {
	public static void main(String[] args) {
		
		System.out.println("Testing Map strategy"); 
		ArrayList<Integer> data = TestingUtils.generateListOfIntegers(5000); 
		
		TestingUtils.displayListElements("Original Data", data);
		
		MapFD<Integer> s = new MapFD(); 
		ArrayList<Map.Entry<Integer, Integer>> fd = s.computeFDList(data); 

		fd.sort(new EntryIntComparator());
		
		TestingUtils.displayListElements("Frequency Distribution", fd);
	}

}
