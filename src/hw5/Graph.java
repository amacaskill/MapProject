/**
 * @author Hope Crandall 
 * 
 * Abstract Value:
 * 	This class represents a mutable directed labeled multi-graph. A Graph<E, T>
 *  defines a graph with nodes of type E and edge labels of type T. 
 * 
 * Specification Fields: 
 * 	@specified nodes : String  // Store information in the graph.
 * 	@specified edges : Edge    // Directed connections between two nodes with a label.
 *                                When two nodes are connected by an edge, a "parent" node is 
 * 							      where the edge is directed from, and a "child" node is 
 * 							      where the edge is directed to. An outgoing edge points to 
 * 								  a node's child and an incoming edge points from its parent. 
 * 
 * Derived Specification Fields: 
 * 	@specified path : List<Edge> // A "sequence of edges" such that that after the first node,
 * 								    the parent on each edge is the child of the previous edge
 * 								    in the sequence. A path from node A to node B is any
 *   							    sequence where the first parent of the first edge is 
 *   							    node A and the child of the last edge is node B
 *
 * Abstract Invariant:
 * 	 // Does not contain duplicate nodes.
 *   // Each node has >= 0 edges (>= 0 parents and >= 0 children).
 * 	 // Does not contain duplicate edges (multiple edges with the same parent and 
 * 	    child, but different labels are allowed).	
 * 	 // Considered empty only if there are no nodes  
 * 	 
 */

