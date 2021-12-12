package experimentClasses;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import strategiesClasses.BubbleSort;
import strategiesClasses.HeapSort;
import strategiesClasses.MapFD;
import strategiesClasses.OrderedFD;
import strategiesClasses.SelectionSort;
import strategiesClasses.SequentialFD;
import testerClasses.IntegerComparator1;
import testerClasses.TestingUtils;

/**
 * This class represents an object data type that is able to carry the 
 * necessary experiments to estimate execution times of particular 
 * strategies to solve a problem. 
 * 
 * @author pedroirivera-vega
 *
 */
public class ExperimentController{
	
	private int initialSize;           // initial size to be tested
	private int repetitionsPerSize;    // experimental repetitions per size
	private int incrementalSizeStep;   // change of sizes (size delta)
	private int finalSize;             // last size to be tested
	
	private ArrayList<StrategiesTimeCollection<Integer>> resultsPerStrategy; 
	// The i-th position will contain a particular strategy being tested. 
	// At the end, the i-th position will also contain a list of 
	// pairs (n, t), where t is the estimated time for size n for
	// the strategy at that position. 
	
	ExperimentController(int is, int rps, int iss, int fs) { 
		initialSize = is; 
		repetitionsPerSize = rps; 
		incrementalSizeStep = iss; 
		finalSize = fs; 
		resultsPerStrategy = new ArrayList<>(); 
		
        //JPanel pane = new JPanel();      // this was intended for a progress bar....
        //pane.setLayout(new FlowLayout());

	}
	
	public void addStrategy(StrategiesTimeCollection<Integer> strategy) { 
		resultsPerStrategy.add(strategy); 
	}

	public void run() { 
		if (resultsPerStrategy.isEmpty())
			throw new IllegalStateException("No strategy has been added."); 
		ArrayList<Integer> dataSet; 
		for (int n=initialSize; n<=finalSize; n+=incrementalSizeStep) { 
			// For each strategy, reset the corresponding variable that will be used
			// to store the sum of times that the particular strategy exhibits for
			// the current size n. That variable is set to 0.
			for (StrategiesTimeCollection<Integer> trc : resultsPerStrategy) 
				trc.resetSum();  
			
			// Run all trials for the current size. 
			for (int r = 0; r<repetitionsPerSize; r++) {
				// The following will be the common dataset to be used in the current 
				// trial. 
				dataSet = TestingUtils.generateListOfIntegers(n); 
				
				// Apply each one of the strategies being tested using the previous 
				// dataset (of size n) as input; and, for each, estimate the time
				// that the execution takes. 
				for (StrategiesTimeCollection<Integer> trc : resultsPerStrategy) { 
					// create a copy of the dataset, just to leave intact the original
                    // one an hence being able to use the same dataset as input of
					// all the strategies. 
					ArrayList<Integer> dsCopy = (ArrayList<Integer>) dataSet.clone(); 
					
					long startTime = System.nanoTime(); // System.currentTimeMillis();   // time before

					trc.runTrial(dsCopy);   // run the particular strategy...
					
					long endTime = System.nanoTime(); // System.currentTimeMillis();    // time after

					// accumulate the estimated time (add it) to sum of times that
					// the current strategy has exhibited on trials for datasets
					// of the current size. 
					trc.incSum((int) (endTime-startTime));    
					
				}
			}
			
			for (StrategiesTimeCollection<Integer> trc : resultsPerStrategy) { 
				trc.add(new AbstractMap.SimpleEntry<Integer, Float>
				(n, (trc.getSum()/((float) repetitionsPerSize)))); 
			}

			System.out.println(n); 

		}
	}
	
	public void saveResults() throws FileNotFoundException { 
		
		for (StrategiesTimeCollection<Integer> trc : resultsPerStrategy) {
			String fileName = "results"+trc.getStrategyName()+".txt"; 
			PrintStream out = new PrintStream(new File("experimentalResults", fileName)); 

			for (Map.Entry<Integer, Float> e : trc)
				out.println(e.getKey()+ "\t" + e.getValue());
			
			out.close(); 
		}

	}
}


