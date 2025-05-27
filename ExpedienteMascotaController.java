
package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.util.List;

public class ExpedienteMascotaController {
    @FXML private Label lblNombreMascota;
    @FXML private Label lblPropietario;
    @FXML private TableView<Consulta> tablaCitas;
    @FXML private TableColumn<Consulta, String> colFecha;
    @FXML private TableColumn<Consulta, String> colHora;
    @FXML private TableColumn<Consulta, String> colServicio;
    @FXML private Button btnAbrirConsulta;

    private Animal mascota;
    private List<Consulta> citas;
    private HelloController helloController;

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void setMascota(Animal mascota) {
        this.mascota = mascota;
        cargarDatosMascota();
    }

    public void setCitas(List<Consulta> citas) {
        this.citas = citas;
        cargarCitas();
    }

    private void cargarDatosMascota() {
        lblNombreMascota.setText("Mascota: " + mascota.getNombre());
        lblPropietario.setText("Propietario: " + mascota.getPropietario());
    }

    private void cargarCitas() {
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colServicio.setCellValueFactory(new PropertyValueFactory<>("tipoServicio"));

        tablaCitas.getItems().setAll(citas);

        // Habilitar el botón solo cuando se seleccione una cita
        btnAbrirConsulta.disableProperty().bind(
                tablaCitas.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    @FXML
    private void abrirConsulta() {
        Consulta consultaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();
        if (consultaSeleccionada != null && mascota != null) {
            mostrarDetallesConsulta(mascota, consultaSeleccionada);
        }
    }

    private void mostrarDetallesConsulta(Animal mascota, Consulta consulta) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles de la Consulta");
        alert.setHeaderText("Consulta del " + consulta.getFecha() + " a las " + consulta.getHora());

        String contenido = "Mascota: " + mascota.getNombre() + "\n" +
                "Propietario: " + mascota.getPropietario() + "\n" +
                "Tipo: " + mascota.getTipoAnimal() + "\n" +  // Cambiado de getTipo() a getTipoAnimal()
                "Sexo: " + mascota.getSexo() + "\n" +
                "Peso: " + mascota.getPeso() + " kg\n" +
                "Temperatura: " + mascota.getTemperatura() + " °C\n" +
                "Servicio: " + consulta.getTipoServicio() + "\n" +
                "Fecha: " + consulta.getFecha() + "\n" +
                "Hora: " + consulta.getHora();

        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    private void cerrarVentana() {
        ((Stage) lblNombreMascota.getScene().getWindow()).close();
    }
}