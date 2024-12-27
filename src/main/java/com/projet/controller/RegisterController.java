package com.projet.controller;

import com.projet.model.Joueur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private EntityManagerFactory entityManager;

    @Override
    public void init() {
        entityManager = Persistence.createEntityManagerFactory("jeuPU");
    }

    private static final long serialVersionUID = 1L;
    private void setPlayerAttribut(Joueur joueur, String nom, String mdp) {
        joueur.setScore(0);
        joueur.setNbSoldats(0);
        joueur.setNbVilles(0);
        joueur.setNbForets(0);
        joueur.setNbTuiles(0);
        joueur.setPointsProduction(0);
        joueur.setNom(nom);
        joueur.setPassword(Joueur.hashPassword(mdp));
    }

    private void throwErrorWhenJoueurNotRegisterCorrectly(HttpServletResponse response, EntityManager em, Exception e) throws IOException {
        em.getTransaction().rollback();
        response.getWriter().println("Erreur lors de l'enregistrement du joueur"+ e.getMessage());
    }
    private void persistPlayerIntoDatabase(EntityManager em, Joueur joueur) {
        em.persist(joueur);
        em.getTransaction().commit();
    }

    @Override
    @PersistenceContext
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        String nom = request.getParameter("nom");
        String mdp = request.getParameter("mdp");
        EntityManager em = entityManager.createEntityManager();
        try{
            em.getTransaction().begin();
            Joueur joueur = new Joueur();
            setPlayerAttribut(joueur, nom, mdp);
            persistPlayerIntoDatabase(em, joueur);
            response.getWriter().println("Joueur enregistré avec succès");
        } catch (Exception e) {
            throwErrorWhenJoueurNotRegisterCorrectly(response, em, e);
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
