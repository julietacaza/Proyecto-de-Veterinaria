package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Consulta {
    private final StringProperty noCita;
    private final StringProperty propietario;
    private final StringProperty nomMascota;
    private final StringProperty tipoMascota;
    private final StringProperty tipoServicio;
    private final StringProperty fecha;
    private final StringProperty hora;

    public Consulta(String propietario, String nomMascota, String fecha, String tipoServicio, String tipoMascota) {
        this.noCita = new SimpleStringProperty("CITA-" + (int)(Math.random() * 1000));
        this.propietario = new SimpleStringProperty(propietario);
        this.nomMascota = new SimpleStringProperty(nomMascota);
        this.tipoMascota = new SimpleStringProperty(tipoMascota);
        this.tipoServicio = new SimpleStringProperty(tipoServicio);
        this.fecha = new SimpleStringProperty(fecha);
        this.hora = new SimpleStringProperty("");
    }

    // Getters para propiedades
    public String getNoCita() { return noCita.get(); }
    public String getPropietario() { return propietario.get(); }
    public String getNomMascota() { return nomMascota.get(); }
    public String getTipoMascota() { return tipoMascota.get(); }
    public String getTipoServicio() { return tipoServicio.get(); }
    public String getFecha() { return fecha.get(); }
    public String getHora() { return hora.get(); }

    // Setters
    public void setHora(String hora) { this.hora.set(hora); }

    // Property getters
    public StringProperty noCitaProperty() { return noCita; }
    public StringProperty propietarioProperty() { return propietario; }
    public StringProperty nomMascotaProperty() { return nomMascota; }
    public StringProperty tipoMascotaProperty() { return tipoMascota; }
    public StringProperty tipoServicioProperty() { return tipoServicio; }
    public StringProperty fechaProperty() { return fecha; }
    public StringProperty horaProperty() { return hora; }
}


