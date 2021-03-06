/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun.physics;

import cz.cvut.fit.ghibefil.darwinrun.puppeteer.MovementSet;
import cz.cvut.fit.ghibefil.darwinrun.puppeteer.Puppeteer;
import cz.cvut.fit.ghibefil.darwinrun.utils.FPSCounter;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author ghibe
 */
public class Simulation implements Runnable {
    float timeStep = 1/60f;
    int velocityIterations = 6;
    int positionIterations = 2;
        
    World world;
    Athlete athlete;
    Puppeteer puppeteer;
    
    final FPSCounter rpsCounter = new FPSCounter(10);
    
    public Simulation() {
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
                
        athlete = new Athlete(world, new Vec2(2.5f, 2f));
    }
    
    @Override
    public void run() {
                
        while (true) {
            
            if (puppeteer!=null) {
                MovementSet movements = puppeteer.move(athlete.getPoints());
                processMovements(movements);
            }
            
            world.step(timeStep, velocityIterations, positionIterations);
                        
            rpsCounter.tick();
            
            try { Thread.sleep(1000/60); } catch(InterruptedException e) {}
        }
        
    }
    
    public Athlete getRunner() {
        return athlete;
    }
    
    public int getRps() {
        return rpsCounter.getFPS();
    }              

    public void setPuppeteer(Puppeteer puppeteer) {
        this.puppeteer = puppeteer;
    }

    private void processMovements(MovementSet movements) {
        if (movements.jump)
            athlete.lift();
    }
                
}
