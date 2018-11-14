/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

/**
 *
 * @author 1721244
 */
public class Coeur extends Entite {

    private int quelCoeur;

    public Coeur(float x, int quelCoeur) {
        super(x, 10, 28, 28, "images/coeur.png");
        this.quelCoeur = quelCoeur;
    }

    public int getQuelCoeur() {
        return quelCoeur;
    }

}
