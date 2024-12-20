package com.projet.model;

public class Soldat {
    private int positionX;
    private int positionY;
    private int pointsDefense;
    private boolean blesse;
    private int coutProduction;

    public Soldat(int positionX, int positionY, int pointsDefense, int coutProduction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.pointsDefense = pointsDefense;
        this.blesse = false;
        this.coutProduction = coutProduction;
    }

    // Getters et Setters
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPointsDefense() {
        return pointsDefense;
    }

    public void setPointsDefense(int pointsDefense) {
        this.pointsDefense = pointsDefense;
    }

    public boolean isBlesse() {
        return blesse;
    }

    public void setBlesse(boolean blesse) {
        this.blesse = blesse;
    }

    public int getCoutProduction() {
        return coutProduction;
    }

    public void setCoutProduction(int coutProduction) {
        this.coutProduction = coutProduction;
    }

    // Méthodes
    public void soigner() {
        if (blesse) {
            this.blesse = false;
            this.pointsDefense += 10; // Règle : augmenter la défense lors de la guérison
        }
    }
}
