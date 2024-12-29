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

    public void enregistrerGrille(Grille grille) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(grille); // Persist la grille et ses tuiles grâce à CascadeType.ALL
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Grille trouverGrilleParId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Grille.class, id);
        } finally {
            em.close();
        }
    }
    public void supprimerGrille(Grille grille) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(grille);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public List<Grille> listerGrilles() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT g FROM Grille g", Grille.class).getResultList();
        } finally {
            em.close();
        }
    }
}
