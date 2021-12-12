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
public abstract class AbstractStrategyToTest<E> {
	private String name;     // the name given to this strategy
	
	public AbstractStrategyToTest(String name) { 
		this.name = name; 
	}
	
	/**
	 * Accesses the name of the particular strategy. 
	 */
	public String getName() { 
		return name; 
	}
	
	/**
	 * Determines the solution for the particular input dataset  
	 * using a valid strategy. It is based on the algorithm defining 
	 * the particular strategy to solve a particular problem on
	 * that input dataset. 
	 * @param input the input dataset 
	 */
	public abstract void experimentallyExecuteStrategy(ArrayList<E> input); 
	
}
