package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcitas")
    private Integer id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    // Relación M-1 con Animal (mascota)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_idmascota", referencedColumnName = "Idmascota")
    private Animal mascota;

    // Relación M-1 con Servicio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_idServicio", referencedColumnName = "idServicio")
    private Servicio servicio;

    // Constructor vacío (requerido por JPA)
    public Cita() {
    }

    // Constructor con campos
    public Cita(LocalDate fecha, LocalTime hora, Animal mascota, Servicio servicio) {
        this.fecha = fecha;
        this.hora = hora;
        this.mascota = mascota;
        this.servicio = servicio;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Animal getMascota() {
        return mascota;
    }

    public void setMascota(Animal mascota) {
        this.mascota = mascota;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}