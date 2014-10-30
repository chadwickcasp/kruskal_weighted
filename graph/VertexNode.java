/* VertexNode.java */

package graph;
import list.*;

/**
 * This object represents the containers that are pointed
 * to by the nodes in the vertex hash table.
 * 
 * @author Casperslide
 *
 */
class VertexNode extends list.DListNode {
	
	/**
	 * FIELDS:
	 * Inherits myList, item from ListNode and prev, next
	 * from DListNode. myList is the list containing all of
	 * the vertices, and item is application's vertex object.
	 * edgeList points to the list of the edges that the
	 * vertex has.
	 */
	EdgeList edgeList;
	
	/**
	 * No argument constructor
	 */
	VertexNode(){
		super();
	}
	
	/**
	 * Multiple argument constructor
	 * @param i
	 * @param l
	 * @param p
	 * @param n
	 */
	VertexNode(Object i, VertexList l, DListNode p, DListNode n) {
	    item = i;
	    myList = l;
	    prev = p;
	    next = n;
	    edgeList = new EdgeList();
	}
	
	/**
	 * Getter for edgeList.
	 */
	public EdgeList getEdgeList(){
		return edgeList;
	}
	
	/**
	 * Removes a VertexNode from the list of all vertices.
	 * Takes the rest of the code from the super class and
	 * also sets edgeList to null.
	 */
	public void remove() throws InvalidNodeException{
		super.remove();
		edgeList = null;
	}
	
}
