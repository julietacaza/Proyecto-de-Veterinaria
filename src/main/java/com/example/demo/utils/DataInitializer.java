package com.example.demo.utils;

import com.example.demo.models.Servicio;
import com.example.demo.services.ServicioService;

public class DataInitializer {
    public static void initialize() {
        ServicioService servicioService = new ServicioService();

        if (servicioService.obtenerTodosLosServicios().isEmpty()) {
            String[] Servicios = {"Vacunación", "Desparasitación", "Consulta general", "Cirugía"};

            for (String nombreServicio : Servicios) {
                 Servicio servicio = new Servicio(nombreServicio);
                servicioService.guardarServicio(servicio);
            }
        }
    }
}

