/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physx;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * 
 */
public class ObjFactory {
    private String name;
    private int radius; //radius
    private int height; //height
    private float mass; //mass
    private int x; //coordinates
    private int y; 
    private int z;
    private ArrayList<ObjInterface> pool = new ArrayList();
            
    
    private static ObjFactory factory = new ObjFactory();
    
    private ObjFactory(){}
    
    public static ObjFactory getFactory(){
        return factory;
    }
    public ObjInterface makeObject(String shape){
        if(pool.size() >= 10){
            System.out.println("Maximum number of objects has been reached. Unable to complete object creation.");
            return null;
        }
        
        Scanner in = new Scanner(System.in);    
        ObjInterface shp;
        name = "Shape";
        radius = 1;
        height = 1;
        mass = 1;
        x = 0;
        y = 0;
        z = 0;
        
        if(shape == "Sphere"){
            System.out.println("Please name this sphere: ");
            name = in.nextLine();
            System.out.println("Please input the radius of the sphere: (r)");
            radius = in.nextInt();
            System.out.println("Please input the mass of the sphere: (m.m) ");
            mass = in.nextFloat();
            System.out.println("Please input the position of the sphere: (x y z)");
            x = in.nextInt();
            y = in.nextInt();
            z = in.nextInt();
            
            shp = new Sphere(name, radius, mass, x, y, z);
            System.out.println("Sphere " + name + " has been created.");
        }
        else if(shape == "Cylinder"){
            System.out.println("Please name this cylinder: ");
            name = in.nextLine();
            System.out.println("Please input the radius of the cylinder: (r)");
            radius = in.nextInt();
            System.out.println("Please input the height of the cylinder: (h)");
            height = in.nextInt();
            System.out.println("Please input the mass of the cylinder: (m.m) ");
            mass = in.nextFloat();
            System.out.println("Please input the position of the cylinder: (x y z)");
            x = in.nextInt();
            y = in.nextInt();
            z = in.nextInt();

            shp = new Cylinder(name, radius, height, mass, x, y, z);
            System.out.println("Cylinder " + name + " has been created.");
        }
        else{
            System.out.println("Please name this cube: ");
            name = in.nextLine();
            System.out.println("Please input the height of the cube: (h)");
            height = in.nextInt();
            System.out.println("Please input the mass of the cube: (m.m) ");
            mass = in.nextFloat();
            System.out.println("Please input the position of the cube: (x y z)");
            x = in.nextInt();
            y = in.nextInt();
            z = in.nextInt();

            shp = new Cube(name, height, mass, x, y, z);
            System.out.println("Cube " + name + " has been created.");
        }
        pool.add(shp);
        return shp;
    };
}
