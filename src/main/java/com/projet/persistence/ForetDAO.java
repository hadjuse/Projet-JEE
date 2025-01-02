package com.projet.persistence;

import com.projet.model.Foret;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class ForetDAO {
    @PersistenceContext
    private EntityManagerFactory emf;

    public ForetDAO() {
        emf = Persistence.createEntityManagerFactory("jeuPU");
    }

    @Transactional
    public void creerForet(Foret foret) {
        emf.createEntityManager().persist(foret);
    }

    @Transactional
    public Foret trouverForetParId(Long id) {
        try (var em = emf.createEntityManager()) {
            return em.find(Foret.class, id);
        }
    }

    @Transactional
    public void mettreAJourForet(Foret foret) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(foret);
            em.getTransaction().commit();
        }
    }

    @Transactional
    public void supprimerForet(Long id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Foret foret = em.find(Foret.class, id);
            if (foret != null) {
                em.remove(foret);
            }
            em.getTransaction().commit();
        }
    }

    @Transactional
    public void listerForets() {
        try (var em = emf.createEntityManager()) {
            em.createQuery("SELECT f FROM Foret f", Foret.class).getResultList();
        }
    }

    @Transactional
    public void supprimerForet(Foret foret) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(foret);
            em.getTransaction().commit();
        }
    }

    @Transactional
    public void supprimerForetParId(Long id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Foret foret = em.find(Foret.class, id);
            if (foret != null) {
                em.remove(foret);
            }
            em.getTransaction().commit();
        }
    }

}
