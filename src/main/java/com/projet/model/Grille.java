package com.projet.model;

import java.util.ArrayList;
import java.util.List;

public class Grille {
    private int lignes;
    private int colonnes;
    private List<Tuile> tuiles;

    /**
     *Initialisation d'une grille vide
     * */
    public Grille(int lignes, int colonnes){
        this.lignes =lignes;
        this.colonnes =colonnes;
        this.tuiles= new ArrayList<>();

        for(int i=0;i<lignes;i++){
            for(int j=0;j<colonnes;j++){
                tuiles.add(new Tuile(i,j,"vide"));
            }
        }
    }

    /**
     * Méthode pour récupérer une tuile par ses coordonnées.
     * Si les coordonnées sont hors intervalles, on retourne null
     * */
    public Tuile getTuile(int xLigne, int yColonne){
        return tuiles.stream()
                .filter(t->t.getX()==xLigne && t.getY()==yColonne)
                .findFirst()
                .orElse(null);

    }

    public int getLignes(){
        return lignes;
    }

    public int getColonnes(){
        return colonnes;
    }

    public List<Tuile> getTuiles(){
        return tuiles;
    }

}
