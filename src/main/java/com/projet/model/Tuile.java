package com.projet.model;

public class Tuile {
    private int coordX;
    private int coordY;
    private String type; // "ville", "montagne", "foret", "vide"
    private Joueur proprietaire;

    public Tuile(int coordX, int coordY, String type) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.type = type;
        this.proprietaire = null;
    }

    // Getters et Setters
    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Joueur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire = proprietaire;
    }

}
