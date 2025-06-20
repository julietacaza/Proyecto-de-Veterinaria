package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "propietario")
public class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPropietario")
    private Integer id;

    @Column(name = "Nombre_del_propietario")
    private String NombrePropietario;

    @Column(name = "Numero")
    private String numero;

    public Propietario() {
    }

    public Propietario(String NombrePropietario, String numero) {
        this.NombrePropietario = NombrePropietario;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrePropietario() {
        return NombrePropietario;
    }

    public void setNombrePropietario(String NombrePropietario) {
        this.NombrePropietario = NombrePropietario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
