package com.projet.model;

public class Foret {
    private int ressourcesProduction;

    public Foret(int coordX, int coordY, int ressourcesProduction) {
        super();
        this.ressourcesProduction = ressourcesProduction;
    }

    // Getters et Setters
    public int getRessourcesProduction() {
        return ressourcesProduction;
    }

    public void setRessourcesProduction(int ressourcesProduction) {
        this.ressourcesProduction = ressourcesProduction;
    }

    // Méthodes
    public int fourrager() {
        int pointsGagnes = ressourcesProduction;
        ressourcesProduction = 0; // Une fois la forêt exploitée, elle ne produit plus
        return pointsGagnes;
    }
}
