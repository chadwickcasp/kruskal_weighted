/* WUGraph.java */

package graph;
import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

  /**
   * 
   */
  private int numVertices;
  private int numEdges;
  private HashTableChained vertexTable;
  private HashTableChained edgeTable;
  private VertexList vertexList;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
    numVertices = 0;
    numEdges = 0;
    vertexTable = new HashTableChained();
    edgeTable = new HashTableChained();
    vertexList = new VertexList();
  }
  
  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	return numVertices;
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
    return numEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	  ListNode temp = vertexList.front();
	  Object[] output = new Object[numVertices];
	  for (int i = 0; i < numVertices; i++){
		  try{
			  output[i] = temp.item();
			  temp = temp.next();
		  }catch (InvalidNodeException e){}
	  }
	  return output;
  }
    
  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	  //Is the vertex in the graph already?
	  if (isVertex(vertex)){
		  return;
	  }else{
		  vertexList.insertBack(vertex);
		  vertexTable.insert(vertex, vertexList.back());
	  }
	  numVertices++;
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	  if (!isVertex(vertex)){
		  return;
	  }
	  Entry e = vertexTable.find(vertex);
	  DListNode v = (DListNode) e.value();
	  EdgeList l = ((VertexNode) v).getEdgeList();
	  int leng = l.length();
	  //Iterate through list of edges and remove each.
	  //After that, remove the node.
	  try {
		  for (int i = 0; i < leng; i++){
			  EdgeNode en = (EdgeNode) l.front();
			  Object item = en.item();
			  Object item2 = en.item2();
			  //en.remove();
			  removeEdge(item, item2);
		  }
		  v.remove();
	  } catch (InvalidNodeException e1) {} 
	  vertexTable.remove(vertex);
	  numVertices--;
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	  if (vertexTable.find(vertex) != null){
		  return true;
	  }else{
		  return false;
	  }
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	  //Is vertex in the graph already?
	  if (isVertex(vertex)){
		  //Find the vertex in the graph and get the length of its list of edges.
		  Entry e = vertexTable.find(vertex);
		  VertexNode v = (VertexNode) e.value();
      if(v.getEdgeList() != null){
		    return v.getEdgeList().length();
      }
	  }
	  return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	  if (degree(vertex) == 0){
		  return null;
	  }
	  Neighbors n = new Neighbors();
	  Entry e = vertexTable.find(vertex);
	  VertexNode v = (VertexNode) e.value();
	  EdgeList l = v.getEdgeList();
	  if (l.length() == 0) {
		  return null;
	  }
	  n.neighborList = new Object[l.length()];
	  n.weightList = new int[l.length()];
	  EdgeNode en = (EdgeNode) l.front();
	  //Iterate through list of edges.
	  for (int i = 0; i < l.length(); i++){
		  try {
			  //If vertex is not the first vertex in the edgeNode,
			  //we put the second into the neighborList and vice versa.
			  if (vertex.equals(en.item())){
				  n.neighborList[i] = en.item2();
			  }else{
				  n.neighborList[i] = en.item();
			  }
			  n.weightList[i] = en.weight();
			  en = (EdgeNode) en.next();
		  } catch (InvalidNodeException e1) {}
	  }
	  return n;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u.equals(v)) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	  Object pair = new VertexPair(u,v);
	  Entry e_entry = edgeTable.find(pair);
	  if (e_entry != null) { // if pair is found in edgeTable, update weight of edge
		  // the Entry e_entry's value is the EdgeNode object associated with the edge
		  try {
			  ((EdgeNode) e_entry.value()).setWeight(weight);
		  } catch (InvalidNodeException e) {}
		  return;
	  }
	  // no edge yet
	  // look for u and v in vertexTable
	  Entry u_entry = vertexTable.find(u);
	  Entry v_entry = vertexTable.find(v);
	  if (u_entry == null || v_entry == null) {
		  // graph unchanged because vertex(ices) not found
		  return;
	  }
	  // vertices exist, no existing edge. Time to add an edge!
	  numEdges++;
	  // u_edges is the EdgeLists for vertex u.
	  EdgeList u_edges = ((VertexNode) u_entry.value()).getEdgeList();
	  // add a new EdgeNode to the back of u's EdgeList
	  u_edges.insertBack(u,v,weight);
	  ListNode node = u_edges.back();
	  if (!u.equals(v)) { // not a self edge
		  // add a new EdgeNode to the back of v's EdgeList
		  EdgeList v_edges = ((VertexNode) v_entry.value()).getEdgeList();
		  v_edges.insertBack(u,v,weight);
		  // partner up the EdgeNodes!
		  try {
			((EdgeNode) node).setPartner((EdgeNode) v_edges.back());
		  } catch (InvalidNodeException e) {}
	  }
	  // hash this new edge into the edgeTable
	  edgeTable.insert(pair, node);
  }
  
  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
	  Object pair = new VertexPair(u,v);
	  Entry e_entry = edgeTable.find(pair);
	  if (e_entry == null) {return;} // don't change the graph if the edge doesn't exist
	  try {
		  ((EdgeNode) e_entry.value()).remove(); // removes the EdgeNode and its partner
	  } catch (InvalidNodeException e) {}
	  edgeTable.remove(e_entry.key());
	  numEdges--;
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
	  Object pair = new VertexPair(u,v);
	  if (edgeTable.find(pair) == null) {
		  return false;
	  }
	  return true;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
	  Object pair = new VertexPair(u,v);
	  Entry e_entry = edgeTable.find(pair);
	  int out = 0;
	  if (e_entry == null) {return out;} // edge doesn't exist
	  try {
		  out = ((EdgeNode) e_entry.value()).weight();
	  } catch (InvalidNodeException e) {}
	  return out;
  }
}
