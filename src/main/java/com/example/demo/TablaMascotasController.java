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
    @FXML private TableView<Mascota> tablaMascotas;
    @FXML private TableColumn<Mascota, String> colNombre;
    @FXML private TableColumn<Mascota, String> colPropietario;
    @FXML private TableColumn<Mascota, String> colTipo;
    @FXML private TextField txtBuscar;
    @FXML private Button btnVerExpediente;

    private HelloController helloController;
    private ObservableList<Mascota> mascotasObservable = FXCollections.observableArrayList();
    private FilteredList<Mascota> mascotasFiltradas;

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotasObservable.setAll(mascotas);
        this.mascotasFiltradas = new FilteredList<>(mascotasObservable, p -> true);
        tablaMascotas.setItems(mascotasFiltradas);

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarMascotas(newValue);
        });
    }

    private void filtrarMascotas(String textoBusqueda) {
        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            mascotasFiltradas.setPredicate(mascota -> true);
            return;
        }

        String textoBusquedaLower = textoBusqueda.toLowerCase();
        mascotasFiltradas.setPredicate(mascota ->
                mascota.getNombre().toLowerCase().contains(textoBusquedaLower) ||
                        mascota.getPropietario().toLowerCase().contains(textoBusquedaLower) ||
                        mascota.getTipoAnimal().toLowerCase().contains(textoBusquedaLower)
        );
    }

    public void actualizarTabla(List<Mascota> mascotas) {
        this.mascotasObservable.setAll(mascotas);
        if (mascotasFiltradas != null) {
            mascotasFiltradas.setPredicate(null);
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
        Mascota mascotaSeleccionada = tablaMascotas.getSelectionModel().getSelectedItem();
        if (mascotaSeleccionada != null && helloController != null) {
            helloController.mostrarExpedienteMascota(mascotaSeleccionada);
        }
    }

    @FXML
    private void cerrarVentana() {
        ((Stage) tablaMascotas.getScene().getWindow()).close();
    }
}