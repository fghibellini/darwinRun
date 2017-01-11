/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun.puppeteer;

import cz.cvut.fit.ghibefil.darwinrun.physics.AthletePoints;

/**
 * Takes a genotype and based on it controls the body
 * @author ghibe
 */
public abstract class Puppeteer {
    
    private final Genotype genotype;

    public Puppeteer(Genotype genotype) {
        this.genotype = genotype;
    }
    
    public abstract MovementSet move(AthletePoints points);
    
}
