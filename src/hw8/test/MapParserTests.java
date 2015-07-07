package hw8.test;

import static org.junit.Assert.*;
import hw5.Edge;
import hw8.MapParser;
import hw8.MapParser.MalformedDataException;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MapParserTests {
	
	@Test
	public void buildingsSimple1() throws MalformedDataException {
		Map<String, String> names = new HashMap<String, String>();
		Map<String, Point2D.Double> coordinates = new HashMap<String, Point2D.Double>(); 
		
		MapParser.parseDataBuildings("buildings_simple1.dat", names, coordinates);
		
		Map<String, String> compNames = new HashMap<String, String>();
		compNames.put("b1", "building1");
		compNames.put("b2", "building2");
		compNames.put("b3", "building3");
		compNames.put("b4", "building4");
		
		Map<String, Point2D.Double> compCoords = new HashMap<String, Point2D.Double>(); 
		compCoords.put("b1", new Point2D.Double(0,0));
		compCoords.put("b2", new Point2D.Double(1,0));
		compCoords.put("b3", new Point2D.Double(0,1));
		compCoords.put("b4", new Point2D.Double(1,1));
		
		
		assertEquals(names, compNames);
		assertEquals(coordinates, compCoords); 
	}
	
	@Test
	public void pathsSimple1() throws MalformedDataException {
		Set<Edge<Point2D.Double, Double>> edges = MapParser.parseDataPaths("paths_simple1.dat");
		
		Set<Edge<Point2D.Double, Double>> compEdges = new HashSet<Edge<Point2D.Double, Double>>();
		
		Point2D.Double p1 = new Point2D.Double(0, 0);
		Point2D.Double p2 = new Point2D.Double(1, 0);
		Point2D.Double p3 = new Point2D.Double(0, 1);
		Point2D.Double p4 = new Point2D.Double(1, 1);
		
		compEdges.add(new Edge<Point2D.Double, Double>(p1, p2, 3.0));
		compEdges.add(new Edge<Point2D.Double, Double>(p1, p3, 4.0));
		compEdges.add(new Edge<Point2D.Double, Double>(p1, p4, 5.0));
		
		compEdges.add(new Edge<Point2D.Double, Double>(p2, p1, 3.0));
		compEdges.add(new Edge<Point2D.Double, Double>(p2, p3, 5.0));
		compEdges.add(new Edge<Point2D.Double, Double>(p2, p4, 4.0));
		
		compEdges.add(new Edge<Point2D.Double, Double>(p3, p1, 4.0));
		compEdges.add(new Edge<Point2D.Double, Double>(p3, p2, 5.0));
		compEdges.add(new Edge<Point2D.Double, Double>(p3, p4, 3.0));
		
		compEdges.add(new Edge<Point2D.Double, Double>(p4, p1, 5.0));
		compEdges.add(new Edge<Point2D.Double, Double>(p4, p2, 4.0));
		compEdges.add(new Edge<Point2D.Double, Double>(p4, p3, 3.0));
		
		assertEquals(edges, compEdges); 
		
	}

}
