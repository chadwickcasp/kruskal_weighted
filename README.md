kruskal_weighted
================

Project implementing Kruskal's algorithm on weighted undirected graphs

This project implements the weighted undirect graph abstract data type. In addition, graph_alg contains the implementation of Kruskal's algorithm.

#### Files and Descriptions:

##### graph:

EdgeList - Keeps track of edges via linked list

EdgeNode - Fields include connected vertices and weights

VertexList - Keeps track of vertices via linked list

VertexNode - Fields include item stored at vertex and list of edges touching the vertex

VertexPair - A pair of vertices and hashCode for this data structure

WUGraph - Stores tables of vertice/edges as hash tables for easy access. Keeps track of all the vertices in a linked list

##### graph_alg:

Kruskal - Actual implementation of Kruskal's algorithm making use of hashtables and linked queues for efficiency

KruskalEdge - The ADT used by Kruskal that keeps track of the edges in the graph


The rest of the routines act as either parent classes for the above classes, or test programs
