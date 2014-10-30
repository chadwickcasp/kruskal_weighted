/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import list.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g){
  	WUGraph t = new WUGraph();
  	//build the graph t with the same vertices as g, but no edges
    //also build a hashtable that contains the vertcies as keys and unique integers as values
  	Object[] g_vertices = g.getVertices();
    HashTableChained vertex_hash_table = new HashTableChained(g.vertexCount());
  	for (int i = 0; i < g_vertices.length; i++ ){
  		t.addVertex(g_vertices[i]);
      //insert into hashtable, i being the integer associated with each vertex
      vertex_hash_table.insert(g_vertices[i], i);
  	}
  	//build a linked queue of all of the edges in g and sort it
	  LinkedQueue edges_queue = getAllEdges(g);
	  ListSorts.quickSort(edges_queue);  	

    DisjointSets disjoint_sets = new DisjointSets(g_vertices.length);


    while (!edges_queue.isEmpty()){
      try{
        KruskalEdge current_edge = (KruskalEdge) edges_queue.front();

        //the actual vertices
        Object vertex1 = current_edge.getV1();
        Object vertex2 = current_edge.getV2();
        

        //unique numbers associated with the numbers (value in hashtable)
        int vertex1_number = (int) vertex_hash_table.find(vertex1).value();
        //System.out.println("vertex 2 entry is : " + vertex_hash_table.find(vertex2));
        int vertex2_number = (int) vertex_hash_table.find(vertex2).value();


        //sets that contains the vertices:
        int root1 = disjoint_sets.find(vertex1_number);
        int root2 = disjoint_sets.find(vertex2_number);

        //combine sets if they are not the same and add the current edge
        if (root1 != root2){
          t.addEdge(vertex1, vertex2, current_edge.getWeight());
          disjoint_sets.union(root1, root2);
        }

        edges_queue.dequeue();

      }
      catch (QueueEmptyException e1){};
    }

  	return t;
  }



  //finds all of the edges in a graph g and returns a LinkedQueue of KruskalEdge objects
  private static LinkedQueue getAllEdges(WUGraph g){
  	LinkedQueue edges_queue = new LinkedQueue();
  	Object[] g_vertices = g.getVertices();

  	for (int i = 0; i < g.vertexCount(); i++){
  		Object current_vertex = g_vertices[i];
  		Neighbors neighbors = g.getNeighbors(current_vertex);

  		for (int j = 0; j < neighbors.neighborList.length; j++){
  			Object current__neighbor = neighbors.neighborList[j];
  			int edge_weight = neighbors.weightList[j];

  			KruskalEdge current_edge = new KruskalEdge(current_vertex, current__neighbor, edge_weight);

  			edges_queue.enqueue(current_edge);

  		}
  	}
  	return edges_queue;
  }



}
