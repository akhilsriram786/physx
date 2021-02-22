/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ActionMenu implements Menu{
    
    private ArrayList<ObjInterface> objects;
    private double gravity;
    private double friction;
    private ArrayList<ObjInterface> prevState;
    private ArrayList<ObjInterface> afterState;
    private Command rCmd;
    private Command uCmd;
    private CareTaker caretaker;
    private boolean resetFlag = true;
    
    ActionMenu(ArrayList<ObjInterface> objs, double grav, double fric, CareTaker ct){
        objects = objs;
        gravity = grav;
        friction = fric;
        caretaker = ct;
    }
    
    @Override
    public void displayOptions(){
        System.out.println("\nAction Menu:");
        System.out.println("\t 1) List Last Command.");
        System.out.println("\t 2) Undo.");
        System.out.println("\t 3) Redo.");
        System.out.println("\t 4) List Objects.");
        System.out.println("\t 5) Command: Push.");
        System.out.println("\t 6) Command: Lift.");
        System.out.println("\t 7) Command: Drop.");
        System.out.println("\t 8) Save Simulation.");
        System.out.println("\t 9) Back to Settings Menu.");
        System.out.println("\t 10) Exit.");
    };

     private void reset(){
        this.displayOptions();
        this.getInput();
    }
     
    @Override
    public void getInput(){
        int num = 999;
        Scanner in = new Scanner(System.in);
        
        while(num != 1 && num != 2 && num != 3 && num != 4 && num != 5 && num != 6 && num != 7 && num != 8 && num != 9 && num != 10){
            System.out.println("\nPlease an option listed above.");
            num = in.nextInt();
        }

        switch (num) {
            case 1:
                System.out.println("Most Recent Command: ");
                if(rCmd == null){
                    System.out.println("No recent command.");
                }
                else{
                 rCmd.print();
                }
                break;
            case 2:
                if(rCmd == null){
                    System.out.println("No recent command.");
                }
                else{
                    System.out.println("Undoing most recent command... ");
                    try {
                        afterState = objects; //store the latest state
                        prevState = caretaker.loadState(); //get previous state
                        } catch (IOException ex) {
                            System.out.println("Unable to restore previous state");
                        } catch (ClassNotFoundException ex) {
                            System.out.println("Unable to restore previous state");
                    }
                    objects = prevState; //restore the data to the previous state
                    System.out.println("Command undone.");
                    uCmd = rCmd;
                    rCmd = null;
                        
                }        
                break;
            case 3:
                if(uCmd == null){
                    System.out.println("No recently undone command.");
                }
                else{
                    System.out.println("Redoing most recently undone command... ");

                    objects = afterState; //restore the latest state (that was undone)
                    
                    rCmd = uCmd;
                    uCmd = null;
                    System.out.println("Command redone.");
                }                
                break;
            case 4:
                System.out.println("Objects currently created are: \n");
                int i = 1;
                
                if(objects.isEmpty()){
                    System.out.println("There are currently no objects.");
                    break;
                }
                
                for(ObjInterface obj : objects){
                    System.out.println("\nObject "+i++ +": ");
                    System.out.println("\t");
                    obj.print();   
                }
                break;
            case 5:
                System.out.println("Running Command: Push."); 
                caretaker.saveState(objects);
                Command push = new Push(objects, gravity, friction);
                rCmd = push;
                push.execute();

                break;
            case 6:
                System.out.println("Running Command: Lift.");  
                caretaker.saveState(objects);
                Command lift = new Lift(objects, gravity, friction);
                rCmd = lift;
                lift.execute();
                break;
            case 7:
                System.out.println("Running Command: Drop.");
                caretaker.saveState(objects);
                Command drop = new Drop(objects, gravity, friction);                
                rCmd = drop;
                drop.execute();
                break;
            case 8:
                Menu sm = new SaveMenu(objects, gravity, friction, caretaker);
                sm.start();
                break;
            case 9:
                System.out.println("Exiting to Settings Menu.");
                rCmd = null;
                uCmd = null;
                prevState = null;
                afterState = null;              
                resetFlag = false;
                break;
            case 10:
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
            reset();
        }
    }
    
}

