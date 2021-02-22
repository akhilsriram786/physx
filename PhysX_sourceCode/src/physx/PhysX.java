/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physx;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class PhysX {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to PhysX.\nPress Enter to Continue:\n\n");
        try {
            System.in.read();
        } catch (IOException ex) {}
        System.out.println("Thank you.");
        Menu mm = new MainMenu();
        mm.start();
    }
}
