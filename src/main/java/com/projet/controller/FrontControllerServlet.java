package com.projet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/FrontController")
public class FrontControllerServlet extends HttpServlet {

    private ActionsController actionsController;

    @Override
    public void init() throws ServletException{
        this.actionsController = new ActionsController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = (String) request.getAttribute("action");
        if("creerGrille".equals(action)){
            actionsController.creerGrille(request,response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
            System.out.println("Méthode creerGrille appelée");
        } else if("afficherGrille".equals(action)) {
            actionsController.afficherGrille(request, response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
        } else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non définie");
        }
    }
}
