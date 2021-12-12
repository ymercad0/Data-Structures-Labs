package strategiesClasses;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class SelectionSort<E> extends AbstractSortingStrategy<E> {

	public SelectionSort(Comparator<E> cmp) { 
		super("SelectionSort", cmp); 
	}
	
	@Override
	public void sortList(ArrayList<E> dataSet) {
		/*TODO ADD YOUR CODE HERE*/
		/*HINT: Use cmp and SortingUtils.swapListElements() to do your implementation*/
		int smallestIndex;

		for(int i = 0; i < dataSet.size(); i++){
			smallestIndex = i; //reset smallest index to get the second smallest num
			for(int j = i; j < dataSet.size(); j++){ //the first num is always the smallest
				if(cmp.compare(dataSet.get(j), dataSet.get(smallestIndex)) < 0){ //new smallest elem found
					smallestIndex = j;
				}
			}
			SortingUtils.swapListElements(dataSet, i, smallestIndex);
		}
	}

}
