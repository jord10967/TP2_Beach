/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1721244
 */
public class Potion extends Entite implements Bonus, Bougeable {

    private float deltaX = 0.5f;

    public Potion(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 4, 3);
    }

    @Override
    public void bouger() {
        x = x - deltaX;
    }

}
