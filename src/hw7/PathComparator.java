package hw7;

import hw5.Edge;

import java.util.ArrayList;
import java.util.Comparator;

public class PathComparator<E> implements Comparator<ArrayList<Edge<E, Double>>> {
	
	// PathComparator is not an ADT so there is no respresentation invariant or 
	// abstraction function. 
	
	/**
	 * Compares two ArrayList<Edge<E, Double>>s as representations of
	 * weight paths. 
	 * 
	 * @param o1 The first path to be compared.
	 * @param o2 The second path to be compared.
	 * @returns Returns 0 if the added weights of each Edge in o1
	 * 			is equal to the added weights of each Edge in o2, 1 if 
	 *          the weight of o1 > the weight of o2, or -1 if the 
	 *          weight of o1 < the weight of o2. 
	 */
	@Override
	public int compare(ArrayList<Edge<E, Double>> o1,
			ArrayList<Edge<E, Double>> o2) {
		
		double weight1 = 0.0;
		for (Edge<E, Double> e : o1) {
			weight1 += e.getLabel(); 
		}
		
		double weight2 = 0.0;
		for (Edge<E, Double> e : o2) {
			weight2 += e.getLabel(); 
		}
		
		if (weight1 == weight2) {
			return 0;
		} else if (weight1 > weight2) {
			return 1;
		} else { // (weight1 < weight2)
			return -1;
		}
	}
}
