package testerClasses;

import java.util.ArrayList;
import java.util.Map;

import strategiesClasses.BubbleSort;
import strategiesClasses.HeapSort;
import strategiesClasses.SelectionSort;
import strategiesClasses.SequentialFD;

public class SelectionSortTester1 {

	public static void main(String[] args) {
		
		ArrayList<Integer> data = TestingUtils.generateListOfIntegers(500); 
		
//		TestingUtils.displayListElements("Original Data", data);
		
		SelectionSort<Integer> sorter = new SelectionSort<>(new IntegerComparator1()); 
		 

		sorter.sortList(data);
		
		TestingUtils.displayListElements("Sorted", data);
	}

}
