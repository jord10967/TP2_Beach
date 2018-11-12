/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1721244
 */
public class Beach extends Entite {

    private int deltaX = 1;
    private int deltaY = 1;
    private ArrayList<Image> listeAnimation = new ArrayList();
    private int animation = 0;

    public Beach(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 0, 3);
        listeAnimation.add(spriteSheet.getSubImage(0, 0));
        listeAnimation.add(spriteSheet.getSubImage(1, 0));
        listeAnimation.add(spriteSheet.getSubImage(2, 0));
        listeAnimation.add(spriteSheet.getSubImage(3, 0));
        listeAnimation.add(spriteSheet.getSubImage(4, 0));
        listeAnimation.add(spriteSheet.getSubImage(5, 0));

    }

    public void bouger(ArrayList<KeyCode> listeKeys) {

        if (listeKeys.contains(KeyCode.A)) {
            if (x - deltaX >= 0) {
                x = x - deltaX;
            }
        }
        if (listeKeys.contains(KeyCode.D)) {
            if (x + deltaX <= LARGEUR - width) {
                x = x + deltaX;
            }
        }
        if (listeKeys.contains(KeyCode.W)) {
            if (y - deltaY >= 0) {
                y = y - deltaY;
            }
        }
        if (listeKeys.contains(KeyCode.S)) {
            if (y + deltaY <= HAUTEUR - height - 64) {
                y = y + deltaY;
            }
        }
        if (y < HAUTEUR - 128) {
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
        } else {
            if (animation == 0) {
                this.image = listeAnimation.get(3);
            } else if (animation == 50) {
                this.image = listeAnimation.get(4);
            } else if (animation == 100) {
                this.image = listeAnimation.get(5);
            } else if (animation == 150) {
                animation = -1;
            }
            animation++;
        }
    }

}