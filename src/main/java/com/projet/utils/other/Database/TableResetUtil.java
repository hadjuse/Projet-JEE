package com.projet.utils.other.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TableResetUtil {

    public static void resetTable(EntityManager em, String tableName) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Supprimer toutes les entités de la table
            em.createQuery("DELETE FROM " + tableName).executeUpdate();

            // Réinitialiser l'auto-incrémentation
            em.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}