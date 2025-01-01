package com.projet.persistence;

import com.projet.model.Grille;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class GrilleDAO {
    private EntityManagerFactory emf;

    public GrilleDAO() {
        emf = Persistence.createEntityManagerFactory("jeuPU"); // Correspond à votre persistence.xml
    }

    // Méthode générique pour exécuter des transactions
    private void executeInTransaction(EntityManager em, Runnable action) {
        try {
            em.getTransaction().begin();
            action.run();
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

    // Enregistre ou met à jour une grille
    public void enregistrerGrille(Grille grille) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(grille); // Mettre à jour ou insérer la grille
            em.flush(); // Synchroniser avec la base de données
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

    // Trouve une grille par son ID
    public Grille trouverGrilleParId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Grille.class, id);
        } finally {
            em.close();
        }
    }

    // Supprime une grille existante
    public void supprimerGrille(Grille grille) {
        EntityManager em = emf.createEntityManager();
        executeInTransaction(em, () -> {
            Grille managedGrille = em.merge(grille); // Attache l'entité
            em.remove(managedGrille);
        });
    }

    // Crée une nouvelle grille
    public void creerGrille(Grille grille) {
        EntityManager em = emf.createEntityManager();
        executeInTransaction(em, () -> em.persist(grille));
    }

    // Liste toutes les grilles
    public List<Grille> listerGrilles() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT g FROM Grille g", Grille.class).getResultList();
        } finally {
            em.close();
        }
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
