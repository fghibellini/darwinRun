/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import org.jbox2d.common.Vec2;

/**
 *
 * @author ghibe
 */
public class AthleteRenderer implements Drawable {
    private final Athlete athlete;
    
    public AthleteRenderer(Athlete athlete) {
        this.athlete = athlete;
    }
    
    @Override
    public void draw(Graphics g, Component c) {
        Dimension bufferSize = new Dimension(c.getWidth(), c.getHeight()); //g.getClipBounds();
                
        Point torso = convertCoordinates(athlete.getPoints().torso, bufferSize);
        Point hips = convertCoordinates(athlete.getPoints().hips, bufferSize);
        
        g.setColor(Color.red);
        g.drawString(String.format("POS: [%.2f, %.2f]", athlete.getPoints().torso.x, athlete.getPoints().torso.y), 10, 15);
        g.drawString(String.format("ANG: %.2f°",(athlete.getAngle() / Math.PI * 180.0)%360), 10, 30);
        
        g.drawRect(0, 0, bufferSize.width - 1, bufferSize.height - 1);
        
        //System.out.printf("POINT: [%d, %d]\n SIM: [%d, %d]\n\n", x, y,  (int)pos.x,  (int)pos.y);
        //g.fillRect(x,y, 10, 10);
        g.drawLine(torso.x,torso.y, hips.x, hips.y);
    }
    
    private Point convertCoordinates(Vec2 v, Dimension bufferSize) {
        int x = (int) (v.x * bufferSize.width / MainWindow.SIMULATION_WIDTH);
        int y = (int) ( (MainWindow.SIMULATION_HEIGHT - v.y) * bufferSize.height / MainWindow.SIMULATION_HEIGHT);
        return new Point(x, y);
    }
}
