package com.projet.controller;

import com.projet.model.Grille;
import com.projet.model.Joueur;
import com.projet.persistence.GrilleDAO;
import com.projet.utils.button.ButtonStrategy;
import com.projet.utils.button.actionMove.MoveDown;
import com.projet.utils.button.actionMove.MoveLeft;
import com.projet.utils.button.actionMove.MoveRight;
import com.projet.utils.button.actionMove.MoveUp;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


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
        HttpSession session = request.getSession(false);
        Grille grille = new Grille(lignes,colonnes);
        Joueur joueur = (Joueur) session.getAttribute("joueur");
        grille.ajouterSoldat(0,0, joueur);
        getGrilleDAO().creerGrille(grille);
        grille.getTuile(0,0).getSoldat();
        request.setAttribute("grille",grille);
    }

    public void afficherGrille(HttpServletRequest request, HttpServletResponse response) {
        String grilleId = request.getParameter("id");
        if (grilleId == null) {
            request.setAttribute("message", "ID de la grille manquant.");
            return;
        }

        // Récupérer la grille depuis la base de données
        Grille grille = getGrilleDAO().trouverGrilleParId(Long.parseLong(grilleId));

        if (grille == null) {
            request.setAttribute("message", "Grille non trouvée.");
        } else {
            request.setAttribute("grille", grille);
        }
    }

    public void deplacerSoldat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String grilleId = request.getParameter("grilleId");
        int xSource = Integer.parseInt(request.getParameter("xSource"));
        int ySource = Integer.parseInt(request.getParameter("ySource"));
        String direction = request.getParameter("direction");
        System.out.printf("Grille ID: %s, xSource: %d, ySource: %d, direction: %s\n", grilleId, xSource, ySource, direction);
        Grille grille = getGrilleDAO().trouverGrilleParId(Long.parseLong(grilleId));
        ButtonStrategy strategy;

        switch (direction) {
            case "up":
                strategy = new MoveUp(grilleDAO);
                break;
            case "down":
                strategy = new MoveDown(grilleDAO);
                break;
            case "left":
                strategy = new MoveLeft(grilleDAO);
                break;
            case "right":
                strategy = new MoveRight(grilleDAO);
                break;
            default:
                request.setAttribute("message", "Direction invalide.");
                return;
        }
        System.out.printf("Grille ID: %s, xSource: %d, ySource: %d, direction: %s\n", grilleId, xSource, ySource, direction);
        strategy.action(grille, xSource, ySource);
        request.setAttribute("grille", grille);
        //forwardToFrontController(request, response, "deplacerSoldat");
    }
    public void setGrilleDAO(GrilleDAO grilleDAO) {
        this.grilleDAO = grilleDAO;
    }

    public GrilleDAO getGrilleDAO() {
        return grilleDAO;
    }
    private void forwardToFrontController(HttpServletRequest request, HttpServletResponse response, String action) throws IOException {
        request.setAttribute("action", action);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/FrontController");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new IOException("Erreur lors de la redirection vers FrontController", e);
        }
    }
}
