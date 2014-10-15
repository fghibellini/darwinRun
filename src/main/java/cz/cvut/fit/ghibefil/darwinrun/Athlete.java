/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

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
public class Athlete {
    static final PolygonShape LIMB_SHAPE;
    static final FixtureDef LIMB_FICTURE_DEF;
    static {
        LIMB_SHAPE = new PolygonShape();
        LIMB_SHAPE.setAsBox(0.5f, 1f);
        
        LIMB_FICTURE_DEF = new FixtureDef();
        LIMB_FICTURE_DEF.shape = LIMB_SHAPE;
        LIMB_FICTURE_DEF.density = 1f;
        LIMB_FICTURE_DEF.friction = 0.3f;
        LIMB_FICTURE_DEF.restitution = 1f;
        LIMB_FICTURE_DEF.filter.categoryBits = 0x0002;
        LIMB_FICTURE_DEF.filter.maskBits = 0x0001;
    }
    
    World world;
    Body torso;
    
    public Athlete(World world) {
        this.world = world;
        torso = createLimb();
    }
    
    public AthletePoints getPoints() {
        Vec2 t = torso.getPosition();
        return new AthletePoints(t, null, null, null, null, null, null, null, null);        
    }
    
    private Body createLimb() {
        // Dynamic body - box        
        BodyDef dynBodyDef = new BodyDef();
        dynBodyDef.type = BodyType.DYNAMIC;
        dynBodyDef.position.set(5f, 5f);
        dynBodyDef.allowSleep = false;
        dynBodyDef.linearDamping = 0.1f;
        Body body = world.createBody(dynBodyDef);
        
        body.createFixture(LIMB_FICTURE_DEF);
        return body;
    }
       
    public void reset() {
        torso.setTransform(new Vec2(5f, 10f), torso.getAngle());
    }
}
