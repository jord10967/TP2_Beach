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
public class Beach extends Entite implements  Collisionnable{

    private int deltaX = 1;
    private int deltaY = 1;
    private ArrayList<Image> listeAnimation = new ArrayList();
    private int animation1 = 0, animation2 = 0;
    private int pointVie=3;
    private boolean possedeArme=true;

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

            if (animation2 < 400) {
                y = y - 0.05f;
            } else if (animation2 < 800) {
                y = y + 0.05f;
            } else if (animation2 == 800) {
                animation2 = -1;
            }

            if (animation1 == 0) {
                this.image = listeAnimation.get(0);

            } else if (animation1 == 50) {
                this.image = listeAnimation.get(1);

            } else if (animation1 == 100) {
                this.image = listeAnimation.get(2);

            } else if (animation1 == 150) {

                animation1 = -1;
            }
            animation1++;
            animation2++;
        } else {
            if (animation1 == 0) {
                this.image = listeAnimation.get(3);
            } else if (animation1 == 50) {
                this.image = listeAnimation.get(4);
            } else if (animation1 == 100) {
                this.image = listeAnimation.get(5);
            } else if (animation1 == 150) {
                animation1 = -1;
            }
            animation1++;
        }
    }

    @Override
    public float getX() {
        return super.getX(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getY() {
        return super.getY(); //To change body of generated methods, choose Tools | Templates.
    }

    public int getPointVie() {
        return pointVie;
    }

    public boolean isPossedeArme() {
        return possedeArme;
    }

    public void setPossedeArme(boolean possedeArme) {
        this.possedeArme = possedeArme;
    }

 

   
    
    

}
