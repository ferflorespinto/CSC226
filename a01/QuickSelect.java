/**
 *
 * @author Jorge Fernando Flores Pinto
 * ID: V00880059
 * October 5, 2017
 *
 * Built from Nishant's starter code.
 * CSC 226 - Fall 2017
 */
public class QuickSelect {

    //k - kth smallest element 
    //PRECONDITIONS: k has to be valid, i.e., between 1 and A.length 
    //QuickSelect and Partition follow (partly) the pseudocode found in:
    //https://en.wikipedia.org/wiki/Quickselect
    public static int QuickSelect(int[] A, int k) {
        if (A.length == 1 && k == 1) {
            return A[0];
        }
        else if (k > A.length || k <= 0) {
            return -1;
        }

        k--; // To be consistent with indices

        int[] medianMap = getMedians(A, A.length, 0, A.length - 1);

        int pivotIndex = 0;
        if (A.length <= 7) {
            pivotIndex = medianMap[0];
        }
        else {
            pivotIndex = medianMap[(medianMap.length / 2) - 1];
        }

        int kSmallest = select(A, pivotIndex, 0, A.length - 1, k);

        k++;
        System.out.println(k + "th smallest element: " + kSmallest);

        return kSmallest;
    } 
    public static void swap(int[] A, int idx1, int idx2) {
        if (A[idx1] != A[idx2]) {
            int temp = A[idx1];
            A[idx1] = A[idx2];
            A[idx2] = temp;
        }
    }
    //partition moves elements smaller than the pivot to the left of the pivot,
    //and larger elements to the right.
    public static int partition(int[] A, int left, int right, int pivotIndex) {
        int pivot = A[pivotIndex];
        int temp = 0;
        swap(A, pivotIndex, right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (A[i] < pivot) {
                swap(A, storeIndex, i);
                storeIndex++;
            }
        }
        swap(A, storeIndex, right);

        return storeIndex;
    }
    //Recursively selects the kth smallest element
    //Based on pseudocode in:
    //https://en.wikipedia.org/wiki/Quickselect
    public static int select(int[] A, int pivotIndex, int left, int right, int k) {
        if (left == right) {
            return A[left];
        }

        //public static int[] getMedians(int[] A, int size, int start, int finish) {
        int[] medianMap = getMedians(A, right - left + 1, left, right);
        pivotIndex = medianMap[(medianMap.length / 2)];

        pivotIndex = partition(A, left, right, pivotIndex);

        if (k == pivotIndex) {
            return A[k];
        }
        else if (k < pivotIndex) {
            return select(A, pivotIndex, left, right - 1, k);
        }
        else {
            return select(A, pivotIndex, left + 1, right, k);
        }

    }
    //Creates a group of k elements from the original array
    public static int[] grouping(int[] A, int[] section, int start, int finish) {
    	int[] sectionMap = new int[section.length];
        int mapIndex = start;
        for(int i = 0; i < section.length; i++) {
            sectionMap[i] = mapIndex;
            mapIndex++;
        }

    	//System.arraycopy(source, start of source, destination, start of destination, # elements)
        System.arraycopy(A, start, section, 0, finish - start + 1);
        sort(section, sectionMap);

    	return sectionMap;
    }

    //Insertion sort. Followed the pseudocode found in:
    //https://en.wikipedia.org/wiki/Insertion_sort 
    //Worst case: O(k^2), where k is the number of elements in each group.
    public static void sort(int[] sect, int[] map) {
        int i = 1;
        int temp = 0;
        while (i < sect.length) {
            int j = i;
            while (j > 0 && sect[j - 1] > sect[j]) {
                //Swapping positions in section
                swap(sect, j - 1, j);

                //Swapping positions in map
                swap(map, j - 1, j);
                j--;
            }
            i++;
        }
    }

    //Generates the array of medians
    public static int[] getMedians(int[] A, int size, int start, int finish) {
        int medianSize = (size / 7);
        if (size % 7 != 0) {
            medianSize = (size / 7) + 1;
        }
        int[] medians = new int[medianSize];
        int[] medianMap = new int[medianSize];
        int index = 0;
        while (start < finish) {
            if (finish - start >= 7) {
                int[] temp = new int[7];
                int[] sectionMap = grouping(A, temp, start, start + 6);
                medians[index] = temp[3];
                medianMap[index] = sectionMap[3];
            }
            else {
                int[] temp = new int[finish - start + 1];
                int[] sectionMap = grouping(A, temp, start, finish);
                medians[index] = temp[(finish - start + 1) / 2];
                medianMap[index] = sectionMap[(finish - start + 1) / 2];
            }
            index++;
            start += 7;

        }
        return medianMap;
    }
    //toString method mainly used for testing
    public static void toString(int[] arr) {
    	for(int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                System.out.println(arr[i]);
            }
            else {
                System.out.print(arr[i] + ", ");
            }
    	}
    }
    public static void main(String[] args) {
        int[] A = {50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59};
        int[] B = {50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59};
        int[] C = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] D = {0};
        int[] E = {1, 2, 3, 4};
        int[] F = {21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 , 0};
        int[] Bmap = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] unsortedFibonacci = {3, 1, 2, 1, 0, 5, 8, 21, 34, 13, 233, 89, 610, 55, 377, 144, 2584, 1597, 987, 17711, 10946, 6765, 46368, 28657, 4081, 317811, 121393, 196418, 75025};
        //System.out.println("Original array A: ");
        //toString(A);
        //System.out.println("Sorted array A: ");
        //sort(B, Bmap);
        //toString(B);
        System.out.println("Original array fib: ");
        toString(unsortedFibonacci);
        System.out.println("Size: " + unsortedFibonacci.length);
        //Sorted: 48, 49, 49, 49, 50, 50, 51, 52, 52, 54, 56, 59

        //int[] section = grouping(A, 0, 7);
        //int[] medians = getMedians(A, A.length);
       // medians[0] = section[3];
        //System.out.println("The median is " + QuickSelect(A, (A.length+1)/2));

        //System.out.println("medians: ");
        //toString(medians);
        int k = QuickSelect(unsortedFibonacci, 2);
        System.out.println(k);
        //System.out.println("Our selected median is: " + medians[(medians.length / 2) - 1]);


    }
    
}