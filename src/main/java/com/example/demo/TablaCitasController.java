package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

public class TablaCitasController {
    @FXML private TableView<Consulta> tablaCitas;
    @FXML private TableColumn<Consulta, String> colNoCita;
    @FXML private TableColumn<Consulta, String> colPropietario;
    @FXML private TableColumn<Consulta, String> colNomMascota;
    @FXML private TableColumn<Consulta, String> colTipoMascota;
    @FXML private TableColumn<Consulta, String> colTipoServicio;
    @FXML private TableColumn<Consulta, String> colFecha;
    @FXML private TableColumn<Consulta, String> colHora;
    @FXML private TextField txtBuscar;
    @FXML private Button btnEditar;

    private List<Consulta> todasLasCitas;
    private HelloController helloController;

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    /**
     * Recibe únicamente las citas provenientes del formulario de citas.
     */

    public void setCitas(List<Consulta> citas) {
        this.todasLasCitas = citas;
        configurarColumnas();
        actualizarTabla(citas);

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarCitas(newValue);
        });

        btnEditar.disableProperty().bind(tablaCitas.getSelectionModel().selectedItemProperty().isNull());
    }

    private void filtrarCitas(String textoBusqueda) {
        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            tablaCitas.getItems().setAll(todasLasCitas);
            return;
        }

        List<Consulta> citasFiltradas = todasLasCitas.stream()
                .filter(consulta ->
                        consulta.getPropietario().toLowerCase().startsWith(textoBusqueda.toLowerCase()) ||
                                consulta.getNomMascota().toLowerCase().startsWith(textoBusqueda.toLowerCase()))
                .collect(Collectors.toList());

        tablaCitas.getItems().setAll(citasFiltradas);
    }

    public void actualizarTabla(List<Consulta> nuevasCitas) {
        this.todasLasCitas = nuevasCitas;
        tablaCitas.getItems().setAll(nuevasCitas);
    }

    private void configurarColumnas() {
        colNoCita.setCellValueFactory(new PropertyValueFactory<>("noCita"));
        colPropietario.setCellValueFactory(new PropertyValueFactory<>("propietario"));
        colNomMascota.setCellValueFactory(new PropertyValueFactory<>("nomMascota"));
        colTipoMascota.setCellValueFactory(new PropertyValueFactory<>("tipoMascota"));
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<>("tipoServicio"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
    }

    @FXML
    private void editarCita() {
        Consulta citaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();
        if (citaSeleccionada != null && helloController != null) {
            helloController.abrirFormularioParaEdicion(citaSeleccionada);
        }
    }

    @FXML
    private void cerrarVentana() {
        ((Stage) tablaCitas.getScene().getWindow()).close();
    }
}
