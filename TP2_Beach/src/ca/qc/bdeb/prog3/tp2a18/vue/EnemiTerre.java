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
public class EnemiTerre extends Enemi {

    private Random r = new Random();
    private ArrayList<Image> listeAnimation = new ArrayList();

    public EnemiTerre(float x, float y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, ligne, colonne);
        int ran = r.nextInt(5);
        while (ran < 3) {
            ran = r.nextInt(5);
        }
        listeAnimation.add(spriteSheet.getSubImage(ran, 0));
        listeAnimation.add(spriteSheet.getSubImage(ran, 1));
        listeAnimation.add(spriteSheet.getSubImage(ran, 2));
    }

}
