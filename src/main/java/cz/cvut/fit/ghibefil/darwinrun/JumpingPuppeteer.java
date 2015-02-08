/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun;

/**
 *
 * @author ghibe
 */
public class JumpingPuppeteer extends Puppeteer {
    private final JumpingGenotype gen;
    
    public JumpingPuppeteer(Genotype genotype) {
        super(genotype);
        gen = (JumpingGenotype) genotype;
    }

    @Override
    public MovementSet move(AthletePoints points) {
        MovementSet m = new MovementSet();
        if (Math.random() < gen.probability)
            m.setJump(true);
        return m;
    }
    
}
