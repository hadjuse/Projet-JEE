package com.projet.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Grille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int lignes;
    private int colonnes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grille", fetch = FetchType.EAGER)
    private List<Tuile> tuiles;

    public Grille(){
    }
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
                Tuile tuile = new Tuile(i,j,type);
                tuile.setGrille(this);
                getTuiles().add(tuile);
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setColonnes(int colonnes) {
        this.colonnes = colonnes;
    }

    public void setLignes(int lignes) {
        this.lignes = lignes;
    }

    public void setTuiles(List<Tuile> tuiles) {
        this.tuiles = tuiles;
    }
}
