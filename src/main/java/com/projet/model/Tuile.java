package com.projet.model;

public class Tuile {
    private int x;
    private int y;
    private TypeTuile type; // "ville", "montagne", "foret", "vide"
    private Joueur proprietaire;

    public Tuile(int x, int y, TypeTuile type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.proprietaire = null;
    }

    // Getters et Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public TypeTuile getType() {
        return type;
    }

    public void setType(TypeTuile type) {
        this.type = type;
    }

    public Joueur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire = proprietaire;
    }

    @Override
    public String toString(){
        return "Tuile{"+"x="+ x+", y="+ y+", type="+ type+'\''+'}';
    }

}
