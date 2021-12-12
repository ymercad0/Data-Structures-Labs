package testerClasses;

import java.util.ArrayList;
import java.util.Map;

import strategiesClasses.MergeSort;

public class MergeSortTester1 {

	public static void main(String[] args) {
		
		ArrayList<Integer> data = TestingUtils.generateListOfIntegers(500); 
		
//		TestingUtils.displayListElements("Original Data", data);
		
		MergeSort<Integer> sorter = new MergeSort<>(new IntegerComparator1()); 
		 

		sorter.sortList(data);
		
		TestingUtils.displayListElements("Sorted", data);
	}

}
