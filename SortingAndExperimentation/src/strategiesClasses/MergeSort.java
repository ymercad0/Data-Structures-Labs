package strategiesClasses;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation of the in-place version of Quicksort.
 * 
 * @author pedroirivera-vega
 *
 * @param <E>
 */
public class MergeSort<E> extends AbstractSortingStrategy<E> {
	private ArrayList<E> list; 

	public MergeSort(Comparator<E> cmp) { 
		super("MergeSort", cmp); 
	}
	
	@Override
	public void sortList(ArrayList<E> dataSet) {
		list = dataSet; 
		ms(0, list.size()-1); 
	}

	/**
	 * Applies the divide and conquer strategy to sort using 
	 * MergeSort algorithm. It works on the portion of the list
	 * from position first to position last (first..last). 
	 * 
	 * @param first the index of the first position in this
	 * portion
	 * @param last the index of the last position in this
	 * portion
	 */
	private void ms(int first, int last) {
		if(first < last){ //while the sub arrays are at least of size 1
			int middle = first+(last-first)/2;
			ms(first, middle); //first half
			ms(middle+1, last); //second half
			merge(first, middle, last); //merge the two halves
		}
	}

	/**
	 * Merges two sorted portions of the list -- see discussion
	 * in the lab's document. 
	 * @param first
	 * @param mid
	 * @param last
	 */
	private void merge(int first, int mid, int last) { //first subarray is from first to mid, second from mid+1 to last
	     E[] tempList = (E[]) new Object[last-first+1]; 
	     int index1 = first, index2 = mid+1;
		 int indexTL = 0;
	     while (index1 <= mid && index2 <= last) //increase indexTL to keep placing items into temp array
	         if (cmp.compare(list.get(index1), list.get(index2)) <=0)  //places items in order
	             tempList[indexTL++] = list.get(index1++); 
	         else 
	             tempList[indexTL++] = list.get(index2++);


	     // move the remaining data to tempList -- notice that only one 
	     // of the following loops will iterate at least once
	     while (index1 <= mid)
	         tempList[indexTL++] = list.get(index1++);
	     while (index2 <= last)
	         tempList[indexTL++] = list.get(index2++);
			
	     // put sorted data back to the list portion....
	     for (int i=0; i<tempList.length; i++) 
	    	 list.set(first+i, tempList[i]); 		
			
	}

}
