/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import javax.swing.JComponent;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author ghibe
 */
public class Simulation implements Runnable {
    float timeStep = 1/60f;
    int velocityIterations = 6;
    int positionIterations = 2;
        
    JComponent renderer;
    World world;
    Athlete athlete;
    
    final FPSCounter rpsCounter = new FPSCounter(10);
    
    public Simulation() {
        //this.renderer = renderer;
        
        // gravity & world
        Vec2 g0 = new Vec2(0f, -10f);
        world = new World(g0);
        
        // Static body - ground
        BodyDef groundBodyDef = new BodyDef();        
        groundBodyDef.position.set(0f, -5);   
        Body groundBody = world.createBody(groundBodyDef);     
        
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50f,5f); 
        groundBody.createFixture(groundBox, 0f);
                
        athlete = new Athlete(world);
    }
    
    public void run() {
                
        while (true) {
            world.step(timeStep, velocityIterations, positionIterations);
            Vec2 pos = athlete.getPoints().center;
            //System.out.printf("[%f, %f]\n", pos.x, pos.y);
            
            rpsCounter.tick();
            
            try { Thread.sleep(1000/60); } catch(InterruptedException e) {}
        }
    }
    
    //sampleRate/timeSpent = rps/1000
    
    public Athlete getRunner() {
        return athlete;
    }
    
    public int getRps() {
        return rpsCounter.getFPS();
    }              
                
}
