package com.eeu436.ticketfinder;

import java.util.*;
/**
 * TicketFinder - Event Manager
 * Class Manages events, e.g. generation and population of seed data.
 * Includes search and sorting algorithms for grid-world.
 * Public methods can be called from other classes.
 * @author D.B. Dressler
 */
public class EventManager {

    //Globals
    //Random number generator
    Random generator;
    //maximum world size (for logic)
    final int WORLD_SIZE = 21;
    //conversion for coordinates
    final int CONVERT_C = 10;
    //stores number of events
    int[][] mapGrid = new int[WORLD_SIZE][WORLD_SIZE];
    //stores events
    Event[][] eventGrid = new Event[WORLD_SIZE][WORLD_SIZE];
    //used in merge sort
    int[][] mergeArray;
    
    
    /**
     * Constructor
     */
    public EventManager(){
        
        //initialize RNG
        generator = new Random();
        
        //percentage of fields with an event
        double percentage = 0.50;
        //create the world
        populateGridsWithSeedData(percentage);
        
    }
    
    
    /**
     * Prints grid
     */
    public void printArr(){
        for(int i = 0; i < WORLD_SIZE; i++){
            for(int j = 0; j < WORLD_SIZE; j++){
                System.out.print(mapGrid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Populates grids with seed data
     * @param eventPercentage denotes the percentage of cells with an event
     */
    private void populateGridsWithSeedData(double eventPercentage){
       
        //local variables
        float rand;
        int id = 0; //unique id for an event
        int isEvent = 1;
        int isNotEvent = 0;
        //populate arrays
        for(int i = 0; i < WORLD_SIZE; i++){
            for(int j = 0; j < WORLD_SIZE; j++){
                rand = generator.nextFloat();
                
                //is an event
                if(rand < eventPercentage){
                    //define event in mapGrid
                    mapGrid[i][j] = isEvent; //1 for event
                    //instantiate new event in eventGrid
                    eventGrid[i][j] = new Event(i, j, id++);
                    
                } else { //not an event
                    mapGrid[i][j] = isNotEvent;
                }
                    
            }
        }
    }
    
    
    /**
     * Calculates Manhattan distance between points.
     * Uses local coordinates (logic-based)
     * @param usrR user's location Row
     * @param usrC user's location Column
     * @param eventR event in Row
     * @param eventC event in Column
     * @return Manhattan distance
     */
    private float getManhattanDistance(int usrR, int usrC, int eventR, 
            int eventC){
            
            //calculate Manhattan Distance
            float dist = Math.abs(usrR  - eventR) + Math.abs(usrC - eventC);
            //return distance
            return dist;
        }
    
    
    /**
     * Converts world coordinates to local.
     * @param row
     * @param col 
     * @return  a set of converted x, y coordinates
     */
    private int[] convertCoordinatesToLocal(int row, int col){
        
        //array to store set of coordinates
        int[] localCoordinates = new int[2];
        //convert
        localCoordinates[0] = (row + CONVERT_C);
        localCoordinates[1] = (col + CONVERT_C);
        
        //return the converted coordinates
        return localCoordinates;
    }
    
    /**
     * Converts local coordinates to world, for user 
     * @param row
     * @param col
     * @return 
     */
    public int[] convertCoordinatesToWorld(int row, int col){
        //array to store set of coordinates
        int[] worldCoordinates = new int[2];
        //convert
        worldCoordinates[0] = (row-CONVERT_C);
        worldCoordinates[1] = (col-CONVERT_C);
        //return converted coordinates
        return worldCoordinates;
    }
    
    /**
     * Gets the 5 closest events from a user position
     * @param x user-world x
     * @param y user-world y
     * @return the 5 closest event locations and distance
     */
    public int[][] getClosestEvents(int x, int y){

        // 1. convert to local coordinates
        int[] x_y = convertCoordinatesToLocal(x, y);
        //System.out.println("Convert to: " + x_y[0] + " " + x_y[1]);
        
        // 2. get closest events from kernel
        ArrayList<Integer> nearbyEvents = getNearbyEvents(x_y[0],x_y[1]);
        
        // 3. sort closest events into closest 5 by distance
        double size = nearbyEvents.size()/2.0;
        int[][] closeEvents = new int[3][(int)size];
        
        //parse all received events
        int j = 0;
        for(int i = 0; i < nearbyEvents.size(); i += 2){
            //get individual distance
            float dist = getManhattanDistance(x_y[0],x_y[1], nearbyEvents.get(i), 
                    nearbyEvents.get(i + 1));
            closeEvents[0][j] = (int)dist;
            closeEvents[1][j] = nearbyEvents.get(i);
            closeEvents[2][j++] = nearbyEvents.get(i+1);
            //System.out.println("Distance: " + dist);
            
        }

        //4. Sort by distance (closest first)
        sort(closeEvents, 0, (int)size-1);
        // Array to return data 5 closest events
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
    private void merge(int[][] arr, int l, int m, int r){
        
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
        //Merge the temp arrays 
 
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
     * Gets all available tickets for an event.
     * @param row local x
     * @param col local y
     * @return a list of tickets in USD.
     */
    public String[] getEventTickets(int row, int col){
        //local event
        Event anEvent = eventGrid[row][col];
        //get tickets for event
        String[] tickets = anEvent.getTickets();
        //rerturn tickets
        return tickets;
    }
    
    /**
     * Retrieves cheapest ticket from an Event
     * @param row event row
     * @param col event column
     * @return the cheapest ticket
     */
    public String getCheapestEventTicket(int row, int col){
        Event anEvent = eventGrid[row][col];
        return anEvent.getCheapestTicket();
    }
    
    public int getEventID(int x, int y){
        
        Event anEvent = eventGrid[x][y];
        return anEvent.getId();
    }
    
   /**
    * Main merge sort function that sorts [l...r]
    * @param arr
    * @param l
    * @param r 
    */
    private void sort(int arr[][], int l, int r)
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
     * Retrieves events close to user (excluding user's location)
     * Finds the events in this kernel
     * @param userRow the user's x
     * @param userCol the user's y
     * @return an List of X,Y coordinates, whitespace separated
     */
            
    private ArrayList<Integer> getNearbyEvents(int userRow, int userCol){
        
        final int UP_BOUND = WORLD_SIZE-1;
        final int LOW_BOUND = 0;
        //local variables
        ArrayList<Integer> nearbyEvents = new ArrayList<>();
        //counts tickets found
        int ticketsFound = 0;
        //initial range for 3x3 kernel
        int range = 1;
        //initialze start positions
        int startRow = 0;
        int startCol = 0;
        
        
        //iterate until 5 or more ave been found
        while(ticketsFound <= 5){
            
            // Reset tickets count
            ticketsFound = 0;
            
            // Clear array lsit
            nearbyEvents.clear();
            
            // Check boundary cases to ensure that kernel does not
            // step outside the array.
            if((userRow - range) >= LOW_BOUND && (userRow - range) < UP_BOUND){
                startRow = userRow-range;
            }
            
            if((userCol - range) >= LOW_BOUND && (userCol - range) < UP_BOUND){
                startCol = userRow-range;
            }
           
            //check boundary cases for kernel offset
            if((userRow - range) < LOW_BOUND || startRow < LOW_BOUND){
                startRow = LOW_BOUND;
            }
            
            if((userCol - range) < LOW_BOUND || startCol < LOW_BOUND){
                startCol = LOW_BOUND;
            }
            
            if(userCol >= UP_BOUND || startCol >=UP_BOUND){
                startCol = UP_BOUND-range;
            }
            
            if(userRow >= UP_BOUND || startRow >= UP_BOUND){
                startRow = UP_BOUND-range;
            }
            
            //traverse kernel searching for events
            for(int i = startRow; i <= startRow+range; i++){
                for(int j = startCol; j <=startCol+range;j++){
                    
                    // Exclude user position
                    if(i == userRow && j == userCol){
                        //do nothing
                    } else {
                        // An event was found
                        if(mapGrid[i][j] == 1){
                            // Add coordinates
                            nearbyEvents.add(i);
                            nearbyEvents.add(j);
                            // Increment couter
                            ++ticketsFound;
                        }
                    }
                }// End inner loop
            }// End outer loop
           
            // Increase kernel range if < 5 matches
            ++range;
        } // Loop exits when >= 5 events have been located.
        
        // Return all nearby events within kernel
        return nearbyEvents;
    } 
}
