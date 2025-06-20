package com.example.demo.services;

import com.example.demo.models.Propietario;
import com.example.demo.utils.HibernateUtils;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PropietarioService {
    public void addPropietario(Propietario propietario) {
        EntityManager entityManager = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(propietario);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public List<Propietario> getAllPropietarios() {
        EntityManager entityManager = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery("FROM Propietario", Propietario.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}