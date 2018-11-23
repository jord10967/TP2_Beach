/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1721244
 */
public class EnnemiVolant1 extends Ennemi  {

    private int animation1;
    private float deltaY = 0.5f;
    private double angle = 0;

    private ArrayList<Image> listeAnimation = new ArrayList();

    public EnnemiVolant1(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 2, 3);

        listeAnimation.add(spriteSheet.getSubImage(2, 2));
        listeAnimation.add(spriteSheet.getSubImage(3, 2));

    }

    @Override
    public void bouger() {

        x = x - 0.25f;

        if (x >= LARGEUR + 32) {
            x = x - 0.25f;
        } else {
            if (animation1 == 0) {
                this.image = listeAnimation.get(0);

            } else if (animation1 == 50) {
                this.image = listeAnimation.get(1);

            } else if (animation1 == 100) {

                animation1 = -1;
            }

            x = x + Float.parseFloat("" + Math.cos(angle));
            y=y+deltaY;
            if (y + 96 >= HAUTEUR || y <= 0) {
                deltaY = -deltaY;
            }

            angle = angle + 0.005;
            animation1++;
        }
    }
}
