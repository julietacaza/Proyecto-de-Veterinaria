package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HelloController {
    private List<Consulta> citas = new ArrayList<>();
    private List<Mascota> mascotas = new ArrayList<>();
    private TablaCitasController tablaCitasController;
    private TablaMascotasController tablaMascotasController;
    private ExpedienteMascotaController expedienteMascotaController;
    private Stage tablaCitasStage;
    private Stage tablaMascotasStage;
    private Consulta citaEnEdicion;

    public TablaMascotasController getTablaMascotasController() {
        return tablaMascotasController;
    }

    public ExpedienteMascotaController getExpedienteMascotaController() {
        return expedienteMascotaController;
    }

    public TablaCitasController getTablaCitasController() {
        return tablaCitasController;
    }

    public List<Consulta> getCitas() {
        return citas;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public boolean agregarMascota(Mascota nuevaMascota, boolean esMascotaExistente) {
        try {
            if (esMascotaExistente) {
                Mascota existente = mascotas.stream()
                        .filter(m -> m.getNombre().equalsIgnoreCase(nuevaMascota.getNombre()) &&
                                m.getPropietario().equalsIgnoreCase(nuevaMascota.getPropietario()))
                        .findFirst()
                        .orElse(null);

                if (existente != null) {
                    mascotas.remove(existente);
                    mascotas.add(nuevaMascota);
                    actualizarVistasMascotas(nuevaMascota);
                    return true;
                }
            }

            boolean existe = mascotas.stream()
                    .anyMatch(m -> m.getNombre().equalsIgnoreCase(nuevaMascota.getNombre()) &&
                            m.getPropietario().equalsIgnoreCase(nuevaMascota.getPropietario()));

            if (existe) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mascota existente");
                alert.setHeaderText("Ya existe una mascota con ese nombre para este propietario");
                alert.setContentText("¿Desea actualizar los datos de la mascota existente?");

                ButtonType btnActualizar = new ButtonType("Actualizar");
                ButtonType btnCancelar = new ButtonType("Cancelar");
                alert.getButtonTypes().setAll(btnActualizar, btnCancelar);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == btnActualizar) {
                    Mascota existente = mascotas.stream()
                            .filter(m -> m.getNombre().equalsIgnoreCase(nuevaMascota.getNombre()) &&
                                    m.getPropietario().equalsIgnoreCase(nuevaMascota.getPropietario()))
                            .findFirst()
                            .orElse(null);

                    if (existente != null) {
                        mascotas.remove(existente);
                        mascotas.add(nuevaMascota);
                        actualizarVistasMascotas(nuevaMascota);
                        return true;
                    }
                }
                return false;
            }

            mascotas.add(nuevaMascota);
            actualizarVistasMascotas(nuevaMascota);
            return true;
        } catch (Exception e) {
            mostrarError("Error al guardar mascota", e);
            return false;
        }
    }

    private void actualizarVistasMascotas(Mascota mascota) {
        if (tablaMascotasController != null) {
            tablaMascotasController.actualizarTabla(mascotas);
        }
        if (expedienteMascotaController != null &&
                expedienteMascotaController.getMascotaSeleccionada() != null &&
                expedienteMascotaController.getMascotaSeleccionada().getNombre().equals(mascota.getNombre())) {
            expedienteMascotaController.setMascota(mascota);
        }
    }

    @FXML
    private void abrirFormulario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectveterinaria/Formulario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Formulario de Citas");
            stage.setScene(new Scene(root));

            Formulario controller = loader.getController();
            controller.setHelloController(this);
            controller.setModoEdicion(false);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al abrir formulario", e);
        }
    }

    @FXML
    private void abrirExpediente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectveterinaria/Expediente.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Expediente Médico");
            stage.setScene(new Scene(root));

            ExpedienteController controller = loader.getController();
            controller.setHelloController(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectveterinaria/tabla-de-cita.fxml"));
            Parent root = loader.load();

            tablaCitasStage = new Stage();
            tablaCitasStage.setTitle("Tabla de Citas");
            tablaCitasStage.setScene(new Scene(root));

            tablaCitasController = loader.getController();
            tablaCitasController.setCitasDesdeFormulario(citas);
            tablaCitasController.setHelloController(this);

            tablaCitasStage.setOnHidden(e -> tablaCitasStage = null);
            tablaCitasStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al abrir tabla de citas", e);
        }
    }

    public void abrirVentanaTablaMascotas() {
        try {
            if (tablaMascotasStage != null && tablaMascotasStage.isShowing()) {
                tablaMascotasStage.toFront();
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectveterinaria/tabla-mascota.fxml"));
            Parent root = loader.load();

            tablaMascotasStage = new Stage();
            tablaMascotasStage.setTitle("Tabla de Mascotas");
            tablaMascotasStage.setScene(new Scene(root));

            tablaMascotasController = loader.getController();
            tablaMascotasController.setMascotas(mascotas);
            tablaMascotasController.setHelloController(this);

            tablaMascotasStage.setOnHidden(e -> tablaMascotasStage = null);
            tablaMascotasStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al abrir tabla de mascotas", e);
        }
    }

    public void abrirFormularioParaEdicion(Consulta cita) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectveterinaria/Formulario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Editar Cita");
            stage.setScene(new Scene(root));

            Formulario controller = loader.getController();
            controller.setHelloController(this);
            controller.cargarDatosParaEdicion(cita);
            controller.setModoEdicion(true);

            this.citaEnEdicion = cita;

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al abrir formulario", e);
        }
    }

    public void mostrarExpedienteMascota(Mascota mascota) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectveterinaria/expediente-mascota.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Expediente de " + mascota.getNombre());
            stage.setScene(new Scene(root));

            expedienteMascotaController = loader.getController();
            expedienteMascotaController.setMascota(mascota);
            expedienteMascotaController.setHelloController(this);

            List<Consulta> citasMascota = citas.stream()
                    .filter(c -> c.getNomMascota().equals(mascota.getNombre()) &&
                            c.getPropietario().equals(mascota.getPropietario()))
                    .collect(Collectors.toList());
            expedienteMascotaController.setCitas(citasMascota);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al abrir expediente", e);
        }
    }

    public boolean agregarCita(Consulta nuevaConsulta, boolean esEdicion) {
        try {
            if (esEdicion && citaEnEdicion != null) {
                citas.remove(citaEnEdicion);
                citaEnEdicion = null;
            }

            boolean existe = citas.stream()
                    .anyMatch(cita -> cita.getFecha().equals(nuevaConsulta.getFecha()) &&
                            cita.getHora().equals(nuevaConsulta.getHora()));

            if (existe) {
                mostrarAlerta("Conflicto de Horario", "Ya existe una cita programada en esa fecha y hora.");
                return false;
            }

            citas.add(nuevaConsulta);

            if (tablaCitasController != null) {
                tablaCitasController.actualizarTabla(citas);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al guardar cita", e);
            return false;
        }
    }

    public void cargarDatosIniciales() {
        try {
            if (tablaCitasController != null) {
                tablaCitasController.actualizarTabla(citas);
            }
            if (tablaMascotasController != null) {
                tablaMascotasController.actualizarTabla(mascotas);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar datos iniciales", e);
        }
    }

    private void mostrarError(String mensaje, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(mensaje);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public boolean agregarCita(Animal nuevoAnimal) {
        return false;
    }
}
