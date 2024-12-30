package com.projet.controller;

import com.projet.model.Grille;
import com.projet.persistence.GrilleDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class ActionsController {
    /**
     * Création d'une grille 8x8
     * */
    private GrilleDAO grilleDAO;
    public ActionsController(){
        setGrilleDAO(new GrilleDAO());
    }
    public void creerGrille(HttpServletRequest request, HttpServletResponse response){
        int lignes = 8;
        int colonnes = 8;

        Grille grille = new Grille(lignes,colonnes);
        getGrilleDAO().enregistrerGrille(grille);
        request.setAttribute("grille",grille);
    }

    public void afficherGrille(HttpServletRequest request, HttpServletResponse response) {
        String grilleId = request.getParameter("id");
        if (grilleId == null) {
            request.setAttribute("message", "ID de la grille manquant.");
            return;
        }

        // Récupérer la grille depuis la base de données
        Grille grille = grilleDAO.trouverGrilleParId(Long.parseLong(grilleId));

        if (grille == null) {
            request.setAttribute("message", "Grille non trouvée.");
        } else {
            request.setAttribute("grille", grille);
        }
    }

    public void setGrilleDAO(GrilleDAO grilleDAO) {
        this.grilleDAO = grilleDAO;
    }

    public GrilleDAO getGrilleDAO() {
        return grilleDAO;
    }
}
