package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "expediente")
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idexpediente")
    private int id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "Peso")
    private Float peso;

    @Column(name = "Temperatura")
    private Float temperatura;

    // Relación M-1 con Animal (mascota)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_idmascota", referencedColumnName = "Idmascota")
    private Animal mascota;

    // Relación M-1 con Servicio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Servicio_idServicio", referencedColumnName = "idServicio")
    private Servicio servicio;

    // Constructor vacío (requerido por JPA)
    public Expediente() {
    }

    // Constructor con campos esenciales
    public Expediente(LocalDate fecha, LocalTime hora, Float peso, Float temperatura,
                      Animal mascota, Servicio servicio) {
        this.fecha = fecha;
        this.hora = hora;
        this.peso = peso;
        this.temperatura = temperatura;
        this.mascota = mascota;
        this.servicio = servicio;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
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
