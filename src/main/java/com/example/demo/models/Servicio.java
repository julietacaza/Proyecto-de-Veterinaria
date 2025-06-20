package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicio")
    private Integer idServicio;

    @Column(name = "Tipo_de_servicio")
    private String tipoDeServicio;

    public Servicio() {
    }

    public Servicio(String tipoDeServicio) {
        this.tipoDeServicio = tipoDeServicio;
    }

    // Getters y Setters
    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getTipoDeServicio() {
        return tipoDeServicio;
    }

    public void setTipoDeServicio(String tipoDeServicio) {
        this.tipoDeServicio = tipoDeServicio;
    }
}
