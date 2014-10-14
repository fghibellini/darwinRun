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
    Body athlete;
    
    public Simulation(JComponent renderer) {
        this.renderer = renderer;
        
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
        
        
        
        // Dynamic body - box        
        BodyDef dynBodyDef = new BodyDef();
        dynBodyDef.type = BodyType.DYNAMIC;
        dynBodyDef.position.set(5f, 10f);
        athlete = world.createBody(dynBodyDef);
        
        PolygonShape dynBodyShape = new PolygonShape();
        dynBodyShape.setAsBox(0.5f, 0.5f);
        
        FixtureDef dynBodyFixure = new FixtureDef();
        dynBodyFixure.shape = dynBodyShape;
        dynBodyFixure.density = 1f;
        dynBodyFixure.friction = 0.3f;        
        athlete.createFixture(dynBodyFixure);
    }
    
    public void run() {
        while (true) {
            world.step(timeStep, velocityIterations, positionIterations);
            Vec2 pos = athlete.getPosition();
            System.out.printf("[%f, %f]\n", pos.x, pos.y);
            renderer.repaint();
            try {
                Thread.sleep(1000/60);
            } catch(InterruptedException e) {
                // the trehad is already in a sleep state we can ignore this
            }
        }
    }
    
    public Body getRunner() {
        return athlete;
    }
              
                
}
