/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
    
    public void draw(Graphics g) {
        Rectangle bufferSize = new Rectangle(400, 400); //g.getClipBounds();
                
        Vec2 pos = athlete.getPoints().center;
        
        g.setColor(Color.red);
        g.drawString("POS: [" + pos.x + ", " + pos.y + "]", 10, 15);
        
        g.drawRect(0, 0, bufferSize.width - 1, bufferSize.height - 1);
        
        int x = (int) (pos.x * bufferSize.width / MainWindow.SIMULATION_WIDTH);
        int y = (int) ( (MainWindow.SIMULATION_HEIGHT - pos.y) * bufferSize.height / MainWindow.SIMULATION_HEIGHT);
        System.out.printf("POINT: [%d, %d]\n SIM: [%d, %d]\n\n", x, y,  (int)pos.x,  (int)pos.y);
        g.fillRect(x,y, 10, 10);
    }
}