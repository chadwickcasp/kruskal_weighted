/* KruskalEdge.java */

package graphalg;

//This class stores Egde objects for use in the Kruskal algorithm

class KruskalEdge implements Comparable<KruskalEdge> {

	//the two vertices connected by the edge:
	private Object v1;
	private Object v2;
	//the weight of the edge:
	private int weight;

	KruskalEdge(Object v1, Object v2, int weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}

	//returns V1
	Object getV1(){
		return v1;
	}

	//returns V2
	Object getV2(){
		return v2;
	}

	//returns weight
	int getWeight(){
		return weight;
	}

	//overridden compareTo method:
	public int compareTo(KruskalEdge e){
		if (this.weight == e.weight){
			return 0;
		}
		else if (this.weight > e.weight){
			return 1;
		}
		else {
			return -1;
		}
	}

	// public int hashCode(){
	// 	return v1.hashCode() + v2.hashCode() + weight;
	// }

}