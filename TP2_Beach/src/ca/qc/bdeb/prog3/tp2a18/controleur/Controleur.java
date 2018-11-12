/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.controleur;

import ca.qc.bdeb.prog3.tp2a18.modele.Modele;
import ca.qc.bdeb.prog3.tp2a18.vue.Jeu;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author MGrenon
 */
public class Controleur {
    private Modele modele = new Modele();

    public Controleur(){

        try {
            AppGameContainer app;
            app = new AppGameContainer(new Jeu("Jeu", this, modele));
            app.setDisplayMode(1200, 900, false);
            app.setShowFPS(false);
            app.setVSync(false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}

