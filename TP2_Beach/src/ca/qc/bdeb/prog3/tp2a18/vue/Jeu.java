/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import ca.qc.bdeb.prog3.tp2a18.controleur.Controleur;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import ca.qc.bdeb.prog3.tp2a18.modele.Modele;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.input.KeyCode;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Le jeu Slick2D
 *
 * @author
 */
public class Jeu extends BasicGame implements Observer {

    private Controleur controleur;
    private Modele modele;
    private ArrayList<Bougeable> listeBougeable = new ArrayList<>(); // Tous ce qui bouge
    private ArrayList<Entite> listeEntite = new ArrayList<>(); // Toutes les entités
    private ArrayList<KeyCode> listeKeys = new ArrayList<>(); // Les touches enfoncées
    private Input input; // L’entrée (souris/touches de clavier, etc.)
    private Ciel ciel;
    private SpriteSheet spriteMonde, spritePrincesse;
    private Plancher plancher1, plancher2;
    private Beach beach;

    /**
     * Contructeur de Jeu
     *
     * @param gamename Le nom du jeu
     * @param controleur Le controleur du jeu
     * @param modele Le modèle du jeu
     */
    public Jeu(String gamename, Controleur controleur, Modele modele) {
        super(gamename);
        this.modele = modele;
        this.controleur = controleur;
        modele.addObserver(this);
        // …
    }

    /**
     * Initialiser le jeu
     *
     * @param container le container du jeu
     * @throws SlickException si le jeu plante
     */
    public void init(GameContainer container) throws SlickException {
        input = container.getInput();
        spriteMonde = new SpriteSheet("images/sprites_monde.png", 32, 32);
        spritePrincesse = new SpriteSheet("images/sprites_princess.png", 32, 64);

        for (int i = 0; i <= HAUTEUR - 64; i = i + 32) {
            for (int j = 0; j <= LARGEUR; j = j + 32) {
                ciel = new Ciel(j, i, spriteMonde);
                listeEntite.add(ciel);
            }

        }
        for (int i = 0; i < LARGEUR; i = i + 32) {

            plancher1 = new Plancher(i, HAUTEUR - 64, spriteMonde, 3, 1);
            plancher2 = new Plancher(i, HAUTEUR - 32, spriteMonde, 4, 1);

            listeEntite.add(plancher1);
            listeEntite.add(plancher2);

        }
        beach = new Beach(10, HAUTEUR - 128, spritePrincesse);
        listeEntite.add(beach);

    }

    /**
     * Update du jeu
     *
     * @param container le container du jeu
     * @param delta N/A
     * @throws SlickException Si le update plante
     */
    public void update(GameContainer container, int delta) throws SlickException {
        gererCollisions();
        getKeys();
        traiterKeys();
    }

    /**
     * Dessiner le jeu
     *
     * @param container le container du jeu
     * @param g le graphics du container
     * @throws SlickException Si le render plante
     */
    public void render(GameContainer container, Graphics g) throws SlickException {
        for (Entite entite : listeEntite) {
            g.drawImage(entite.getImage(), entite.getX(), entite.getY());
        }
    }

    /**
     * Update du patron observateur (MVC)
     *
     * @param o N/A
     * @param arg N/A
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    private void getKeys() {
        if (input.isKeyDown(Input.KEY_A)) {
            if (!listeKeys.contains(KeyCode.A)) {
                listeKeys.add(KeyCode.A);
            }
        } else {
            listeKeys.remove(KeyCode.A);
        }

        if (input.isKeyDown(Input.KEY_D)) {
            if (!listeKeys.contains(KeyCode.D)) {
                listeKeys.add(KeyCode.D);
            }
        } else {
            listeKeys.remove(KeyCode.D);
        }

        if (input.isKeyDown(Input.KEY_W)) {
            if (!listeKeys.contains(KeyCode.W)) {
                listeKeys.add(KeyCode.W);
            }
        } else {
            listeKeys.remove(KeyCode.W);
        }

        if (input.isKeyDown(Input.KEY_S)) {
            if (!listeKeys.contains(KeyCode.S)) {
                listeKeys.add(KeyCode.S);
            }
        } else {
            listeKeys.remove(KeyCode.S);
        }
    }

    private void traiterKeys() {
        beach.bouger(listeKeys);
    }

    private void gererCollisions() {
    }

}
