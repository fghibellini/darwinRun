/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 *
 * @author ghibe
 */
public class RenderPanel extends java.awt.Canvas implements Runnable {
     private BufferStrategy strategy;
     private ArrayList<Drawable> drawables = new ArrayList<>();
    
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
    
     @Override
    public void run() {
        while (true) {
            if (strategy==null)
                initStrategy();
            Graphics buffer = strategy.getDrawGraphics();
            
            buffer.setColor(Color.black);
            buffer.fillRect(0, 0, getWidth(), getHeight());

            for (Drawable d : drawables)
                d.draw(buffer, this);

            buffer.dispose();
            strategy.show();
            
            try { Thread.sleep(1000/20); } catch(Exception e) {}
        }
    }
}
