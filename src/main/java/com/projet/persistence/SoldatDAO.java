package com.projet.persistence;

import com.projet.model.Soldat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public class SoldatDAO {
    @PersistenceContext
    private EntityManagerFactory emf;

    public SoldatDAO() {
        emf = Persistence.createEntityManagerFactory("jeuPU");
    }

    @Transactional
    public void creerSoldat(Soldat soldat) {
        emf.createEntityManager().persist(soldat);
    }

    @Transactional
    public Soldat trouverSoldatParId(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Soldat.class, id);
        }
    }

    @Transactional
    public void mettreAJourSoldat(Soldat soldat) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(soldat);
            em.getTransaction().commit();
        }
    }

    @Transactional
    public void supprimerSoldat(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Soldat soldat = em.find(Soldat.class, id);
            if (soldat != null) {
                em.remove(soldat);
            }
            em.getTransaction().commit();
        }
    }

    @Transactional
    public List<Soldat> listerSoldats() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Soldat s", Soldat.class).getResultList();
        }
    }
}