/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CareTaker {
    
     public ArrayList<ObjInterface> serializeDataIn(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fin = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        ArrayList<ObjInterface> objects = (ArrayList<ObjInterface>) ois.readObject();
        ois.close(); 
        return objects;
    }
     private static CareTaker ct = new CareTaker();
     private CareTaker(){}
     
     public static CareTaker getCareTaker(){
         return ct;
     }
    
    public void serializeDataOut(ArrayList<ObjInterface> objects, File file)throws IOException{
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
            System.out.println("Error: Unable to save file.");
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
    
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(objects);
        oos.close();
    }   
    
    public void listFiles(){

        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
        String s = path.toAbsolutePath().toString();
        String directory = s.substring(0, s.length() - 1);
        String filename;
        int i = 1;
        
        System.out.println("Current PhysX Files: ");
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(("physX"))) {
                filename = file.getName();
                filename = filename.substring(0, filename.length() -6);
                if(!filename.equals("tmp")){
                    System.out.println(i++ + ": " + filename);
                
                }
            }
        }
    }
    
    public void saveState(ArrayList<ObjInterface> objects){
        String filename = "tmp.physX";
        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();        
        String s = path.toAbsolutePath().toString();
        String directory = s.substring(0, s.length() - 1) + filename;
        File file = new File(directory);
         try {
             this.serializeDataOut(objects, file);
         } catch (IOException ex) {
             System.out.println("Error: Unable to update current data.");
         }
        
    }
    
    public ArrayList<ObjInterface> loadState()throws FileNotFoundException, IOException, ClassNotFoundException{
        ArrayList<ObjInterface> objects; 
        objects = this.serializeDataIn("tmp.physX");
        return objects;
    }
    
    public void deleteFile(String filename){
        filename = filename+".physX";
        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();        
        String s = path.toAbsolutePath().toString();
        String directory = s.substring(0, s.length() - 1) + filename;
        File file = new File(directory);
        boolean deleted = file.delete();
        if(!filename.equals("tmp.physX")){
            if(deleted){
                System.out.println(filename + " successfully deleted.\n");
            }
            else{
                System.out.println("Unable to delete " +filename+ ".");
            }
        }
    }
    
    public void deleteState(){
        this.deleteFile("tmp");
        System.out.println("Cleaning...");
        
    }
}
