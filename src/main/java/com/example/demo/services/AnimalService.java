package com.example.demo.services;

import com.example.demo.Mascota;
import com.example.demo.utils.HibernateUtils;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AnimalService {
    // Guardar una mascota
    public void addMascota(Mascota mascota) {
        EntityManager entityManager = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(mascota);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // Obtener todas las mascotas
    public List<Mascota> getAllMascotas() {
        EntityManager entityManager = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery("FROM Animal", Mascota.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    // Buscar por ID
    public Mascota getMascotaById(int id) {
        EntityManager entityManager = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(Mascota.class, id);
        } finally {
            entityManager.close();
        }
    }

    // Actualizar mascota
    public void updateMascota(Mascota mascota) {
        EntityManager entityManager = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(mascota);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}