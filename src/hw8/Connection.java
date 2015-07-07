package hw8;

import hw5.Edge;

import java.awt.geom.Point2D;

/**
 * Abstract Value:
 * 		Represents an immutable connection between two coordinates on a map, storing
 * 		a beginning coordinate, a destination coordinate, the distance between
 *      two coordinates in feet, and the direction of travel between the start
 *      and destination.
 * 
 * Specification Fields:
 * 		@specified Starting Coordinate: Point // The coordinate of a location or place
 * 											  // on an image of a map that represents 
 * 											  // a starting location
 *		@specified Destination Coordinate: Point // The coordinate of a location or place
 * 											     // on an image of a map that represents 
 * 											     // a final location
 * 		@specified Distance : double // The physical distance between the two locations 
 * 									 // represented by the above coordinates
 * 		@specified Direction: String // The physical direction of travel from the starting
 * 									 // coordinate to the destination coordinate
 * 
 * Abstract Invariant:
 * 		// Distances are all positive
 * 		// Directions are N, S, E, W, NE, NW, SE, and SW -- each having an eighth of the
 * 		   degrees on a compass
 *
 */
public class Connection {
	private final Edge<Point2D.Double, Double> e; 
	private final String direction; 
	
	/*
	 * Representation Invariant: 
	 * 		// e.getLabel() >= 0
	 * 
	 * Abstraction Function: 
	 * 		// e stores the coordinates of the start and end as Point2D.Double
	 *         objects, and the distance as a double
	 *      // direction stores the direction between the start and end locations
	 *         as a String
	 */
	
	/**
	 * Constructs a new Connection from start and end coordinates and the
	 * distance between them. 
	 * 
	 * @param from The first Point on the map.
	 * @param to The destination Point on the map. 
	 * @param dist The distance in feet between the two locations. 
	 * @effects Constructs a new Connection. 
	 */
	public Connection(Point2D.Double from, Point2D.Double to, double dist) {
		e = new Edge<Point2D.Double, Double>(from, to, dist);
		direction = getDirection(e); 
		checkRep(); 
	}
	
	/**
	 * Returns the coordinates of the start location. 
	 * 
	 * @return Returns a copy of the start coordinates, changing this object
	 *         does not modify the MapPath
	 */
	public Point2D.Double getSource() {
		return new Point2D.Double(e.getParent().getX(),e.getParent().getY());
	}
	

	/**
	 * Returns the coordinates of the destination location. 
	 * 
	 * @return Returns a copy of the destination coordinates, changing this object
	 *         does not modify the MapPath
	 */
	public Point2D.Double getDest() {
		return new Point2D.Double(e.getChild().getX(),e.getChild().getY());
	}
	
	/**
	 * Returns the distance from the start to the destination. 
	 * 
	 * @return Returns the distance, in feet, from the start location to the 
	 *         destination location
	 */
	public double getDist() {
		return e.getLabel(); 
	}
	
	/**
	 * Returns the direction of travel from the start location to the destination 
	 * location. 
	 * 
	 * @return Returns the geographical direction between the two coordinates. 
	 */
	public String getDir() {
		return direction; 
	}
	
	@Override
	public String toString() {
		return "(" + e.getParent().toString() + ", " + e.getChild().toString() + ", " + e.getLabel() + ", " 
	            + direction + ")"; 
	}
	
	// Calculates the direction between the coordinates 
	private String getDirection(Edge<Point2D.Double, Double> e) {
		double x1 = e.getParent().getX();
		double x2 = e.getChild().getX(); 
		double y1 = e.getParent().getY();
		double y2 = e.getChild().getY(); 
		double angle = Math.atan2((y1 - y2), (x2 - x1));
		if (angle < (7 * Math.PI / 8) && angle > (5 * Math.PI / 8)) {
			return "NW";
		} else if (angle <= (5 * Math.PI / 8) && angle >= (3 * Math.PI / 8)) {
			return "N"; 
		} else if (angle < (3 * Math.PI / 8) && angle > (Math.PI / 8)) {
			return "NE";
		} else if (angle <= (Math.PI / 8) && angle >= -(Math.PI / 8)) {
			return "E";
		} else if (angle < -(Math.PI / 8) && angle > -(3 * Math.PI / 8)) {
			return "SE";
		} else if (angle <= -(3 * Math.PI / 8) && angle >= -(5 * Math.PI / 8)) {
			return "S";
		} else if (angle < -(5 * Math.PI / 8) && angle > -(7 * Math.PI / 8)) {
			return "SW";
		} else {
			return "W"; 
		}
	}
	
	private void checkRep() {
		assert(e.getLabel() >= 0); 
	}
}
