/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * 
 */
public class LoadMenu implements Menu{
    
    private ArrayList<ObjInterface> objects;
    private double gravity;
    private double friction;
    private CareTaker caretaker;
    Scanner in = new Scanner(System.in);  
    boolean resetFlag = true;
    
    LoadMenu(CareTaker ct){
        caretaker = ct;
    }
    
    @Override
    public void displayOptions(){
        System.out.println("\nLoad Menu:");
        System.out.println("\t 1) Load Simulation.");
        System.out.println("\t 2) Back to Main Menu.");
        System.out.println("\t 3) Exit.");
    }
    
    @Override
    public void getInput(){
        int num = 999;
        
        while(num != 1 && num != 2 && num != 3){
            System.out.println("\nPlease choose an option listed above.");
            num = in.nextInt();
        }

        switch (num) {
            case 1:
                caretaker.listFiles();
                System.out.println("Please input the name of the file you would like to load: ");
                in.nextLine();
                String fileName = in.nextLine()+".physX";
                    if(fileName.equals(".physX")){
                        break;
                    }
        
                {
                try {
                    objects = caretaker.serializeDataIn(fileName);
                    } catch (IOException ex) {
                        System.out.println("Error: Unable to load file.");
                        resetFlag = false;
                        break;
                    } catch (ClassNotFoundException ex) {
                        System.out.println("Error: Unable to load file.");
                        resetFlag = false;
                        break;
                    }
                }
                System.out.println("File successfully loaded.\n");
                
                System.out.println("Loading Settings Menu...\n");
                Menu gm = new GlobalMenu(objects, caretaker);
                gm.start();
                break;
            case 2:
                System.out.println("Returning to Main Menu...\n");
                resetFlag = false;
                break;
            case 3:
                caretaker.deleteState();
                System.out.println("\nPress Enter to End Program:\n\n");
                try {
                    System.in.read();
                } catch (IOException ex) {}                
                System.exit(0);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void start(){
        while(resetFlag){
            this.displayOptions();
            this.getInput();
        }
    }

}
    


