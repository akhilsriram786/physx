/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physx;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * 
 */
public class Cylinder implements ObjInterface{
    private String type = "Cylinder";
    private String name;
    private int radius; //radius
    private int height; //height
    private double mass; //mass
    private int x; //coordinates
    private int y; 
    private int z;
    private int right;
    private int left;
    private int top;
    private int bottom;
    private int front;
    private int back;
    
    public Cylinder(String namei, int radiusi, int heighti, float massi, int xi, int yi, int zi){
        name = namei;
        radius = radiusi;
        height = heighti;
        mass = massi;
        x = xi;
        y = yi;
        z = zi;
        right = x + height;
        left = x - height;
        front = y + radius;
        back = y - radius;
        top = z + radius;
        bottom = z - radius;
    }
    
    
    public Cylinder(ObjInterface o){
        if(o.getType().equals("Cylinder")){
            name = o.getName();
            height = o.getHeight();
            radius = o.getRadius();
            mass = o.getMass();
            x = o.getX();
            y = o.getY();
            z = o.getZ();
            right = x + height;
            left = x - height;
            front = y + height;
            back = y - height;
            top = z + height;
            bottom = z - height;

        }
    }
    
    @Override
    public void print(){
        System.out.println("\t\tType: Cylinder");
        System.out.println("\t\tName: "+name);
        System.out.println("\t\tRadius: "+radius);
        System.out.println("\t\tHeight: "+height);
        System.out.println("\t\tMass: "+mass);
        System.out.println("\t\tPosition: "+x+", "+y+", "+z);
    }
    
    
    @Override
    public void move(int xi, int yi, int zi){
        x += xi;
        y += yi;
        z += zi;
    }
    
    @Override
    public String getName(){
        return name;
    }
    
    @Override
    public double getMass(){
        return mass;
    }
    
    @Override
    public int calcMovement(double force, double gravity, double friction){
        double fricF =  (friction/35) * (gravity * this.getMass()); //divide by 25 to get the rolling coefficient from the sliding coefficient (wider surface area)
        double accel = (force - fricF) / this.getMass(); //find the net force and divide by mass to get acceleration
        double initV = accel * 0.5; //multiply the acceloration from the push with the duration of the push to get the starting velocity
        int distance = abs((int)((0 - (initV * initV)) / (2 * accel))); //get the distance moved based on the velocity and acceloration
        return distance; //return the distance moved
    }
    
    @Override
    public String getPos(){
        String pos = ("Cylinder: "+ this.name + " is at position: "+x+", "+y+", "+z+ ".");
        return pos;
    }
    
    @Override
    public int calcLift(double force, double gravity){
        int distance;
        double accel = force / this.getMass();
        if(accel <= gravity){
            distance = 0;
        }
        else{
            double initV = (accel - gravity) * 0.5;
            distance = abs((int) ((0 - (initV * initV)) / (2 * (accel - gravity))));
        }
        return distance;
    }

    @Override
    public int calcDrop(double gravity){
        int distance;
        double fallForce = this.z * gravity;
        distance = this.z; 
        return distance;
    }
    
    @Override
    public String getType(){
        return type;        
    }
    @Override    
    public int getRadius(){
        return radius;
    }

    @Override    
    public int getHeight(){
        return height;
    }
    
    @Override    
    public int getX(){
        return x;
    }
    
    @Override    
    public int getY(){
        return y;
    }
    
    @Override    
    public int getZ(){
        return z;
    }

        @Override
    public int getFront(){
        return front;
    }

    @Override    
    public int getBack(){
        return back;
    }

    @Override
    public int getLeft(){
        return left;
    }

    @Override
    public int getRight(){
        return right;    
    }

    @Override
    public int getTop(){
        return top;
    }

    @Override
    public int getBottom(){
        return bottom;
    }
    
        @Override
    public ArrayList<ObjInterface> checkCollisions(ArrayList<ObjInterface> objs, int distance, double force, String direction){
        ArrayList<ObjInterface> collides = new ArrayList();
        ArrayList<ObjInterface> twoFlags = new ArrayList();
        boolean xFlag = false;
        boolean yFlag = false;
        boolean zFlag = false;
        
        for(ObjInterface obj: objs){            
            //if the left-most point of our shape is between the left and right most point of the other object
            if(this.getLeft() <= obj.getRight() && this.getLeft() >= obj.getLeft() ){                
                xFlag = true; //they overlap on the x-axis
            }
            //if the right-most point of our shape is between the left and right most point of the other object
            else if(this.getRight() <= obj.getRight() && this.getRight() >= obj.getLeft() ){
                xFlag = true; //they overlap on the x-axis
            }
            //if the back-most point of our shape is between the front and back most point of the other object
            if(this.getBack() <= obj.getFront() && this.getBack() >= obj.getBack()){
                yFlag= true; //they overlap on the y-axis
            }
            //if the front-most point of our shape is between the front and back most point of the other object
            if(this.getFront() <= obj.getFront() && this.getFront() >= obj.getBack()){
                yFlag= true; //they overlap on the y-axis
            }
         
            //if the bottom-most point of our shape is between the top and bottom most point of the other object
            if(this.getBottom() <= obj.getTop() && this.getBottom() >= obj.getBottom() ){
                zFlag = true; //they overlap on the z-axis
            }   
            //if the top-most point of our shape is between the top and bottom most point of the other object
            else if(this.getTop() <= obj.getTop() && this.getTop() >= obj.getBottom() ){
                zFlag = true; //they overlap on the z-axis
            }
            
            //track any object that overlaps on two axes and the names don't match our current moving object
            if( ( (xFlag && yFlag) || (xFlag && zFlag) || (yFlag && zFlag) ) && (!obj.getName().equals(this.getName())) ){
                twoFlags.add(obj);
            }
        }
        
        //if the objects are not aligned on two axes, then they can't collide, so for each object that is aligned
        for(ObjInterface obj: twoFlags){
            
            //if we're pushing this object forward (y+) and its aligned on x and z, and its front is behind the other objects back
            if(    direction.equals("f") && (xFlag && zFlag) && (this.getFront() < obj.getBack())   ){
                if( (this.getFront() + distance) >= obj.getBack()){
                    collides.add(obj);
                }
            }
            if(    direction.equals("b") && (xFlag && zFlag) && (this.getBack() > obj.getFront())    ){
                if(  (this.getBack() - distance) <= obj.getFront()){
                    collides.add(obj);
                }
            }
            if(    direction.equals("l") && (yFlag && zFlag) && (this.getLeft() > obj.getRight())    ){
                if( (this.getLeft() - distance)  <= obj.getRight()){
                    collides.add(obj);
                }
            }
            if(    direction.equals("r") && (yFlag && zFlag) && (this.getRight() < obj.getLeft())    ){
                if( (this.getRight() + distance) >= obj.getLeft()){
                    collides.add(obj);
                }
            }
            if(    direction.equals("u") && (xFlag && yFlag) && (this.getTop() < obj.getBottom())    ){
                if( (this.getTop() + distance) >= obj.getBottom()){
                    collides.add(obj);
                }
            }
            if(    direction.equals("d") && (xFlag && yFlag) && (this.getBottom() > obj.getTop())    ){
                if( (this.getBottom() - distance) <= obj.getTop()){
                    collides.add(obj);
                }
            }
        }
        return collides;
    }
    

}
