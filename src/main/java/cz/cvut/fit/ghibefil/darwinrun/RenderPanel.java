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
import org.jbox2d.dynamics.Body;

/**
 *
 * @author ghibe
 */
public class RenderPanel extends javax.swing.JPanel {
    private Body athlete; 
    Graphics offscreenGraphics;
    
    /**
     * Creates new form RenderPanel
     */
    public RenderPanel() {
        initComponents();
        
//        offscreenImage = createImage(400, 400);
//        offscreenGraphics = offscreenImage.getGraphics();
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics buffer = g;
        
        Rectangle rect = g.getClipBounds();
        buffer.clearRect(0, 0, rect.width, rect.height);
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
        
        //g.drawImage(offscreenImage,0,0, this); 
    }
    
    public void setRunner(Body athlete) {
        this.athlete = athlete;
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
