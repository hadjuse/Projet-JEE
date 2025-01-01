package com.projet.persistence;

import com.projet.model.Soldat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SoldatDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("jeuPU");

    public void creerSoldat(Soldat soldat) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(soldat);
        em.getTransaction().commit();
        em.close();
    }

    public Soldat trouverSoldatParId(Long id) {
        EntityManager em = emf.createEntityManager();
        Soldat soldat = em.find(Soldat.class, id);
        em.close();
        return soldat;
    }

    public void mettreAJourSoldat(Soldat soldat) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(soldat);
        em.getTransaction().commit();
        em.close();
    }

    public void supprimerSoldat(Soldat soldat) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(soldat) ? soldat : em.merge(soldat));
        em.getTransaction().commit();
        em.close();
    }
}