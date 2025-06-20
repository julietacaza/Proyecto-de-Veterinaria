package com.example.demo;

import com.example.demo.Consulta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.util.List;
import java.util.stream.Collectors;

public class ExpedienteMascotaController {
    @FXML
    private Label lblNombreMascota;
    @FXML
    private Label lblPropietario;
    @FXML
    private TableView<Consulta> tablaCitas;
    @FXML
    private TableColumn<Consulta, String> colFecha;
    @FXML
    private TableColumn<Consulta, String> colHora;
    @FXML
    private TableColumn<Consulta, String> colServicio;
    @FXML
    private Button btnAbrirConsulta;

    private Mascota mascotaSeleccionada;
    private List<Consulta> citas;
    private HelloController helloController;

    public Mascota getMascotaSeleccionada() {
        return mascotaSeleccionada;
    }

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void setMascota(Mascota mascota) {
        this.mascotaSeleccionada = mascota;
        cargarDatosMascota();
        if (helloController != null) {
            List<Consulta> citasMascota = helloController.getCitas().stream()
                    .filter(c -> c.getNomMascota().equals(mascota.getNombre()) &&
                            c.getPropietario().equals(mascota.getPropietario()))
                    .collect(Collectors.toList());
            setCitas(citasMascota);
        }
    }

    public void setCitas(List<Consulta> citas) {
        this.citas = citas;
        cargarCitas();
    }

    private void cargarDatosMascota() {
        if (mascotaSeleccionada != null) {
            lblNombreMascota.setText("Mascota: " + mascotaSeleccionada.getNombre());
            lblPropietario.setText("Propietario: " + mascotaSeleccionada.getPropietario());
        }
    }

    private void cargarCitas() {
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colServicio.setCellValueFactory(new PropertyValueFactory<>("tipoServicio"));

        if (citas != null && !citas.isEmpty()) {
            tablaCitas.getItems().setAll(citas);
        } else {
            tablaCitas.getItems().clear();
        }

        btnAbrirConsulta.setDisable(tablaCitas.getSelectionModel().getSelectedItem() == null);
        tablaCitas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                        btnAbrirConsulta.setDisable(newValue == null)
        );
    }

    @FXML
    private void abrirConsulta() {
        if (citas == null || citas.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Consulta requerida");
            alerta.setHeaderText("No se puede crear una cita");
            alerta.setContentText("Debe realizarse al menos una consulta antes de agendar una cita.");
            alerta.showAndWait();
            return;
        }

        Consulta consultaSeleccionada = tablaCitas.getSelectionModel().getSelectedItem();
        if (consultaSeleccionada != null && mascotaSeleccionada != null) {
            mostrarDetallesConsulta(mascotaSeleccionada, consultaSeleccionada);
        }
    }

    private void mostrarDetallesConsulta(Mascota mascota, Consulta consulta) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles de la Consulta");
        alert.setHeaderText("Consulta del " + consulta.getFecha() + " a las " + consulta.getHora());

        StringBuilder contenido = new StringBuilder();
        contenido.append("Mascota: ").append(mascota.getNombre()).append("\n")
                .append("Propietario: ").append(mascota.getPropietario()).append("\n")
                .append("Tipo: ").append(mascota.getTipoAnimal()).append("\n")
                .append("Sexo: ").append(mascota.getSexo()).append("\n")
                .append("Peso: ").append(mascota.getPeso()).append(" kg\n")
                .append("Temperatura: ").append(mascota.getTemperatura()).append(" °C\n")
                .append("Servicio: ").append(consulta.getTipoServicio()).append("\n")
                .append("Fecha: ").append(consulta.getFecha()).append("\n")
                .append("Hora: ").append(consulta.getHora());

        alert.setContentText(contenido.toString());
        alert.showAndWait();
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) lblNombreMascota.getScene().getWindow();
        stage.close();
    }
}