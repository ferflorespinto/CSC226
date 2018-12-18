/*
   Written by Jorge Fernando Flores Pinto, based on starter code from Robert Sedgewick.
   ID: V00880059
*/
import java.util.*;

public class EdgeWeightedGraph {
	private int V;
	private int E;

	public PriorityQueue<Edge> adj;
	//Initializes the graph from adjacency matrix
	public EdgeWeightedGraph(int V, int[][] matrix) {
		if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;

        Comparator<Edge> comp = new EdgeWeightComparator();
        adj = new PriorityQueue<Edge>(comp);
        int[][] visitedEdges = new int[matrix.length][matrix[0].length]; //Initialize to 0

        for (int i = 0; i < matrix.length; i++) {
        	for (int j = 0; j < matrix[0].length; j++) {
        		if(matrix[i][j] != 0 && visitedEdges[i][j] == 0) {
        			Edge e = new Edge(i, j, matrix[i][j]);
        			visitedEdges[i][j] = 1;
        			visitedEdges[j][i] = 1;
        			adj.add(e);
        		}
        	}
        }
	}
	public PriorityQueue<Edge> kruskalMST(int[][] matrix) {
		Comparator<Edge> comp = new EdgeWeightComparator();
		PriorityQueue<Edge> tree = new PriorityQueue<Edge>(comp);
		int[][] matrixCopy = new int[matrix.length][matrix[0].length];

		boolean areConnected = false;

		while (!adj.isEmpty()) {
			areConnected = false;
			Edge e = adj.poll();
			int vertex = e.either();

			int otherVertex = e.other(vertex);


			boolean[] coveredV = isConnected(matrixCopy, vertex);
			boolean[] coveredOtherV = isConnected(matrixCopy, otherVertex);

			if (coveredOtherV[vertex] && coveredV[otherVertex]) {
				areConnected = true;
			}

			if (!areConnected) {
				tree.add(e);
			}

			matrixCopy[vertex][otherVertex] = e.weight();
			matrixCopy[otherVertex][vertex] = e.weight();


		}	
		return tree;
	}

	static void isConnectedDFS(int[][] G, boolean[] covered, int v) {
		covered[v] = true;
		for (int i = 0; i < G.length; i++) {
		    if (G[v][i] > 0 && !covered[i]) {
				isConnectedDFS(G, covered, i);
		    }
		}

    }
	   
    /* isConnected(G)
       Test whether G is connected.
       You may modify this, but nothing in this function will be marked.
    */
    static boolean[] isConnected(int[][] G, int vertex) {
		boolean[] covered = new boolean[G.length];
		/*for (int i = 0; i < covered.length; i++) {
		    covered[i] = false;
		}*/
		isConnectedDFS(G, covered, vertex);
		/*for (int i = 0; i < covered.length; i++) {
		    if (!covered[i]) {
				return false;
		    }
		}*/
		return covered;
    }
	public static void main(String[] args) {
		//Scanner sc = new Scanner(new File(args[0]));
		int [][] matrix = new int[3][3];
		matrix[0][0] = 0;
		matrix[0][1] = 1;
		matrix[0][2] = 0;
		matrix[1][0] = 1;
		matrix[1][1] = 0;
		matrix[1][2] = 2;
		matrix[2][0] = 0;
		matrix[2][1] = 2;
		matrix[2][2] = 0;

		EdgeWeightedGraph G = new EdgeWeightedGraph(3, matrix);

		for(Edge e : G.adj) {
			System.out.println("Vertex: " + e.either() + ", vertex: " + e.other(e.either()) + ", weight: " + e.weight());
		}

		System.out.println();
	}
}