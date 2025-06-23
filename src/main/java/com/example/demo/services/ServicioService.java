package com.example.demo.services;

import com.example.demo.models.Servicio;
import com.example.demo.utils.HibernateUtils;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ServicioService {
    public void guardarServicio(Servicio servicie) {  // Cambiado de addServicio a guardarServicio
        EntityManager entityManager = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(servicie);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }


    public List<Servicio> obtenerTodosLosServicios() {  // Cambiado de getAllServicios
        EntityManager entityManager = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.createQuery("FROM Servicio", Servicio.class).getResultList();  // Cambiado "servicio" a "Servicio"
        } finally {
            entityManager.close();
        }
    }
}
