package com.projet.persistence;

import com.projet.model.Tuile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class TuileDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TuileDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void creerTuile(Tuile tuile) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tuile);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Transactional
    public Tuile trouverTuileParId(Long id) {
        return entityManager.find(Tuile.class, id);
    }

    @Transactional
    public void mettreAJourTuile(Tuile tuile) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tuile);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Transactional
    public void supprimerTuile(Long id) {
        try {
            entityManager.getTransaction().begin();
            Tuile tuile = entityManager.find(Tuile.class, id);
            if (tuile != null) {
                entityManager.remove(tuile);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }
}