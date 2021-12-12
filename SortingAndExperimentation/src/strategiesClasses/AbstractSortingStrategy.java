package strategiesClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

/**
 * General datatype of a strategy to solve a particular problem. This 
 * applies to any problem in which the input can be received as a
 * List object and the output can also be given as a List object. 
 * For example: frequency counting, sorting, searching, etc. 
 * 
 * @author pedroirivera-vega
 *
 * @param <E> the data type of the objects in the dataset that
 * contains the input to be processed by the strategy
 */
public abstract class AbstractSortingStrategy<E> 
extends AbstractStrategyToTest<E> {
	protected Comparator<E> cmp; 
	public AbstractSortingStrategy(String name, Comparator<E> cmp) { 
		super(name);
		this.cmp = cmp; 
	}
	

	public void experimentallyExecuteStrategy(ArrayList<E> dataSet) {
		sortList(dataSet); 
		
	}

	/**
	 * Determines the solution for the particular input dataset  
	 * using a valid strategy. It is based on the algorithm defining the particular 
	 * strategy to solve the frequency counting problem.
	 * @param dataSet the dataset of objects to be sorted
	 *        the sorted list will remain in dataSet
	 */
	public abstract void sortList(ArrayList<E> dataSet); 
	
}
