package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    private List<Consulta> citas = new ArrayList<>();
    private List<Animal> animales = new ArrayList<>();
    private TablaCitasController tablaCitasController;
    private TablaMascotasController tablaMascotasController;
    private Stage tablaCitasStage;
    private Stage tablaMascotasStage;
    private Consulta citaEnEdicion;

    @FXML
    private void abrirFormulario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Formulario de Citas");

            Formulario controller = loader.getController();
            controller.setHelloController(this);
            controller.setModoEdicion(false);

            stage.show();
        } catch (IOException e) {
            mostrarError("Error al abrir formulario", e);
        }
    }

    @FXML
    private void abrirExpediente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Expediente.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Expediente Médico");

            Expediente controller = loader.getController();
            controller.setHelloController(this);

            stage.show();
        } catch (IOException e) {
            mostrarError("Error al abrir expediente", e);
        }
    }

    @FXML
    private void abrirTablaCitas() {
        abrirVentanaTablaCitas();
    }

    @FXML
    private void abrirTablaMascotas() {
        abrirVentanaTablaMascotas();
    }

    public void abrirVentanaTablaCitas() {
        try {
            if (tablaCitasStage != null && tablaCitasStage.isShowing()) {
                tablaCitasStage.toFront();
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("tabla-de-cita.fxml"));
            tablaCitasStage = new Stage();
            tablaCitasStage.setScene(new Scene(loader.load()));
            tablaCitasStage.setTitle("Tabla de Citas");

            tablaCitasController = loader.getController();
            tablaCitasController.setCitas(citas);
            tablaCitasController.setHelloController(this);

            tablaCitasStage.setOnHidden(e -> tablaCitasStage = null);
            tablaCitasStage.show();
        } catch (IOException e) {
            mostrarError("Error al abrir tabla de citas", e);
        }
    }

    public void abrirVentanaTablaMascotas() {
        try {
            if (tablaMascotasStage != null && tablaMascotasStage.isShowing()) {
                tablaMascotasStage.toFront();
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("tabla-mascota.fxml"));
            tablaMascotasStage = new Stage();
            tablaMascotasStage.setScene(new Scene(loader.load()));
            tablaMascotasStage.setTitle("Tabla de Animales");

            tablaMascotasController = loader.getController();
            tablaMascotasController.setAnimales(animales);
            tablaMascotasController.setHelloController(this);

            tablaMascotasStage.setOnHidden(e -> tablaMascotasStage = null);
            tablaMascotasStage.show();
        } catch (IOException e) {
            mostrarError("Error al abrir tabla de animales", e);
        }
    }

    public void abrirFormularioParaEdicion(Consulta cita) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Editar Cita");

            Formulario controller = loader.getController();
            controller.setHelloController(this);
            controller.cargarDatosParaEdicion(cita);
            controller.setModoEdicion(true);

            this.citaEnEdicion = cita;

            stage.show();
        } catch (IOException e) {
            mostrarError("Error al abrir formulario", e);
        }
    }

    public void mostrarExpedienteMascota(Animal mascota) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("expediente-mascota.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Expediente de " + mascota.getNombre());

            ExpedienteMascotaController controller = loader.getController();
            controller.setMascota(mascota);
            controller.setCitas(citas.stream()
                    .filter(c -> c.getNomMascota().equals(mascota.getNombre()))
                    .collect(Collectors.toList()));
            controller.setHelloController(this);

            stage.show();
        } catch (IOException e) {
            mostrarError("Error al abrir expediente", e);
        }
    }

    public boolean agregarCita(Consulta nuevaConsulta, boolean esEdicion) {
        if (esEdicion && citaEnEdicion != null) {
            citas.remove(citaEnEdicion);
            citaEnEdicion = null;
        }

        boolean existe = citas.stream().anyMatch(cita ->
                cita.getFecha().equals(nuevaConsulta.getFecha()) &&
                        cita.getHora().equals(nuevaConsulta.getHora())
        );

        if (existe) {
            mostrarAlerta("Conflicto de Horario", "Ya existe una cita programada en esa fecha y hora.");
            return false;
        }

        citas.add(nuevaConsulta);
        if (tablaCitasController != null) {
            tablaCitasController.actualizarTabla(citas);
        }
        return true;
    }

    public boolean agregarAnimal(Animal nuevoAnimal) {
        animales.add(nuevoAnimal);
        if (tablaMascotasController != null) {
            tablaMascotasController.actualizarTabla(animales);
        }
        return true;
    }

    private void mostrarError(String mensaje, Exception e) {
        System.err.println(mensaje);
        e.printStackTrace();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        javafx.application.Platform.runLater(() -> {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle(titulo);
            alerta.setHeaderText(null);
            alerta.setContentText(mensaje);
            alerta.showAndWait();
        });
    }
}
