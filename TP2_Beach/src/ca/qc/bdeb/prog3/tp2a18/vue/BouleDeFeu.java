/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1721244
 */
public class BouleDeFeu extends Entite implements Bougeable, Collisionnable {

    private int deltaY = 1;
    private int deltaX = 1;

    private ArrayList<Image> listeAnimation = new ArrayList();
    private int animation;

    public BouleDeFeu(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 5, 3);
        listeAnimation.add(spriteSheet.getSubImage(4, 5));
        listeAnimation.add(spriteSheet.getSubImage(5, 5));
        listeAnimation.add(spriteSheet.getSubImage(3, 5));
    }

    @Override
    public void bouger() {
        y = y - deltaY;
        x = x - deltaX;
        if (animation == 0) {
            this.image = listeAnimation.get(0);

        } else if (animation == 50) {
            this.image = listeAnimation.get(1);

        } else if (animation == 100) {
            this.image = listeAnimation.get(2);

        } else if (animation == 150) {

            animation = -1;
        }
        animation++;
    }

}
