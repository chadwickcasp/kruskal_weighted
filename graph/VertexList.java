/* VertexList.java */

package graph;
import list.*;

class VertexList extends list.DList {
	
	/**
	 * FIELDS:
	 * Inherited size from List, head from DList
	 */

	
	protected VertexNode newNode(Object item, VertexList list,
							    DListNode prev, DListNode next) {
		return new VertexNode(item, list, prev, next);
	}
	
	/**
	 *  VertexList() constructs for an empty VertexList.
	 **/
	public VertexList() {
		head = newNode(null, null, head, head);
		head.next = head;
		head.prev = head;
		size = 0;
	}

	/**
	 *  insertFront() inserts an item at the front of this VertexList.
	 *
	 *  @param item is the item to be inserted.
	 **/
	public void insertFront(Object item) {
		DListNode insert = newNode(item, this, head, head.next);
		head.next.prev = insert;
		head.next = insert;
		size++;
	}

	/**
	 *  insertBack() inserts an item at the back of this VertexList.
	 *
	 *  @param item is the item to be inserted.
	 **/
	public void insertBack(Object item) {
		DListNode insert = newNode(item, this, head.prev, head);
		head.prev.next = insert;
		head.prev = insert;
		size++;
	}
}
