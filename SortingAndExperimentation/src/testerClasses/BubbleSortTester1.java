package testerClasses;

import java.util.ArrayList;
import java.util.Map;

import strategiesClasses.BubbleSort;
import strategiesClasses.HeapSort;
import strategiesClasses.SequentialFD;

public class BubbleSortTester1 {

	public static void main(String[] args) {
		
		ArrayList<Integer> data = TestingUtils.generateListOfIntegers(500); 
		
//		TestingUtils.displayListElements("Original Data", data);
		
		BubbleSort<Integer> sorter = new BubbleSort<>(new IntegerComparator1()); 
		 

		sorter.sortList(data);
		
		TestingUtils.displayListElements("Sorted", data);
	}

}
