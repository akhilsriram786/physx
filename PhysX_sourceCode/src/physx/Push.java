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
public class Push implements Command{
    private ArrayList<ObjInterface> objects = new ArrayList();
    private ArrayList<ObjInterface> collides = new ArrayList();
    private ArrayList<ObjInterface> collided = new ArrayList();
    private String cName;
    private double force;
    private double friction;
    private double gravity;
    private int distance;
    private Scanner in = new Scanner(System.in);
    private ObjInterface cObject = null;
    private ObjInterface nObject = null;
    private ObjInterface rObject = null;
    private String direction;
    Push(ArrayList<ObjInterface> objs, double grav, double fric){
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
        
        System.out.println("How much force?");
        force = in.nextFloat();
        
        System.out.println("Which direction would you like to push?");
        System.out.println("\t 1) Forward (y+).");
        System.out.println("\t 2) Backward (y-).");
        System.out.println("\t 3) Right (x+).");
        System.out.println("\t 4) Left (x-)."); 
    }
    
    public void getInput(){
        int num = 999;
        
        
        while(num != 1 && num != 2 && num != 3 && num != 4){
            System.out.println("\nPlease an option listed above.");
            num = in.nextInt();
        }
        
        distance = cObject.calcMovement(force, gravity, friction);
        collided.add(cObject);
        cName = cObject.getName();
        
        switch (num) {
            case 1:
                direction = "forward";
                System.out.println("Pushing " + cObject.getName() + " forward... ");
                collides = cObject.checkCollisions(objects, distance, force, "f");
                
                if(!collides.isEmpty()){ //if this object hit something, it stops
                    cObject.move(0,  abs(cObject.getY() - collides.get(0).getY()), 0);
                }
                else{ //else it moves the full distance
                    cObject.move(0, distance, 0);
                } 
                
                while(!collides.isEmpty()){ //while there are still collisions
                    ObjInterface obj = collides.get(0); //get the first object that collides
                    collides.clear(); //remove the rest of the objects (they may never get hit depending on the amount of force)
                    force = ((force * abs(obj.getBack() - cObject.getFront()) )/distance); //find the amount of force left from the initial amount
                    distance = obj.calcMovement(force, gravity, friction); //calculate how far this new object moves based on remaining force
                    collides = obj.checkCollisions(objects, distance, force, "f"); //find if this collides with any other objects

                    if(!collides.isEmpty()){ //if this object hit something, it stops
                        obj.move(0,  abs(obj.getY() - collides.get(0).getY()), 0);
                    }
                    else{ //else it moves the full distance
                        obj.move(0, distance, 0);
                    } 

                   
                    collided.add(obj); //keep the record of it
                    cObject = obj;
                }                    
                break;
            case 2:
                direction = "backward";
                System.out.println("Pushing " + cObject.getName() + " backward... ");
                collides = cObject.checkCollisions(objects, distance, force, "b");

                if(!collides.isEmpty()){ //if this object hit something, it stops
                    cObject.move(0, (-1 * abs(cObject.getY() - collides.get(0).getY())), 0);
                }
                else{ //else it moves the full distance
                    cObject.move(0, -1 * distance, 0);
                } 

                while(!collides.isEmpty()){ //while there are still collisions
                    ObjInterface obj = collides.get(0); //get the first object that collides
                    collides.clear(); //remove the rest of the objects (they may never get hit depending on the amount of force)
                    force = ((force * abs(obj.getFront() - cObject.getBack()) )/distance); //find the amount of force left from the initial amount
                    distance = obj.calcMovement(force, gravity, friction); //calculate how far this new object moves based on remaining force
                    collides = obj.checkCollisions(objects, distance, force, "b"); //find if this collides with any other objects

                    if(!collides.isEmpty()){ //if this object hit something, it stops
                        obj.move(0, (-1 * abs(obj.getY() - collides.get(0).getY())), 0);
                    }
                    else{ //else it moves the full distance
                        obj.move(0, -1 * distance, 0);
                    } 
                    collided.add(obj); //keep the record of it
                    cObject = obj;
                }           
                break;
            case 3:
                direction = "right";
                System.out.println("Pushing " + cObject.getName() + " right... ");
                collides = cObject.checkCollisions(objects, distance, force, "r");
                
                if(!collides.isEmpty()){ //if this object hit something, it stops
                    cObject.move(  abs(cObject.getX() - collides.get(0).getX()), 0, 0);
                }
                else{ //else it moves the full distance
                    cObject.move(distance, 0, 0);
                }
                
                while(!collides.isEmpty()){ //while there are still collisions
                    ObjInterface obj = collides.get(0); //get the first object that collides
                    collides.clear(); //remove the rest of the objects (they may never get hit depending on the amount of force)
                    force = ((force * abs(obj.getLeft() - cObject.getRight()) )/distance); //find the amount of force left from the initial amount
                    distance = obj.calcMovement(force, gravity, friction); //calculate how far this new object moves based on remaining force
                    collides = obj.checkCollisions(objects, distance, force, "r"); //find if this collides with any other objects
                    
                    if(!collides.isEmpty()){ //if this object hit something, it stops
                        obj.move(  abs(obj.getX() - collides.get(0).getX()), 0, 0);
                    }
                    else{ //else it moves the full distance
                        obj.move(distance, 0, 0);
                    } 
                    
                    obj.move(distance, 0, 0); //move this object accordingly
                    
                    
                    collided.add(obj); //keep the record of it
                    cObject = obj;
                }                           
                break;
            case 4:
                direction = "left";
                System.out.println("Pushing " + cObject.getName() + " left... ");
                collides = cObject.checkCollisions(objects, distance, force, "l");
                if(!collides.isEmpty()){ //if this object hit something, it stops
                    cObject.move( (-1 *  abs(cObject.getX() - collides.get(0).getX())), 0, 0);
                }
                else{ //else it moves the full distance
                    cObject.move( -1 * distance, 0, 0);
                }
                
                
                while(!collides.isEmpty()){ //while there are still collisions
                    ObjInterface obj = collides.get(0); //get the first object that collides
                    collides.clear(); //remove the rest of the objects (they may never get hit depending on the amount of force)
                    force = ((force * abs(obj.getRight() - cObject.getLeft()) )/distance); //find the amount of force left from the initial amount
                    distance = obj.calcMovement(force, gravity, friction); //calculate how far this new object moves based on remaining force
                    collides = obj.checkCollisions(objects, distance, force, "l"); //find if this collides with any other objects
                    
                    if(!collides.isEmpty()){ //if this object hit something, it stops
                        obj.move(  (-1 * abs(obj.getX() - collides.get(0).getX())), 0, 0);
                    }
                    else{ //else it moves the full distance
                        obj.move(-1 * distance, 0, 0);
                    }
                    
                    collided.add(obj); //keep the record of it
                    cObject = obj;
                }                           
                break;
            default:
                break;
        }
    }
    
    public void printResult(){
        for(int i = 0; i<collided.size(); ++i){
            System.out.println( (i+1) + ": " +collided.get(i).getName() + " was moved "+direction +".\n\t" + collided.get(i).getPos() );
        }
    }
    
    public ObjInterface checkName(){
        System.out.println("Which object would you like to push?");
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
        System.out.println("\tObject \'"+ cName + "\' was pushed with " + force +" N of force.");
    }
        
}
