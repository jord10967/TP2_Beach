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
public class FeuilleArbre extends Entite implements Bougeable {

    public FeuilleArbre(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 1, 5);

    }

    @Override
    public void bouger() {
        x = x - 0.5f;
       
    }
}
