package strategiesClasses;

import java.util.ArrayList;

public class SortingUtils {

	public static <E> void swapListElements(ArrayList<E> list, int i, int j) { 
		list.set(i, list.set(j, list.get(i)));
	}
}
