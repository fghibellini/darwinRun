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
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 *
 * @author ghibe
 */
public class RenderPanel extends java.awt.Canvas implements Runnable {
     private Body athlete;
     private BufferStrategy strategy;
    
    /**
     * Creates new form RenderPanel
     */
    public RenderPanel() {
        setIgnoreRepaint(true);
        setBounds(0, 0, 600, 500);        
    }
    
    public void initStrategy() {
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        System.out.println("creating canvas thread!");
        new Thread(this, "draw thread").start();
    }
    
    public void setRunner(Body athlete) {
        this.athlete = athlete;
    }
    
     @Override
    public void run() {
        while (true) {
            System.out.println("repaint of canvas");
            if (strategy==null)
                initStrategy();
            Graphics buffer = strategy.getDrawGraphics();

            Rectangle rect = getBounds();
            System.out.printf("rectangle: %d, %d", rect.width, rect.height);
            buffer.setColor(Color.black);
            buffer.fillRect(0, 0, rect.width, rect.height);
            if (athlete==null)
                return;

            buffer.setColor(Color.red);

            Vec2 pos = athlete.getPosition(), speed = athlete.getLinearVelocity();
            buffer.drawString("POS: [" + pos.x + ", " + pos.y + "]", 100, 100);
            buffer.drawString("SPD: [" + speed.x + ", " + speed.y + "]", 100, 112);

            int x = (int) (pos.x * getWidth() / MainWindow.SIMULATION_WIDTH);
            int y = (int) ( (MainWindow.SIMULATION_HEIGHT - pos.y) * getHeight() / MainWindow.SIMULATION_HEIGHT);
            System.out.printf("POINT: [%d, %d]\n SIM: [%d, %d]\nWINSIZE:[%d, %d]\n", x, y,  (int)pos.x,  (int)pos.y, rect.width, rect.height);
            buffer.fillRect(x,y, 10, 10);

            buffer.dispose();
            strategy.show();
            
            try { Thread.sleep(1000/20); } catch(Exception e) {}
        }
    }
}
