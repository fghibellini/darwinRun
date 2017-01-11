/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.ghibefil.darwinrun.drawing;

import java.awt.Component;
import java.awt.Graphics;

/**
 *
 * @author ghibe
 */
public interface Drawable {    
    // passing even the component based on
    // http://www.dreamincode.net/forums/topic/179427-calling-getwidth-or-getheight-on-a-graphics-object/
    public void draw(Graphics g, Component c);
}
