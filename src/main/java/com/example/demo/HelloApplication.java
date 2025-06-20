package com.example.demo;

import com.example.demo.utils.DataInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Inicializar datos básicos
        DataInitializer.initialize();

        // Cargar el FXML principal con la ruta correcta
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/proyectveterinaria/Veterinaria.fxml"));
        Parent root = fxmlLoader.load();

        // Obtener el controlador y cargar datos
        HelloController controller = fxmlLoader.getController();
        controller.cargarDatosIniciales();

        Scene scene = new Scene(root);
        stage.setTitle("Sistema Veterinario");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        com.example.demo.utils.HibernateUtils.closeEntityManagerFactory();
    }

    public static void main(String[] args) {
        launch(args);
    }
}