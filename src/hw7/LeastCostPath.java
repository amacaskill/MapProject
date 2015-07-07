package hw7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import hw5.Graph;
import hw5.Edge;

public class LeastCostPath {
	
	// LeastCostPath is not an ADT so there is no representation invariant or
	// abstraction function 
	
	/**
	 * For a Graph<E, Double> g, finds the least cost path between two nodes, i.e. 
	 * finds the path will the least total sum of all weights. 
	 * 
	 * @param g Graph to be searched for a path.
	 * @param first The first node in the path to be found.
	 * @throws Throws IllegalArgumentException if any argument is null.
	 * 		   Throws NoSuchElementException if first or last is not in g. 
	 * @param last The last nodes in the path to be found.
	 * @return Returns an ArrayList of edges of the least cost path from first to last in g. If
	 *         there is no such path, returns null.
	 */
	public static <E> List<Edge<E, Double>> getPath (Graph<E,Double> g, E first, E last) {
		if (g == null | first == null | last == null) {
			throw new IllegalArgumentException("Null arguments"); 
		}
		if (!g.containsNode(first) | !g.containsNode(last)) {
			throw new NoSuchElementException ("Node not in graph"); 
		}
		
		
		Queue<ArrayList<Edge<E, Double>>> active = 
				new PriorityQueue<ArrayList<Edge<E, Double>>>(1, new PathComparator<E>()); 
		Set<E> finished = new HashSet<E>(); 
		
		// Adds the only known path to active, i.e. the path from first to itself
		// with a cost of 0.0, which is the empty path
		active.add(new ArrayList<Edge<E, Double>>()); 
		
		while (!active.isEmpty()) {
			// minPath is the lowest-cost path in active 
			// minDest is the last node in that path
			ArrayList<Edge<E, Double>> minPath = active.remove(); 
			
			// sets minDest to first to handle the case of the empty 
			// list when the loop is entered for the first time 
			E minDest = first; 
			if (!minPath.isEmpty()) {
				minDest = minPath.get(minPath.size() - 1).getChild();
			}
			
			if (minDest.equals(last)) {
				return minPath; 
			}
			
			if (!finished.contains(minDest)) {
				for (Edge<E, Double> e: g.getOutEdgesOf(minDest)) {
					if (!finished.contains(e.getChild())) {
						ArrayList<Edge<E, Double>> newPath = new ArrayList<Edge<E, Double>>(minPath);
						newPath.add(e);
						active.add(newPath);
					}
				}
				
				finished.add(minDest); 
			}
		}
		
		return null;
	}
}
