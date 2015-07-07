package hw7;

import hw5.Edge;
import hw5.Graph;
import hw6.MarvelPaths;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MarvelPaths2 {
	
	// Marvel Paths is not an ADT so there is no representation invariant or 
	// abstraction function.
	
	public static Graph<String, Double> buildGraph (String filename) {
		Graph<String, String> g1 = MarvelPaths.buildGraph(filename); 
		
		Set<String> characters = g1.getNodeSet(); 
		Set<Edge<String, Double>> edges = new HashSet<Edge<String, Double>>();
		
		for (String s: characters) {
			Set<Edge<String, String>> children = g1.getOutEdgesOf(s); 
			
			Map<String, Integer> m = new HashMap<String, Integer>(); 
			
			for (Edge<String, String> e: children) {
				String child = e.getChild();
				if (m.containsKey(child)) {
					m.put(child, m.get(child) + 1); 
				} else {
					m.put(child,  1); 
				}
			}
			
			for (String t : m.keySet()) {
				double relation = (double) 1 / m.get(t);
				edges.add(new Edge<String, Double>(s, t, relation)); 
				edges.add(new Edge<String, Double>(t, s, relation));
			}
		}
		
		return new Graph<String, Double>(characters, edges);
	}
}

