package strategiesClasses;

import java.util.ArrayList;
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
public abstract class AbstractFDStrategy<E extends Comparable<E>> 
extends AbstractStrategyToTest<E> {
	
	public AbstractFDStrategy(String name) { 
		super(name); 
	}
	

	public void experimentallyExecuteStrategy(ArrayList<E> dataSet) {
		computeFDList(dataSet); 
		
	}

	/**
	 * Determines the solution for the particular input dataset  
	 * using a valid strategy. It is based on the algorithm defining the particular 
	 * strategy to solve the frequency counting problem.
	 * @param dataSet the dataset of objects to be analized
	 * @return output set, the result of solving the problem in input DataSet
	 */
	public abstract ArrayList<Map.Entry<E, Integer>> computeFDList(ArrayList<E> dataSet); 
	
}
