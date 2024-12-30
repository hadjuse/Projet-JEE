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
        String actionPost = (String) request.getAttribute("action");
        String actionGet = request.getParameter("action");
        System.out.printf("Action: %s\n", actionPost);
        if("creerGrille".equals(actionPost)){
            actionsController.creerGrille(request,response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
            System.out.println("Méthode creer Grille appelée");
        } else if("afficherGrille".equals(actionPost)) {
            actionsController.afficherGrille(request, response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
        } else if("deplacerSoldat".equals(actionGet)) {
            System.out.printf("Action: %s\n", actionGet);
            actionsController.deplacerSoldat(request, response);
            request.getRequestDispatcher("grille/index.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non définie");
        }
    }
}
