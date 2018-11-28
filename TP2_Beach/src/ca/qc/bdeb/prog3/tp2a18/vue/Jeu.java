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
import java.util.Collection;
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

    private SpriteSheet spriteMonde, spritePrincesse, spriteDivers;

    private Beach beach;

    private Random r;

    private String points1 = "0";
    private int points2;

    private int typeVague;
    private long millis;
    private long millis2;
    private long millis3;

    private boolean tempsTirer;

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
        tempsTirer = false;

        millis = System.currentTimeMillis();
        millis2 = System.currentTimeMillis();

        input = container.getInput();
        r = new Random();
        spriteMonde = new SpriteSheet("images/sprites_monde.png", 32, 32);
        spritePrincesse = new SpriteSheet("images/sprites_princess.png", 32, 64);
        spriteDivers = new SpriteSheet("images/sprites_divers.png", 32, 32);

        background();

        beach = new Beach(10, HAUTEUR - 128, spritePrincesse);
        listeEntite.add(beach);
        for (int i = 1; i < 4; i++) {
            Coeur coeur = new Coeur(i * 32, i);
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
        typeVague = r.nextInt(3);
        points1 = "" + points2;
        bouger();
        creerBouleDeFeu();
        gestionEnnemiTerre();

        if (System.currentTimeMillis() >= millis + 4000) {
            millis = System.currentTimeMillis();
            int cpt = 0;
            for (int i = 0; i < 4; i++) {

                vaguesEnnemi(typeVague, cpt);
                cpt++;
            }

        }

        gestionArbres();
        delete();
        gererCollisions();
        gererNuke();

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
        g.drawString(points1, LARGEUR - 50, 10);

    }

    /**
     * Update du patron observateur (MVC)
     *
     * @param o N/A
     * @param arg N/A
     */
    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Entite> listTemp = new ArrayList<>();

        int nbVie = modele.getPointVie();
        System.out.println(nbVie);
        for (Entite entite : listeEntite) {
            if (entite instanceof Coeur) {
                listTemp.add(entite);
            }
        }
        listeEntite.removeAll(listTemp);
        listTemp.clear();

        for (int i = 0; i < nbVie; i++) {
            Coeur coeur = new Coeur(i * 32, i);
            listeEntite.add(coeur);

        }
        points2 = modele.getScore();

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

        if (listeKeys.contains(KeyCode.SPACE) && System.currentTimeMillis() >= millis2 + 500) {

            if (beach.isPossedeArme() && tempsTirer) {
                tirerAvecArme();

                if (System.currentTimeMillis() >= millis3 + 10000) {
                    tempsTirer = false;
                }

            } else {

                tirerBalle();
            }

            millis2 = System.currentTimeMillis();
        }
    }

    private void gererCollisions() {
        float posX = 0, posY = 0;
        boolean bonus = false;
        ArrayList<Entite> listeTempBougeable = new ArrayList<>();

        //gestion de la récuperation des bonus
        for (Entite b1 : listeEntite) {
            if (b1 instanceof Bonus && b1.getRectangle().intersects(beach.getRectangle())) {
                controleur.ajouterPoint(25);
                listeTempBougeable.add(b1);
                if (b1 instanceof BonusVie) {

                    controleur.modifierPointVie(1);

                } else if (b1 instanceof BonusArme) {
                    beach.setPossedeArme(true);
                    tempsTirer = true;
                    millis3 = System.currentTimeMillis();

                } else if (b1 instanceof BonusBombe) {
                    beach.setPossedeBombe(true);
                }
            }
        }

        for (Bougeable b1 : listeBougeable) {
            if ((b1 instanceof Méchant || b1 instanceof BouleDeFeu) && (b1.getRectangle().intersects(beach.getRectangle()))) {
                listeTempBougeable.add((Entite) b1);

                controleur.modifierPointVie(-1);

            }

            for (Bougeable b2 : listeBougeable) {
                if (b1 != b2) {
                    if (b1 instanceof Collisionnable && b2 instanceof Collisionnable) {

                        if (b1.getRectangle().intersects(b2.getRectangle())) {

                            if ((b1 instanceof Projectile || b2 instanceof Projectile) && (b1 instanceof Méchant || b2 instanceof Méchant)) {
                                listeTempBougeable.add((Entite) b1);
                                listeTempBougeable.add((Entite) b2);
                                if (b1 instanceof Projectile && b2 instanceof Méchant) {
                                    controleur.ajouterPoint(5);
                                }

                                Entite b3 = (Entite) b1;
                                posX = b3.getX();
                                posY = b3.getY();
                                bonus = true;

                            }

                        }

                    }

                }

            }
        }
        if (bonus) {
            creerBonus(posX, posY);
        }
        listeBougeable.removeAll(listeTempBougeable);
        listeEntite.removeAll(listeTempBougeable);
        listeTempBougeable.clear();

    }

    private void tirerBalle() {
        Projectile balle = new Projectile(beach.getX() + 32, beach.getY() + 24, 0);
        listeEntite.add(balle);
        listeBougeable.add(balle);

    }

    private void tirerAvecArme() {
        Projectile balle = null;
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    balle = new Projectile(beach.getX() + 32, beach.getY() + +24, 0);
                    break;
                case 1:
                    balle = new Projectile(beach.getX() + 32, beach.getY(), -0.5f);
                    break;
                case 2:
                    balle = new Projectile(beach.getX() + 32, beach.getY() + 44, 0.5f);
                    break;
                default:
                    break;
            }
            listeEntite.add(balle);
            listeBougeable.add(balle);
        }
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
        FeuilleArbre feuilleArbre = new FeuilleArbre(position, HAUTEUR - 64 - 32 * nombreHauteur, spriteMonde);
        for (float i = HAUTEUR - 32 - 32 * nombreHauteur; i < HAUTEUR - 64; i = i + 32) {
            TroncArbre troncArbre = new TroncArbre(position, i, spriteMonde);
            listeEntite.add(troncArbre);
            listeBougeable.add(troncArbre);
        }

        listeEntite.add(feuilleArbre);
        listeBougeable.add(feuilleArbre);

    }

    private void delete() {
        ArrayList<Entite> listeTemp = new ArrayList();

        for (int i = 0; i < listeBougeable.size(); i++) {

            if (listeBougeable.get(i).getRectangle().getX() < -32 && !(listeBougeable.get(i) instanceof Plancher) || (listeBougeable.get(i) instanceof Projectile && listeBougeable.get(i).getRectangle().getX() > LARGEUR)) {
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
                Ciel ciel = new Ciel(j, i, spriteMonde);
                listeEntite.add(ciel);
            }

        }
        for (int i = 0; i < 2; i++) {
            creerArbre(0);

        }
        for (int i = 0; i < LARGEUR + 32; i = i + 32) {

            Plancher plancher1 = new Plancher(i, HAUTEUR - 64, spriteMonde, 3, 1);
            Plancher plancher2 = new Plancher(i, HAUTEUR - 32, spriteMonde, 4, 1);

            listeEntite.add(plancher1);
            listeEntite.add(plancher2);
            listeBougeable.add(plancher1);
            listeBougeable.add(plancher2);
        }
    }

    private void creerEnemiTerre(int intervale) {
        int position = r.nextInt(2 * LARGEUR);

        while (position <= intervale) {
            position = r.nextInt(2 * LARGEUR);

        }
        EnnemiTerre enemiTerre = new EnnemiTerre(position, HAUTEUR - 96, spriteDivers);

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
                    BouleDeFeu bouleDeFeu = new BouleDeFeu(ennemi.getX(), ennemi.getY() - 32, spriteDivers);
                    listeBougeable.add(bouleDeFeu);
                    listeEntite.add(bouleDeFeu);
                }
            }
        }
    }

    private void vaguesEnnemi(int typeVague, int cpt) {
        int intervalle = LARGEUR;

        switch (typeVague) {

            case 0:
                if (cpt == 1) {
                    intervalle += 40;
                } else if (cpt == 2) {
                    intervalle += 80;

                } else if (cpt == 3) {
                    intervalle += 120;
                }
                EnnemiVolant1 ennemiVolant1 = new EnnemiVolant1(intervalle, HAUTEUR - 850, spriteDivers);
                listeBougeable.add(ennemiVolant1);
                listeEntite.add(ennemiVolant1);

                break;
            case 1:
                if (cpt == 1) {
                    intervalle += 20;
                } else if (cpt == 2) {
                    intervalle += 40;

                } else if (cpt == 3) {
                    intervalle += 60;
                }
                EnnemiVolant2 ennemiVolant2 = new EnnemiVolant2(intervalle, HAUTEUR - 850, spriteDivers);
                listeBougeable.add(ennemiVolant2);
                listeEntite.add(ennemiVolant2);

                break;
            case 2:
                if (cpt == 1) {
                    intervalle += 0;
                } else if (cpt == 2) {
                    intervalle += 80;

                } else if (cpt == 3) {
                    intervalle += 120;
                }
                EnnemiVolant3 ennemiVolant3 = new EnnemiVolant3(intervalle, HAUTEUR - 850, spriteDivers);
                listeBougeable.add(ennemiVolant3);
                listeEntite.add(ennemiVolant3);
                break;
        }

    }

    private void gestionEnnemiTerre() {
        int ennemiRan = r.nextInt(1000);
        if (ennemiRan == 123) {
            creerEnemiTerre(LARGEUR);
        }
    }

    public Beach getBeach() {
        return beach;
    }

    private void creerBonus(float posX, float posY) {

        int nb = r.nextInt(10);
        int quelBonus = r.nextInt(3);
        if (nb == 1) {
            if (quelBonus == 0) {
                BonusBombe bombe = new BonusBombe(posX, posY, spriteDivers);
                listeEntite.add(bombe);
                listeBougeable.add(bombe);
            } else if (quelBonus == 1) {
                BonusVie vie = new BonusVie(posX, posY, spriteDivers);
                listeEntite.add(vie);
                listeBougeable.add(vie);
            } else if (quelBonus == 2) {
                BonusArme arme = new BonusArme(posX, posY, spriteDivers);
                listeEntite.add(arme);
                listeBougeable.add(arme);
            }
        }

    }

    private void gererNuke() {
        ArrayList<Entite> listeTemp = new ArrayList<>();
        if (beach.isPossedeBombe()) {
            for (Bougeable b : listeBougeable) {
                if (b.getRectangle().getX() < LARGEUR && !(b instanceof Plancher) && !(b instanceof TroncArbre) && !(b instanceof FeuilleArbre)) {
                    listeTemp.add((Entite) b);
                }
            }
            listeBougeable.removeAll(listeTemp);
            listeEntite.removeAll(listeTemp);
            listeTemp.clear();
            beach.setPossedeBombe(false);
        }
    }

}
