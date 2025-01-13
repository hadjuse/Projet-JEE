package com.projet.controller;

import com.projet.model.Joueur;
import com.projet.persistence.JoueurDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private JoueurDAO joueurDAO;

    @Override
    public void init() {
        joueurDAO = new JoueurDAO();
    }

    private void setPlayerAttribut(Joueur joueur, String nom) {
        joueur.setScore(0);
        joueur.setNbSoldats(0);
        joueur.setNbVilles(0);
        joueur.setNbForets(0);
        joueur.setNbTuiles(0);
        joueur.setPointProduction(0);
        joueur.setNom(nom);
        joueur.setTurn(false);
    }

    private void throwErrorWhenJoueurNotRegisterCorrectly(HttpServletResponse response, Exception e) throws IOException {
        response.getWriter().println("Erreur lors de l'enregistrement du joueur: " + e.getMessage());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        String nom = request.getParameter("nom");
        String mdp = request.getParameter("mdp");
        try {
            Joueur joueur = new Joueur();
            joueur.setPassword(mdp);
            setPlayerAttribut(joueur, nom);
            joueurDAO.creerJoueur(joueur);
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            throwErrorWhenJoueurNotRegisterCorrectly(response, e);
        }
    }
}