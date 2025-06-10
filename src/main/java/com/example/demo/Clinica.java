package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Clinica {

    @FXML
    private TextField txtBuscarMascota;
    @FXML
    private ListView<String> listViewHistorial;

    private List<Animal> animales; // Lista de animales

    // Constructor
    public Clinica() {
        this.animales = new ArrayList<>(); // Lista vacía inicialmente
    }

    @FXML
    public void initialize() {
        // Limpiar la lista al inicio
        listViewHistorial.getItems().clear();

        // Cambios para realizar búsqueda incremental
        txtBuscarMascota.textProperty().addListener((observable, oldValue, newValue) -> actualizarListaMascotas(newValue));
    }

    // Método que actualiza la lista de animales en tiempo real según la búsqueda
    private void actualizarListaMascotas(String textoBusqueda) {
        // Limpiar la lista antes de actualizarla
        listViewHistorial.getItems().clear();

        // Si no hay texto de búsqueda, no mostrar nada
        if (textoBusqueda.isEmpty()) {
            return;
        }

        // Filtrar los animales cuyo nombre empiece con las iniciales del texto ingresado
        List<String> animalesFiltrados = animales.stream()
                .filter(animal -> animal.getNombre().toLowerCase().startsWith(textoBusqueda.toLowerCase()))
                .map(Animal::getNombre)
                .collect(Collectors.toList());

        // Si hay coincidencias, se muestran en el ListView
        if (!animalesFiltrados.isEmpty()) {
            listViewHistorial.getItems().addAll(animalesFiltrados);
        } else {
            // Si no se encuentran coincidencias, se muestra un mensaje
            listViewHistorial.getItems().add("No se encontraron animales con esas iniciales.");
        }
    }

    // Método para realizar una búsqueda de consultas para el animal cuando se presiona el botón de búsqueda
    @FXML
    public void onBuscarHistorialClick(ActionEvent actionEvent) {
        String nombreAnimal = txtBuscarMascota.getText().trim();

        if (nombreAnimal.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Campo vacío");
            alerta.setContentText("Por favor, ingrese el nombre del animal.");
            alerta.showAndWait();
            return;
        }

        Animal animal = buscarAnimal(nombreAnimal);

        if (animal == null) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Sin historial");
            alerta.setHeaderText("No se encontraron consultas");
            alerta.setContentText("No se encontraron consultas para el animal \"" + nombreAnimal + "\".");
            alerta.showAndWait();
            return;
        }

        listViewHistorial.getItems().clear();
        // Cambio aquí: usar getHistorialConsultas() en lugar de obtenerHistorialConsultas()
        for (Consulta consulta : animal.getHistorialConsultas()) {
            listViewHistorial.getItems().add(consulta.toString());
        }
    }

    // Método para agregar un nuevo animal al sistema
    public void agregarAnimal(String nombreAnimal) {
        Animal nuevoAnimal = new Animal(nombreAnimal);
        animales.add(nuevoAnimal); // Agrega el nuevo animal a la lista
    }

    // Buscar un animal por nombre
    private Animal buscarAnimal(String nombre) {
        return animales.stream()
                .filter(animal -> animal.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null); // Retorna null si no encuentra el animal
    }
}