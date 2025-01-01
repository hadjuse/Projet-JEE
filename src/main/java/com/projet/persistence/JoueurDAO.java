package com.projet.persistence;

import com.projet.model.Joueur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class JoueurDAO {
    private EntityManagerFactory emf;

    public JoueurDAO() {
        emf = Persistence.createEntityManagerFactory("jeuPU");
    }

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

    public Joueur trouverJoueurParId(Long id) {
        EntityManager em = emf.createEntityManager();
        Joueur joueur = em.find(Joueur.class, id);
        em.close();
        return joueur;
    }

    public Joueur trouverJoueurParNomEtMdp(String nom, String mdp) throws NoResultException {
        EntityManager em = emf.createEntityManager();
        Joueur joueur = em.createQuery("SELECT j FROM Joueur j WHERE j.nom = :nom AND j.password = :mdp", Joueur.class)
                .setParameter("nom", nom)
                .setParameter("mdp", mdp)
                .getSingleResult();
        em.close();
        return joueur;
    }

    public void mettreAJourJoueur(Joueur joueur) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(joueur);
        em.getTransaction().commit();
        em.close();
    }

    public void supprimerJoueur(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Joueur joueur = em.find(Joueur.class, id);
        if (joueur != null) {
            em.remove(joueur);
        }
        em.getTransaction().commit();
        em.close();
    }
}