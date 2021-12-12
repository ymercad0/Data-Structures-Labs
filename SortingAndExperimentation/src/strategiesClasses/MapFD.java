package strategiesClasses;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

public class MapFD<E extends Comparable<E>> extends AbstractFDStrategy<E> {

	public MapFD() { 
		super("Map"); 
	}
	
	@Override
	public ArrayList<Entry<E, Integer>> computeFDList(ArrayList<E> dataSet) {
		Map<E, Integer> map = new Hashtable<>(); 
		ArrayList<Map.Entry<E, Integer>> results = 
				new ArrayList<Map.Entry<E, Integer>>(); 
		
		for (E e : dataSet) { 
			Integer v = map.get(e); 
			
			if (v != null)
				map.put(e, v+1); 
			else 
				map.put(e, 1);
		}
		
		for (Map.Entry<E, Integer> e : map.entrySet())
			results.add(e); 
		
		return results;
	}

}
