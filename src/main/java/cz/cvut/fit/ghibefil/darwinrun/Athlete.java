/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 *
 * @author ghibe
 */
public class Athlete {
    
    static final float HALF_TORSO_LENGTH = .3f;
    static final float HALF_THIGH_LENGTH = .3f;
    
    static final FixtureDef TORSO_FICTURE_DEF, LEG_FICTURE_DEF;
    static {
        PolygonShape TORSO_SHAPE = new PolygonShape();
        TORSO_SHAPE.setAsBox(.125f, HALF_TORSO_LENGTH);
        
        TORSO_FICTURE_DEF = new FixtureDef();
        TORSO_FICTURE_DEF.shape = TORSO_SHAPE;
        TORSO_FICTURE_DEF.density = 1f;
        TORSO_FICTURE_DEF.friction = 0.3f;
        TORSO_FICTURE_DEF.restitution = 1f;
        TORSO_FICTURE_DEF.filter.categoryBits = 0x0002;
        TORSO_FICTURE_DEF.filter.maskBits = 0x0003;
        
        PolygonShape LEG_SHAPE = new PolygonShape();
        LEG_SHAPE.setAsBox(.110f, HALF_THIGH_LENGTH);
        
        LEG_FICTURE_DEF = new FixtureDef();
        LEG_FICTURE_DEF.shape = LEG_SHAPE;
        LEG_FICTURE_DEF.density = 1f;
        LEG_FICTURE_DEF.friction = 0.3f;
        LEG_FICTURE_DEF.restitution = 0.9f;
        LEG_FICTURE_DEF.filter.categoryBits = 0x0002;
        LEG_FICTURE_DEF.filter.maskBits = 0x0003;
    }
    
    World world;
    Vec2 torsoCenter;
    Body torso, left_leg;
    
    public Athlete(World world, Vec2 torsoCenter) {
        this.world = world;
        this.torsoCenter = torsoCenter;
        
        torso = createTorso();
        left_leg = createLeg();
        
        RevoluteJointDef leftLegJoint = new RevoluteJointDef();
        leftLegJoint.initialize(torso, left_leg, new Vec2(torsoCenter.x, torsoCenter.y - HALF_TORSO_LENGTH));
        leftLegJoint.lowerAngle = (float) -Math.PI/3f;
        leftLegJoint.upperAngle = (float) Math.PI/3f;
        leftLegJoint.enableLimit = true;
        world.createJoint(leftLegJoint);
    }
    
    public AthletePoints getPoints() {
        float torsoAngle = (float) (torso.getAngle() + Math.PI / 2.);        
        Vec2 torsoUpVector = new Vec2((float) cos(torsoAngle),(float) sin(torsoAngle)).mul(HALF_TORSO_LENGTH);
        
        Vec2 pTorso = torso.getPosition().add(torsoUpVector),
             pHip = torso.getPosition().add(torsoUpVector.mul(-1f));
        
        Vec2 pLeftKnee = radialFrom(left_leg.getPosition(), pHip, HALF_THIGH_LENGTH);
        Vec2 pLeftAncle = pHip;
        
        return new AthletePoints(pTorso, pHip, null, null, null, null, pLeftKnee, null, pLeftAncle, null);        
    }
    
    private static Vec2 radialFrom(Vec2 v, Vec2 from, float distance) {
        Vec2 direction = v.sub(from);
        direction.normalize();
        return v.add(direction.mulLocal(distance));
    }
    
    private Body createTorso() {
        // Dynamic body - box        
        BodyDef dynBodyDef = new BodyDef();
        dynBodyDef.type = BodyType.DYNAMIC;
        dynBodyDef.position.set(torsoCenter.x, torsoCenter.y);
        dynBodyDef.allowSleep = false;
        //dynBodyDef.linearDamping = 0.1f;
        //dynBodyDef.fixedRotation = true;
        //dynBodyDef.bullet = true; // might need to turn on in case high speeds are reached to prevent tunneling
        Body body = world.createBody(dynBodyDef);
        
        body.createFixture(TORSO_FICTURE_DEF);
        return body;
    }
    
    private Body createLeg() {
        // Dynamic body - box        
        BodyDef dynBodyDef = new BodyDef();
        dynBodyDef.type = BodyType.DYNAMIC;
        dynBodyDef.position.set(torsoCenter.x, torsoCenter.y - HALF_TORSO_LENGTH - HALF_THIGH_LENGTH);
        dynBodyDef.allowSleep = false;
        //dynBodyDef.linearDamping = 0.1f;
        //dynBodyDef.fixedRotation = true;
        //dynBodyDef.bullet = true; // might need to turn on in case high speeds are reached to prevent tunneling
        Body body = world.createBody(dynBodyDef);
        
        body.createFixture(LEG_FICTURE_DEF);
        return body;
    }
    
    public float getAngle() {
        return torso.getAngle();
    }
       
    public void reset() {
        torso.setTransform(torsoCenter/*.add(new Vec2(-1f, 1f))*/, 0);
        //torso.setTransform(new Vec2(5f, 10f), torso.getAngle());
    }
}
