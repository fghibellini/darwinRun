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
        
        AthletePoints athletePoints = athlete.getPoints();
        Point torso = convertCoordinates(athletePoints.torso, bufferSize);
        Point hips = convertCoordinates(athletePoints.hips, bufferSize);
        Point leftKnee = convertCoordinates(athletePoints.leftKnee, bufferSize);
        Point leftAncle = convertCoordinates(athletePoints.leftAncle, bufferSize);
        
        g.setColor(Color.red);
        g.drawString(String.format("POS: [%.2f, %.2f]", athletePoints.torso.x, athletePoints.torso.y), 10, 15);
        g.drawString(String.format("ANG: %.2f°",(athlete.getAngle() / Math.PI * 180.0)%360), 10, 30);
        
        g.drawRect(0, 0, bufferSize.width - 1, bufferSize.height - 1);
        
        //System.out.printf("POINT: [%d, %d]\n SIM: [%d, %d]\n\n", x, y,  (int)pos.x,  (int)pos.y);
        //g.fillRect(x,y, 10, 10);
        
        // torso
        g.setColor(Color.green);
        g.drawLine(torso.x,torso.y, hips.x, hips.y);
        
        
        // leftThigh
        g.setColor(Color.red);
        g.drawLine(hips.x, hips.y, leftKnee.x, leftKnee.y);
        
        // DEBUG RECTANGLES
        if (true) {
            drawThickLine(g, bufferSize, athletePoints.torso, athletePoints.hips, .125f);
            drawThickLine(g, bufferSize, athletePoints.hips, athletePoints.leftKnee, .110f);
            drawThickLine(g, bufferSize, athletePoints.leftAncle, athletePoints.leftKnee, .110f);
        }
    }
    
    private void drawThickLine(Graphics g, Dimension bufferSize, Vec2 from, Vec2 to, float thickness) {
        // compute the perpendicular vector
        Vec2 torsoPerpVec = from.sub(to);
        torsoPerpVec.set(torsoPerpVec.y, -torsoPerpVec.x);
        torsoPerpVec.normalize();
        
        Vec2 rightPerp = torsoPerpVec.clone().mul(thickness);
        Vec2 leftPerp = torsoPerpVec.clone().mul(-thickness);
        
        Point A = convertCoordinates(from.add(rightPerp), bufferSize);
        Point B = convertCoordinates(from.add(leftPerp), bufferSize);
        Point C = convertCoordinates(to.add(rightPerp), bufferSize);
        Point D = convertCoordinates(to.add(leftPerp), bufferSize);
                
        g.setColor(Color.yellow);
        g.drawLine(A.x,A.y, B.x, B.y);
        g.drawLine(C.x,C.y, D.x, D.y);
        g.drawLine(B.x,B.y, D.x, D.y);
        g.drawLine(A.x,A.y, C.x, C.y);
    }
    
    private Point convertCoordinates(Vec2 v, Dimension bufferSize) {
        int x = (int) (v.x * bufferSize.width / MainWindow.SIMULATION_WIDTH);
        int y = (int) ( (MainWindow.SIMULATION_HEIGHT - v.y) * bufferSize.height / MainWindow.SIMULATION_HEIGHT);
        return new Point(x, y);
    }
}
