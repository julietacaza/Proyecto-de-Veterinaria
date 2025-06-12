package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;

public class TablaMascotasController {
    @FXML private TableView<Animal> tablaMascotas;
    @FXML private TableColumn<Animal, String> colNombre;
    @FXML private TableColumn<Animal, String> colPropietario;
    @FXML private TableColumn<Animal, String> colTipo;
    @FXML private TextField txtBuscar;
    @FXML private Button btnVerExpediente;

    private HelloController helloController;
    private ObservableList<Animal> animalesObservable = FXCollections.observableArrayList();
    private FilteredList<Animal> animalesFiltrados;

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void setAnimales(List<Animal> animales) {
        this.animalesObservable.setAll(animales);
        this.animalesFiltrados = new FilteredList<>(animalesObservable, p -> true);
        tablaMascotas.setItems(animalesFiltrados);

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarMascotas(newValue);
        });
    }

    private void filtrarMascotas(String textoBusqueda) {
        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            animalesFiltrados.setPredicate(mascota -> true);
            return;
        }

        String textoBusquedaLower = textoBusqueda.toLowerCase();
        animalesFiltrados.setPredicate(mascota ->
                mascota.getNombre().toLowerCase().contains(textoBusquedaLower) ||
                        mascota.getPropietario().toLowerCase().contains(textoBusquedaLower) ||
                        mascota.getTipoAnimal().toLowerCase().contains(textoBusquedaLower)
        );
    }

    public void actualizarTabla(List<Animal> animales) {
        this.animalesObservable.setAll(animales);
        if (animalesFiltrados != null) {
            animalesFiltrados.setPredicate(null);
        }
    }

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPropietario.setCellValueFactory(new PropertyValueFactory<>("propietario"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoAnimal"));

        btnVerExpediente.disableProperty().bind(
                tablaMascotas.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    @FXML
    private void verExpediente() {
        Animal mascotaSeleccionada = tablaMascotas.getSelectionModel().getSelectedItem();
        if (mascotaSeleccionada != null && helloController != null) {
            helloController.mostrarExpedienteMascota(mascotaSeleccionada);
        }
    }

    @FXML
    private void cerrarVentana() {
        ((Stage) tablaMascotas.getScene().getWindow()).close();
    }
}