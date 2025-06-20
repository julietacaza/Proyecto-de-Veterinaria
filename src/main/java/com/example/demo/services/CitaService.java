package com.example.demo.services;

import com.example.demo.models.Cita;
import com.example.demo.utils.HibernateUtils;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CitaService {
    public void guardarCita(Cita cita) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cita);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Cita> getAllCitas() {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("FROM Cita", Cita.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void eliminarCita(Integer id) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Cita cita = em.find(Cita.class, id);
            if (cita != null) {
                em.remove(cita);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}