/* ShortestPaths.java
   CSC 226 - Fall 2017
      
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPaths
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014

   Edited for assignment by Jorge Fernando Flores Pinto - 09/11/17.
   ID: V00880059.

*/

import java.util.*;
import java.io.*;

class Edge {
	private final int v;
	private final int w;
	private final int weight;

	 /**
	 * Constructor.
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w} 
     *         is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, int weight) {
        if (v < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (w < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        //if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN"); //Is not a number
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public int weight() {
    	return weight;
    }
        /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }


    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }



}
/*
   Written by Jorge Fernando Flores Pinto, based on starter code from Robert Sedgewick.
   ID: V00880059
*/

class EdgeWeightComparator implements Comparator<Edge> {
	@Override
    public int compare(Edge e1, Edge e2) {
        // Assume edges are not null
        //System.out.println("comparing e1.weight() " + e1.weight() + " and e2.weight() " + e2.weight());
    	if (e1.weight() < e2.weight()) {
    		
            return -1;
    	}
        if (e1.weight() > e2.weight()) {
            return 1;
        }

        if (e1.weight() == e2.weight()) {
        	if (e1.either() < e2.either()) {
        		return -1;
        	}
        	if (e1.either() > e2.either()) {
        		return 1;
        	}

        }
        return 0;

    }
    public boolean equals(Edge e1, Edge e2) {
        return e1.weight() == e2.weight();
    }

}
//Do not change the name of the ShortestPaths class
public class ShortestPaths{

    public static int numVerts;
    public static int[] map;
    public static int[] distances;
    public static PriorityQueue<Edge> minPath;
    public static PriorityQueue<Edge> edgeList;
	/* ShortestPaths(G) 
	   Given an adjacency matrix for graph G, calculates and stores the
	   shortest paths to all the vertices from the source vertex.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static void ShortestPaths(int[][] G, int source){
		numVerts = G.length;
		
		
		Comparator<Edge> comp = new EdgeWeightComparator();
		edgeList = new PriorityQueue<Edge>(comp);

		int currDist = 0;
		distances = new int[numVerts];

		for (int i = 0; i < numVerts; i++) {
			distances[i] = 2000000000;
		}
		distances[source] = 0;

		map = new int[numVerts];
		map[source] = source;

		boolean[][] visited = new boolean[numVerts][numVerts];

		for (int j = 0; j < numVerts; j++) {
				if (G[source][j] != 0 && !visited[source][j]) {
					visited[source][j] = true;
					visited[j][source] = true;

					map[j] = source;
					distances[j] = G[source][j];


					Edge e = new Edge(source, j, currDist);
					edgeList.add(e);
				}
		}
		while(!edgeList.isEmpty()) {
			Edge f = edgeList.poll();

			int vertex = f.other(f.either());

			for(int j = 0; j < numVerts; j++) {
				if (G[vertex][j] != 0) {

					currDist = distances[vertex] + G[vertex][j];

					if (currDist < distances[j]) {
						distances[j] = currDist;
						map[j] = vertex;
						

						Edge e = new Edge(vertex, j, G[vertex][j]);
						edgeList.add(e);
					}
				}
			}
		}





		/*while(!edgeList.isEmpty()) {
			Edge f = edgeList.poll();
			System.out.println("Vertices of f: " + f.either() + " and " + f.other(f.either()) + ". Weight: " + f.weight());

		}*/

                
	}
        
    static void PrintPaths(int source){
        boolean[] visited = new boolean[numVerts];
       
        for(int i = 0; i < numVerts; i++) {

			String links = "";
			
			int vertex = i;
			
			String prompt = "The path from " + source + " to " + vertex + " is: ";
			visited[vertex] = true;

			links = doPath(map, source, vertex, links);
			
			prompt += links;
			prompt += " and the total distance is : " + distances[vertex];

			System.out.println(prompt);

		}


    }
    static String doPath(int[] map, int source, int curr, String links) {
    	if (source == curr) {
    		links = source + links;
    		//System.out.println("final links: " + links);
    		return links;
    	}
    	links =  " --> " + curr + links;
    	//System.out.println("step. curr: " + curr + ". links: " + links);
    	return doPath(map, source, map[curr], links);

    }
        
		
	/* main()
	   Contains code to test the ShortestPaths function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			ShortestPaths(G, 0);
                        PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
		

		/*Edge e1 = new Edge(0, 1, 5);
		Edge e2 = new Edge(0, 2, 6);

		Comparator<Edge> comp = new EdgeWeightComparator();
		PriorityQueue<Edge> test = new PriorityQueue<Edge>(comp);

		int[] mapping = new int[3];
		mapping[0] = 0;
		mapping[1] = 0;
		mapping[2] = 0;

		String linksTest = "";

		linksTest = doPath(mapping, 0, 2, linksTest);

		System.out.println(linksTest);*/

		
	}
}
