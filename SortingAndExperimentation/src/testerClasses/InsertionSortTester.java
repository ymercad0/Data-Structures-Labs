package testerClasses;

import strategiesClasses.InsertionSort;

import java.util.ArrayList;

public class InsertionSortTester {

	public static void main(String[] args) {
		
		ArrayList<Integer> data = TestingUtils.generateListOfIntegers(500);

//		TestingUtils.displayListElements("Original Data", data);

		InsertionSort<Integer> sorter = new InsertionSort<>(new IntegerComparator1());
		 

		sorter.sortList(data);
		
		TestingUtils.displayListElements("Sorted", data);
	}

}
