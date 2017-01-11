/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun.physics;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 * Class representing an athlete in the box2d environment.
 * Handles the creation of all the rigid bodies and the getters
 * of their position.
 * @author ghibe
 */
public class Athlete {
    
    static final float HALF_TORSO_LENGTH = .3f,
                       HALF_THIGH_LENGTH = 0.21f,
                       HALF_CALF_LENGTH = 0.23f,
                       HALF_FOOT_LENGTH = 0.15f;
    
    static final float HALF_CALF_WIDTH = 0.125f;
    
    /* Immutable data structure */
    private static class Leg {
        public final Joint hip;
        public final Joint knee;
        public final Joint ankle;
        public final Body foot;

        public Leg(Joint hip, Joint knee, Joint ankle, Body foot) {
            this.hip = hip;
            this.knee = knee;
            this.ankle = ankle;
            this.foot = foot;
        }
    }
    
    World world;
    Vec2 torsoCenter;
    Body torso;
    Leg leftLeg, rightLeg;
    
    public Athlete(World world, Vec2 torsoCenter) {
        this.world = world;
        this.torsoCenter = torsoCenter;
        
        torso = createTorso();
        
        leftLeg = createLeg();
        
        rightLeg = createLeg();   
    }
    
    public synchronized AthletePoints getPoints() {
        float torsoAngle = (float) (torso.getAngle() + Math.PI / 2.);        
        Vec2 torsoUpVector = new Vec2((float) cos(torsoAngle),(float) sin(torsoAngle)).mul(HALF_TORSO_LENGTH);
        
        Vec2 pTorso, pHip,
             pLeftKnee, pLeftAncle,
             pRightKnee, pRightAncle;
        
        pTorso = torso.getPosition().add(torsoUpVector);
        
        pHip = new Vec2();
        leftLeg.hip.getAnchorA(pHip);
        
        // LEFT LEG
        
        pLeftKnee = new Vec2();
        leftLeg.knee.getAnchorA(pLeftKnee);
        
        pLeftAncle = new Vec2();
        leftLeg.ankle.getAnchorA(pLeftAncle);
        
        Vec2 pLeftToe = new Vec2();
        leftLeg.ankle.getAnchorA(pLeftToe);
        pLeftToe = radialFrom(leftLeg.foot.getPosition(), pLeftAncle, HALF_FOOT_LENGTH);
        
        // RIGHT LEG
        
        pRightKnee = new Vec2();
        rightLeg.knee.getAnchorA(pRightKnee);
        
        pRightAncle = new Vec2();
        rightLeg.ankle.getAnchorA(pRightAncle);
        
        Vec2 pRightToe = new Vec2();
        rightLeg.ankle.getAnchorA(pRightToe);
        pRightToe = radialFrom(rightLeg.foot.getPosition(), pRightAncle, HALF_FOOT_LENGTH);
                
        return new AthletePoints(pTorso, pHip, null, null, null, null, pLeftKnee, pRightKnee, pLeftAncle, pRightAncle, pLeftToe, pRightToe);        
    }
    
    private static Vec2 radialFrom(Vec2 through, Vec2 from, float distance) {
        Vec2 direction = through.sub(from);
        direction.normalize();
        return through.add(direction.mulLocal(distance));
    }
    
    private Body createTorso() {
        return createRectangleBody(torsoCenter, HALF_TORSO_LENGTH, 0);
    }
    
    private Leg createLeg() {
        Vec2 hipPoint = new Vec2(torsoCenter.x, torsoCenter.y - HALF_TORSO_LENGTH);
        Vec2 thighCenter = hipPoint.clone().addLocal(0, -HALF_THIGH_LENGTH);
        Vec2 kneePoint = thighCenter.clone().addLocal(0f, -HALF_CALF_LENGTH);
        Vec2 calfCenter = kneePoint.clone().addLocal(0f, -HALF_CALF_LENGTH);
        Vec2 anclePoint = calfCenter.clone().addLocal(0f, -HALF_CALF_LENGTH);
        Vec2 footCenter = anclePoint.clone().addLocal(HALF_FOOT_LENGTH - HALF_CALF_WIDTH, 0);
        
        Body thigh = createRectangleBody(thighCenter, HALF_THIGH_LENGTH, 0);
        Body calf = createRectangleBody(calfCenter, HALF_CALF_LENGTH, 0);
        Body foot = createRectangleBody(footCenter, HALF_FOOT_LENGTH, (float) -Math.PI/2f);
        
        Joint hipJoint = createRevoluteJoint(torso, thigh, hipPoint, 0, (float) Math.PI/3f);
        Joint kneeJoint = createRevoluteJoint(thigh, calf, kneePoint, -(float) Math.PI/3f, 0);
        Joint ankleJoint = createRevoluteJoint(calf, foot, anclePoint, 0, 0);
        
        return new Leg(hipJoint, kneeJoint, ankleJoint, foot);
    }
    
    private Joint createRevoluteJoint(Body a, Body b, Vec2 centerOfRevolution, float minAngle, float maxAngle) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(a, b, centerOfRevolution);
        jointDef.lowerAngle = minAngle;
        jointDef.upperAngle = maxAngle;
        jointDef.enableLimit = true;
        return world.createJoint(jointDef);
    }
    
    public float getTorsoAngle() {
        return torso.getAngle();
    }
    
    /**
     * Applies a force to the torso (for temporary debugging purposes).
     */
    public void lift() {
        torso.applyForceToCenter(new Vec2(0f,100f));
    }
    
    private Body createRectangleBody(Vec2 center, float length, float angle) {
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
        
        body.setTransform(center, angle);
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
