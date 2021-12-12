package testerClasses;

import java.util.Comparator;
import java.util.Map.Entry;

public class EntryIntComparator implements
		Comparator<Entry<Integer, Integer>> {

	@Override
	public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
		// TODO Auto-generated method stub
		return o1.getKey().compareTo(o2.getKey());
	}
	
	

}
