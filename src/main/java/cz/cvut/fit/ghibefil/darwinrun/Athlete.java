/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import java.awt.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.jbox2d.collision.broadphase.Pair;
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
    
    static final float HALF_TORSO_LENGTH = .3f,
                       HALF_THIGH_LENGTH = .3f,
                       HALF_CALF_LENGTH = 0.2f;
    
    private static class Leg {
        private final Body thigh;
        private final Body calf;

        public Leg(Body thigh, Body calf) {
            this.thigh = thigh;
            this.calf = calf;
        }

        public Body getThigh() {
            return thigh;
        }

        public Body getCalf() {
            return calf;
        }
    }
    
    World world;
    Vec2 torsoCenter;
    Body torso,
         left_thigh, left_calf,
         right_thigh, right_calf;
    
    public Athlete(World world, Vec2 torsoCenter) {
        this.world = world;
        this.torsoCenter = torsoCenter;
        
        Leg l;
        
        torso = createTorso();
        
        l = createLeg();
        left_thigh = l.getThigh();
        left_calf = l.getCalf();
        
        l = createLeg();
        right_thigh = l.getThigh();
        right_calf = l.getCalf();
        
    }
    
    public AthletePoints getPoints() {
        float torsoAngle = (float) (torso.getAngle() + Math.PI / 2.);        
        Vec2 torsoUpVector = new Vec2((float) cos(torsoAngle),(float) sin(torsoAngle)).mul(HALF_TORSO_LENGTH);
        
        Vec2 pTorso = torso.getPosition().add(torsoUpVector),
             pHip = torso.getPosition().add(torsoUpVector.mul(-1f));
        
        Vec2 pLeftKnee = radialFrom(left_thigh.getPosition(), pHip, HALF_THIGH_LENGTH);
        Vec2 pLeftAncle = radialFrom(left_calf.getPosition(), pLeftKnee, HALF_CALF_LENGTH);
        
        Vec2 pRightKnee = radialFrom(right_thigh.getPosition(), pHip, HALF_THIGH_LENGTH);
        Vec2 pRightAncle = radialFrom(right_calf.getPosition(), pRightKnee, HALF_CALF_LENGTH);
        
        return new AthletePoints(pTorso, pHip, null, null, null, null, pLeftKnee, pRightKnee, pLeftAncle, pRightAncle);        
    }
    
    private static Vec2 radialFrom(Vec2 v, Vec2 from, float distance) {
        Vec2 direction = v.sub(from);
        direction.normalize();
        return v.add(direction.mulLocal(distance));
    }
    
    private Body createTorso() {
        return createRectangleBody(torsoCenter, HALF_TORSO_LENGTH);
    }
    
    private Leg createLeg() {
        Vec2 hipPoint = new Vec2(torsoCenter.x, torsoCenter.y - HALF_TORSO_LENGTH);
        Vec2 thighCenter = hipPoint.clone().addLocal(0, -HALF_THIGH_LENGTH);
        Vec2 kneePoint = thighCenter.clone().addLocal(0f, -HALF_CALF_LENGTH);
        Vec2 calfCenter = kneePoint.clone().addLocal(0f, -HALF_CALF_LENGTH);
        
        Body thigh = createRectangleBody(thighCenter, HALF_THIGH_LENGTH);
        Body calf = createRectangleBody(calfCenter, HALF_CALF_LENGTH);
        
        createRevoluteJoint(torso, thigh, hipPoint, (float) -Math.PI/3f, (float) Math.PI/3f);
        createRevoluteJoint(thigh, calf, kneePoint, (float) -Math.PI/3f, (float) Math.PI/3f);
        
        return new Leg(thigh, calf);
    }
    
    private void createRevoluteJoint(Body a, Body b, Vec2 centerOfRevolution, float minAngle, float maxAngle) {
        RevoluteJointDef joint = new RevoluteJointDef();
        joint.initialize(a, b, centerOfRevolution);
        joint.lowerAngle = minAngle;
        joint.upperAngle = maxAngle;
        joint.enableLimit = true;
        world.createJoint(joint);
    }
    
    public float getAngle() {
        return torso.getAngle();
    }
       
    public void reset() {
        //torso.setTransform(torsoCenter.add(new Vec2(0, 2f)), 0f);
        
    }
    
    public void lift() {
        torso.applyForceToCenter(new Vec2(0f,500f));
    }
    
    private Body createRectangleBody(Vec2 center, float length) {
        // Dynamic body - box        
        BodyDef dynBodyDef = new BodyDef();
        dynBodyDef.type = BodyType.DYNAMIC;
        dynBodyDef.position.set(center);
        dynBodyDef.allowSleep = false;
        //dynBodyDef.linearDamping = 0.1f;
        //dynBodyDef.fixedRotation = true;
        //dynBodyDef.bullet = true; // might need to turn on in case high speeds are reached to prevent tunneling
        Body body = world.createBody(dynBodyDef);
        
        FixtureDef fixtureDef = createRectangleFixtureDef(length);
        
        body.createFixture(fixtureDef);
        return body;
    }
    
    private FixtureDef createRectangleFixtureDef(float height) {
        float width = .125f;
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.density = 1f;
        fixture.friction = 0.3f;
        fixture.restitution = .8f;
        fixture.filter.categoryBits = 0x0002;
        fixture.filter.maskBits = 0x0001;
        
        return fixture;
    }
}
