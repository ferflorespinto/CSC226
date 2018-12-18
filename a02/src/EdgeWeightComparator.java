/*
   Written by Jorge Fernando Flores Pinto, based on starter code from Robert Sedgewick.
   ID: V00880059
*/

import java.util.*;

public class EdgeWeightComparator implements Comparator<Edge> {
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

}