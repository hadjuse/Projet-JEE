package com.projet.model;

public class Soldat {
    private int x;
    private int y;
    private int pointsDefense;
    private boolean blesse;
    private int coutProduction;

    public Soldat(int x, int y, int pointsDefense, int coutProduction) {
        this.x = x;
        this.y = y;
        this.pointsDefense = pointsDefense;
        this.blesse = false;
        this.coutProduction = coutProduction;
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
