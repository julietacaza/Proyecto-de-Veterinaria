package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Formulario {
    private HelloController helloController;
    private boolean modoEdicion = false;

    @FXML private TextField txtPropietario;
    @FXML private TextField txtNomMascota;
    @FXML private TextField txtNumTelefono;
    @FXML private ComboBox<String> cmbAnimal;
    @FXML private ComboBox<String> cmbServicio;
    @FXML private ComboBox<String> cmbDia;
    @FXML private ComboBox<String> cmbMes;
    @FXML private ComboBox<String> cmbAnio;
    @FXML private ComboBox<String> cmbHora;
    @FXML private ComboBox<MascotaInfo> cmbMascotasPropietario;

    private final List<String> meses = Arrays.asList(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    );

    private static class MascotaInfo {
        private String nombre;
        private String tipo;

        public MascotaInfo(String nombre, String tipo) {
            this.nombre = nombre;
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return nombre + " (" + tipo + ")";
        }

        public String getNombre() {
            return nombre;
        }

        public String getTipo() {
            return tipo;
        }
    }

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }

    public void cargarDatosParaEdicion(Consulta cita) {
        String[] partesFecha = cita.getFecha().split("/");
        String dia = partesFecha[0];
        String mes = String.valueOf(meses.get(Integer.parseInt(partesFecha[1]) - 1));
        String anio = partesFecha[2];

        txtPropietario.setText(cita.getPropietario());
        txtNomMascota.setText(cita.getNomMascota());
        cmbAnimal.setValue(cita.getTipoMascota());
        cmbServicio.setValue(cita.getTipoServicio());

        cmbMes.getSelectionModel().select(mes);
        cmbAnio.getSelectionModel().select(anio);
        cmbDia.getSelectionModel().select(dia);

        cmbHora.setValue(cita.getHora());
    }

    @FXML
    public void onBtnAceptarClick(ActionEvent actionEvent) {
        if (!validarCampos()) {
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación de " + (modoEdicion ? "Edición" : "Guardado"));
        confirmacion.setHeaderText("¿Deseas " + (modoEdicion ? "editar" : "guardar") + " esta cita?");

        String datos = String.format(
                "Propietario: %s\nMascota: %s\nTeléfono: %s\nAnimal: %s\nServicio: %s\nFecha: %s %s %s\nHora: %s",
                txtPropietario.getText(),
                txtNomMascota.getText(),
                txtNumTelefono.getText(),
                cmbAnimal.getValue(),
                cmbServicio.getValue(),
                cmbDia.getValue(), cmbMes.getValue(), cmbAnio.getValue(),
                cmbHora.getValue()
        );
        confirmacion.setContentText("Verifica los datos ingresados:\n\n" + datos);

        ButtonType botonSi = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == botonSi) {
            if (guardarCita()) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                        modoEdicion ? "Cita editada" : "Cita guardada",
                        "La cita se ha " + (modoEdicion ? "editado" : "registrado") + " correctamente.");
                cerrarVentana(actionEvent);
            }
        }
    }

    private boolean validarCampos() {
        if (txtPropietario.getText().isEmpty() ||
                txtNomMascota.getText().isEmpty() ||
                txtNumTelefono.getText().isEmpty() ||
                cmbAnimal.getValue() == null ||
                cmbServicio.getValue() == null ||
                cmbDia.getValue() == null ||
                cmbMes.getValue() == null ||
                cmbAnio.getValue() == null ||
                cmbHora.getValue() == null) {

            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campos vacíos",
                    "Por favor, complete todos los campos antes de continuar.");
            return false;
        }
        return true;
    }

    private boolean guardarCita() {
        String fecha = String.format("%s/%s/%s",
                cmbDia.getValue(),
                (meses.indexOf(cmbMes.getValue()) + 1),
                cmbAnio.getValue());

        Consulta nuevaConsulta = new Consulta(
                txtPropietario.getText(),
                txtNomMascota.getText(),
                fecha,
                cmbServicio.getValue(),
                cmbAnimal.getValue()
        );
        nuevaConsulta.setHora(cmbHora.getValue());

        if (helloController != null) {
            boolean guardadoExitoso = helloController.agregarCita(nuevaConsulta, modoEdicion);
            if (!guardadoExitoso) {
                mostrarAlerta(Alert.AlertType.WARNING, "Conflicto de Horario",
                        "Ya existe una cita programada en esa fecha y hora",
                        "Por favor, seleccione una fecha y hora diferente.");
                return false;
            }
        }
        return true;
    }

    @FXML
    public void onBtnCancelarClick(ActionEvent actionEvent) {
        cerrarVentana(actionEvent);
    }

    @FXML
    public void onBtnRegresarClick(ActionEvent actionEvent) {
        cerrarVentana(actionEvent);
    }

    @FXML
    public void initialize() {
        cmbAnimal.getItems().addAll("Gato", "Perro", "Conejo");
        cmbServicio.getItems().addAll("Vacunación", "Desparasitación", "Consulta general", "Cirugía");
        cmbMes.getItems().addAll(meses);

        for (int i = 2025; i <= 2040; i++) {
            cmbAnio.getItems().add(String.valueOf(i));
        }

        cmbHora.getItems().addAll(
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
        );

        txtNumTelefono.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));

        cmbMes.valueProperty().addListener((observable, oldValue, newValue) -> actualizarDias());
        cmbAnio.valueProperty().addListener((observable, oldValue, newValue) -> actualizarDias());

        txtPropietario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty() && helloController != null) {
                List<Consulta> citasPropietario = helloController.getCitas().stream()
                        .filter(c -> c.getPropietario().equalsIgnoreCase(newValue))
                        .collect(Collectors.toList());

                cmbMascotasPropietario.getItems().clear();
                Set<String> nombresMascotas = new HashSet<>();

                citasPropietario.forEach(c -> {
                    if (!nombresMascotas.contains(c.getNomMascota().toLowerCase())) {
                        cmbMascotasPropietario.getItems().add(
                                new MascotaInfo(c.getNomMascota(), c.getTipoMascota()));
                        nombresMascotas.add(c.getNomMascota().toLowerCase());
                    }
                });

                if (!cmbMascotasPropietario.getItems().isEmpty()) {
                    cmbMascotasPropietario.show();
                } else {
                    cmbMascotasPropietario.hide();
                }
            } else {
                cmbMascotasPropietario.hide();
            }
        });

        cmbMascotasPropietario.setOnAction(event -> {
            MascotaInfo seleccionada = cmbMascotasPropietario.getValue();
            if (seleccionada != null) {
                txtNomMascota.setText(seleccionada.getNombre());
                cmbAnimal.setValue(seleccionada.getTipo());
            }
        });
    }

    private void actualizarDias() {
        cmbDia.getItems().clear();

        if (cmbMes.getValue() != null && cmbAnio.getValue() != null) {
            int diasDelMes = obtenerDiasDelMes(cmbMes.getValue(), Integer.parseInt(cmbAnio.getValue()));
            for (int i = 1; i <= diasDelMes; i++) {
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
                return esBisiesto(anio) ? 29 : 28;
            default:
                return 0;
        }
    }

    private boolean esBisiesto(int anio) {
        return (anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0));
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}