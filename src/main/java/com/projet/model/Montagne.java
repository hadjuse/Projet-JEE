package com.projet.model;

public class Montagne extends Tuile {
    public Montagne(int coordX, int coordY) {
        super(coordX, coordY, "montagne");
    }

    // Méthode pour vérifier si une montagne bloque le déplacement
    public boolean bloqueDeplacement() {
        return true; // Les montagnes bloquent toujours les déplacements
    }
}
