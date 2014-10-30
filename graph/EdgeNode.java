/* EdgeNode.java */

package graph;
import list.*;
/**
 * A DListNode for edge lists. 
 * @author Kevin
 *
 */
class EdgeNode extends DListNode{
	/**
	 * Inherited: myList, item from ListNode
	 * Inherited: prev, next from DListNode
	 * myList is the list of the edges that the associated vertex has
	 * item is one of the vertices of this edge
	 * item2 is the other vertex of this edge
	 * partner is the partner EdgeNode of this EdgeNode (as explained in the pj3 readme)
	 * (partner = this if item == item2)
	 */
	int weight;
	Object item2;
	EdgeNode partner;
	
	/**
	 * no arg constructor
	 */
	EdgeNode() {
		super();
	}
	/**
	 * constructor. Always sets partner as itself as a default. 
	 * @param i vertex
	 * @param i2 vertex
	 * @param l list of edges
	 * @param p previous
	 * @param n next
	 */
	EdgeNode(int w, Object i, Object i2, EdgeList l, DListNode p, DListNode n) {
		weight = w;
		item = i;
		item2 = i2;
	    myList = l;
	    prev = p;
	    next = n;
	    partner = this;
	}
	
	/**
	 * item2() acts as a getter for item2 which is the other vertex in the vertex
	 * pair. If this node is invalid, throws an exception.
	 * @return item2
	 */
	public Object item2() throws InvalidNodeException {
	    if (!isValidNode()) {
	    	throw new InvalidNodeException();
	    }
	    return item2;
	}
	
	/**
	 * partner() returns the node identical to this node but in a different vertex's list.  
	 * If this node is invalid, throws an exception.
	 * @return partner node
	 * @throws InvalidNodeException
	 */
	public ListNode partner() throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException("partner() called on invalid node");
		}
		return partner;
	}
	
	/**
	 * weight() returns the weight associated with this edge.
	 * If this node is invalid, throws an exception.
	 * @return int weight
	 * @throws InvalidNodeException
	 */
	public int weight() throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException("weight() called on invalid node");
		}
		return weight;
	}
	
	/**
	 * setWeight() changes the weight of this edge by changing the weight of
	 * this node as well as the partner EdgeNode.
	 * @param w new weight
	 * @throws InvalidNodeException
	 */
	public void setWeight(int w) throws InvalidNodeException {
		if (!isValidNode()) {
			throw new InvalidNodeException("setWeight() called on invalid node");
		}
		weight = w;
		if (partner.weight() != w) {
			partner.setWeight(w);
		}
	}
	/**
	 * connects this EdgeNode with another EdgeNode
	 * calling addPartner will also call addPartner on the partner Node. 
	 * @param pn
	 */
	public void setPartner(EdgeNode pn) throws InvalidNodeException {
		partner = pn;
		if (pn.partner() != this) {
			pn.setPartner(this);
		}
	}
	
	/**
	 * calls the DListNode remove, but also removes the partner node (to make sure
	 * there are no nodes referencing invalid partners)
	 */
	public void remove() throws InvalidNodeException {
		super.remove();
		if (partner.isValidNode()) {
			partner.remove();
		}
	}
}
