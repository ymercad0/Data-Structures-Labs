package testerClasses;

import java.util.ArrayList;
import java.util.Random;

public class TestingUtils {
	public static ArrayList<Integer> generateListOfIntegers(int n) { 
		ArrayList<Integer> results = new ArrayList<>(n); 
		
		Random r = new Random(); 
		
		for (int i=0; i<n; i++) 
			results.add(r.nextInt(n)); 
		
		return results; 
	}
	
	public static <E> void displayListElements(String msg, ArrayList<E> list) { 
		System.out.println(msg); 
		for (int i=0; i<list.size(); i++) 
			System.out.println("list["+i + "] = " + list.get(i)); 
		System.out.println("END\n\n"); 
	}

}
