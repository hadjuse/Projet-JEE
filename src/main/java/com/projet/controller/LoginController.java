package com.projet.controller;

import com.projet.model.Joueur;
import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    // TODO : implement login controller
    private static final long serialVersionUID = 1L;
    private EntityManagerFactory entityManager;
    @Override
    public void init() {
        entityManager = Persistence.createEntityManagerFactory("jeuPU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String mdp = request.getParameter("mdp");
        EntityManager em = entityManager.createEntityManager();
        try {
            Joueur joueur = em.createQuery("SELECT j FROM Joueur j WHERE j.nom = :nom AND j.password = :mdp", Joueur.class)
                    .setParameter("nom", nom)
                    .setParameter("mdp", Joueur.hashPassword(mdp))
                    .getSingleResult();
            HttpSession session = request.getSession();
            session.setAttribute("joueur", joueur);
            response.sendRedirect("connected/index.jsp");
        } catch (NoResultException e) {
            response.getWriter().println("Nom d'utilisateur ou mot de passe incorrect");
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if(entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

}
