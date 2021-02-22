/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package physx;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * 
 */
public class Drop implements Command{
    private ArrayList<ObjInterface> objects = new ArrayList();
    private ArrayList<ObjInterface> collides = new ArrayList();
    private ArrayList<ObjInterface> collided = new ArrayList();
    private String cName;
    private double friction;
    private double gravity;
    private int distance;
    private Scanner in = new Scanner(System.in);
    private ObjInterface cObject = null;
    private ObjInterface rObject = null;
    
    Drop(ArrayList<ObjInterface> objs, double grav, double fric){
        objects = objs;
        gravity = grav;
        friction = fric;
    }
    
    @Override
    public ObjInterface execute(){
        displayOptions();
        getInput();
        printResult();
        return rObject;
    }
    
    
    public void displayOptions(){
        
        while(rObject == null){
            rObject = checkName();
        }
    }
    
    public void getInput(){
        collided.add(cObject);
        distance = cObject.calcDrop(gravity);
        collides = cObject.checkCollisions(objects, distance, gravity, "d");

        if(!collides.isEmpty()){ //if this object hit something, it stops
            cObject.move(0, 0, -1 * (abs(cObject.getZ() - collides.get(0).getZ())));
            }
        else{ //else it moves the full distance
            cObject.move(0, 0, -1 * distance);
        } 

        while(!collides.isEmpty()){ //while there are still collisions
            ObjInterface obj = collides.get(0); //get the first object that collides
            collides.clear(); //remove the rest of the objects (they may never get hit depending on the amount of force)
            distance = obj.calcDrop(gravity); //calculate how far this new object moves based on remaining force
            collides = obj.checkCollisions(objects, distance, gravity, "u"); //find if this collides with any other objects

            if(!collides.isEmpty()){ //if this object hit something, it stops
                obj.move(0, 0, -1 * (abs(obj.getZ() - collides.get(0).getZ())));
                    }
            else{ //else it moves the full distance
                obj.move(0, 0, -1 * distance);
            } 
            collided.add(obj); //keep the record of it
            cObject = obj;
        }           
        
    }
    
    public void printResult(){
        for(int i = 0; i<collided.size(); ++i){
            System.out.println( (i+1) + ": " +collided.get(i).getName() + " was dropped.\n\t" + collided.get(i).getPos() );
        }
    }
    
       public ObjInterface checkName(){
        System.out.println("Which object would you like to drop?");
        cName = in.nextLine();
        for(ObjInterface obj : objects){
            if(obj.getName().equals(cName)){
                cObject = obj;
                if(obj.getType().equals("Sphere")){
                    ObjInterface nObject = new Sphere(obj);
                    rObject = nObject;
                }
                else if(obj.getType().equals("Cube")){
                    ObjInterface nObject = new Cube(obj);
                    rObject = nObject;
                }
                else{
                    ObjInterface nObject = new Cylinder(obj);
                    rObject = nObject;
                }
                System.out.println("Object found.");
                return rObject;
            }
        }
        System.out.println("Object not found.");
        return null;
    }

    @Override
    public void print(){
        if(cObject == null){
            System.out.println("No object was selected.");
            return;
        }
        System.out.println("\tObject \'"+ cObject.getName() + "\' was dropped with " + (cObject.getMass() * 9.8) +" N of force (gravity).");
    }

        
}
