package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Clinics {

    @FXML
    private TextField txtBuscarMascota;
    @FXML
    private ListView<String> listViewHistorial;

    private List<Mascota> mascotas;

    // Constructor
    public Clinics() {
        this.mascotas = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        listViewHistorial.getItems().clear();
        txtBuscarMascota.textProperty().addListener((observable, oldValue, newValue) -> actualizarListaMascotas(newValue));
    }

    private void actualizarListaMascotas(String textoBusqueda) {
        listViewHistorial.getItems().clear();

        if (textoBusqueda.isEmpty()) {
            return;
        }

        List<String> mascotasFiltradas = mascotas.stream()
                .filter(mascota -> mascota.getNombre().toLowerCase().startsWith(textoBusqueda.toLowerCase()))
                .map(Mascota::getNombre)
                .collect(Collectors.toList());

        if (!mascotasFiltradas.isEmpty()) {
            listViewHistorial.getItems().addAll(mascotasFiltradas);
        } else {
            listViewHistorial.getItems().add("No se encontraron mascotas con esas iniciales.");
        }
    }

    @FXML
    public void onBuscarHistorialClick(ActionEvent actionEvent) {
        String nombreMascota = txtBuscarMascota.getText().trim();

        if (nombreMascota.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Campo vacío", "Por favor, ingrese el nombre de la mascota.");
            return;
        }

        Mascota mascota = buscarMascota(nombreMascota);

        if (mascota == null) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sin historial", "No se encontraron consultas",
                    "No se encontraron consultas para la mascota \"" + nombreMascota + "\".");
            return;
        }

        listViewHistorial.getItems().clear();
        // Aquí puedes agregar lógica para mostrar el historial de la mascota encontrada
    }

    public void agregarMascota(Mascota nuevaMascota) {
        mascotas.add(nuevaMascota);
    }

    private Mascota buscarMascota(String nombre) {
        return mascotas.stream()
                .filter(mascota -> mascota.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}