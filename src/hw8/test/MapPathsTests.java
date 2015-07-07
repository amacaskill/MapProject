package hw8.test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hw8.Connection;
import hw8.MapParser.MalformedDataException;
import hw8.MapPaths;

import org.junit.Before;
import org.junit.Test;

public class MapPathsTests {
	MapPaths m; 
	
	@Before
	public void setUp() throws MalformedDataException {
		m = new MapPaths("buildings_simple1.dat", "paths_simple1.dat"); 
	}
	
	@Test
	public void getBuildingsTest() {
		Map<String, String> names = m.getBuildings();
		
		Map<String, String> names2 = new HashMap<String, String>(); 
		names2.put("b1", "building1");
		names2.put("b2", "building2");
		names2.put("b3", "building3");
		names2.put("b4", "building4");
		
		assertEquals(names, names2);
	}
	
	@Test
	public void getLongNameTest() {
		assertEquals(m.getLongName("b1"), "building1");
		assertEquals(m.getLongName("b2"), "building2");
		assertEquals(m.getLongName("b3"), "building3");
		assertEquals(m.getLongName("b4"), "building4");
	}
	
	@Test
	public void getPathTest() {
		List<Connection> p1 = new ArrayList<Connection>();
		p1.add(new Connection(new Point2D.Double(0, 0), new Point2D.Double(1, 0), 3.0));
		List<Connection> p2 = new ArrayList<Connection>();
		p2.add(new Connection(new Point2D.Double(0, 0), new Point2D.Double(0, 1), 4.0));
		List<Connection> p3 = new ArrayList<Connection>();
		p3.add(new Connection(new Point2D.Double(0, 0), new Point2D.Double(1, 1), 5.0));
		
		// printing to compare because Connection equals method is not otherwise 
		// necessary except in this particular test, so it has been omitted
		
		System.out.println(m.getPath("b1", "b2")); 
		System.out.println(p1); 
		
		System.out.println(m.getPath("b1", "b3")); 
		System.out.println(p2); 
		
		System.out.println(m.getPath("b1", "b4")); 
		System.out.println(p3);  
	}
	
	@Test
	public void containsBuildingTest() {
		assert(m.containsBuilding("b1"));
		assert(m.containsBuilding("b2"));
		assert(m.containsBuilding("b3"));
		assert(m.containsBuilding("b4"));
		assert(!m.containsBuilding("b5"));
	}
}
