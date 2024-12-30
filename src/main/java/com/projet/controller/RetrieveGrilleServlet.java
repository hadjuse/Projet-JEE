// src/main/java/com/projet/controller/RetrieveGrilleServlet.java
package com.projet.controller;

import com.projet.model.Grille;
import com.projet.persistence.GrilleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/retrieveGrilles")
public class RetrieveGrilleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GrilleDAO grilleDAO;

    public RetrieveGrilleServlet() {
        this.grilleDAO = new GrilleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Grille> grilles = grilleDAO.listerGrilles();
        request.setAttribute("grilles", grilles);
        request.getRequestDispatcher("/parties/index.jsp").forward(request, response);
    }
}