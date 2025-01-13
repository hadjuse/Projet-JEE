package com.projet.model;

import jakarta.persistence.*;

@Entity
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Joueur proprietaire;

    private int pointsDefense;
    private int productionParTour;
    private int x;
    private int y;
    @ManyToOne
    private Grille grille;
    public Ville(int x, int y, int productionParTour) {
        this.pointsDefense = 12;
        this.x = x;
        this.y = y;
        this.productionParTour = productionParTour;
    }

    public Ville(int x, int y, Joueur proprietaire) {
        this.pointsDefense = 12;
        this.productionParTour = 10;
        this.proprietaire = proprietaire;
        proprietaire.addNbVilles(1);
    }

    public Ville(int x, int y, int productionParTour, Joueur proprietaire) {
        this.pointsDefense = 12;
        this.productionParTour = productionParTour;
        this.proprietaire = proprietaire;
        proprietaire.addNbVilles(1);
    }

    public Ville() {
    }

    // Getters et Setters
    public int getPointsDefense() {
        return this.pointsDefense;
    }

    public Joueur getProprietaire() {
        return proprietaire;
    }

    public int getProductionParTour() {
        return productionParTour;
    }

    public void setPointsDefense(int pointsDefense) {
        this.pointsDefense = pointsDefense;
    }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire = proprietaire;
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
        this.pointsDefense -= pointsAttaque;
        if (pointsDefense <= 0) {
            pointsDefense = 0;
            return true; // La ville est capturée
        }
        return false; // La ville résiste encore
    }

    public void produireSoldat() {
        if (this.getProprietaire() != null && this.getProprietaire().getPointProduction() >= 20) {
            this.getProprietaire().addNbSoldats(1);
            this.getProprietaire().retirerPointsProduction(20);
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Grille getGrille() {
        return grille;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
