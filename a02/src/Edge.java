/*
   Written by Jorge Fernando Flores Pinto, based on starter code from Robert Sedgewick.
   ID: V00880059
*/

public class Edge {
	private int v;
	private int w;
	private int weight;

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