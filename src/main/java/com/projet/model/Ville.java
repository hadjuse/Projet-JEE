package com.projet.model;

public class Ville extends Tuile {
    private int pointsDefense;
    private int productionParTour;

    public Ville(int x, int y, int pointsDefense, int productionParTour) {
        super(x,y,TypeTuile.VILLE);
        this.pointsDefense = pointsDefense;
        this.productionParTour = productionParTour;
    }

    // Getters et Setters
    public int getPointsDefense() {
        return pointsDefense;
    }

    public void setPointsDefense(int pointsDefense) {
        this.pointsDefense = pointsDefense;
    }

    public int getProductionParTour() {
        return productionParTour;
    }

    public void setProductionParTour(int productionParTour) {
        this.productionParTour = productionParTour;
    }

    // Méthodes
    public void produirePoints() {
        if (this.getProprietaire() != null) {
            this.getProprietaire().ajouterPointsProduction(productionParTour);
        }
    }

    public boolean subirAttaque(int pointsAttaque) {
        pointsDefense -= pointsAttaque;
        if (pointsDefense <= 0) {
            pointsDefense = 0;
            return true; // La ville est capturée
        }
        return false; // La ville résiste encore
    }
}
