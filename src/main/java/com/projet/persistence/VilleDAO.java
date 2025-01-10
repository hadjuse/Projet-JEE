package com.projet.persistence;

import com.projet.model.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public class VilleDAO implements AutoCloseable {
    @PersistenceContext
    private EntityManagerFactory emf;

    public VilleDAO() {
        emf = Persistence.createEntityManagerFactory("jeuPU");
    }

    @Transactional
    public void creerVille(Ville ville) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ville);
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
    public Ville trouverVilleParId(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Ville.class, id);
        }
    }

    @Transactional
    public void mettreAJourVille(Ville ville) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(ville);
            em.getTransaction().commit();
        }
    }

    @Transactional
    public void supprimerVille(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Ville ville = em.find(Ville.class, id);
            if (ville != null) {
                em.remove(ville);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void close() throws Exception {
        emf.close();
    }

    public List<Ville> trouverVillesParJoueurId(long l) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT v FROM Ville v WHERE v.proprietaire.id = :id", Ville.class)
                    .setParameter("id", l)
                    .getResultList();
        }
    }
}