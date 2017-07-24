/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eeu436.ticketfinder;

/**
 *
 * @author Mepnomon
 */
public class EventTester {
    
    public static void main(String[] args){
        Event anEvent = new Event(-5, -5, 1);
        System.out.println("x: " + anEvent.getX() + ", y: " + anEvent.getY());
        System.out.println("ID: " + anEvent.getId());
        String[] tickets = anEvent.getTickets();
        System.out.println("\nshowing tickets");
        for(String s : tickets){
            System.out.println(s);
        }
    }
}
