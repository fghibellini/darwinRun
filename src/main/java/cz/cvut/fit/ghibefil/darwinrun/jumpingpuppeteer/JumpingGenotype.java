/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun.jumpingpuppeteer;

import cz.cvut.fit.ghibefil.darwinrun.puppeteer.Genotype;

/**
 *
 * @author ghibe
 */
public class JumpingGenotype implements Genotype {
    public final float probability;
    
    public JumpingGenotype(float prob) {
        this.probability = prob;
    }

    @Override
    public String toString() {
        return "JumpingGenotype{" + "probability=" + probability + '}';
    }
}
