/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1721244
 */
public class Plancher extends Entite implements Bougeable {

    private float deltaX = 0.5f;

    public Plancher() {
        super(0, 0, 0, 0, null);
    }

    public Plancher(float x, float y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, ligne, colonne);
    }

    @Override
    public void bouger() {
        x = x - deltaX;
        if (x == -32) {
            x = LARGEUR + 16;
        }
    }

}
