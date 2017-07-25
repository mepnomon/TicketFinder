package com.eeu436.ticketfinder;

import java.util.*;
//import java.util.*;
/**
 * Manhattan distance test class.
 * Implements the ability to populate a grid
 * Find the Manhattan distance between two places
 * Find the nearest five venues that have the value 1
 * 
 * @author D.B. Dressler
 */
public class EventManager {

    //Globals
    Random generator;
    final int WORLD_SIZE = 21;
    int[][] gridShadowArr = new int[WORLD_SIZE][WORLD_SIZE];
    Event[][] eventArr = new Event[WORLD_SIZE][WORLD_SIZE];
    int[][] mergeArray;
    
    
    /**
     * Constructor
     */
    public EventManager(){
        
        generator = new Random();
        createEventGrid();
        
    }
    
    
    /**
     * Prints grid
     */
    public void printArr(){
        for(int i = 0; i < WORLD_SIZE; i++){
            for(int j = 0; j < WORLD_SIZE; j++){
                System.out.print(gridShadowArr[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * 
     */
    public void createEventGrid(){
       
        float rand;
        int id = 0;
        //populate array
        for(int i = 0; i < WORLD_SIZE; i++){
            for(int j = 0; j < WORLD_SIZE; j++){
                rand = generator.nextFloat();
                //System.out.println(rand);
                if(rand < 0.25){
                    gridShadowArr[i][j] = 1;
                    eventArr[i][j] = new Event(i, j, id++);
                    //get real x y coordinates
                    
                } else {
                    gridShadowArr[i][j] = 0;
                }
                    
            }
        }
    }
    
    
    /**
     * Calculates Manhattan distance between points
     * @param x
     * @param y
     * @param eventX
     * @param eventY
     * @return 
     */
    public float getManhattanDistance(int x, int y, int eventX, int eventY){

            float dist = Math.abs(x  - eventX) + Math.abs(y - eventY);
            return dist;
            //System.out.println(dist);
        }
    
    
    /**
     * Convert a range of coordinates from -10 to 10
     * into 0 to 20
     * @param x
     * @param y 
     * @return  
     */
    public int[] convertCoordinatesToLocal(int x, int y){
        
        //int newX = 0, newY = 0;
        int[] convCoordinates = new int[2];
        
        //check if valid range - not really necessary
        //check from caller
        if(x > (-11) && x < 11 && y > (-11) && y < 11){
            //System.out.println("x input= " + x);
            //coonvert
            convCoordinates[0] = (x + 10);
            convCoordinates[1] = (y + 10);
        } else {
            //error, out of bounds
            System.out.println("Out of bounds");
        }
        
        //return the converted coordinates
        return convCoordinates;
    }
    
    public int[] convertCoordinatesToUser(int x, int y){
        
        int[] userCoordinates = new int[2];
        userCoordinates[0] = (x-10);
        userCoordinates[1] = (y-10);
        
        return userCoordinates;
    }
    
    /**
     * localizes the 5 closest events
     * takes raw coordinates for now
     * @param x
     * @param y
     * @return 
     */
    public int[][] getClosestEvents(int x, int y){
        //TODO:
        
        // 1. convert the user supplied coordinates
        int[] x_y = convertCoordinatesToLocal(x, y);
        
        // 2. get closest events by kernel
        ArrayList<Integer> nearbyEvents = getNearbyEvents(x_y[0],x_y[1]);
        
        // 3. Get manhattan distance for these events
        System.out.println("Size:" + nearbyEvents.size());
        //x rows, 3 columns
        //size needs to be adjusted
        double size = nearbyEvents.size()/2.0;
        int[][] closeEvents = new int[3][(int)size];
        //parse all received events
        int j = 0;
        for(int i = 0; i < nearbyEvents.size(); i += 2){
            //System.out.print(i + ", " + (i+1) + " ");
            System.out.println(nearbyEvents.get(i) + 
                    " " + nearbyEvents.get(i+1) + ", ");
            //get individual distance
            float dist = getManhattanDistance(x_y[0],x_y[1], nearbyEvents.get(i), 
                    nearbyEvents.get(i + 1));
            closeEvents[0][j] = (int)dist;
            closeEvents[1][j] = nearbyEvents.get(i);
            closeEvents[2][j++] = nearbyEvents.get(i+1);
            System.out.println("Distance: " + dist);
            
        }
        // 4. locate the five closest
        for(int i = 0; i < 3; i++){
            for(j = 0; j < size; j++){
                System.out.print(closeEvents[i][j] + " ");
            }
            System.out.println("");
        }
        
        //merge sort closeArray
        //merge(closeEvents, 0, (int)size/2, (int)size-1);
        sort(closeEvents, 0, (int)size-1);
        
        //print the merged array
        //System.out.println("sorted");
//        for(int i = 0; i < 3; i++){
//            for(int k = 0; k < mergeArray[0].length; k++){
//                System.out.print(mergeArray[i][k] + " ");
//            }
//            System.out.println("");
//        }
        int[][] returnArr = new int[3][5];
        //populate returnArr
        for(int i = 0; i < returnArr.length; i++){
            for(int k = 0; k < returnArr[0].length; k++){
                returnArr[i][k] = mergeArray[i][k];
            }
        }
        
        //return 5 closest events
        return returnArr;
    }
    
    /**
     * Merge sort implementation
     * Base Code from: http://www.geeksforgeeks.org/merge-sort/
     * Adapted for 2D.
     * @param arr 2d array
     * @param l leftmost
     * @param m middle
     * @param r rightmost
     */
    public void merge(int[][] arr, int l, int m, int r){
        //System.out.println("\nMerge:");
        //System.out.println(arr[0][l] + " " + arr[0][m] + " " + arr[0][r]);
        
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
        
        //Create temp arrays 
        int L[][] = new int [3][n1];
        int R[][] = new int [3][n2];
        
        //Copy data to temp arrays
        for (int i = 0; i < n1; ++i){
            L[0][i] = arr[0][l + i];
            L[1][i] = arr[1][l + i];
            L[2][i] = arr[2][l + i];
        }
        for (int j=0; j<n2; ++j){
            R[0][j] = arr[0][m + 1+ j];
            R[1][j] = arr[1][m + 1+ j];
            R[2][j] = arr[2][m + 1+ j];
        }
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[0][i] <= R[0][j])
            {
                arr[0][k] = L[0][i];
                arr[1][k] = L[1][i];
                arr[2][k] = L[2][i];
                
                i++;
            }
            else
            {
                arr[0][k] = R[0][j];
                arr[1][k] = R[1][j];
                arr[2][k] = R[2][j];
                j++;
            }
            k++;
        }
 
        //Copy remaining elements of L[] if any 
        while (i < n1)
        {
            arr[0][k] = L[0][i];
            arr[1][k] = L[1][i];
            arr[2][k] = L[2][i];
            
            i++;
            k++;
        }
 
        // Copy remaining elements of R[] if any
        while (j < n2)
        {
            arr[0][k] = R[0][j];
            arr[1][k] = R[1][j];
            arr[2][k] = R[2][j];
            j++;
            k++;
        }
        mergeArray = arr;
    }
   
    /**
     * 
     * @param x
     * @param y 
     */
    public String[] getEventTickets(int x, int y){
        Event anEvent = eventArr[x][y];
        String[] tickets = anEvent.getTickets();
        return tickets;
    }
    
    public int getEventID(int x, int y){
        
        Event anEvent = eventArr[x][y];
        return anEvent.getId();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   /**
    * Main merge sort function that sorts [l...r]
    * @param arr
    * @param l
    * @param r 
    */
    public void sort(int arr[][], int l, int r)
    {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;
 
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr , m+1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
        
    }
    
    /**
     * Generates a kernel a dynamically increasing kernel
     * Finds the events in this kernel
     * @param offsRow the user's x
     * @param offsCol the user's y
     * @return an List of X,Y coordinates, whitespace separated
     */
            
    public ArrayList<Integer> getNearbyEvents(int offsRow, int offsCol){
        //local variables
        ArrayList<Integer> arrList = new ArrayList<>();
        int ticketsFound = 0;
        //initial range for 3x3 kernel
        int range = 3;
        //initialze start positions
        int startRow = 0;
        int startCol = 0;
        //iterate until 5 or more ave been found
        while(ticketsFound <= 5){
            //reset tickets count
            ticketsFound = 0;
            //reset arraylist
            arrList.clear();
           //System.out.println("new loop " + range);
            
            //check boundary cases for kernel offset
            if((offsRow - range) >= 0 || offsRow - range < 20){
                startRow = offsRow-range;
                System.out.println("case 0 " + startRow);
            }
            
            if((offsCol - range) >= 0 || offsCol - range < 20){
                startCol = offsRow-range;
                System.out.println("case 1 " + startCol);
            }
            
            
            if((offsRow - range) < 0){
                startRow = 0;
                System.out.println("case 2 " + startRow);
            }
            
            if((offsCol - range) < 0){
                startCol = 0;
                System.out.println("case 3 " + startCol);
            }
            
            if(offsCol >= 20){
                startCol = 20-range;
                System.out.println("case 4 " + startCol);
            }
            
            if(offsRow >= 20){
                startRow = 20-range;
                System.out.println("Case 5 " + startRow);
            }
            
            //traverse kernel searching for events
            for(int i = startRow; i <= startRow+range; i++){
                for(int j = startCol; j <=startCol+range;j++){
                    System.out.println("start Row " + startRow);
                    System.out.println("Start Col " + startCol);
                    //exclude user position
                    if(i == offsRow && j == offsCol){
                        //System.out.println("Your position");

                    } else {
                        
                        if(gridShadowArr[i][j] == 1){
                            //add both coordinates
                            arrList.add(i);
                            arrList.add(j);
                            ++ticketsFound;
                        }
                        //System.out.println("Visiting "  + i + " " + j);
                        //System.out.println("Value" + arr[i][j]);
                    }
                }//inner for loop
            }//outer for loop
           
            //increase kernel range if < 5 matches
            ++range;
        }
        //a list of events
        return arrList;
    } 
}
