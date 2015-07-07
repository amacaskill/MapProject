package hw5.test;

import static org.junit.Assert.*;
import hw5.Graph;
import hw5.Edge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class GraphTests {
	
	String n1 = "node1";
	String n2 = "node2";
	String n3 = "node3";
	String n4 = "node4";
	String n5 = "node5";
    String n6 = "node6"; 
	String n7 = "node7"; 
	    
	String l1 = "label1";
	String l2 = "label2";
	String l3 = "label3";
	    
	String[] a1 = {n1, n2, n3, n4, n5, n6, n7}; 
	HashSet<String> nodes = new HashSet<String>(Arrays.asList(a1));
		
	Edge<String, String> e1 = new Edge<String, String>(n1, n2, l1);
	Edge<String, String> e2 = new Edge<String, String>(n2, n5, l1); 
	Edge<String, String> e3 = new Edge<String, String>(n2, n5, l2); 
	Edge<String, String> e4 = new Edge<String, String>(n2, n5, l3); 
	Edge<String, String> e5 = new Edge<String, String>(n3, n2, l1); 
	Edge<String, String> e6 = new Edge<String, String>(n3, n4, l1);
	Edge<String, String> e7 = new Edge<String, String>(n3, n5, l1);
	Edge<String, String> e8 = new Edge<String, String>(n5, n2, l1);
	Edge<String, String> e9 = new Edge<String, String>(n5, n4, l1);
	Edge<String, String> e10 = new Edge<String, String>(n7, n7, l1); 
	
	HashSet<Edge<String, String>> edges = new HashSet<Edge<String, String>>();

	Graph<String, String> empty1;
	Graph<String, String> empty2;
	Graph<String, String> main; 
	
	
	@Before 
	public void setUpGraph() { 
		edges.add(e1); 
		edges.add(e2);
		edges.add(e3);
		edges.add(e4);
		edges.add(e5);
		edges.add(e6);
		edges.add(e7);
		edges.add(e8);
		edges.add(e9);
		edges.add(e10);
		
		empty1 = new Graph<String, String>();
		empty2 = new Graph<String, String>(new HashSet<String>(), new HashSet<Edge<String, String>>());
		main = new Graph<String, String>(nodes, edges);
	}
	
	

	// ***** CONSTRUCTOR TESTS *****
	
	
	@Test(expected=NoSuchElementException.class)
	public void testConstructorBadEdge1() {
		new Graph<String, String>(new HashSet<String>(), edges); 
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullArg() {
		new Graph<String, String>(null, null); 
	}
	
	
	// ***** IS EMPTY TESTS *****
	@Test
	public void testIsEmptyTrue() {
		assert(empty2.isEmpty()); 
		assert(empty1.isEmpty());
	}
	
	@Test
	public void testIsEmptyFalse() {
		assert(!main.isEmpty()); 
	}
	
	
	// ***** CONTAINS Edge<String, String> TESTS ******
	
	@Test
	public void testContainsEdgeSpecificFalse() {
		assert(!main.containsEdge(n1, n2, "label4"));
		assert(!main.containsEdge(n1, n6, l1)); 
		assert(!main.containsEdge(n2, n1, l1)); 
		assert(!main.containsEdge(n2, n5, "label4"));
	}
	
	@Test
	public void testContainsEdgeSpecificTrue() {
		assert(main.containsEdge(n1, n2, l1));
		assert(main.containsEdge(n2, n5, l1));
		assert(main.containsEdge(n2, n5, l2));
		assert(main.containsEdge(n3, n4, l1));
	}
	
	@Test
	public void testContainsEdgeNoLabelNone() {
		assert(!main.containsEdge(n6, n6));
		assert(!main.containsEdge(n1, n4));
		assert(!main.containsEdge(n4, n3));
	}
	
	@Test
	public void testContainsEdgeNoLabelOne() {
		assert(main.containsEdge(n7, n7));
		assert(main.containsEdge(n1, n2)); 
	}
	
	@Test
	public void testContainsEdgeNoLabelMult() {
		assert(main.containsEdge(n2, n5)); 
	}
	
	// ***** CONTAINS NODE TESTS ****
	
	@Test
	public void testContainsNode() {
		assert(!main.containsNode("node8")); 
		assert(main.containsNode(n1));
		assert(main.containsNode(n6));
		assert(main.containsNode(n7)); 
	}
	
	
	// ***** GET Labels BETWEEN TESTS *****
	
	@Test
	public void testGetEdgesBetweenNone() {
		HashSet<String> s = new HashSet<String>(); 
		assertEquals(main.getLabelsBetween(n1, n3), s);
		assertEquals(main.getLabelsBetween(n6, n3), s);
		assertEquals(main.getLabelsBetween(n4, n5), s); 
		s.add(l1);
		s.add(l2);
		s.add(l3); 
		assertEquals(main.getLabelsBetween(n2, n5), s);
	}
	
	@Test
	public void testGetEdgesBetweenOne() {
		HashSet<String> s = new HashSet<String>(); 
		s.add(l1);
		assertEquals(main.getLabelsBetween(n1, n2), s);
		assertEquals(main.getLabelsBetween(n3, n4), s);
		assertEquals(main.getLabelsBetween(n7, n7), s);
	}
	
	@Test
	public void testGetEdgesBetweenMult() {
		HashSet<String> s = new HashSet<String>(); 
		s.add(l1);
		s.add(l2);
		s.add(l3); 
		assertEquals(main.getLabelsBetween(n2, n5), s);
	}
	
	// ****** GET IN Edges *******
	
	@Test
	public void testGetInEdgesNone() {
		HashSet<Edge<String, String>> e = new HashSet<Edge<String, String>>(); 
		assertEquals(main.getInEdgesOf(n3), e);
		assertEquals(main.getInEdgesOf(n1), e);
	}
	
	@Test
	public void testGetInEdgesMult() {
		HashSet<Edge<String, String>> e = new HashSet<Edge<String, String>>(); 
		e.add(e10);
		assertEquals(main.getInEdgesOf(n7), e);
		
		HashSet<Edge<String, String>> es1 = new HashSet<Edge<String, String>>(); 
		es1.add(e1);
		es1.add(e5);
		es1.add(e8);
		assertEquals(main.getInEdgesOf(n2), es1);
		
		HashSet<Edge<String, String>> es2 = new HashSet<Edge<String, String>>(); 
		es2.add(e9);
		es2.add(e6);
		assertEquals(main.getInEdgesOf(n4), es2); 
		
		HashSet<Edge<String, String>> es3 = new HashSet<Edge<String, String>>(); 
		es3.add(e2);
		es3.add(e3);
		es3.add(e4);
		es3.add(e7);
		assertEquals(main.getInEdgesOf(n5), es3); 
	}
	
	@Test
	public void testGetOutEdgesNone() {
		HashSet<Edge<String, String>> e = new HashSet<Edge<String, String>>(); 
		assertEquals(main.getOutEdgesOf(n6), e);
		assertEquals(main.getOutEdgesOf(n4), e);
	}
	
	@Test
	public void testGetOutEdgesMult() {
		HashSet<Edge<String, String>> e = new HashSet<Edge<String, String>>();
		e.add(e10);
		assertEquals(main.getOutEdgesOf(n7), e);
		
		HashSet<Edge<String, String>> es1 = new HashSet<Edge<String, String>>(); 
		es1.add(e5);
		es1.add(e7);
		es1.add(e6);
		assertEquals(main.getOutEdgesOf(n3), es1); 
		
		HashSet<Edge<String, String>> es2 = new HashSet<Edge<String, String>>(); 
		es2.add(e2);
		es2.add(e3);
		es2.add(e4); 
		assertEquals(main.getOutEdgesOf(n2), es2);
		
		HashSet<Edge<String, String>> es3 = new HashSet<Edge<String, String>>(); 
		es3.add(e8);
		es3.add(e9);
		assertEquals(main.getOutEdgesOf(n5), es3);	
	}
	
	// ******* GET ALL Edges TESTS *****
	
	@Test
	public void testGetAllEdgesNone() {
		HashSet<Edge<String, String>> e = new HashSet<Edge<String, String>>();
		assertEquals(main.getAllEdgesOf(n6), e); 
	}
	
	@Test
	public void testGetAllEdgesIn() {
		HashSet<Edge<String, String>> es2 = new HashSet<Edge<String, String>>(); 
		es2.add(e9);
		es2.add(e6);
		assertEquals(main.getAllEdgesOf(n4), es2); 
	}
	
	@Test
	public void testGetAllEdgesOut() {
		HashSet<Edge<String, String>> es1 = new HashSet<Edge<String, String>>(); 
		es1.add(e5);
		es1.add(e7);
		es1.add(e6);
		assertEquals(main.getAllEdgesOf(n3), es1); 
	}
	
	@Test
	public void testGetAllEdgesBoth() {
		HashSet<Edge<String, String>> es1 = new HashSet<Edge<String, String>>(); 
		es1.add(e2);
		es1.add(e3);
		es1.add(e4); 
		es1.add(e5);
		es1.add(e1);
		es1.add(e8);
		assertEquals(main.getAllEdgesOf(n2), es1);
		
		HashSet<Edge<String, String>> es2 = new HashSet<Edge<String, String>>(); 
		es2.add(e8);
		es2.add(e9);
		es2.add(e2);
		es2.add(e3);
		es2.add(e4);
		es2.add(e7); 
		assertEquals(main.getAllEdgesOf(n5), es2);	
	}
	
	// ****** GET PARENTS TESTS ******
	
	@Test
	public void testGetParentsNone() {
		HashSet<String> p = new HashSet<String>();
		assertEquals(main.getParentsOf(n6), p); 
		assertEquals(main.getParentsOf(n3), p); 
	}
	
	@Test
	public void testGetParentsMult() {
		HashSet<String> p1 = new HashSet<String>();
		p1.add(n7);
		assertEquals(main.getParentsOf(n7), p1);
		
		HashSet<String> p2 = new HashSet<String>();
		p2.add(n3);
		p2.add(n5);
		assertEquals(main.getParentsOf(n4), p2);
		
		HashSet<String> p3 = new HashSet<String>();
		p3.add(n3);
		p3.add(n5);
		p3.add(n1);
		assertEquals(main.getParentsOf(n2), p3);
		

		HashSet<String> p4 = new HashSet<String>();
		p4.add(n3);
		p4.add(n2);
		assertEquals(main.getParentsOf(n5), p4);	
	}

	// ***** GET CHILDREN TESTS ******
	
	@Test
	public void testGetChildrenNone() {
		HashSet<String> p = new HashSet<String>();
		assertEquals(main.getChildrenOf(n6), p); 
		assertEquals(main.getChildrenOf(n4), p); 
	}
	
	@Test
	public void testGetChildrenMult() {
		HashSet<String> p1 = new HashSet<String>();
		p1.add(n7);
		assertEquals(main.getChildrenOf(n7), p1);
		
		HashSet<String> p2 = new HashSet<String>();
		p2.add(n4);
		p2.add(n5);
		p2.add(n2);
		assertEquals(main.getChildrenOf(n3), p2);
		
		HashSet<String> p3 = new HashSet<String>();
		p3.add(n4);
		p3.add(n2);
		assertEquals(main.getChildrenOf(n5), p3);
	}
	
	// ****** GET ALL NODES TESTS *****
	
	@Test
	public void testGetAllNodesNone() {
		HashSet<String> p = new HashSet<String>();
		assertEquals(main.getAllNodesOf(n6), p); 
	}
	
	@Test
	public void testGetAllNodesMult() {
		HashSet<String> p1 = new HashSet<String>();
		p1.add(n7);
		assertEquals(main.getAllNodesOf(n7), p1);
		
		HashSet<String> p2 = new HashSet<String>();
		p2.add(n4);
		p2.add(n5);
		p2.add(n2);
		assertEquals(main.getAllNodesOf(n3), p2);
		
		HashSet<String> p3 = new HashSet<String>();
		p3.add(n3);
		p3.add(n4);
		p3.add(n2);
		assertEquals(main.getAllNodesOf(n5), p3);
	}
	
	// ***** INSERT NODE TEST ******
	
	@Test
	public void testInsertNodeFalse() {
		assert(!main.insertNode(n1)); 
		assert(!main.insertNode(n2)); 
		assert(!main.insertNode(n3)); 
		assert(!main.insertNode(n4)); 
		assert(!main.insertNode(n5)); 
		assert(!main.insertNode(n6)); 
		assert(!main.insertNode(n7)); 
	}
	
	@Test
	public void testInsertNodeTrue() {
		assert(main.insertNode("node8")); 
		assert(main.containsNode("node8"));
		assert(main.insertNode("node9"));
		assert(main.containsNode("node9")); 
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInsertNodeNull() {
		main.insertNode(null);
	}
	
	// ****** INSERT Edge<String, String> TESTS ******
	
	@Test
	public void testInsertEdgeFalse() {
		assert(!main.insertEdge(n1, n2, l1));
		assert(!main.insertEdge(n7, n7, l1));
		assert(!main.insertEdge(n2, n5, l1));
	}
	

	@Test
	public void testInsertEdgeTrue() {
		assert(main.insertEdge(n2, n4, l1));
		assert(main.containsEdge(n2, n4, l1));
		assert(main.insertEdge(n2, n5, "label4"));
		assert(main.containsEdge(n2, n5, "label4"));	
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testInsertEdgeNull() {
		main.insertEdge(null, null, null);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testInsertEdgeNodeNotInGraph() {
		main.insertEdge("node8", n1, l1); 
	}
	
	// ******** DELETE NODE TESTS ******
	
	@Test
	public void testDeleteNodeNone() {
		HashSet<Edge<String, String>> e = new HashSet<Edge<String, String>>();
		assertEquals(main.deleteNode(n6), e); 
	}
	
	@Test
	public void testDeleteNodeMult1() {
		HashSet<Edge<String, String>> es = new HashSet<Edge<String, String>>(); 
		es.add(e6);
		es.add(e7);
		es.add(e5);
		assertEquals(main.deleteNode(n3), es);
	}
	
	@Test
	public void testDeleteNodeMult2() {
		HashSet<Edge<String, String>> es2 = new HashSet<Edge<String, String>>(); 
		es2.add(e6);
		es2.add(e9);
		assertEquals(main.deleteNode(n4), es2);
	}
	
	@Test
	public void testDeleteNodeMult3() {
		HashSet<Edge<String, String>> es3 = new HashSet<Edge<String, String>>(); 
		es3.add(e8);
		es3.add(e7);
		es3.add(e2);
		es3.add(e3);
		es3.add(e4);
		es3.add(e9);
		assertEquals(main.deleteNode(n5), es3);	
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDeleteNodeIllegalArgException() {
		main.deleteNode(null); 
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testDeleteNodeNoNodeException() {
		main.deleteNode("node8"); 
	}
	
	// ****** DELETE Edge<String, String> TESTS ******
	
	@Test
	public void testDeleteEdgeFalse() {
		assert(!main.deleteEdge(n1, n6, l1));
	}
	
	@Test
	public void testDeleteEdgeTrue() {
		assert(main.deleteEdge(n7, n7, l1));
		assert(main.deleteEdge(n1, n2, l1));
		assert(main.deleteEdge(n2, n5, l1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteEdgeNull() {
		main.deleteEdge(n1, null, l1);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testDeleteEdgeNodeNotInGraph() {
		main.deleteEdge(n1, "node8", l1);
	}
	
    // ***** GET NODE SET TEST *****
	
	@Test
	public void testGetNodeSetNone() {
		assertEquals(empty1.getNodeSet(), new HashSet<String>()); 
	}
	
	@Test
	public void testGetNodeSetMult() {
		assertEquals(main.getNodeSet(), nodes); 
	}
	
	
	// ******* NODE ITERATOR TESTS *****
	
	@Test
	public void testGetNodeIteratorHasNextFalse() {
		assert(!empty1.getNodeIterator().hasNext()); 
		Iterator<String> it = main.getNodeIterator(); 
		it.next();
		it.next();
		it.next();
		it.next();
		it.next();
		it.next();
		it.next();
		assert(!it.hasNext()); 
	}
	
	@Test
	public void testGetNodeIteratorHasNextTrue() {
		Iterator<String> it = main.getNodeIterator(); 
		assert(it.hasNext()); 
	}
	
	@Test
	public void testGetNodeIteratorNext() {
		HashSet<String> s = new HashSet<String>(); 
		Iterator<String> it = main.getNodeIterator(); 
		while (it.hasNext()) {
			s.add(it.next()); 
		}
		assertEquals(nodes, s); 
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testGetNodeIteratorNextException() {
		empty1.getNodeIterator().next(); 
	}
	
	// ******** Edge<String, String> ITERATOR TESTS *********
	
	@Test
	public void testGetIncomingEdgeIteratorHasNextFalse() {
		assert(!empty1.getIncomingEdgeIterator().hasNext()); 
	}
	
	@Test
	public void testGetIncomingEdgeIteratorHasNextTrue() {
		assert(main.getIncomingEdgeIterator().hasNext()); 
	}
	
	@Test
	public void testGetIncomingEdgeIteratorNext() {
		HashSet<Edge<String, String>> s = new HashSet<Edge<String, String>>(); 
		Iterator<Edge<String, String>> it = main.getIncomingEdgeIterator(); 
		while (it.hasNext()) {
			s.add(it.next()); 
		}
		assertEquals(edges, s); 
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testGetIncomingEdgeIteratorNextException() {
		empty1.getIncomingEdgeIterator().next(); 
	}

	@Test
	public void testRepExposure() {
		HashSet<String> nodes1 = main.getChildrenOf(n2); 
		nodes1.remove(n5);
		assert(main.containsNode(n5)); 
		
		HashSet<String> nodes2 = main.getParentsOf(n5); 
		nodes2.remove(n2);
		assert(main.containsNode(n2)); 
	}
}
