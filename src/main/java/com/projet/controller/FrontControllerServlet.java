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
    public void init() throws ServletException {
        actionsController = new ActionsController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getAction(request);
        executeAction(action, request, response);
    }

    private String getAction(HttpServletRequest request) {
        String action = (String) request.getAttribute("action");
        if (action == null) {
            action = request.getParameter("action");
        }
        System.out.println("Action = " + action);
        return action;
    }

    private void executeAction(String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (action) {
            case "creerGrille":
                actionsController.creerGrille(request, response);
                break;
            case "afficherGrille":
                actionsController.afficherGrille(request, response);
                break;
            case "deplacerSoldat":
                actionsController.deplacerSoldat(request, response);
                break;
            case "collecterRessources":
                actionsController.collecterResources(request, response);
                break;
            case "occuperVille":
                actionsController.occuperVille(request, response);
                break;
            case "guerirSoldat":
                actionsController.guerirSoldat(request, response);
                break;
            case "creerSoldat":
                actionsController.creerSoldat(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non définie");
                return;
        }
        request.getRequestDispatcher("grille/index.jsp").forward(request, response);
    }
}