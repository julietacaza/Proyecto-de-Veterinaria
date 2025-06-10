package com.example.demo;

public class Mascota {
    private String nombre;
    private String tipoAnimal;
    private String sexo;
    private double peso;
    private double temperatura;
    private String servicio;
    private String propietario; // Añade este campo

    public Mascota(String nombre, String tipoAnimal, String sexo,
                      double peso, double temperatura, String servicio, String propietario) {
        this.nombre = nombre;
        this.tipoAnimal = tipoAnimal;
        this.sexo = sexo;
        this.peso = peso;
        this.temperatura = temperatura;
        this.servicio = servicio;
        this.propietario = propietario; // Añade esta línea
    }

    // Getters (añade el getter para propietario)
    public String getNombre() { return nombre; }
    public String getTipoAnimal() { return tipoAnimal; }
    public String getSexo() { return sexo; }
    public double getPeso() { return peso; }
    public double getTemperatura() { return temperatura; }
    public String getServicio() { return servicio; }
    public String getPropietario() { return propietario; } // Nuevo getter
}
