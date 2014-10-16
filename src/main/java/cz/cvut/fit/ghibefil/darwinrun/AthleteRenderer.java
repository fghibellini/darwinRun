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
        
        Vec2 torsoVec = athlete.getPoints().torso;
        Vec2 hipsVec = athlete.getPoints().hips;
        Vec2 torsoPerpVec = torsoVec.sub(hipsVec);
        torsoPerpVec.set(torsoPerpVec.y, -torsoPerpVec.x);
        torsoPerpVec.normalize();
        Point torsoPerp1 = convertCoordinates(torsoVec.add(torsoPerpVec.mul(.125f)), bufferSize);
        Point torsoPerp2 = convertCoordinates(torsoVec.add(torsoPerpVec.mul(-.125f)), bufferSize);
        Point torsoPerp3 = convertCoordinates(hipsVec.add(torsoPerpVec.mul(.125f)), bufferSize);
        Point torsoPerp4 = convertCoordinates(hipsVec.add(torsoPerpVec.mul(-.125f)), bufferSize);
        
        Point torso = convertCoordinates(athlete.getPoints().torso, bufferSize);
        Point hips = convertCoordinates(athlete.getPoints().hips, bufferSize);
        Point leftKnee = convertCoordinates(athlete.getPoints().leftKnee, bufferSize);
        Point leftAncle = convertCoordinates(athlete.getPoints().leftAncle, bufferSize);
        
        g.setColor(Color.red);
        g.drawString(String.format("POS: [%.2f, %.2f]", athlete.getPoints().torso.x, athlete.getPoints().torso.y), 10, 15);
        g.drawString(String.format("ANG: %.2f°",(athlete.getAngle() / Math.PI * 180.0)%360), 10, 30);
        
        g.drawRect(0, 0, bufferSize.width - 1, bufferSize.height - 1);
        
        //System.out.printf("POINT: [%d, %d]\n SIM: [%d, %d]\n\n", x, y,  (int)pos.x,  (int)pos.y);
        //g.fillRect(x,y, 10, 10);
        
        // torso
        g.setColor(Color.green);
        g.drawLine(torso.x,torso.y, hips.x, hips.y);
        g.setColor(Color.yellow);
        g.drawLine(torsoPerp1.x,torsoPerp1.y, torsoPerp2.x, torsoPerp2.y);
        g.drawLine(torsoPerp3.x,torsoPerp3.y, torsoPerp4.x, torsoPerp4.y);
        g.drawLine(torsoPerp2.x,torsoPerp2.y, torsoPerp4.x, torsoPerp4.y);
        g.drawLine(torsoPerp1.x,torsoPerp1.y, torsoPerp3.x, torsoPerp3.y);
        // leftThigh
        g.setColor(Color.red);
        g.drawLine(hips.x, hips.y, leftKnee.x, leftKnee.y);
        g.setColor(Color.yellow);
        g.drawLine(leftAncle.x, leftAncle.y, leftKnee.x, leftKnee.y);
    }
    
    private Point convertCoordinates(Vec2 v, Dimension bufferSize) {
        int x = (int) (v.x * bufferSize.width / MainWindow.SIMULATION_WIDTH);
        int y = (int) ( (MainWindow.SIMULATION_HEIGHT - v.y) * bufferSize.height / MainWindow.SIMULATION_HEIGHT);
        return new Point(x, y);
    }
}