package hw5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class Graph<E, T> {
	
	private HashMap<E, HashSet<Edge<E, T>>> parents;
	private HashMap<E, HashSet<Edge<E, T>>> children; 
	
	/* Representation Invariant:
	 * 	-parents and children must contain no null elements
	 * 	-parents.values() and children.values() must not contain null
	 * 	-parents.keySet() must equals childre.keySet() 
	 *  -The set of all Edges stored in all HashSets in parents 
	 *   must be equals to the set of all Edges stored in all HashSets
	 *   in children
	 *  -Within parents OR children, no Edge can appear in more than
	 *   one HashSet 
	 *  -For each key in parents, the associated HashSet must only contain
	 *   Edges where the parent node is equal to the key
	 *  -For each key in children, the associated HashSet must only contain
	 *   Edges where the child node is equal to the key
	 */
	
	/*
	 * Abstraction Function:
	 * 	-The keys (Strings) stored in parents and children are 
	 *   the nodes of the Graph
	 *  -Both the set of all Edges stored in parents and the 
	 *   set of all Edges stored in children are each the set
	 *   of all edges contained in the Graph
	 *  -parents associates each node with its outgoing edges
	 *  -children associates each node with its incoming edges 
	 */
	
	
	/**
	 * Constructs an empty Graph. 
	 * 
	 * @effects Constructs an empty Graph. 
	 */
	public Graph () {
		this(new HashSet<E>(), new HashSet<Edge<E, T>>());
		checkRep();
	}
	
	/**
	 * Constructs a Graph populated by the nodes and Edges given by the
	 * collection arguments. 
	 * 
	 * @param characters A set of Strings that will be nodes in the Graph. 
	 * @param edges A set of Edges that will be edges in the Graph.
	 * @requires Neither nodeSet nor edgeSet can contain null or be null. 
	 * 			 For each Edge e in edgeSet, the nodes e.getParent() and e.getChild()
	 * 			 must be contained in nodeSet. No String in nodeSet can be the
	 * 			 empty String. 
	 * @throws If nodeSet or edgeSet are or contain null, or if edgeSet contains
	 * 		   edges with nodes not in nodeSet, or nodeSet contains the empty
	 * 		   String, throws IllegalArgumentException.
	 * @effects Constructs a new Graph populated by all nodes in nodeSet
	 * 			and all edges in edgeSet. 
	 */
	public Graph (Collection<? extends E> characters, Collection<Edge<E,T>> edges) {
		if (characters == null | edges == null) {
			throw new IllegalArgumentException("Passed in null argument");
		} 
		if (characters.contains(null) | edges.contains(null)) {
			throw new IllegalArgumentException("Set contains null");
		} 
		
		parents = new HashMap<E, HashSet<Edge<E,T>>>();
		children = new HashMap<E, HashSet<Edge<E,T>>>();
		
		for (E s: characters) {
			this.insertNode(s); 
		}
		
		for(Edge<E, T> e: edges) {
			this.insertEdge(e); 
		}
		
		checkRep();
	}
	
	// CONTAINS METHODS
	
	/**
	 * Returns whether or not the Graph is empty. 
	 * 
	 * @return Returns true if there are 0 nodes in the graph; else
	 * 		   returns false. 
	 */
	public boolean isEmpty() {
		return parents.isEmpty(); 
	}
	
	/**
	 * Returns whether or not an Edge in the Graph. 
	 * 
	 * @param parent Parent node of Edge e to be tested for its presence.
	 * @param child Child node of Edge e to be tested for its presence.
	 * @param label Label of Edge e to be tested for its presence. 
	 * @requires all parameters must be non null and parent and child must 
	 *           be in Graph. 
	 * @throws Throws IllegalArgumentException if any parameter is null.
	 * 		   Throws NoSuchElementException if parent or child is not in 
	 * 		   Graph.
	 * @return Returns true if e is in the Graph; else returns false. 
	 */
	public <V extends E, L extends T> boolean containsEdge (V parent, V child, L label) {
		if (parent == null | child == null | label == null) {
			throw new IllegalArgumentException("Null argument"); 
		}
		return containsEdge(new Edge<E, T>(parent, child, label));
	}

	/* 
	 * @param e An Edge to be tested for its presence in the Graph. 
	 * @return Returns true if e is in the Graph; else returns false. 
	 */
	private boolean containsEdge (Edge<E, T> e) {
		if (!parents.containsKey(e.parent) | !parents.containsKey(e.child)) {
			throw new NoSuchElementException("Nodes not in Graph");
		} 
		return parents.get(e.parent).contains(e);
	}
	
	/**
	 * Returns whether or not there is any Edge that matches the 
	 * parameters in the Graph.
	 * 
	 * @param parent Possible parent node in the Graph.
	 * @param child Possible child node in the Graph. 
	 * @requires The Graph must contain parent and child, and both
	 *           must be non null. 
	 * @throws Throws IllegalArgumentException for a null argument.
	 * 		   Throws a NoSuchElementException if parent or child 
	 * 		   is not in the Graph. 
	 * @return Returns true if there is an edge, with any label,
	 *         whose parent node is parent and whose child node is
	 *         child; else returns false. 
	 */
	public <V extends E> boolean containsEdge (V parent, V child) {
		if (parent == null | child == null) {
			throw new IllegalArgumentException("Null argument"); 
		}
		if (!parents.containsKey(parent) | !parents.containsKey(child)) {
			throw new NoSuchElementException("Node not in Graph");
		} 
		for (Edge<E, T> e: parents.get(parent)) {
			if (e.child.equals(child)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns whether or not a node is in the Graph. 
	 * 
	 * @param node A node to be tested for its presence in the Graph.
	 * @return Returns true if there is a node equal to node
	 * 		   in the Graph; else returns false. 
	 */
	public <V extends E> boolean containsNode (V node) {
		return parents.containsKey(node); 
	}	
	
	// GET LABELS
	
	/**
	 * Returns all the labels between a parent and child node. 
	 * 
	 * @param parent Parent node of the edges whose labels will be returned.
	 * @param child Child node of the edges whose labels will be returned.
	 * @requires parent and child must not be null and Graph must contain them.
	 * @throws Throws IllegalArgumentException if parent or child is null.
	 * 		   Throws NoSuchElementException if parent or child is not in
	 *         Graph.
	 * @return Returns an immutable HashSet of all the labels of edges with  
	 * 		   parent and child; if there are no such edges,
	 * 		   returns the empty list. Does NOT reflect the Graph as
	 * 		   it changes. Changing the set does not change the Graph. 
	 */
	public <V extends E> HashSet<T> getLabelsBetween (V parent, V child) {
		if (parent == null | child == null) {
			throw new IllegalArgumentException("Null string"); 
		}
		if (!parents.containsKey(parent) | !parents.containsKey(child)) {
			throw new NoSuchElementException("Node not in Graph"); 
		}
		
		HashSet<T> labels = new HashSet<T>(); 
		
		for (Edge<E, T> e: parents.get(parent)) {
			if (e.child.equals(child)) {
				labels.add(e.label);
			}
		} 
		return labels; 
	}
	
	// GET EDGES 
	
	/**
	 * Returns all of the incoming Edges to a node.
	 * 
	 * @param child The node whose incoming edges will be returned. 
	 * @requires child must be non null and the Graph must contain it.
	 * @throws Throws IllegalArgumentException if child is null.
	 * 		   Throws NoSuchElementException if child in not in Graph.
	 * @return Returns an immutable HashSet of all Edges that are 
	 *         directed to child.
	 */
	public <V extends E> Set<Edge<E, T>> getInEdgesOf (V child) {
		if (child == null) {
			throw new IllegalArgumentException("Null argument");
		}
		if (!this.containsNode(child)) {
			throw new NoSuchElementException("Node not in graph"); 
		}
		return Collections.unmodifiableSet(children.get(child));
	}
	
	/**
	 * Returns all of the outgoing Edges from a node. 
	 * 
	 * @param parent The node whose outgoing edges will be returned.
	 * @require parent must be non null and the Graph must contain it. 
	 * @throws Throws IllegalArgument Exception if parent is null.
	 * 		   Throws NoSuchElementException if parent is not in Graph.
	 * @return Returns an immutable Set of all Edges that are
	 *         directed from parent.
	 */
	public <V extends E> Set<Edge<E, T>> getOutEdgesOf (V parent) {
		if (parent == null) {
			throw new IllegalArgumentException("Null argument");
		}
		if (!this.containsNode(parent)) {
			throw new NoSuchElementException("Node not in graph"); 
		}
		return Collections.unmodifiableSet(parents.get(parent));
	}
	
	/**
	 * Returns all the Edges to or from a node. 
	 * 
	 * @param node The node whose edges will be returned.
	 * @require node must be non null and the Graph must contain it. 
	 * @throws Throws IllegalArgument Exception if node is null.
	 * 		   Throws NoSuchElementException if node is not in Graph.
	 * @return Returns an immutable HashSet that contains all incoming
	 *         and outgoing edges of node. Does NOT reflect the 
	 *         state of the Graph as it changes. Changing the set
	 *         does not change the Graph. 
	 */
	public <V extends E> HashSet<Edge<E, T>> getAllEdgesOf (V node) {
		if (node == null) {
			throw new IllegalArgumentException("Null argument");
		}
		if (!this.containsNode(node)) {
			throw new NoSuchElementException("Node not in graph"); 
		}
		HashSet<Edge<E, T>> all = new HashSet<Edge<E, T>>(this.getOutEdgesOf(node)); 
		all.addAll(this.getInEdgesOf(node));
		
		return all; 
	}
	
	// GET NODES
	
	/**
	 * Returns all of the parents of a node. 
	 * 
	 * @param child The node whose parents will be returned.
	 * @require child must be non null and the Graph must contain it. 
	 * @throws Throws IllegalArgument Exception if child is null.
	 * 		   Throws NoSuchElementException if child is not in Graph.
	 * @return Returns an immutable HashSet of all nodes that have
	 *         edges directed toward child. Does NOT reflect the 
	 *         state of the Graph as it changes. Changing the set
	 *         does not change the Graph.  
	 */
	public <V extends E> HashSet<E> getParentsOf (V child) {
		if (child == null) {
			throw new IllegalArgumentException("Null argument");
		}
		if (!this.containsNode(child)) {
			throw new NoSuchElementException("Node not in graph"); 
		}
		HashSet<E> inNodes = new HashSet<E>(); 
		for(Edge<E, T> e: children.get(child)) {
			inNodes.add(e.parent);
		}
		return inNodes;
	}
	
	/**
	 * Returns all of the children of a node. 
	 * 
	 * @param parent The node whose children will be returned.
	 * @require parent must be non null and the Graph must contain it. 
	 * @throws Throws IllegalArgument Exception if parent is null.
	 * 		   Throws NoSuchElementException if parent is not in Graph.
	 * @return Returns a HashSet of all nodes that have 
	 * 		   edges directed from parent. Does NOT represent the state
	 *         of the Graph as it changes. Changing the set does not change
	 *         the Graph.
	 */
	public <V extends E> HashSet<E> getChildrenOf (V parent) {
		if (parent == null) {
			throw new IllegalArgumentException("Null argument");
		}
		if (!this.containsNode(parent)) {
			throw new NoSuchElementException("Node not in graph"); 
		}
		HashSet<E> outNodes = new HashSet<E>(); 
		for(Edge<E, T> e: parents.get(parent)) {
			outNodes.add(e.child);
		} 
		return outNodes;
	}
	
	/**
	 * Returns the parents and children of a node. 
	 * 
	 * @param node The node whose connected nodes will be returned.
	 * @require node must be non null and the Graph must contain it. 
	 * @throws Throws IllegalArgument Exception if node is null.
	 * 		   Throws NoSuchElementException if node is not in Graph.
	 * @return Returns an immutable HashSet of all nodes that node is 
	 *         connected to by an incoming or outgoing edge. Does NOT reflect  
	 *         the state of the Graph as it changes. Changing the set
	 *         does not change the Graph.  
	 */
	public <V extends E> HashSet<E> getAllNodesOf (V node) {
		if (node == null) {
			throw new IllegalArgumentException("Null argument");
		}
		if (!this.containsNode(node)) {
			throw new NoSuchElementException("Node not in graph"); 
		}
		HashSet<E> all = new HashSet<E>(this.getParentsOf(node));
		all.addAll(this.getChildrenOf(node));
		return all; 
	}
	
	// INSERT
	
	
	/**
	 * Inserts a node into the Graph. 
	 * 
	 * @param node The node to be inserted into the Graph. 
	 * @requires Requires that node is not null.
	 * @throw Throws IllegalArgumentException if node is null.
	 * @modifies Modifies the Graph. 
	 * @effects If node is not in the Graph, adds the node 
	 * 			 to the Graph with no edges; else does not
	 *           modify the Graph. 
	 * @return If node was in the Graph before execution, returns true 
	 *         (node was inserted); else returns false (node wasn't
	 *         inserted because it was already in the Graph).
	 */
	public <V extends E> boolean insertNode (V node) {
		if (node == null) {
			throw new IllegalArgumentException("Null argument");
		}
		if (parents.containsKey(node)) {
			return false;
		} else {
			parents.put(node, new HashSet<Edge<E, T>>());
			children.put(node, new HashSet<Edge<E, T>>()); 
			checkRep(); 
			return true; 
		}
	}
	
	/**
	 * Inserts an Edge into the Graph. 
	 * 
	 * @param parent Parent node of Edge e to be inserted.
	 * @param child Child node of Edge e to be inserted. 
	 * @param label Label of Edge e to be inserted. 
	 * @requires Requires that parent and child are
	 * 			 already in the Graph. Requires all parameters to be 
	 *           non null.
	 * @throws  Throws IllegalArgumentException for null argument. 
	 *          Throws NoSuchElementException if parent and child
	 * 			are not already in the Graph. 
	 * @modifies What edges the Graph contains.  
	 * @effects  If e is not in the Graph, then it is inserted; else 
	 * 			 does not modify the Graph. 
	 * @return   If e was not in the Graph before execution, returns
	 * 			 true (e was inserted); else returns false (e was 
	 * 			 not inserted because it was already in the Graph). 
	 */
	public <V extends E, L extends T> boolean insertEdge (V parent, V child, L label) {
		return insertEdge(new Edge<E, T>(parent, child ,label));
	}
	
	/*
	 * @param e The edge to be inserted into the Graph. 
	 * @requires Requires that e.getParent() and e.getChild() are
	 * 			 already in the Graph. 
	 * @throws Throws NoSuchElementException if e contains nodes
	 * 		   that are not already in the Graph. 
	 * @modifies What edges the Graph contains.  
	 * @effects  If e is not in the Graph, then it is inserted; else 
	 * 			 does not modify the Graph. 
	 * @return   If e was not in the Graph before execution, returns
	 * 			 true (e was inserted); else returns false (e was 
	 * 			 not inserted because it was already in the Graph). 
	 */
	private boolean insertEdge (Edge<E, T> e) {
		if (!parents.containsKey(e.parent) | !parents.containsKey(e.child)) {
			throw new NoSuchElementException("Contains node not not in Graph");
		}
		if (this.containsEdge(e)) {
			return false;
		} else {
			parents.get(e.parent).add(e);
			children.get(e.child).add(e);
			checkRep(); 
			return true; 
		}
	}
	

	// DELETE
	
	/**
	 * Deletes an Edge from the Graph. 
	 * 
	 * @param parent Parent node of Edge e to be deleted.
	 * @param child Child node of Edge e to be deleted.
	 * @param label Label of Edge e to be deleted. 
	 * @requires Requires all parameters to be non null. Requires
	 *           parent and child to be in Graph.
	 * @throws  Throws IllegalArgumentException for a null parameter.
	 * 			Throws NoSuchElementException if parent or child
	 *          is not in Graph. 
	 * @modifies What edges the Graph contains. 
	 * @effects If this contains e, then removes e from Graph;
	 * 			 else does nothing
	 * @return If e was in Graph before execution, returns true 
	 *         (e was deleted from the Graph); else returns false 
	 *         (e was not in the Graph, nothing was deleted). 
	 */
	public <V extends E, L extends T> boolean deleteEdge (V parent, V child, L label) {
		return deleteEdge(new Edge<E, T>(parent, child, label)); 
	}
	
	/*
	 * @param e An edge to be deleted. 
	 * @requires e must contain nodes in the Graph.
	 * @throw Throws NoSuchElementException if nodes not in Graph.
	 * @modifies What edges the Graph contains. 
	 * @effects If this contains e, then removes e from Graph;
	 * 			 else does nothing
	 * @return If e was in Graph before execution, returns true 
	 *         (e was deleted from the Graph); else returns false 
	 *         (e was not in the Graph, nothing was deleted). 
	 */
	private boolean deleteEdge (Edge<E, T> e) {
		if (!this.containsNode(e.parent) | !this.containsNode(e.child)) {
			throw new NoSuchElementException("Node not in Graph"); 
		}
		if (!this.containsEdge(e)) {
			return false; 
		} else {
			parents.get(e.parent).remove(e);
			children.get(e.child).remove(e); 
			checkRep();
			return true; 
		}
	}
	
	/**
	 * Deletes a node from the Graph. 
	 * 
	 * @param node A node to be deleted. 
	 * @requires The Graph must contain node. 
	 * @throws Throws IllegalArgumentException if the Graph does
	 * 		   not contain node.
	 * @modifies What nodes the Graph contains. 
	 * @effects Removes node and all edges containing it 
	 *           from the Graph. 
	 * @return Returns a HashSet of the Edges that contained
	 * 		   node as a parent or child; if there were no such
	 *         edges, returns the empty list.
	 */
	public <V extends E> HashSet<Edge<E, T>> deleteNode (V node) {
		if (node == null) {
			throw new IllegalArgumentException("Null node");
		}
		if (!this.containsNode(node)) {
			throw new NoSuchElementException("Node not in Graph");
		}
		
		HashSet<Edge<E, T>> delEdges = new HashSet<Edge<E, T>>(); 
		
		for (Edge<E, T> e: parents.get(node)) {
			delEdges.add(e);
		}
		
		for (Edge<E, T> e: children.get(node)) {
			delEdges.add(e);
		}
		
		for (Edge<E, T> e: delEdges) {
			this.deleteEdge(e); 
		}
		
		parents.remove(node);
		children.remove(node);

		checkRep(); 
		
		return delEdges; 
	}
	
	// GET GRAPH VIEWS
	
	/**
	 * Returns a set of all the nodes in the Graph. 
	 * 
	 * @return Returns a HashSet of all nodes in the Graph. Does NOT reflect the 
	 *         state of the Graph as it changes. Changing the set
	 *         does not change the Graph.  
	 */
	public HashSet<E> getNodeSet() {
		HashSet<E> nodes = new HashSet<E>(); 
		for (E n: parents.keySet()) {
			nodes.add(n); 
		}
		return nodes; 
	}
	
	// converts the map of Strings to Edges into an ArrayList<Edge> of all
	// Edges in the graph--grouped as the map is grouped
	private ArrayList<Edge<E, T>> getEdgeSet (HashMap<E, HashSet<Edge<E, T>>> graph) {
		ArrayList<Edge<E, T>> allEdges = new ArrayList<Edge<E, T>>(); 
		for (E key: graph.keySet()) {
			for (Edge<E, T> e: graph.get(key)) {
				allEdges.add(e); 
			}
		}
		return allEdges;
	}
	
	// ITERATORS 
	
	/**
	 * Returns an iterator over the nodes in the Graph.
	 * 
	 * @return Returns an Iterator<String> over all nodes in the Graph
	 *  	   with no order. 
	 */
	public Iterator<E> getNodeIterator() {
		return new NodeIterator(); 
	}
	
	/**
	 * Returns an iterator over the Edges in the Graph, in order by
	 * child nodes. 
	 * 
	 * @return Returns an Iterator<Edge> over all Edges in the Graph.
	 * 		   Edges are grouped by their child node, but are not 
	 * 		   otherwise ordered. 
	 */
	public Iterator<Edge<E, T>> getIncomingEdgeIterator() {
		return new EdgeIterator(this.children); 
	}
	
	/**
	 * Returns an iterator over Edges in the Graph, in order by 
	 * parent nodes. 
	 * 
	 * @return Returns an Iterator<Edge> over all Edges in the Graph.
	 * 		   Edges are grouped by their parent node, but are not 
	 * 		   otherwise ordered. 
	 */
	public Iterator<Edge<E, T>> getOutgoingEdgeIterator() {
		return new EdgeIterator(this.parents); 
	}
	
	private class NodeIterator implements Iterator<E> {
		
		private HashSet<E> nodeset = getNodeSet(); 
		private Iterator<E> it = nodeset.iterator();

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public E next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException(); 
			}
			return it.next(); 
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(); 	
		}
			
	}
	
	
	private class EdgeIterator implements Iterator<Edge<E, T>>{
		
		private Iterator<Edge<E, T>> it;
		
		public EdgeIterator (HashMap<E, HashSet<Edge<E, T>>> graph) {
			this.it = getEdgeSet(graph).iterator(); 
		}
		
		@Override
		public boolean hasNext() {
			return it.hasNext(); 
		}

		@Override
		public Edge<E, T> next() {
			return it.next(); 
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(); 
			
		}	
	}
	
	private void checkRep() {
		boolean debugging = false; 
		if (debugging) {
			assert parents.keySet().equals(children.keySet()) :
					   "parents and children should have the same keySet";
			assert !parents.containsKey(null) :
					   "Shoudln't contain null keys";
			HashSet<Edge<E, T>> e1 = new HashSet<Edge<E, T>>();
			HashSet<Edge<E, T>> e2 = new HashSet<Edge<E, T>>(); 
			for (E key: parents.keySet()) {
				HashSet<Edge<E, T>> s1 = parents.get(key);
				assert s1 != null : "HashSet in parents should not be null"; 
				for(Edge<E, T> e: s1) {
					assert !e1.contains(e) : 
						"Two edges should not appear twice in parents" ;
					assert e.parent.equals(key) :
						"Edges in parent should have the key as parent";
					e1.add(e);
				}
				
				
				HashSet<Edge<E, T>> s2 = children.get(key); 
				assert s2 != null : "HashSet in children should not be null";
				for (Edge<E, T> e: s2) {
					assert !e2.contains(e) :
						 "Two edges should not appear twice in children" ;
					assert e.child.equals(key) :
						 "Edges in parent should have the key as parent";
					e2.add(e);
				}
				
			}
			assert e1.equals(e2) : 
					"parents and children should contain the same set of Edges";
		}
	}
	
	
	/**
	 * Returns a String representation of the Graph. 
	 * 
	 * @returns Returns a representation of the Graph where each node is listed on its
	 *          own line followed by a colon, where each line after is a tabbed in
	 *          String of each outgoing Edge of the node. 
	 */
	@Override
	public String toString() {
		String graph = ""; 
		for (E s: parents.keySet()) {
			graph += s.toString() + "\n";
			for (Edge<E, T> e : parents.get(s)) {
				graph += "\t" + e.toString() + "\n";
			}
			graph += "\n"; 
		}
		return graph; 
	}
}
