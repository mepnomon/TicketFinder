package com.eeu436.ticketfinder;

import java.util.Scanner;

/**
 * Ticket Finder Main Entry Point
 * @author D.B. Dressler
 */
public class TicketFinderMain {
    
    //Globals
    EventManager manager = new EventManager();
    
    public void showWelcome(){
        System.out.println("********************************************");
        System.out.println("TICKET FINDER V 0.8 - D.B. Dressler (c) 2017\n\n");
    }
    
    /**
     * Main game loop
     */
    public void gameLoop(){
        
        //local variables
        boolean gameOver = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter exit at any point to exit.");
        int x = 0, y = 0;
        boolean isValid = false;
        while(!gameOver){
        boolean exitFlag = false;  
            String input = "";
            
            //make sure coordinate input is valid
            while(!isValid){
                System.out.println("Enter your coordinates! From -10 to 10 (incl)");
                System.out.print("Enter Row:> ");
                //get next user input parameter
                input = sc.nextLine();
                //convert to lower case
                input = input.toLowerCase();
                if(input.equals("exit")){
                    exitFlag = true;
                    break;
                }
                System.out.print("Enter Col:> ");
                input = sc.nextLine();
                input = input.toLowerCase();
                if(input.equals("exit")){
                    exitFlag = true;
                    break;
                }
                x = Integer.parseInt(input);
                y = Integer.parseInt(input);
                if(x >= -10 && x <= 10 && y >= -10 && y <= 10){
                    isValid = true;
                } else {
                    System.out.println("Please Enter valid coordinates.");
                    System.out.println("x,y in range of -10 to 10 (inclusive)\n");
                }
            }
            //check if user selected to exit
            if(exitFlag){
                break;
            }
            isValid = false;
            printEvents(x, y);
            
            while(!isValid){
                System.out.print("\n\nSearch for another location Y/N:>");
                input = sc.nextLine();
                input.toLowerCase();
                
                if(input.charAt(0) == 'n'){
                    gameOver = true;
                    isValid = true;
                } else if(input.charAt(0) == 'y'){
                    isValid = true;
                } else {
                    System.out.println("Your answer is invalid.");
                    System.out.println("Please types Yes or No");
                }
            }
            isValid = false;
        }
        System.out.println("\n\nThank you for using TicketFinder");
        sc.close();
    }
    
    /**
     * 
     * @param x
     * @param y 
     */
    private void printEvents(int x, int y){
        int[][] eventsNearby = manager.getClosestEvents(x, y);
            System.out.println("\n 5 events closest to (" + x + "," + y + "):");
            for(int i = 0; i < eventsNearby[0].length; i++){
                int j = eventsNearby[1][i];
                int k = eventsNearby[2][i];
                int x_y[] = manager.convertCoordinatesToWorld(j, k);
                int worldJ = x_y[0];
                int worldK = x_y[1];
                int dist = eventsNearby[0][i];
                int id = manager.getEventID(j, k);
                System.out.print("\nEvent " + id + " at(" + worldJ + ", " 
                        + worldK +")," + dist + "KM");
                getEventTickets(j, k);
            }
    }
    
    /**
     * 
     * @param eventRow
     * @param eventCol 
     */
    private void getEventTickets(int eventRow,int eventCol){
        String[] tickets = manager.getEventTickets(eventRow, eventCol);
        System.out.println("\nTickets available:");
        for(String s : tickets){
            System.out.println(s);
        }
    }
    
    
    public static void main(String[] args){
        
        TicketFinderMain m = new TicketFinderMain();
        m.showWelcome();
        m.gameLoop();
    }
}
