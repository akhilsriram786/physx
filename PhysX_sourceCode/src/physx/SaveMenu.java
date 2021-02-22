/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class SaveMenu implements Menu{
    
    private ArrayList<ObjInterface> objects;
    private double gravity;
    private double friction;
    Scanner in = new Scanner(System.in);
    CareTaker caretaker;
    boolean resetFlag = true;
    SaveMenu(ArrayList<ObjInterface> objs, double grav, double fric, CareTaker ct){
        objects = objs;
        gravity = grav;
        friction = fric;
        caretaker = ct;
    }
    
    @Override
    public void displayOptions(){
        System.out.println("\nSave Menu:");
        System.out.println("\t 1) Save Simulation.");
        System.out.println("\t 2) Back to Simulation.");
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
                System.out.println("Please input the name you would like for your save file: ");
                in.nextLine();
                String filename = in.nextLine() + ".physX";
                
                Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
                String s = path.toAbsolutePath().toString();
                String directory = s.substring(0, s.length() - 1) + filename;
                System.out.println("Saving to: "+directory+ ".");
                File file = new File(directory);
                
                try{
                   caretaker.serializeDataOut(objects, file);
                   } catch (IOException ex)
                     { 
                         System.out.println(" Error: Unable to create save file.\n");
                     }
                System.out.println("Save Complete.\n");
                break;
            case 2:
                System.out.println("Returning to simulation...\n");
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

