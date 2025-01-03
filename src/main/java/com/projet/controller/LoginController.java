package com.projet.controller;

import com.projet.model.Joueur;
import com.projet.persistence.JoueurDAO;
import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String mdp = request.getParameter("mdp");

        try (JoueurDAO joueurDAO = new JoueurDAO()) {
            Joueur joueur = joueurDAO.trouverJoueurParNomEtMdp(nom, Joueur.hashPassword(mdp));

            if (joueur != null) {
                HttpSession session = request.getSession(true); // Crée une nouvelle session si elle n'existe pas
                session.setAttribute("joueur", joueur);
                response.sendRedirect("connected/index.jsp");
            } else {
                response.getWriter().println("Nom d'utilisateur ou mot de passe incorrect");
            }
        } catch (NoResultException e) {
            response.getWriter().println("Nom d'utilisateur ou mot de passe incorrect");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la connexion", e);
        }
    }
}