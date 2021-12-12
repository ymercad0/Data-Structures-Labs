package testerClasses;

import java.util.ArrayList;
import java.util.Map;

import strategiesClasses.HeapSort;
import strategiesClasses.SequentialFD;

public class HeapSortTester1 {

	public static void main(String[] args) {
		
		ArrayList<Integer> data = TestingUtils.generateListOfIntegers(500); 
		
//		TestingUtils.displayListElements("Original Data", data);
		
		HeapSort<Integer> sorter = new HeapSort<>(new IntegerComparator1()); 
		 

		sorter.sortList(data);
		
		TestingUtils.displayListElements("Sorted", data);
	}

}
