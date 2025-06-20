package com.example.demo.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mascota")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Idmascota")
    private Integer idMascota;

    @Column(name = "Nombre_de_la_mascota")
    private String nombre;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "tipo_de_mascotas")
    private String tipoMascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Propietario_idPropietario", referencedColumnName = "IdPropietario")
    private Propietario propietario;

    // para saber que una mascota puede tener muchas citas y muchos cosultas
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expediente> expedientes = new ArrayList<>();

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    // Constructor vacío (requerido por JPA)
    public Animal() {
    }

    public Animal(String nombre, String sexo, String tipoMascota, Propietario propietario) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.tipoMascota = tipoMascota;
        this.propietario = propietario;
    }

    // Getters y Setters
    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoMascota() {
        return tipoMascota;
    }

    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public List<Expediente> getExpedientes() {
        return expedientes;
    }

    public void setExpedientes(List<Expediente> expedientes) {
        this.expedientes = expedientes;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    // Métodos de conveniencia para manejar la relación bidireccional con Expediente
    public void addExpediente(Expediente expediente) {
        expedientes.add(expediente);
        expediente.setMascota(this);
    }

    public void removeExpediente(Expediente expediente) {
        expedientes.remove(expediente);
        expediente.setMascota(null);
    }

    // Métodos de conveniencia para manejar la relación bidireccional con Cita
    public void addCita(Cita cita) {
        citas.add(cita);
        cita.setMascota(this);
    }

    public void removeCita(Cita cita) {
        citas.remove(cita);
        cita.setMascota(null);
    }
}
