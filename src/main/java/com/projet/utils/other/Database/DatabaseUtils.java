package com.projet.utils.other.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseUtils {
    public static void resetAutoIncrement(EntityManager em, String tableName) {
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        em.getTransaction().commit();
    }
}

