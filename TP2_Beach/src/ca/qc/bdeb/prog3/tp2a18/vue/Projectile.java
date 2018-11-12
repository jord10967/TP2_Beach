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
public class Projectile extends Entite implements Bougeable{
private int deltaX=2;
    public Projectile(float x, float y) {
        super(x, y, 16, 16, "images/boulet.png");
    }

    @Override
    public void bouger() {
        x=x+deltaX;
    }
    
}
