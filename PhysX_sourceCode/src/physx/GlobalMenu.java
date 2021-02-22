/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * 
 */
public class GlobalMenu implements Menu{
    
    private double friction = 0.7;
    private double gravity = 9.8;
    private int count = 1;
    private ArrayList<ObjInterface> objects = new ArrayList();
    private CareTaker caretaker;
    private boolean resetFlag = true;
    
    private void reset(){
        this.displayOptions();
        this.getInput();
    }
    
    GlobalMenu(ArrayList<ObjInterface> objs, CareTaker ct){
        objects = objs;
        friction = 0.7;
        gravity = 9.8;
        count = 1;
        caretaker = ct;
    }
    GlobalMenu(CareTaker ct){
        caretaker = ct;
    }
    
    @Override
    public void displayOptions(){ 
        System.out.println("\nSettings Menu:");
        System.out.println("\t 1) Set Friction. (Default: 0.7)");
        System.out.println("\t 2) Set Gravity. (Default : 9.8)");
        System.out.println("\t 3) Set Object Count. (MAX: 10, MIN: 1)");
        System.out.println("\t 4) Create Objects.");
        System.out.println("\t 5) List Objects.");
        System.out.println("\t 6) Erase Objects.");
        System.out.println("\t 7) Begin Simulation.");
        System.out.println("\t 8) Back to Main Menu.");
        System.out.println("\t 9) Exit.");    
    };

    @Override
    public void getInput(){
        int num = 999;
        Scanner in = new Scanner(System.in);
        
        while(num != 1 && num != 2 && num != 3 && num != 4 && num != 5 && num != 6 && num != 7 && num != 8 && num != 9){
            System.out.println("\nPlease choose an option listed above.");
            num = in.nextInt();
        }

        switch (num) {
            case 1:
                System.out.println("Input Friction Value: ");
                friction = in.nextFloat();
                System.out.println("Friction set.\n");
                break;
            case 2:
                System.out.println("Input Gravity Value: ");
                gravity = in.nextFloat();   
                if(gravity < 0){
                    gravity = 0.0;
                    System.out.println("Gravity cannot be negative. Setting Gravity to 0...");
                }
                System.out.println("Gravity set.\n");
                break;
            case 3:
                System.out.println("Input Object Count: ");
                int tCount = in.nextInt();
                if(tCount > 10 || tCount < 1){
                    System.out.println("Please input an number between 1 and 10.");
                }
                else{
                    count = tCount;  
                   System.out.println("Count set.\n");
                }
                break;
            case 4:
                System.out.println("Erasing Current Objects...");
                if(!objects.isEmpty())
                    objects.clear();
                
                    
                System.out.println("Beginning Object Creation...");
                ObjFactory factory = ObjFactory.getFactory();
                ObjInterface object = null;
                int sNum = 999;
                for(int i = 0; i<count; ++i){
                        sNum = 999;
                        System.out.println("\nWhat shape should object "+ (i+1) +" be?");
                        System.out.println("\t 1) Sphere");
                        System.out.println("\t 2) Cylinder");
                        System.out.println("\t 3) Cube");

                        System.out.println("\tPlease choose an option listed above.");
                        sNum = in.nextInt();
                    
                        switch (sNum) {
                                case 1:
                                    System.out.println("Creating Sphere...");
                                    object = factory.makeObject("Sphere");
                                    if(object != null){
                                        objects.add(object);
                                    }
                                    else{
                                        System.out.println("Failed to create sphere.");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Creating Cylinder...");
                                    object = factory.makeObject("Cylinder");
                                    if(object != null){
                                        objects.add(object);
                                    }
                                    else{
                                        System.out.println("Failed to create cylinder.");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Creating Cube...");
                                    object = factory.makeObject("Cube");
                                    if(object != null){
                                        objects.add(object);
                                    }
                                    else{
                                        System.out.println("Failed to create cube.");
                                    }
                                    break;
                                default:
                                    break;                    
                        }
                        
                        if(! (sNum == 1 || sNum == 2 || sNum == 3)){
                            System.out.println("Please choose a valid option.");
                            --i;
                        }
                        if(object == null){
                            System.out.println("An error has occured. Please restart simulation.");
                            System.exit(1);
                        }
                }
                System.out.println("Objects created.\n");
                break;
            case 5:
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
            case 6:
                System.out.println("Erasing Current Objects...");
                if(!objects.isEmpty()){
                    objects.clear();
                    System.out.println("Objects erased.\n");

                }
                else{
                    System.out.println("\nNo objects have been created.");
                }
               
                break;
            case 7:
                System.out.println("Beginning Simulation...");
                Menu am = new ActionMenu(objects, gravity, friction, caretaker);
                am.start();
                break;
            case 8:
                System.out.println("Exiting to Main Menu.");
                objects.clear();
                resetFlag = false;
                break;
            case 9:
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


    public void start(){
        while(resetFlag){
            reset();
        }
    }    
}
