package com.projet.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DAOFactory {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jeuPU");

    public static TuileDAO getTuileDAO() {
        EntityManager em = emf.createEntityManager();
        return new TuileDAO(em);
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }
}