package com.projet.persistence;

import com.projet.model.Foret;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class ForetDAO implements AutoCloseable {

    @PersistenceContext
    private EntityManager entityManager;

    public ForetDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void creerForet(Foret foret) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(foret);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Transactional
    public Foret trouverForetParId(Long id) {
        return entityManager.find(Foret.class, id);
    }

    @Transactional
    public void mettreAJourForet(Foret foret) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(foret);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Transactional
    public void supprimerForet(Long id) {
        try {
            entityManager.getTransaction().begin();
            Foret foret = entityManager.find(Foret.class, id);
            if (foret != null) {
                entityManager.remove(foret);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
    }
}