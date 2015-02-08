/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 *
 * @author ghibe
 */
public class RenderPanel extends java.awt.Canvas implements Runnable {
    private final ArrayList<Drawable> drawables = new ArrayList<>();
    private final FPSCounter fpsCounter = new FPSCounter(40);
    private BufferStrategy strategy;     
    private Simulation simulation;
    
    /**
     * Creates new form RenderPanel
     */
    public RenderPanel() {
        setIgnoreRepaint(true);
        setBounds(0, 0, 400, 400);        
    }
    
    public void initStrategy() {
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        new Thread(this, "draw thread").start();
    }
    
    public void resetDrawables() {
        drawables.clear();
    }
    
    public void addDrawable(Drawable obj) {
        drawables.add(obj);
    }
    
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
    
     @Override
    public void run() {
        while (true) {            
            Graphics buffer = strategy.getDrawGraphics();
            
            buffer.setColor(Color.black);
            buffer.fillRect(0, 0, getWidth(), getHeight());
            
            buffer.setColor(Color.red);
            buffer.drawString("RPS: " + simulation.getRps(), 10, 45);
            buffer.drawString("FPS: " + fpsCounter.getFPS(), 10, 60);

            for (Drawable d : drawables)
                d.draw(buffer, this);

            buffer.dispose();
            strategy.show();
            Toolkit.getDefaultToolkit().sync(); // http://stackoverflow.com/a/26388175/3343425

            fpsCounter.tick();
            
            try { Thread.sleep(1000/60); } catch(Exception e) {}
        }
    }
}
