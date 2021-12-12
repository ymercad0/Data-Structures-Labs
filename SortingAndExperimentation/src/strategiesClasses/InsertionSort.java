package strategiesClasses;

import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort<E> extends AbstractSortingStrategy<E> {
	public InsertionSort(Comparator<E> cmp) {
		super("InsertionSort", cmp);
	}

	@Override
	public void sortList(ArrayList<E> dataSet) {
		/*TODO ADD YOUR CODE HERE*/
		/*HINT: Use cmp to do your implementation*/
		if(dataSet.size() <= 1) return;
		int swapIndex;

		for(int i = 1; i < dataSet.size(); i++){ //start at 1 because we always compare backwards
			swapIndex = i; //current num is less than the one before it
			while(swapIndex > 0 && cmp.compare(dataSet.get(swapIndex), dataSet.get(swapIndex-1)) < 0){
				SortingUtils.swapListElements(dataSet, swapIndex, swapIndex-1);
				swapIndex--;
			}
		}
	}
}
