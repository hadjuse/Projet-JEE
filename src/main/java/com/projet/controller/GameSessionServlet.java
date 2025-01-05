package com.projet.controller;

import com.projet.model.Grille;
import com.projet.persistence.GrilleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/gameSession")
public class GameSessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessionPlayer = request.getSession(false);

        if (sessionPlayer == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Retrieve the grid ID from the request parameter
        String grilleIdParam = request.getParameter("grilleId");
        if (grilleIdParam == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Long grilleId = Long.parseLong(grilleIdParam);

        try (GrilleDAO grilleService = new GrilleDAO()) {
            Grille grille = grilleService.trouverGrilleParId(grilleId);

            // Set the grid data as a request attribute
            request.setAttribute("grille", grille);
            request.setAttribute("action", "afficherGrille");
            // Forward the request to the JSP page
            request.getRequestDispatcher("/FrontController").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération de la grille", e);
        }
    }
}