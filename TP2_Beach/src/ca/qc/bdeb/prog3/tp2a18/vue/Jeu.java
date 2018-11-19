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
import java.util.Random;
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
    private SpriteSheet spriteMonde, spritePrincesse, spriteDivers;
    private Plancher plancher1, plancher2;
    private Beach beach;
    private FeuilleArbre feuilleArbre;
    private TroncArbre troncArbre;
    private Random r;
    private Projectile balle;
    private String points1 = "0";
    private int points2;
    private Coeur coeur;
    private EnnemiTerre enemiTerre;
    private BouleDeFeu bouleDeFeu;
    private EnnemiVolant1 ennemiVolant1;
    private int intervalleVagues;
    private int spawn;

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
        intervalleVagues = 0;
        spawn = 0;
        points2 = Integer.parseInt(points1);
        input = container.getInput();
        r = new Random();
        spriteMonde = new SpriteSheet("images/sprites_monde.png", 32, 32);
        spritePrincesse = new SpriteSheet("images/sprites_princess.png", 32, 64);
        spriteDivers = new SpriteSheet("images/sprites_divers.png", 32, 32);

        background();

        beach = new Beach(10, HAUTEUR - 128, spritePrincesse);
        listeEntite.add(beach);
        for (int i = 1; i < 4; i++) {
            coeur = new Coeur(i * 32, i);
            listeEntite.add(coeur);
        }

    }

    /**
     * Update du jeu
     *
     * @param container le container du jeu
     * @param delta N/A
     * @throws SlickException Si le update plante
     */
    public void update(GameContainer container, int delta) throws SlickException {

        bouger();
        creerBouleDeFeu();
        gestionEnemi();
        gestionArbres();
        delete();
        gererCollisions();
        getKeys();
        traiterKeys();
        intervalleVagues++;
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
        g.drawString(points1, LARGEUR - 30, 10);
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
        if (input.isKeyDown(Input.KEY_SPACE)) {
            if (!listeKeys.contains(KeyCode.SPACE)) {
                listeKeys.add(KeyCode.SPACE);
            }
        } else {
            listeKeys.remove(KeyCode.SPACE);
        }
    }

    private void traiterKeys() {
        beach.bouger(listeKeys);

        long millis = System.currentTimeMillis() % 500;
        if (listeKeys.contains(KeyCode.SPACE) && millis > 0 && millis < 10) {
            tirerBalle();

        }
    }

    private void gererCollisions() {

    }

    private void tirerBalle() {
        balle = new Projectile(beach.getX() + 32, beach.getY() + 24);
        listeEntite.add(balle);
        listeBougeable.add(balle);

    }

    public void creerArbre(int intervale) {
        int position = r.nextInt(2 * LARGEUR);
        int nombreHauteur = r.nextInt(13);
        while (position <= intervale) {
            position = r.nextInt(2 * LARGEUR);

        }
        while (nombreHauteur < 2) {
            nombreHauteur = r.nextInt(13);
        }
        feuilleArbre = new FeuilleArbre(position, HAUTEUR - 64 - 32 * nombreHauteur, spriteMonde);
        for (float i = HAUTEUR - 32 - 32 * nombreHauteur; i < HAUTEUR - 64; i = i + 32) {
            troncArbre = new TroncArbre(position, i, spriteMonde);
            listeEntite.add(troncArbre);
            listeBougeable.add(troncArbre);
        }

        listeEntite.add(feuilleArbre);
        listeBougeable.add(feuilleArbre);

    }

    private void delete() {
        ArrayList<Entite> listeTemp = new ArrayList();

        for (int i = 0; i < listeBougeable.size(); i++) {

            if (listeBougeable.get(i).getRectangle().getX() < -32 && listeBougeable.get(i).getClass() != Plancher.class) {
                listeTemp.add((Entite) listeBougeable.get(i));
            }

        }
        listeBougeable.removeAll(listeTemp);
        listeEntite.removeAll(listeTemp);
        listeTemp.clear();
    }

    private void gestionArbres() {
        int arbreRan = r.nextInt(850);
        if (arbreRan == 123) {
            creerArbre(LARGEUR);
        }
    }

    private void background() {
        for (int i = 0; i <= HAUTEUR - 64; i = i + 32) {
            for (int j = 0; j <= LARGEUR; j = j + 32) {
                ciel = new Ciel(j, i, spriteMonde);
                listeEntite.add(ciel);
            }

        }
        for (int i = 0; i < 2; i++) {
            creerArbre(0);

        }
        for (int i = 0; i < LARGEUR + 32; i = i + 32) {

            plancher1 = new Plancher(i, HAUTEUR - 64, spriteMonde, 3, 1);
            plancher2 = new Plancher(i, HAUTEUR - 32, spriteMonde, 4, 1);

            listeEntite.add(plancher1);
            listeEntite.add(plancher2);
            listeBougeable.add(plancher1);
            listeBougeable.add(plancher2);
        }
    }

    private void gestionEnemi() {
        int typeVague = r.nextInt(1);
        int ennemiRan = r.nextInt(1000);
        if (ennemiRan == 123) {
            creerEnemiTerre(LARGEUR);
        }
        if (intervalleVagues % 4000 == 0) {

            vaguesEnnemi(typeVague);
        }

    }

    private void creerEnemiTerre(int intervale) {
        int position = r.nextInt(2 * LARGEUR);

        while (position <= intervale) {
            position = r.nextInt(2 * LARGEUR);

        }
        enemiTerre = new EnnemiTerre(position, HAUTEUR - 96, spriteDivers);

        listeEntite.add(enemiTerre);
        listeBougeable.add(enemiTerre);
    }

    private void bouger() {
        for (int i = 0; i < listeBougeable.size(); i++) {
            listeBougeable.get(i).bouger();
        }
    }

    private void creerBouleDeFeu() {
        for (int i = 0; i < listeBougeable.size(); i++) {
            if (listeBougeable.get(i) instanceof EnnemiTerre) {
                EnnemiTerre ennemi = (EnnemiTerre) listeBougeable.get(i);
                if (ennemi.getAnimation() == 800) {
                    bouleDeFeu = new BouleDeFeu(ennemi.getX(), ennemi.getY() - 32, spriteDivers);
                    listeBougeable.add(bouleDeFeu);
                    listeEntite.add(bouleDeFeu);
                }
            }
        }
    }

    private void vaguesEnnemi(int typeVague) {

        switch (typeVague) {
            case 0:
                ennemiVolant1 = new EnnemiVolant1(LARGEUR, HAUTEUR - 850, spriteDivers);
                listeBougeable.add(ennemiVolant1);
                listeEntite.add(ennemiVolant1);

                break;
            case 1:

                break;
            case 2:
                break;
        }

    }

}
