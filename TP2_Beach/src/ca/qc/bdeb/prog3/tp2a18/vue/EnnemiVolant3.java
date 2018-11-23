/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1721244
 */
public class EnnemiVolant3 extends Ennemi  {

    private int animation1;
    private double angle = 0;

    private ArrayList<Image> listeAnimation = new ArrayList();

    public EnnemiVolant3(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 3, 3);

        listeAnimation.add(spriteSheet.getSubImage(4,3));
        listeAnimation.add(spriteSheet.getSubImage(3, 3));

    }

    @Override
    public void bouger() {

        x = x - 0.25f;
        if (x >= LARGEUR + 32) {
            x = x - 0.25f;
        } else {
            if (animation1 == 0) {
                this.image = listeAnimation.get(0);

            } else if (animation1 == 500) {
                this.image = listeAnimation.get(1);

            } else if (animation1 == 1000) {

                animation1 = -1;
            }

            x = x + Float.parseFloat("" + 1.8 * Math.cos(angle));
            y = y + Float.parseFloat("" + 1.8 * Math.sin(angle));

            angle = angle + 0.005;
            animation1++;
        }
    }
}
