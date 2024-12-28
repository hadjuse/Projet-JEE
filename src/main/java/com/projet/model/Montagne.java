package com.projet.model;

public class Montagne extends Tuile {
    public Montagne(int x, int y) {
        super(x, y, "montagne");
    }

    // Méthode pour vérifier si une montagne bloque le déplacement
    public boolean bloqueDeplacement() {
        return true; // Les montagnes bloquent toujours les déplacements
    }
}
