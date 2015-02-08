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
 * Component used to draw an athlete on a Graphics.
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
        Point torso = convertCoordinates(athletePoints.torso, athletePoints.torso, bufferSize);
        Point hips = convertCoordinates(athletePoints.hips, athletePoints.torso, bufferSize);
        
        Point leftKnee = convertCoordinates(athletePoints.leftKnee, athletePoints.torso, bufferSize);
        Point leftAncle = convertCoordinates(athletePoints.leftAncle, athletePoints.torso, bufferSize);
        Point leftToe = convertCoordinates(athletePoints.leftToe, athletePoints.torso, bufferSize);
        
        Point rightKnee = convertCoordinates(athletePoints.rightKnee, athletePoints.torso, bufferSize);
        Point rightAncle = convertCoordinates(athletePoints.rightAncle, athletePoints.torso, bufferSize);
        Point rightToe = convertCoordinates(athletePoints.rightToe, athletePoints.torso, bufferSize);
        
        
        g.setColor(Color.red);
        g.drawString(String.format("POS: [%.2f, %.2f]", athletePoints.torso.x, athletePoints.torso.y), 10, 15);
        g.drawString(String.format("ANG: %.2f°",(athlete.getTorsoAngle() / Math.PI * 180.0)%360), 10, 30);
        
        g.drawRect(0, 0, bufferSize.width - 1, bufferSize.height - 1);
        
        //System.out.printf("POINT: [%d, %d]\n SIM: [%d, %d]\n\n", x, y,  (int)pos.x,  (int)pos.y);
        //g.fillRect(x,y, 10, 10);
        
        // torso
        g.setColor(Color.green);
        g.drawLine(torso.x,torso.y, hips.x, hips.y);
        
        
        // leftLeg
        g.setColor(Color.red);
        g.drawLine(hips.x, hips.y, leftKnee.x, leftKnee.y);
        g.drawLine(leftKnee.x, leftKnee.y, leftAncle.x, leftAncle.y);
        g.drawLine(leftAncle.x, leftAncle.y, leftToe.x, leftToe.y);
        
        // rightLeg
        g.setColor(Color.red);
        g.drawLine(hips.x, hips.y, rightKnee.x, rightKnee.y);
        g.drawLine(rightKnee.x, rightKnee.y, rightAncle.x, rightAncle.y);
        g.drawLine(rightAncle.x, rightAncle.y, rightToe.x, rightToe.y);
        
        
        // DEBUG RECTANGLES
        if (true) {
            drawThickLine(g, bufferSize, athletePoints.torso, athletePoints.torso, athletePoints.hips, .125f);
            
            drawThickLine(g, bufferSize, athletePoints.torso, athletePoints.hips, athletePoints.leftKnee, .110f);
            drawThickLine(g, bufferSize, athletePoints.torso, athletePoints.leftAncle, athletePoints.leftKnee, .110f);
            drawThickLine(g, bufferSize, athletePoints.torso, athletePoints.leftAncle, athletePoints.leftToe, .110f);
            
            drawThickLine(g, bufferSize, athletePoints.torso, athletePoints.hips, athletePoints.rightKnee, .110f);
            drawThickLine(g, bufferSize, athletePoints.torso, athletePoints.rightAncle, athletePoints.rightKnee, .110f);
            drawThickLine(g, bufferSize, athletePoints.torso, athletePoints.rightAncle, athletePoints.rightToe, .110f);
        }
       
    }
  
    private void drawThickLine(Graphics g, Dimension bufferSize, Vec2 center, Vec2 from, Vec2 to, float thickness) {
        // compute the perpendicular vector
        Vec2 torsoPerpVec = from.sub(to);
        torsoPerpVec.set(torsoPerpVec.y, -torsoPerpVec.x);
        torsoPerpVec.normalize();
        
        Vec2 rightPerp = torsoPerpVec.clone().mul(thickness);
        Vec2 leftPerp = torsoPerpVec.clone().mul(-thickness);
        
        Point A = convertCoordinates(from.add(rightPerp), center, bufferSize);
        Point B = convertCoordinates(from.add(leftPerp), center, bufferSize);
        Point C = convertCoordinates(to.add(rightPerp), center, bufferSize);
        Point D = convertCoordinates(to.add(leftPerp), center, bufferSize);
                
        g.setColor(Color.yellow);
        g.drawLine(A.x,A.y, B.x, B.y);
        g.drawLine(C.x,C.y, D.x, D.y);
        g.drawLine(B.x,B.y, D.x, D.y);
        g.drawLine(A.x,A.y, C.x, C.y);
    }
    
    private Point convertCoordinates(Vec2 v, Vec2 center, Dimension bufferSize) {
        //v = v.sub(center);
        v = v.clone().addLocal(-center.x, 0);
        int x = (int) (v.x * bufferSize.width / MainWindow.SIMULATION_WIDTH);
        int y = (int) ( (MainWindow.SIMULATION_HEIGHT - v.y) * bufferSize.height / MainWindow.SIMULATION_HEIGHT);
        return new Point(x + bufferSize.width/2, y);
    }
}
