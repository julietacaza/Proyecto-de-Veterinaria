package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    private String nombre;
    private String tipoAnimal;
    private String sexo;
    private double peso;
    private double temperatura;
    private String servicio;
    private String propietario;
    private List<Consulta> historialConsultas;

    // Constructor completo
    public Animal(String nombre, String tipoAnimal, String sexo,
                  double peso, double temperatura, String servicio, String propietario) {
        this.nombre = nombre;
        this.tipoAnimal = tipoAnimal;
        this.sexo = sexo;
        this.peso = peso;
        this.temperatura = temperatura;
        this.servicio = servicio;
        this.propietario = propietario;
        this.historialConsultas = new ArrayList<>();
    }

    // Constructor simplificado para Clinica.java
    public Animal(String nombre) {
        this.nombre = nombre;
        this.historialConsultas = new ArrayList<>();
    }

    // --- Getters (requeridos para PropertyValueFactory en la tabla) ---
    public String getNombre() { return nombre; }
    public String getTipoAnimal() { return tipoAnimal; }
    public String getSexo() { return sexo; }
    public double getPeso() { return peso; }
    public double getTemperatura() { return temperatura; }
    public String getServicio() { return servicio; }
    public String getPropietario() { return propietario; }
    public List<Consulta> getHistorialConsultas() { return historialConsultas; }

    // --- Métodos adicionales ---
    public void agregarConsulta(Consulta consulta) {
        historialConsultas.add(consulta);
    }

    // (Opcional) Si necesitas setters para modificar datos:
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoAnimal(String tipoAnimal) { this.tipoAnimal = tipoAnimal; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }
    public void setServicio(String servicio) { this.servicio = servicio; }
    public void setPropietario(String propietario) { this.propietario = propietario; }
}
