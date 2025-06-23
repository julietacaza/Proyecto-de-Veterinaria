package com.example.demo.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtils {

    private static final EntityManagerFactory emf = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("com.example.demo");
        } catch (Exception e) {
            System.err.println(" Error al crear EntityManagerFactory: " + e.getMessage());
            throw new RuntimeException("No se pudo crear EntityManagerFactory", e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("✅ EntityManagerFactory cerrado correctamente.");
        }
    }
}
