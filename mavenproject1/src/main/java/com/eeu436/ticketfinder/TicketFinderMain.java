package com.eeu436.ticketfinder;

import java.util.Scanner;

/**
 *
 * @author D.B. Dressler
 */
public class TicketFinderMain {
    
    EventManager manager = new EventManager();
    
    
    public void showWelcome(){
        System.out.println("********************************************");
        System.out.println("TICKET FINDER V 0.8 - D.B. Dressler (c) 2017\n\n");
    }
    
    public void gameLoop(){
        boolean gameOver = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter exit at any point to exit.");
        int x = 0, y = 0;
        while(!gameOver){
            
            System.out.println("Enter your location:");
            System.out.print("Enter x: ");
            String input = sc.nextLine();
            input = input.toLowerCase();
            if(input.equals("exit")){
                break;
            }
            System.out.print("Enter y: ");
            input = sc.nextLine();
            input = input.toLowerCase();
            if(input.equals("exit")){
                break;
            }
            x = Integer.parseInt(input);
            y = Integer.parseInt(input);
            
            int[][] eventsNearby = manager.getClosestEvents(x, y);
            System.out.println("5 closest events:");
            for(int i = 0; i < eventsNearby[0].length; i++){
                int j = eventsNearby[1][i];
                int k = eventsNearby[2][i];
                int dist = eventsNearby[0][i];
                int id = manager.getEventID(j, k);
                System.out.println("Event " + id + " at(" + j + ", " + k +")," + dist + "KM");
            }
            
            System.out.println("Search for another location Y/N ");
            input = sc.nextLine();
            input.toLowerCase();
            if(input.charAt(0) == 'n'){
                gameOver = true;
            }
            
        }
        System.out.println("End");
        sc.close();
    }
    
    
    public static void main(String[] args){
        
        TicketFinderMain m = new TicketFinderMain();
        m.showWelcome();
        m.gameLoop();
    }
}
