/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

/**
 *
 * @author ghibe
 */
public class Main {
    static MainWindow win;
    static Simulation sim;
        
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
        win = new MainWindow();
        sim = new Simulation();
        
               
        win.getRenderPanel().setRunner(sim.getRunner());
        
    }
    
    public static void startSimulation() {
        new Thread(sim, "simulation1").start();
    }
    
    public static MainWindow getMainWindow() {
        return win;
    }
}
