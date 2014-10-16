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
public class FPSCounter {
    int runs = 0, rps = 0;    
    long firstRunTime;
    final int sampleRate;
    
    public FPSCounter(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    
    public void tick() {
        
        if (firstRunTime==0)
            firstRunTime = System.currentTimeMillis();
        
        if (++runs==sampleRate) {
                long deltaTime = (System.currentTimeMillis() - firstRunTime);
                rps = (int) ((sampleRate * 1000 ) / deltaTime);
                
                //System.out.printf("deltaTime %d\n", deltaTime);
                //System.out.printf("DOF %d\n", (sampleRate / deltaTime));
                
                // reset counters
                runs = 0;
                firstRunTime = System.currentTimeMillis();
            }
    }
    
    public int getFPS() {
        return rps;
    }
}
