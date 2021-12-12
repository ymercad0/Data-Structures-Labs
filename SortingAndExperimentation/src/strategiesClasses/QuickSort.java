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
public class QuickSort<E> extends AbstractSortingStrategy<E> {
	private ArrayList<E> list;

	public QuickSort(Comparator<E> cmp) {
		super("QuickSort", cmp);
	}

	@Override
	public void sortList(ArrayList<E> dataSet) {
		list = dataSet;
		qs(0, list.size()-1);
	}

	/**
	 * Applies the divide and conquer strategy to sort using 
	 * QuickSort algorithm. It works on the portion of the list
	 * from position first to position last (first..last). 
	 *
	 * @param first the index of the first position in this
	 * portion
	 * @param last the index of the last position in this
	 * portion
	 */
	private void qs(int first, int last) {
		if(first < last){
			int pivot = partitionList(first, last);
			/* Don't actually sort the pivot. */
			qs(first, pivot-1); //sort elems before the pivot
			qs(pivot+1, last); //sort elements after the pivot
		}
	}

	/**
	 * Partition method. ... See discussion in lab's document.
	 * @param first First index of the subarray to partition.
	 * @param last Last index of the subarray to partition.
	 * @return The index of the pivot.
	 */
	private int partitionList(int first, int last) {
		E pivot = list.get(last);

		int i = first; //index where you last placed an item less than the pivot
		for(int j = first; j < last; j++){
			if(cmp.compare(list.get(j), pivot) <= 0){ //elements less than the pivot stay to the left
				SortingUtils.swapListElements(list, i, j);
				i++;
			}
		}
		SortingUtils.swapListElements(list, i, last); //swap with the pivot
		return i;
	}
}
