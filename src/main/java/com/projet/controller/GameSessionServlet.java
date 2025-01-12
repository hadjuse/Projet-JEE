package com.projet.controller;

import com.projet.model.Grille;
import com.projet.model.Joueur;
import com.projet.persistence.GrilleDAO;
import com.projet.persistence.JoueurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/gameSession")
public class GameSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessionPlayer = request.getSession(false);

        if (sessionPlayer == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Retrieve the grid ID
        String grilleIdParam = request.getParameter("grilleId");
        if (grilleIdParam == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Long grilleId = Long.parseLong(grilleIdParam);

        // ********** AJOUTEZ CE CODE **********
        Joueur sessionJoueur = (Joueur) sessionPlayer.getAttribute("joueur");
        if (sessionJoueur != null) {
            try (JoueurDAO joueurDAO = new JoueurDAO()) {
                Joueur reloaded = joueurDAO.trouverJoueurParId(sessionJoueur.getId());
                sessionPlayer.setAttribute("joueur", reloaded);
            }catch (Exception e) {
                throw new ServletException("Erreur lors de la récupération du joueur", e);
        }
        // **************************************

        try (GrilleDAO grilleService = new GrilleDAO()) {
            Grille grille = grilleService.trouverGrilleParId(grilleId);

            request.setAttribute("grille", grille);
            request.setAttribute("action", "afficherGrille");
            request.getRequestDispatcher("/FrontController").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération de la grille", e);
        }
    }
}

}