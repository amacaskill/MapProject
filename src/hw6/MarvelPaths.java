package hw6;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hw5.Edge;
import hw5.Graph;
import hw6.MarvelParser.MalformedDataException;

/**
 * 
 * @author Hope Crandall
 *
 */

public class MarvelPaths {

	// Marvel Paths is not an ADT so there is no representation invariant or 
	// abstraction function.
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in); 
		
		System.out.println("Filename: ");
		
		String filename = in.next(); 
		
		Graph<String, String> g = buildGraph(filename); 
		
		boolean cont = true; 
		
		while(cont) {
			System.out.println("Two character names (e to exit): ");
			String char1 = in.next();
			if (char1.equals("e")) {
				cont = false;
			} else {
				String char2 = in.next(); 
				if (!g.containsNode(char1) | !g.containsNode(char2)) {
					if (!g.containsNode(char1)) {
						System.out.println(char1 + "is not in the dataset");
					}
					if (!g.containsNode(char2)) {
						System.out.println(char2 + "is not in the dataset");
					}
				}
				ArrayList<Edge<String, String>> path = getPath(g, char1, char2);
				if (path == null) {
					System.out.println("No path found");
				} else {
					System.out.println("Path from " + char1 + " to " + char2 + ":");
					System.out.println(path.toString()); 
				}
			}
		}	
	}
	
	
	/**
	 * Takes the data from a tsv file and returns a Graph 
	 * representation of it.
	 * 
	 * @param filename The filename of a tsv file.
	 * @return Returns a Graph populated by data from the tsv file.
	 */
	public static Graph<String, String> buildGraph (String filename) {
		Set<String> characters = new HashSet<String>(); 
		Map<String, List<String>> books = new HashMap<String, List<String>>();
		try {
			MarvelParser.parseData(filename, characters, books);
		} catch (MalformedDataException e) {
			e.printStackTrace();
			System.exit(1);
		} 
		
		Set<Edge<String, String>> edges = new HashSet<Edge<String, String>>(); 
		
		// creates Edges to be added to Graph by going through
		// each list of characters for each book in the Map books
		for (String s: books.keySet()) {
			List<String> chars = books.get(s);
			for (int i = 0; i < chars.size(); i++) {
				String c1 = chars.get(i); 
				// creates Edges both ways between the above node and the
				// remaining nodes in the list 
				for (int j = i+1; j < chars.size(); j++) {
					String c2 = chars.get(j);
					edges.add(new Edge<String, String>(c1, c2, s));
					edges.add(new Edge<String, String>(c2, c1, s)); 
				}
			}
		}
		
		return new Graph<String, String>(characters, edges); 
	}
	
	/**
	 * Finds and returns the alphabetically least path in the given Graph
	 * between the two given nodes as an ArrayList of Edges
	 * 
	 * @param g The Graph whose paths will be searched.
	 * @param char1 The node at the beginning of the path.
	 * @param char2 The node at the end of the path. 
	 * @requires Requires that char1 and char2 are in g and not null and that
	 *           g is not null.
	 * @throws If char1 or char2 is not in g, throws NoSuchElementException.
	 * 		   If char1, char2, or g is null, throws IllegalArgumentException.
	 * @return Returns the alphabetically least path between char1 and char2 in 
	 *         g as an ArrayList. If char1 and char2 are the same node, returns 
	 *         the empty list. If there is no path between char1 and char2 
	 *         returns null. 
	 */
	public static ArrayList<Edge<String, String>> getPath (Graph<String, String> g, String char1, String char2) {
		if (g == null | char1 == null | char2 == null) {
			throw new IllegalArgumentException("Null ");
		}
		if (!g.containsNode(char1) | !g.containsNode(char2)) {
			throw new NoSuchElementException("Node not in graph"); 
		}
		
		LinkedList<String> visitNodes = new LinkedList<String>(); 
		Map<String, ArrayList<Edge<String, String>>> paths = new HashMap<String, ArrayList<Edge<String, String>>>();
		visitNodes.push(char1); 
		paths.put(char1, new ArrayList<Edge<String, String>>()); 
		while (!visitNodes.isEmpty()) {
			String last = visitNodes.remove(0);
			if (last.equals(char2)) {
				return paths.get(last); 
			} // else, not at the end of the path, keep looking
			Set<Edge<String, String>> nextNodes = g.getOutEdgesOf(last); 
			Set<String> nodesInOrder = new TreeSet<String>();
			for (Edge<String, String> e: nextNodes) {
				String nextNode = e.getChild();
				if (!paths.containsKey(nextNode)) {
					ArrayList<Edge<String, String>> oldPath = paths.get(e.getParent());
					ArrayList<Edge<String, String>> newPath = new ArrayList<Edge<String, String>>(oldPath);
					newPath.add(e); 
					paths.put(nextNode, newPath);
					nodesInOrder.add(e.getChild());
				} else {
					// if node is already in the graph, checks the last edge
					// if its parent node equals last, then checks which edge has 
					// the alphabetically lowest label and ensures that one is in the list
					ArrayList<Edge<String, String>> oldPath = paths.get(nextNode);
					if (!oldPath.isEmpty()) {
						Edge<String, String> lastEdge = oldPath.get(oldPath.size() - 1); 
						String lastLabel = lastEdge.getLabel();
						String thisLabel = e.getLabel();
						if (lastEdge.getParent().equals(last) 
						    && lastLabel.compareTo(thisLabel) > 0) {
						    oldPath.remove(oldPath.size() - 1);
							oldPath.add(e); 
						}
					}
				} 
			}
			
			// uses TreeSet to store the nodes visited in the above for-each
			// loop in order, then adds them to the visitNodes list alphabetically
			// below -- necessary because getOutEdgesOf returns an unordered Set
			for (String s: nodesInOrder) {
				visitNodes.add(s);
			}
		}
	
		return null; 
	}	
}
