package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Arrays;
import java.util.List;

public class ExpedienteController {
    @FXML
    private TextField txtPropietario;
    @FXML
    private ComboBox<String> cmbMascotasPropietario;
    @FXML
    private TextField txtNombreMascota;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtTemperatura;
    @FXML
    private ComboBox<String> cmbSexo;
    @FXML
    private ComboBox<String> cmbAnimal;
    @FXML
    private ComboBox<String> cmbServicio;
    @FXML
    private ComboBox<String> cmbDia;
    @FXML
    private ComboBox<String> cmbMes;
    @FXML
    private ComboBox<String> cmbAnio;
    @FXML
    private ComboBox<String> cmbHora;

    private HelloController helloController;
    private Consulta consultaActual;

    private final List<String> meses = Arrays.asList(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    );

    @FXML
    private void initialize() {
        // Inicialización de comboboxes
        cmbSexo.getItems().addAll("Macho", "Hembra");
        cmbAnimal.getItems().addAll("Perro", "Gato", "Conejo");
        cmbServicio.getItems().addAll("Consulta", "Vacunación", "Cirugía", "Desparasitación");
        cmbMes.getItems().addAll(meses);

        for (int i = 2025; i <= 2040; i++) {
            cmbAnio.getItems().add(String.valueOf(i));
        }


        // Configurar horas
        cmbHora.getItems().addAll(
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
        );

        // Listeners para actualizar días cuando cambia mes/año
        cmbMes.valueProperty().addListener((obs, oldVal, newVal) -> actualizarDias());
        cmbAnio.valueProperty().addListener((obs, oldVal, newVal) -> actualizarDias());
    }

    private void actualizarDias() {
        cmbDia.getItems().clear();
        if (cmbMes.getValue() != null && cmbAnio.getValue() != null) {
            int dias = obtenerDiasDelMes(cmbMes.getValue(), Integer.parseInt(cmbAnio.getValue()));
            for (int i = 1; i <= dias; i++) {
                cmbDia.getItems().add(String.format("%02d", i));
            }
        }
    }

    private int obtenerDiasDelMes(String mes, int anio) {
        switch (mes) {
            case "Enero":
            case "Marzo":
            case "Mayo":
            case "Julio":
            case "Agosto":
            case "Octubre":
            case "Diciembre":
                return 31;
            case "Abril":
            case "Junio":
            case "Septiembre":
            case "Noviembre":
                return 30;
            case "Febrero":
                return (anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0)) ? 29 : 28;
            default:
                return 31;
        }
    }

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void setConsulta(Consulta consulta) {
        this.consultaActual = consulta;
        cargarDatosConsulta();
    }

    private void cargarDatosConsulta() {
        if (consultaActual != null) {
            txtPropietario.setText(consultaActual.getPropietario());
            txtNombreMascota.setText(consultaActual.getNomMascota());
            cmbAnimal.setValue(consultaActual.getTipoMascota());
            cmbServicio.setValue(consultaActual.getTipoServicio());

            String[] fechaParts = consultaActual.getFecha().split("/");
            if (fechaParts.length == 3) {
                cmbDia.setValue(fechaParts[0]);
                cmbMes.setValue(meses.get(Integer.parseInt(fechaParts[1]) - 1));
                cmbAnio.setValue(fechaParts[2]);
            }

            if (consultaActual.getHora() != null) {
                cmbHora.setValue(consultaActual.getHora());
            }
        }
    }

    @FXML
    private void onBtnOkClick() {
        if (!validarCampos()) {
            return;
        }

        Consulta consulta = consultaActual != null ? consultaActual : new Consulta();

        consulta.propietarioProperty().set(txtPropietario.getText());
        consulta.nomMascotaProperty().set(txtNombreMascota.getText());
        consulta.tipoMascotaProperty().set(cmbAnimal.getValue());
        consulta.tipoServicioProperty().set(cmbServicio.getValue());

        String fecha = String.format("%s/%d/%s",
                cmbDia.getValue(),
                meses.indexOf(cmbMes.getValue()) + 1,
                cmbAnio.getValue());

        consulta.fechaProperty().set(fecha);

        if (cmbHora.getValue() != null) {
            consulta.horaProperty().set(cmbHora.getValue());
        }

        if (helloController != null) {
            boolean resultado = helloController.agregarCita(consulta, consultaActual != null);
            if (resultado) {
                cerrarVentana();
            }
        }
    }

    private boolean validarCampos() {
        if (txtPropietario.getText().isEmpty() ||
                txtNombreMascota.getText().isEmpty() ||
                cmbAnimal.getValue() == null ||
                cmbServicio.getValue() == null ||
                cmbDia.getValue() == null ||
                cmbMes.getValue() == null ||
                cmbAnio.getValue() == null) {

            mostrarAlerta("Error", "Campos requeridos", "Por favor complete todos los campos obligatorios");
            return false;
        }
        return true;
    }

    @FXML
    private void onBtnCancelarClick() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        txtPropietario.getScene().getWindow().hide();
    }

    private void mostrarAlerta(String titulo, String header, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
