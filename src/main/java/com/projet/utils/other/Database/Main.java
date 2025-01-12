package com.projet.utils.other.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jeuPU");
        EntityManager em = emf.createEntityManager();

        try {
            TableResetUtil.dropTable(em, "Tuile");
            TableResetUtil.dropTable(em, "Soldat");
            TableResetUtil.dropTable(em, "Grille");
            TableResetUtil.dropTable(em, "Foret");
            TableResetUtil.dropTable(em, "Ville");
            TableResetUtil.dropTable(em, "Joueur");
        } finally {
            em.close();
            emf.close();
        }
    }
}