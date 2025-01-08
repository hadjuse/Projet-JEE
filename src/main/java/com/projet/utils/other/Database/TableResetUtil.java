package com.projet.utils.other.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TableResetUtil {

    public static void dropTable(EntityManager em, String tableName) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Désactiver les contraintes de clé étrangère
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            // Supprimer la table
            em.createNativeQuery("DROP TABLE IF EXISTS " + tableName).executeUpdate();

            // Réactiver les contraintes de clé étrangère
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}