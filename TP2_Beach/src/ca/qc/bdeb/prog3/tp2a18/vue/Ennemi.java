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
public abstract class Ennemi extends Entite implements Bougeable {

    protected int deltaX = 1;

    public Ennemi(float x, float y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, ligne, colonne);
        this.deltaX = deltaX;
    }

    @Override
    public abstract void bouger();

}
