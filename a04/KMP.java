/* 

   Rahnuma Islam Nishat - 08/02/2014
   Edited by Jorge Fernando Flores Pinto
   ID: V00880059
   For CSC226, Fall 2017

   This class KMP was adapted from the lecture slides, where an almost finished version
   of code can be found for both the KMP constructor and the search method.
   The code from the lecture slides was written by Robert Sedgewick and Kevin Wayne.

*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class KMP 
{
    private static String pattern;
    public static int[][] dfa;

   
    public KMP(String pattern) {  
    	this.pattern = pattern;
    	int patLen = pattern.length();
    	dfa = new int[128][patLen]; //128 possible ASCII characters
    	dfa[pattern.charAt(0)][0] = 1;

    	for(int X = 0, j = 1; j < patLen; j++) {
    		for (int k = 0; k < 128; k++) {
    			dfa[k][j] = dfa[k][X];
    		}
    		dfa[pattern.charAt(j)][j] = j + 1;
    		X = dfa[pattern.charAt(j)][X];
    	}
    }
    
    public static int search(String txt) { 
    	int i = 0;
    	int j = 0; 
    	int txtLen = txt.length();
    	int patLen = dfa[0].length;
    	for (i = 0, j = 0; i < txtLen && j < patLen; i++) {
    		j = dfa[txt.charAt(i)][j];
    	}
    	if (j == patLen) {
    		return i - patLen;
    	}
    	else {
    		return txtLen;
    	}
		//return 0;
    }
    
    
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    try{
			s = new Scanner(new File(args[0]));
	    } catch(java.io.FileNotFoundException e){
			System.out.println("Unable to open "+args[0]+ ".");
			return;
	    }
	    System.out.println("Opened file "+args[0] + ".");
	    String text = "";
	    while(s.hasNext()){
			text += s.next() + " ";
	    }
	    
	    for(int i = 1; i < args.length; i++){
			KMP k = new KMP(args[i]);
			int index = search(text);
			if(index >= text.length())System.out.println(args[i] + " was not found.");
			else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
	    }
	    
	    //System.out.println(text);
	    
	}
	else{
	    System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
	}
	
	
    }
}