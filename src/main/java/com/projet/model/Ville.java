package com.projet.model;

public class Ville {
    private int coordX;
    private int coordY;
    private int pointsDefense;
    private Joueur proprietaire;

    public Ville(int coordX, int coordY, int pointsDefense, Joueur proprietaire) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.pointsDefense = pointsDefense;
        this.proprietaire = proprietaire;
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

    public int getPointsDefense() {
        return pointsDefense;
    }

    public void setPointsDefense(int pointsDefense) {
        this.pointsDefense = pointsDefense;
    }

    public Joueur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire = proprietaire;
    }

    // Méthodes
    public void produirePoints(int points) {
        if (proprietaire != null) {
            proprietaire.setPointsProduction(proprietaire.getPointsProduction() + points);
        }
    }
}
