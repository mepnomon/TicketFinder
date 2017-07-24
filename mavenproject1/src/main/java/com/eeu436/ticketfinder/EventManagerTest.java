package com.eeu436.ticketfinder;

import java.util.Arrays;

/**
 *
 * @author D.B. Dressler
 */
public class EventManagerTest {
    
      /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        // TODO code application logic here
        EventManager test = new EventManager();
        test.printArr();
        System.out.println("Manhattan distance  test\n\n");
        //test manhattan distance
        //test.getManhattanDistance();
          //test coordinate conversion
//        for(int i = -10; i <= 10; i++){
//            test.convertCoordinates(i, i);
//        }
          //test getting the nearby events
//        ArrayList<Integer> arrList = test.getNearbyEvents(4,4);     
//        for(int i : arrList){
//            System.out.println(i);
//        }
        //test finding the closest events, using manhattan distance
       //int closest[][] = test.getClosestEvents(-5, -5);
       //int closest[][] = test.getClosestEvents(-10, -10);
       int closest[][] = test.getClosestEvents(-8, -8);
       for(int i = 0; i < closest.length; i++){
           for(int j = 0; j < closest[0].length; j++){
               System.out.print(closest[i][j] + " ");
           }
           System.out.println("");
       }
        System.out.println("\n\n\n\n");
        for(int i = 0; i < closest[0].length; i++){
            String s[] = test.getEventTickets(closest[1][i], closest[2][i]);
            System.out.println("Event id: " + test.getEventID(closest[1][i], closest[2][i]));
            System.out.println("Distance: " + closest[0][i]);
            int[] userCoordinates = test.convertCoordinatesToUser(closest[1][i], closest[2][i]);
            System.out.println("User coordinnates:" + Arrays.toString(userCoordinates));
            for(String str : s){
                System.out.println(str);
            }
            System.out.println("");
        }
        //test.convertCoordinates(-11, 0);
    }   
}
