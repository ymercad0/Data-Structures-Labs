package strategiesClasses;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class BubbleSort<E> extends AbstractSortingStrategy<E> {

	public BubbleSort(Comparator<E> cmp) { 
		super("BubbleSort", cmp); 
	}
	
	@Override
	public void sortList(ArrayList<E> dataSet) {
		/*TODO ADD YOUR CODE HERE*/
		/*HINT: Use cmp and SortingUtils.swapListElements() to do your implementation*/
		boolean swap = true;

		while(swap){
			swap = false;
			for(int i = 0; i < dataSet.size()-1; i++){
				if(cmp.compare(dataSet.get(i), dataSet.get(i+1)) > 0){ //current num is greater than the one next
					SortingUtils.swapListElements(dataSet, i, i+1);
					swap = true;
				}
			}
		}
		
	}

}
