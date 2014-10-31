/* EdgeList.java */

package graph;
import list.*;

/**
 * EdgeList is a DList of EdgeNodes
 *
 */
class EdgeList extends DList {
	/**
	 * Inherited: size from List
	 * Overwrite: head from DList
	 */
	protected EdgeNode head;
	/**
	 *  newNode() calls the EdgeNode constructor.
	 *  @param item the vertex to store in the node.
	 *  @param item2 the other vertex to store in the node.
	 *  @param list the list that owns this node.  (null for sentinels.)
	 *  @param prev the node previous to this node.
	 *  @param next the node following this node.
	 **/
	protected EdgeNode newNode(int weight, Object item, Object item2, EdgeList list,
	                            DListNode prev, DListNode next) {
	  return new EdgeNode(weight, item, item2, list, prev, next);
	}
	
	/**
	 *  EdgeList() constructs for an empty EdgeList.
	 **/
	public EdgeList() {
		head = newNode(Integer.MIN_VALUE, null, null, null, head, head);
		head.next = head;
		head.prev = head;
		size = 0;
	}

	/**
	 *  insertFront() inserts an item at the front of this EdgeList.
	 *
	 *  @param item is the item to be inserted.
	 **/
	public void insertFront(Object item, Object item2, int weight) {
		DListNode insert = newNode(weight, item, item2, this, head, head.next);
		head.next.prev = insert;
		head.next = insert;
		size++;
	}

	/**
	 *  insertBack() inserts an item at the back of this EdgeList.
	 *
	 *  @param item is the item to be inserted.
	 **/
	public void insertBack(Object item, Object item2, int weight) {
		DListNode insert = newNode(weight, item, item2, this, head.prev, head);
		head.prev.next = insert;
		head.prev = insert;
		size++;
	}
	
	/**
	 *  front() returns the node at the front of this EdgeList.
	 *
	 *  @return a ListNode at the front of this EdgeList.
	 */
	public ListNode front() {
		return head.next;
	}

	/**
	 *  back() returns the node at the back of this EdgeList.
	   *
	   *  @return a ListNode at the back of this EdgeList.
	   */
	public ListNode back() {
		return head.prev;
	}
}

