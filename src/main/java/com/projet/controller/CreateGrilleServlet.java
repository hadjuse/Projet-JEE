package com.projet.controller;

import com.projet.model.Joueur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/createGrille")
public class CreateGrilleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private void setPlayerAttributes(HttpSession session, Joueur joueur) {
        session.setAttribute("playerName", joueur.getNom());
        session.setAttribute("playerScore", joueur.getScore());
        session.setAttribute("playerNbSoldats", joueur.getNbSoldats());
        session.setAttribute("playerNbVilles", joueur.getNbVilles());
        session.setAttribute("playerNbForets", joueur.getNbForets());
        session.setAttribute("playerNbTuiles", joueur.getNbTuiles());
        session.setAttribute("playerPointsProduction", joueur.getPointsProduction());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessionPlayer = request.getSession(false);

        if (sessionPlayer == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        setPlayerAttributes(sessionPlayer, (Joueur) sessionPlayer.getAttribute("joueur"));
        request.setAttribute("action", "creerGrille");
        System.out.println(request.getAttribute("action"));
        System.out.println("Forwarding request to /FrontController");
        request.getRequestDispatcher("/FrontController").forward(request, response);
    }
}
