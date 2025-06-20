module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.example.demo to javafx.fxml, org.hibernate.orm.core;
    exports com.example.demo;
    exports com.example.demo.models to javafx.fxml, org.hibernate.orm.core;
    opens com.example.demo.models;
}