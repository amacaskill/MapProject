package hw8;

/**
 * @author Hope Crandall
 * 
 * Abstract Value:
 * 	This class represents an immutable map, which stores locations and paths between them. 
 * 
 * Specification Fields:
 * 		@specified location :  Point  // A location stores a coordinate pair 
 * 								      // (Point) of pixels on a given map picture 
 * 		@specified place : String  // A location that has a particular name, in an 
 *                                 // abbreviated and non-abbreviated form
 * 		@specified connections : Connection // stores two locations in the MapPaths
 * 											// object, the distance, in feet, of the 
 * 											// path between them, and the geographical direction
 * 											// traveled from the starting location to the ending
 * 											// location
 * 
 * Derived Specification Fields:
 * 		@specified Paths : List<Connection>  // a list of connections defined by a starting location
 *  										 // and an ending location (the destination), such that
 *  										 // following each connection from its start location to 
 *  										 // its end location (the start of the next connection)
 *  										 // will lead to a final connection with the destination
 *  										 // as its end
 * Abstract Invariant: 
 * 		// No two places have the same abbreviated name, non-abbreviated name, or coordinates
 * 		// No two Connections can store exactly the same information
 *      // There is a path between every two places in MapPath
 * 
 */

import hw5.Graph;
import hw5.Edge;
import hw7.LeastCostPath;
import hw8.MapParser.MalformedDataException;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class MapPaths {
	private final Map<String, String> names;
	private final Map<String, Point2D.Double> coordinates;
	private final Graph<Point2D.Double, Double> graph; 
	
	/*
	 * Representation Invariant: 
	 * 		// There are no duplicates in the list of values stored by names
	 * 		// There are no duplicates in the list of values stored by coordinates
	 * 
	 * Abstraction Function:
	 * 		// names stores abbreviated names as keys and associates them with their
	 * 		   long names
	 * 		// coordinates stores a place's abbreviated name as a key and its 
	 *         coordinates as Point2D.Double objects
	 *      // graph stores the information of all a MapGraph's connections, except 
	 *         the direction, which is calculated only if a path is returned from 
	 *         getPath
	 * 
	 */
	
	/**
	 * Constructs a new MapPath object populated by the places, locations, and connections
	 * provided by the input files. 
	 * 
	 * @param buildingFile	A file containing a list of places, including their abbreviated name,
	 * 						long name, x-coordinate, and y-coordinate as tab separated elements,
	 * 						with each place on its own line. 
	 * @param pathFile A file containing a list of locations and how those locations are related
	 *                 to other locations by distance. A location will be on its own line in the 
	 *                 format x-coordinate, y-coordinate, followed by new tabbed in lines for each
	 *                 location it is connected to, in the format x-coordinate,y-coordinate: distance.	
	 * @throws MalformedDataException Throws exception if one of the passed in files
	 * 								  is formatted incorrectly. 
	 * @effects Constructs a new MapPath object. 
	 */
	public MapPaths(String buildingFile, String pathFile) throws MalformedDataException {
		names = new HashMap<String, String>();
		coordinates = new HashMap<String, Point2D.Double>();
		
		MapParser.parseDataBuildings(buildingFile, names, coordinates);
		
		Set<Edge<Point2D.Double, Double>> edges = MapParser.parseDataPaths(pathFile); 
		
		graph = new Graph<Point2D.Double, Double>(coordinates.values(), new HashSet<Edge<Point2D.Double, Double>>()); 
		for (Edge<Point2D.Double, Double> e: edges) {
			graph.insertNode(e.getParent());
			graph.insertNode(e.getChild());
			graph.insertEdge(e.getParent(), e.getChild(), e.getLabel());
		}
		checkRep(); 
	}
	
	/**
	 * Gets a Map<String, String> of all places, such that each abbreviated name
	 * is a key mapped to its corresponding long name. 
	 * 
	 * @return Returns a map of all places in the MapPath. 
	 */
	public Map<String, String> getBuildings() {
		return Collections.unmodifiableMap(names); 
	}
	
	/**
	 * Gets the long name corresponding to the entered short name. 
	 * 
	 * @param shortName The abbreviated name of a particular place.
	 * @throws Throws a NoSuchElementException if shortName is not a
	 * 		   place in the MapPath.
	 * @return Returns the long name associated with shortName. 
	 */
	public String getLongName(String shortName) {
		if (!this.containsBuilding(shortName)) {
			throw new NoSuchElementException();
		}
		return names.get(shortName); 
	}
	
	/**
	 * Returns the the shortest path between the two places. 
	 * 
	 * @param n1 The abbreviated name for the first place in the path. 
	 * @param n2 The abbreviated name for the last place in the path. 
	 * @throws Throws NoSuchElementException if either of the parameters are
	 *         not places within the MapPath
	 * @return Returns the shortest path between n1 and n2 in MapPath. 
	 */
	public List<Connection> getPath(String n1, String n2) {
		if (!this.containsBuilding(n1) | !this.containsBuilding(n2)){
			throw new NoSuchElementException(); 
		}
		Point2D.Double p1 = coordinates.get(n1);
		Point2D.Double p2 = coordinates.get(n2);
		List<Edge<Point2D.Double, Double>> edgelist = LeastCostPath.getPath(graph, p1, p2); 
		List<Connection> path = new ArrayList<Connection>();
		for (Edge<Point2D.Double, Double> e: edgelist) {
			path.add(new Connection(e.getParent(), e.getChild(), e.getLabel())); 
		}
		return path; 
	}
	
	/**
	 * Returns whether or not a place in is the MapPath. 
	 * 
	 * @param shortName An abbreviated name of a place potentially in MapPath.
	 * @return Returns true if shortName corresponds to a place in the MapPath and
	 *         false if not. 
	 */
	public boolean containsBuilding(String shortName) {
		return names.containsKey(shortName); 
	}
	
	/**
	 * Returns the coordinates of a given place.
	 * 
	 * @param shortName An abbreviated name of a place in the MapPath.
	 * @throws Throws NoSuchElementException if shortName is not a place in the MapPath. 
	 * @return Returns the coordinates of shortName in the MapPath.
	 */
	public Point2D.Double getBuildingCoords(String shortName) {
		if (!containsBuilding(shortName)) {
			throw new NoSuchElementException(); 
		}
		Point2D.Double coords = coordinates.get(shortName); 
		return new Point2D.Double(coords.getX(), coords.getY()); 
	}
	
	private void checkRep() {
		boolean testing = false; 
		if (testing) {
			List<String> longNames = (List<String>) names.values(); 
			Set<String> setNames = new HashSet<String>(longNames); 
			assert(longNames.size() == setNames.size()); 
			
			List<Point2D.Double> coords = (List<Point2D.Double>) coordinates.values(); 
			Set<Point2D.Double>  setCoords = new HashSet<Point2D.Double>(coords);
			assert(coords.size() == setCoords.size()); 
		}
	}
}
