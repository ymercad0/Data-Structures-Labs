package strategiesClasses;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import testerClasses.GenericComparator;

public class OrderedFD<E extends Comparable<E>> extends AbstractFDStrategy<E> {
	public OrderedFD() {
		super("Ordered");
	}

	@Override
	public ArrayList<Map.Entry<E, Integer>> computeFDList(ArrayList<E> dataSet) {
		
		ArrayList<Map.Entry<E, Integer>> results = 
				new ArrayList<Map.Entry<E, Integer>>(); 
		
		dataSet.sort(new GenericComparator<E>());
		
		E cElement = dataSet.get(0); 
		Map.Entry<E, Integer> entry = new AbstractMap.SimpleEntry<E, Integer>(cElement, 1); 
		for (int i=1; i < dataSet.size(); i++) { 
			E e = dataSet.get(i); 
			if (e.equals(cElement))
				entry.setValue(entry.getValue()+1); 
			else { 
				results.add(entry);
				entry = new AbstractMap.SimpleEntry<E, Integer>(e, 1);
				cElement = e; 
			}
		}
		results.add(entry); 
		
		return results;
	}

}
