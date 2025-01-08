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
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionPost = (String) request.getAttribute("action");
        if (actionPost == null) {
            actionPost = request.getParameter("action");
        }
        System.out.println("Action = " + actionPost);
        if("creerGrille".equals(actionPost)){
            actionsController.creerGrille(request,response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
            System.out.println("Méthode creer Grille appelée");
        } else if("afficherGrille".equals(actionPost)) {
            actionsController.afficherGrille(request, response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
        } else if("deplacerSoldat".equals(actionPost)) {
            System.out.printf("Action: %s\n", actionPost);
            actionsController.deplacerSoldat(request, response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
        } else if("collecterRessources".equals(actionPost)) {
            System.out.printf("Action: %s\n", actionPost);
            actionsController.collecterResources(request, response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
        } else if("occuperVille".equals(actionPost)) {
            System.out.printf("Action: %s\n", actionPost);
            actionsController.occuperVille(request, response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
        } else if ("passerTour".equals(actionPost)) {
            System.out.printf("Action: %s\n", actionPost);
            actionsController.passerTour(request, response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non définie");
        }
    }
}
