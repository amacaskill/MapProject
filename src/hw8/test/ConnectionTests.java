package hw8.test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import hw8.Connection;

import org.junit.Test;

public class ConnectionTests {
	
	Connection n = new Connection(new Point2D.Double(0, 0), new Point2D.Double(1, 1), 10.0); 
	
	@Test
	public void getSourceTest() {
		assertEquals(n.getSource(), new Point2D.Double(0, 0)); 
	}
	
	@Test
	public void getDestTest() {
		assertEquals(n.getDest(), new Point2D.Double(1, 1)); 
	}
	
	@Test
	public void getDistTest() {
		assert(n.getDist() == 10.0); 
	}
	
	@Test
	public void getDirTestN() {
		Connection m = new Connection(new Point2D.Double(1, 1), new Point2D.Double(1, 0), 10.0); 
		assertEquals(m.getDir(), "N"); 
	}
	
	@Test
	public void getDirTestS() {
		Connection m = new Connection(new Point2D.Double(1, 1), new Point2D.Double(1, 2), 10.0); 
		assertEquals(m.getDir(), "S"); 
	}
	
	@Test
	public void getDirTestE() {
		Connection m = new Connection(new Point2D.Double(1, 1), new Point2D.Double(2, 1), 10.0); 
		assertEquals(m.getDir(), "E"); 
	}
	
	@Test
	public void getDirTestW() {
		Connection m = new Connection(new Point2D.Double(1, 1), new Point2D.Double(0, 1), 10.0); 
		assertEquals(m.getDir(), "W"); 
	}
	
	@Test
	public void getDirTestNW() {
		Connection m = new Connection(new Point2D.Double(1, 1), new Point2D.Double(0, 0), 10.0); 
		assertEquals(m.getDir(), "NW"); 
	}
	
	@Test
	public void getDirTestNE() {
		Connection m = new Connection(new Point2D.Double(1, 1), new Point2D.Double(2, 0), 10.0); 
		assertEquals(m.getDir(), "NE"); 
	}
	
	@Test
	public void getDirTestSW() {
		Connection m = new Connection(new Point2D.Double(1, 1), new Point2D.Double(0, 2), 10.0); 
		assertEquals(m.getDir(), "SW"); 
	}

	@Test
	public void getDirTestSE() {
		Connection m = new Connection(new Point2D.Double(1, 1), new Point2D.Double(2, 2), 10.0); 
		assertEquals(m.getDir(), "SE"); 
	}
}
