package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Expediente implements Initializable {

    @FXML private TextField txtPropietario;
    @FXML private TextField txtNombreMascota;
    @FXML private ComboBox<String> cmbDia;
    @FXML private ComboBox<String> cmbMes;
    @FXML private ComboBox<String> cmbAnio;
    @FXML private TextField txtPeso;
    @FXML private ComboBox<String> cmbSexo;
    @FXML private TextField txtTemperatura;
    @FXML private ComboBox<String> cmbAnimal;
    @FXML private ComboBox<String> cmbServicio;
    @FXML private ComboBox<String> cmbHora;
    @FXML private ComboBox<String> cmbMascotasPropietario; // Cambiado a ComboBox<String>

    private final List<String> meses = Arrays.asList(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    );

    private HelloController helloController;

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    @FXML
    public void onBtnOkClick(ActionEvent actionEvent) {
        if (!validarCampos()) {
            return;
        }

        String propietario = txtPropietario.getText();
        String nombreMascota = txtNombreMascota.getText();
        String tipoAnimal = cmbAnimal.getValue();
        String sexo = cmbSexo.getValue();
        double peso = Double.parseDouble(txtPeso.getText());
        double temperatura = Double.parseDouble(txtTemperatura.getText());
        String servicio = cmbServicio.getValue();
        String fecha = String.format("%s/%s/%s", cmbDia.getValue(), cmbMes.getValue(), cmbAnio.getValue());
        String hora = cmbHora.getValue();

        boolean esMascotaExistente = cmbMascotasPropietario.getValue() != null &&
                cmbMascotasPropietario.getValue().contains(nombreMascota);

        Animal nuevoAnimal = new Animal(
                nombreMascota,
                tipoAnimal,
                sexo,
                peso,
                temperatura,
                servicio,
                propietario
        );

        Consulta nuevaConsulta = new Consulta(propietario, nombreMascota, fecha, servicio, tipoAnimal);
        nuevaConsulta.setHora(hora);
        nuevoAnimal.agregarConsulta(nuevaConsulta);

        if (helloController != null) {
            boolean guardadoExitoso = helloController.agregarAnimal(nuevoAnimal);
            if (guardadoExitoso) {
                helloController.agregarCita(nuevaConsulta, false);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                        "Expediente guardado", "El expediente se ha registrado correctamente.");
                limpiarFormulario();
            }
        }
    }

    @FXML
    public void onBtnCancelarClick(ActionEvent actionEvent) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText("¿Deseas cancelar y salir?");
        confirmacion.setContentText("Los datos no guardados se perderán.");

        ButtonType botonSi = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == botonSi) {
            cerrarVentana(actionEvent);
        }
    }

    private void cerrarVentana(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean validarCampos() {
        if (txtPropietario.getText().isEmpty() ||
                txtNombreMascota.getText().isEmpty() ||
                cmbDia.getValue() == null ||
                cmbMes.getValue() == null ||
                cmbAnio.getValue() == null ||
                txtPeso.getText().isEmpty() ||
                cmbSexo.getValue() == null ||
                txtTemperatura.getText().isEmpty() ||
                cmbAnimal.getValue() == null ||
                cmbServicio.getValue() == null ||
                cmbHora.getValue() == null) {

            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Campos vacíos",
                    "Por favor, complete todos los campos antes de continuar.");
            return false;
        }

        try {
            Double.parseDouble(txtPeso.getText());
            Double.parseDouble(txtTemperatura.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Valores inválidos",
                    "El peso y la temperatura deben ser valores numéricos.");
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        txtPropietario.clear();
        txtNombreMascota.clear();
        cmbDia.getSelectionModel().clearSelection();
        cmbMes.getSelectionModel().clearSelection();
        cmbAnio.getSelectionModel().clearSelection();
        txtPeso.clear();
        cmbSexo.getSelectionModel().clearSelection();
        txtTemperatura.clear();
        cmbAnimal.getSelectionModel().clearSelection();
        cmbServicio.getSelectionModel().clearSelection();
        cmbHora.getSelectionModel().clearSelection();
        cmbMascotasPropietario.getItems().clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbAnimal.getItems().addAll("Gato", "Perro", "Conejo");
        cmbServicio.getItems().addAll("Vacunación", "Desparasitación", "Consulta general", "Cirugía");
        cmbSexo.getItems().addAll("Macho", "Hembra");
        cmbMes.getItems().addAll(meses);

        cmbHora.getItems().addAll(
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
        );

        for (int i = 2025; i <= 2040; i++) {
            cmbAnio.getItems().add(String.valueOf(i));
        }

        txtPeso.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9.]*")) {
                return change;
            }
            return null;
        }));

        txtTemperatura.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9.]*")) {
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
                Set<String> mascotasUnicas = new HashSet<>();

                citasPropietario.forEach(c -> {
                    String mascotaInfo = c.getNomMascota() + " (" + c.getTipoMascota() + ")";
                    if (!mascotasUnicas.contains(mascotaInfo.toLowerCase())) {
                        cmbMascotasPropietario.getItems().add(mascotaInfo);
                        mascotasUnicas.add(mascotaInfo.toLowerCase());
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
            String seleccion = cmbMascotasPropietario.getValue();
            if (seleccion != null) {
                String nombreMascota = seleccion.split("\\(")[0].trim();
                String tipoMascota = seleccion.split("\\(")[1].replace(")", "").trim();

                txtNombreMascota.setText(nombreMascota);
                cmbAnimal.setValue(tipoMascota);

                // Buscar el sexo en los animales registrados
                if (helloController != null) {
                    Optional<Animal> animal = helloController.getAnimales().stream()
                            .filter(a -> a.getNombre().equalsIgnoreCase(nombreMascota) &&
                                    a.getPropietario().equalsIgnoreCase(txtPropietario.getText()))
                            .findFirst();

                    if (animal.isPresent()) {
                        cmbSexo.setValue(animal.get().getSexo());
                    }
                }
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
}