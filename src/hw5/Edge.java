/**
 * @author Hope Crandall 
 * 
 * Abstract Value: 
 * 		Represents an immutable directed, labeled connection between a parent 
 * 		node and a child node within a graph. An Edge<E, T> represents and Edge
 *      connecting nodes of type E with a label of type T. 
 * 
 * Specification Fields: 
 * 		@specified label  : String   // label on the edge 
 * 		@specified parent : String   // parent node 
 * 		@specified child  : String   // child node 
 * 
 * Abstract Invariant: 	
 * 		// Two Edges are considered equal if they have the same parent node, 
 *         child node, and label. 
 * 		// The parent and the child in an edge may the be same node. 
 */

package hw5;

public class Edge<E, T> {
	/* Abstraction Function given by field comments*/
	
	// parent node, where the edge begins and points from
	protected final E parent; 
	
	// child node, where the edge ends and points to 
	protected final E child; 
	
	// the label on the edge 
	protected final T label; 
	
	/* Representation Invariant:
	 * 		-None of parent, child, or label can be null 
	 */
	
	/**
	 * @param parent The parent node, where the edge is directed from.
	 * @param child  The child node, where the edge is directed to. 
	 * @param label  The label on the edge.
	 * @requires The parent, child, and label must all be non-null and not
     *           the empty string. 
     * @effects Constructs an Edge object with the given parent, child, and
     *          label. 
	 */
	public <V1 extends E, V2 extends T> Edge (V1 parent, V1 child, V2 label) {
		if (parent == null | child == null | label == null) {
			throw new IllegalArgumentException("Null argument");
		}
		this.parent = parent;
		this.child = child;
		this.label = label; 
		checkRep(); 
	}
	
	/**
	 * @return Returns the parent node. 
	 */
	public E getParent() {
		return this.parent;
	}
	
	/**
	 * @return Returns the child node. 
	 */
	public E getChild() {
		return this.child;
	}
	
	/**
	 * @return Returns the label on the edge. 
	 */
	public T getLabel() {
		return this.label; 
	}
	
	/**
	 * Returns whether or not two Edges are equal.
	 * @param o An object to be compared with this Edge.
	 * @returns Returns true if o is an Edge with the same parent,
	 *          child, and label as this Edge (given their respective
	 *          equals methods). Returns false otherwise. 
	 */
	@Override 
	public boolean equals (Object o) {
		if (! (o instanceof Edge)) {
			return false; 
		}
		Edge<?, ?> e = (Edge<?, ?>) o; 
		return this.parent.equals(e.parent) && this.child.equals(e.child) 
			   && this.label.equals(e.label); 
	}
	
	
	/**
	 * Returns an int to be used in hashing. 
	 * 
	 * @returns Returns an int corresponding to this Edge. 
	 */
	@Override 
	public int hashCode() {
		int a = this.parent.hashCode();
		int b = this.child.hashCode();
		int c = this.label.hashCode();
		return a ^ b * c;  
	}
	
	/**
	 * Returns a String representation of this Edge. 
	 * 
	 * @returns Returns a String in the format (parent, child, label)
	 */
	@Override 
	public String toString() {
		return "(" + this.parent + ", " + this.child + ", " + 
				this.label + ")"; 
	}
	
	private void checkRep() {
		assert(this.parent != null && this.child != null && this.label != null);
	}
}
