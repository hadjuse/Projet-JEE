package com.projet.controller;

import com.projet.model.Grille;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class ActionsController {
    /**
     * Création d'une grille 8x8
     * */
    public void creerGrille(HttpServletRequest request, HttpServletResponse response){
        int lignes = 8;
        int colonnes = 8;

        Grille grille = new Grille(lignes,colonnes);

        request.setAttribute("grille",grille);
    }
}
