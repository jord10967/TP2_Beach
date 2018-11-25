/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.modele;

import java.util.Observable;

/**
 *
 * @author MGrenon
 */
public class Modele extends Observable {

    private int pointVie;
    private int score;

    public void modifierPointVie(int point) {
        pointVie = pointVie + point;
        majObserver();
    }

    public void augmenterScore(int point) {
        score = score + point;
        majObserver();
    }

    private void majObserver() {
        setChanged();
        notifyObservers();
    }

    public int getPointVie() {
        return pointVie;
    }

    public int getScore() {
        return score;
    }

}
