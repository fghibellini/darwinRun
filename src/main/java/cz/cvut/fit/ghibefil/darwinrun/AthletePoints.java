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
    public Vec2 torso;
    public Vec2 hips;
    public Vec2 leftElbow; Vec2 rightElbov;
    public Vec2 leftHand; Vec2 rightHand;
    public Vec2 leftKnee; Vec2 rightKnee;
    public Vec2 leftAncle; Vec2 rightAncle;

    public AthletePoints(Vec2 torso, Vec2 hips, Vec2 leftElbow, Vec2 rightElbov, Vec2 leftHand, Vec2 rightHand, Vec2 leftKnee, Vec2 rightKnee, Vec2 leftAncle, Vec2 rightAncle) {
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
    }

}
