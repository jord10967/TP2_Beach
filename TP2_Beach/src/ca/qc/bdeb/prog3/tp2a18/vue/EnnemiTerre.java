/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1721244
 */
public class EnnemiTerre extends Ennemi {

    private int animation1;
    private Random r = new Random();
    private ArrayList<Image> listeAnimation = new ArrayList();

    public EnnemiTerre(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 3, 0);

        listeAnimation.add(spriteSheet.getSubImage(0, 3));
        listeAnimation.add(spriteSheet.getSubImage(1, 3));
        listeAnimation.add(spriteSheet.getSubImage(2, 3));
    }

    @Override
    public void bouger() {

        x = x - deltaX;

        if (animation1 == 0) {
            this.image = listeAnimation.get(1);

        } else if (animation1 == 400) {
            this.image = listeAnimation.get(2);

        } else if (animation1 == 800) {
            this.image = listeAnimation.get(0);

        } else if (animation1 == 1000) {

            animation1 = -1;
        }
        

        animation1++;

    }

    public int getAnimation() {

        return animation1;
    }

}
