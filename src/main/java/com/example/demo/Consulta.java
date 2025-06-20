package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Consulta {
    private final StringProperty noConsulta = new SimpleStringProperty();
    private final StringProperty propietario = new SimpleStringProperty();
    private final StringProperty nomMascota = new SimpleStringProperty();
    private final StringProperty tipoMascota = new SimpleStringProperty();
    private final StringProperty tipoServicio = new SimpleStringProperty();
    private final StringProperty fecha = new SimpleStringProperty();
    private final StringProperty hora = new SimpleStringProperty();
    private LocalDate fechaReal;
    private LocalTime horaReal;
    private String observaciones;

    public Consulta() {
    }

    public Consulta(String propietario, String nomMascota, String fecha,
                    String tipoServicio, String tipoMascota) {
        this.noConsulta.set("CONS-" + (int) (Math.random() * 1000));
        this.propietario.set(propietario);
        this.nomMascota.set(nomMascota);
        this.tipoMascota.set(tipoMascota);
        this.tipoServicio.set(tipoServicio);
        this.fecha.set(fecha);
        this.hora.set("");

        // Parsear fecha para almacenamiento
        String[] partes = fecha.split("/");
        this.fechaReal = LocalDate.of(
                Integer.parseInt(partes[2]),
                Integer.parseInt(partes[1]),
                Integer.parseInt(partes[0])
        );
    }

    // Getters y setters básicos
    public String getNoConsulta() {
        return noConsulta.get();
    }

    public String getPropietario() {
        return propietario.get();
    }

    public String getNomMascota() {
        return nomMascota.get();
    }

    public String getTipoMascota() {
        return tipoMascota.get();
    }

    public String getTipoServicio() {
        return tipoServicio.get();
    }

    public String getFecha() {
        return fecha.get();
    }

    public String getHora() {
        return hora.get();
    }

    public LocalDate getFechaReal() {
        return fechaReal;
    }

    public LocalTime getHoraReal() {
        return horaReal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setHora(String hora) {
        this.hora.set(hora);
        this.horaReal = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Property getters para JavaFX
    public StringProperty noConsultaProperty() {
        return noConsulta;
    }

    public StringProperty propietarioProperty() {
        return propietario;
    }

    public StringProperty nomMascotaProperty() {
        return nomMascota;
    }

    public StringProperty tipoMascotaProperty() {
        return tipoMascota;
    }

    public StringProperty tipoServicioProperty() {
        return tipoServicio;
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public StringProperty horaProperty() {
        return hora;
    }

    @Override
    public String toString() {
        return "Consulta #" + noConsulta.get() + " - " + nomMascota.get() +
                " (" + tipoMascota.get() + ") - " + fecha.get() + " " + hora.get();
    }
}