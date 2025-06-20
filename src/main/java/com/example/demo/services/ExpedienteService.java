package com.example.demo.services;

import com.example.demo.models.Expediente;
import com.example.demo.utils.HibernateUtils;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ExpedienteService {

    // Guardar expediente
    public void guardarExpediente(Expediente expediente) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(expediente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Obtener todos los expedientes
    public List<Expediente> obtenerTodos() {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("FROM Expediente", Expediente.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Eliminar por ID
    public void eliminarExpediente(Integer id) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Expediente exp = em.find(Expediente.class, id);
            if (exp != null) em.remove(exp);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
