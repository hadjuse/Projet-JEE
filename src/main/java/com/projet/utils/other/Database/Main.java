package com.projet.utils.other.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jeuPU");
        EntityManager em = emf.createEntityManager();

        try {
            TableResetUtil.resetTable(em, "Tuile");
            TableResetUtil.resetTable(em, "Soldat");
            TableResetUtil.resetTable(em, "Joueur");
            TableResetUtil.resetTable(em, "Grille");
            TableResetUtil.resetTable(em, "Foret");
        } finally {
            em.close();
            emf.close();
        }
    }
}