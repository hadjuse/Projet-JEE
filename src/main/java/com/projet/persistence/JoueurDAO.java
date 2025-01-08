package com.projet.persistence;

import com.projet.model.Joueur;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

public class JoueurDAO implements AutoCloseable {
    @PersistenceContext
    private EntityManagerFactory emf;

    public JoueurDAO() {
        emf = Persistence.createEntityManagerFactory("jeuPU");
    }
    @Transactional
    public void creerJoueur(Joueur joueur) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(joueur);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Transactional
    public Joueur trouverJoueurParId(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Joueur.class, id);
        }
    }

    @Transactional
    public Joueur trouverJoueurParNomEtMdp(String nom, String mdp) throws NoResultException {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT j FROM Joueur j WHERE j.nom = :nom AND j.password = :mdp", Joueur.class)
                    .setParameter("nom", nom)
                    .setParameter("mdp", mdp)
                    .getSingleResult();
        }
    }

    @Transactional
    public void mettreAJourJoueur(Joueur joueur) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(joueur);
            em.getTransaction().commit();
        }
    }

    @Transactional
    public void supprimerJoueur(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Joueur joueur = em.find(Joueur.class, id);
            if (joueur != null) {
                em.remove(joueur);
            }
            em.getTransaction().commit();
        }
    }
    @Transactional
    public Joueur trouverJoueurParNom(String nom) {
        try (EntityManager em = emf.createEntityManager()) {
         return em.createQuery("SELECT j FROM Joueur j WHERE j.nom = :nom", Joueur.class)
                    .setParameter("nom", nom)
                    .getSingleResult();
        }
    }

    @Transactional
    public List<Joueur> trouverTousLesJoueurs() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT j FROM Joueur j", Joueur.class).getResultList();
        }
    }
    @Override
    public void close() throws Exception {
        emf.close();
    }
}