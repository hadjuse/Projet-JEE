package com.projet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grille {
    private int lignes;
    private int colonnes;
    private List<Tuile> tuiles;

    /**
     * Initialisation de la grille avec des types aléatoires
     */
    public Grille(int lignes, int colonnes){
        this.lignes=lignes;
        this.colonnes=colonnes;
        this.tuiles=new ArrayList<>();

        Random random = new Random();

        for(int i=0; i<lignes; i++){
            for(int j=0; j<colonnes; j++){
                TypeTuile type = TypeTuile.values()[random.nextInt(TypeTuile.values().length)];
                tuiles.add(new Tuile(i,j,type));
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
