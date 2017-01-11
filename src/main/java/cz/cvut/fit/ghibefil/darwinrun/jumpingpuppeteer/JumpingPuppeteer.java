/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun.jumpingpuppeteer;

import cz.cvut.fit.ghibefil.darwinrun.physics.AthletePoints;
import cz.cvut.fit.ghibefil.darwinrun.puppeteer.Genotype;
import cz.cvut.fit.ghibefil.darwinrun.puppeteer.MovementSet;
import cz.cvut.fit.ghibefil.darwinrun.puppeteer.Puppeteer;

/**
 * Example implementation of a puppeteer.
 * It randomly jumps with a probability stored in the genotype.
 * 
 * @author ghibe
 */
public class JumpingPuppeteer extends Puppeteer {
    private final JumpingGenotype genotype;
    
    public JumpingPuppeteer(Genotype genotype) {
        super(genotype);
        this.genotype = (JumpingGenotype) genotype;
    }

    @Override
    public MovementSet move(AthletePoints points) {
        MovementSet m = new MovementSet(Math.random() < genotype.probability);
        return m;
    }
    
}
