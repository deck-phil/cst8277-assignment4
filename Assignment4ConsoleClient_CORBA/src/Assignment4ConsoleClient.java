
import business.FishStickFacadeRemote;
import entity.FishStick;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Console client for the assignment 4
 * JEE Project
 * 
 * Permits clients to 
 *  [get] the data
 *  [insert] records
 *  [quit] the application
 * 
 * @author Philip Deck, Mahad Osman, Ivan Zubaryev, Adam Tremblett
 */
public class Assignment4ConsoleClient {

    public static void main(String[] args) {
        FishStickFacadeRemote session = null;
        
        // CORBA properties and values and lookup taken after earlier work provided by
        // Todd Kelley (2016) Personal Communication
        System.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        try {
            System.out.println("about to try for a session...");
            InitialContext ic = new InitialContext();
            //Grab a session
            session = (FishStickFacadeRemote) ic.lookup("java:global/Assignment4/Assignment4-ejb/FishStickFacade");
            System.out.println("Got a session :) ");
            
            System.out.println("Creating and inserting a Stuff record into database");
            //Line reader for client input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            
            //Loop while the client doesnt write quit
            do{
                System.out.println("Enter Command:");
                //Read the line
                line = br.readLine();
                switch(line){
                    case "insert" :
                        System.out.println("Inserting Stuff");
                        FishStick fishStick = new FishStick();
                        
                        //Loop while record number isnt a number
                        do {
                            System.out.print("Please entera record number: ");
                            line = br.readLine();// read the user input
			} while (!line.matches("-?\\d+")); // while the input isn't only numeric
			fishStick.setRecordNumber(Integer.parseInt(line)); // set the fishstick record number
                        System.out.print("Please enter omega: ");
			fishStick.setOmega(br.readLine()); // set the fishstick omega
			System.out.print("Please enter lambda: ");
			fishStick.setLambda(br.readLine()); // set the fishstick lambda
                        session.create(fishStick);
                        break;
                    case "get" :
                        System.out.println("Listing contents of database");
                        for(FishStick s: session.findAll()){
                            //For each fishstick, print it
                            System.out.println(s.toString());
                        }  
                        break;
                }
                
            }while(!line.equals("quit"));
        } catch (NamingException e) {
            System.out.println(e.getMessage());
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        } 
    }
}
