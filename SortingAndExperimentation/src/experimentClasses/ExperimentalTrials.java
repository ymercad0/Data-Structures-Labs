package experimentClasses;

import java.io.FileNotFoundException;

import strategiesClasses.BubbleSort;
import strategiesClasses.HeapSort;
import strategiesClasses.InsertionSort;
import strategiesClasses.MapFD;
import strategiesClasses.MergeSort;
import strategiesClasses.OrderedFD;
import strategiesClasses.QuickSort;
import strategiesClasses.SelectionSort;
import strategiesClasses.SequentialFD;
import testerClasses.IntegerComparator1;

/**
 * 
 * @author pedroirivera-vega
 *
 */
public class ExperimentalTrials {

	public static void main(String[] args) {
		// Parm1: initial size
		// Parm2: trials per size
		// Parm3: incremental steps (size)
		// Parm4: last size to consider
		ExperimentController ec = new ExperimentController(50, 200, 50, 1000); 
		
		/**
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new SequentialFD<Integer>())); 
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new OrderedFD<Integer>())); 
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new MapFD<Integer>())); 
		**/
		
		/* TODO UNCOMMENT TO TEST YOUR ALGORITHMS, results will be in experimentalResults/
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new HeapSort<Integer>(new IntegerComparator1()))); 
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new QuickSort<Integer>(new IntegerComparator1()))); 
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new MergeSort<Integer>(new IntegerComparator1()))); 
		*/

		ec.addStrategy(new StrategiesTimeCollection<Integer>(new SelectionSort<Integer>(new IntegerComparator1())));
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new BubbleSort<Integer>(new IntegerComparator1())));
		ec.addStrategy(new StrategiesTimeCollection<Integer>(new InsertionSort<Integer>(new IntegerComparator1())));


		ec.run();    // run the experiments on all the strategies added to the controller object (ec)
		
		// save the results for each strategy....
		try {
			ec.saveResults();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
