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

    private int pointVie=3;
    private int score;
    private int pointVieMax=3;
    public void modifierPointVie(int point) {
       if(pointVie<=pointVieMax && pointVie>0)
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
