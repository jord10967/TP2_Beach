/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

/**
 *
 * @author 1721244
 */
public class Projectile extends Entite implements Bougeable, Collisionnable {

    private int deltaX = 2;
    private float deltaY;
    Jeu jeu;

    public Projectile(float x, float y, float deltaY) {
        super(x, y, 16, 16, "images/boulet.png");
        this.deltaY = deltaY;
    }

    @Override
    public void bouger() {
        x = x + deltaX;

        y = y + deltaY;

    }

}
