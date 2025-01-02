package com.projet.model;

import jakarta.persistence.*;

@Entity
public class Foret{
    private int ressourcesProduction;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int x;
    private int y;
    public Foret(int x, int y, int ressourcesProduction) {
        this.ressourcesProduction = ressourcesProduction;
    }
    public Foret() {

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
