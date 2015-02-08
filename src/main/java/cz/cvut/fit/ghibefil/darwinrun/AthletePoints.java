/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

import org.jbox2d.common.Vec2;

/**
 * 
 * @author ghibe
 */
public class AthletePoints {
    public final Vec2 torso;
    public final Vec2 hips;
    public final Vec2 leftElbow; Vec2 rightElbov;
    public final Vec2 leftHand; Vec2 rightHand;
    public final Vec2 leftKnee; Vec2 rightKnee;
    public final Vec2 leftAncle; Vec2 rightAncle;
    public final Vec2 leftToe; Vec2 rightToe;

    public AthletePoints(Vec2 torso, Vec2 hips, Vec2 leftElbow, Vec2 rightElbov, Vec2 leftHand, Vec2 rightHand, Vec2 leftKnee, Vec2 rightKnee, Vec2 leftAncle, Vec2 rightAncle, Vec2 leftToe, Vec2 rightToe) {
        this.torso = torso;
        this.hips = hips;
        this.leftElbow = leftElbow;
        this.rightElbov = rightElbov;
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.leftKnee = leftKnee;
        this.rightKnee = rightKnee;
        this.leftAncle = leftAncle;
        this.rightAncle = rightAncle;
        this.leftToe = leftToe;
        this.rightToe = rightToe;
    }

}
