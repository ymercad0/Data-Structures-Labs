package strategiesClasses;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class HeapSort<E> extends AbstractSortingStrategy<E> {

	private ArrayList<E> list; 
	
	public HeapSort(Comparator<E> cmp) { 
		super("HeapSort", cmp); 
	}
	
	@Override
	public void sortList(ArrayList<E> dataSet) {
		list = dataSet; 
		
		// convert to to heap
		int n = list.size(); 
		for (int r = (n-2)/2; r>=0; r--)
			downHeap(r, n); 
		
		for (int i = n-1; i>0; i--) { 
			SortingUtils.swapListElements(list, i, 0);
			downHeap(0, i); 
		}
	}

	private int left(int r) { 
		return 2*r+1; 
	}
	private int right(int r) { 
		return 2*r+2; 
	}
	private int parent(int r) { 
		return (r-1)/2; 
	}
	
	private boolean hasLeft(int r, int n) { 
		return left(r) < n; 
	}
	
	private boolean hasRight(int r, int n) { 
		return right(r) < n; 
	}
	
	private void downHeap(int r, int n) { 
		// r is a root of a subtree in the complete tree formed
		// by the first n elements in dataSet --- 0..n-1
		boolean isHeap = false; 
		while (!isHeap && hasLeft(r, n)) { 
			int mci = left(r);  //get left element
			if (hasRight(r, n) && cmp.compare(list.get(right(r)), list.get(mci)) < 0)
				mci = right(r); //if the right element is less than the left, the right is the new smallest element
			if (cmp.compare(list.get(mci), list.get(r)) < 0) { 
				SortingUtils.swapListElements(list, r, mci); //swap with the root if the root is bigger
				r = mci; 
			}
			else 
				isHeap = true; 
				
		}
	}
	
}
