package com.projet.persistence;

import com.projet.model.Grille;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public class GrilleDAO {
    @PersistenceContext
    private EntityManagerFactory emf;

    public GrilleDAO() {
        emf = Persistence.createEntityManagerFactory("jeuPU"); // Correspond à votre persistence.xml
    }
    @Transactional
    public void enregistrerGrille(Grille grille) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(grille); // Persist la grille et ses tuiles grâce à CascadeType.ALL
            em.getTransaction().commit();
        }
    }

    @Transactional
    public Grille trouverGrilleParId(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Grille.class, id);
        }
    }
    @Transactional
    public void supprimerGrille(Grille grille) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(grille);
            em.getTransaction().commit();
        }
    }

    @Transactional
    public void creerGrille(Grille grille) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(grille); // Utilise persist pour créer une nouvelle grille
            em.getTransaction().commit();
        }
    }
    @Transactional
    public List<Grille> listerGrilles() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT g FROM Grille g", Grille.class).getResultList();
        }
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }
}