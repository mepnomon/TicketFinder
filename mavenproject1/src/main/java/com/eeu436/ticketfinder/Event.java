package com.eeu436.ticketfinder;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author D.B. Dressler
 */
public class Event {
    
    //user-world x y locations and unique identifier for event
    int x, y, id;
    String[] tickets;
    
    /**
     * Constructor
     * @param x user-world x
     * @param y user-world y
     * @param id unique id for an event
     */
    public Event(int x, int y,int id){
        
        tickets = new String[0];
        this.x = x;
        this.y = y;
        this.id = id;
        generateTickets();
    }
    
    
    /**
     * Generates random seed tickets
     * with values $10.99 to $150.00
     */
    private void generateTickets(){
        
        Random generator = new Random();
        
        //at a maximum price of USD 150
        double minPrice = 10.99;
        double maxPrice = 150.00;
        int maxTickets = 10;
        //generate a maximum of 5 tickets
        int ticketCount = generator.nextInt(maxTickets);
        
        //System.out.println("Tickets generated: " + ticketCount);
        
        double ticketDecimal[] = new double[ticketCount];
        //generate random ticket prices between min and max
        for(int i = 0; i < ticketCount; i++){
            double range = maxPrice - minPrice;
            double scaled = generator.nextDouble() * range;
            double shifted = scaled + minPrice;
            ticketDecimal[i] = shifted;
        }
        //sort array
        Arrays.sort(ticketDecimal);
        
        
        tickets = new String[ticketCount];
        //convert double array to formatted string array
        for(int i = 0; i < ticketDecimal.length;i++){
            String temp = String.format("%1$,.2f", ticketDecimal[i]);
             tickets[i] = "$" + temp;
        }
    }
    
    /**
     * Returns the tickets in ascending order
     * least to most expensive
     * @return an array of tickets
     */
    public String[] getTickets(){
        
        if(tickets.length == 0){
            tickets = new String[1];
            String returnString = "Sorry! All sold out.";
            tickets[0] = returnString;
            return tickets;
        } else {
           return tickets;  
        }
    }
    
    
    public int getX(){
        
        return x;
    }
    
    public int getY(){
        
        return y;
    }
    
    public int getId(){
        
        return id;
    }
}
