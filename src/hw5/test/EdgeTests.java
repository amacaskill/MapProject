package hw5.test;

import static org.junit.Assert.*;
import hw5.Edge;

import org.junit.Test;

public class EdgeTests {
	private String a = "node1";
	private String b = "node2";
	private String c = "label1"; 
	private String d = "node3";
	private String e = "node4";
	private String f = "label2";
	
	private Edge<String, String> e0 = new Edge<String, String>(a, b, c); 
	private Edge<String, String> e1 = new Edge<String, String>(a, b, c);
	private Edge<String, String> e2 = new Edge<String, String>(d, e, f); 
	private Edge<String, String> e3 = new Edge<String, String>(d, b, c);
	private Edge<String, String> e4 = new Edge<String, String>(a, e, c);
	private Edge<String, String> e5 = new Edge<String, String>(a, b, f);
	
	@Test
	public void testGetParent() {
		assertEquals(e1.getParent(), a);
		assertEquals(e2.getParent(), d);
		assertEquals(e3.getParent(), d);
		assertEquals(e4.getParent(), a);
		assertEquals(e5.getParent(), a);
	}
	
	@Test
	public void testGetChild() {
		assertEquals(e1.getChild(), b);
		assertEquals(e2.getChild(), e);
		assertEquals(e3.getChild(), b);
		assertEquals(e4.getChild(), e);
		assertEquals(e5.getChild(), b);
	}
	
	@Test
	public void testGetLabel() {
		assertEquals(e1.getLabel(), c);
		assertEquals(e2.getLabel(), f);
		assertEquals(e3.getLabel(), c);
		assertEquals(e4.getLabel(), c);
		assertEquals(e5.getLabel(), f);
	}
	
	@Test
	public void testEquals() {
		assertTrue(e1.equals(e0)); 
		assertTrue(e1.equals(e1));
		assertFalse(e1.equals(e2));
		assertFalse(e1.equals(e3));
		assertFalse(e1.equals(e4));
		assertFalse(e1.equals(e5));
	}


}
