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
public class EnnemiVolant1 extends Ennemi {

    private int animation1, animation2;
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

        if (animation1 == 0) {
            this.image = listeAnimation.get(0);

        } else if (animation1 == 50) {
            this.image = listeAnimation.get(1);

        } else if (animation1 == 100) {

            animation1 = -1;
        }
        
            x = x + Float.parseFloat("" + 1.8 * Math.cos(angle));
            y = y + Float.parseFloat("" + 1.8 * Math.sin(angle));
      

//        if (animation2 < 200) {
//            y = y - 1f;
//            x = x - 1f;
//        } else if (animation2 < 400) {
//            y = y + 1f;
//            x = x - 1f;
//        } else if (animation2 < 600) {
//            y = y + 1f;
//            x = x + 1f;
//        } else if (animation2 < 800) {
//            y = y - 1f;
//            x = x + 1f;
//        } else if (animation2 == 800) {
//            animation2 = -1;
//        }
        animation2++;
        angle = angle + 0.005;
        animation1++;
    }

}
