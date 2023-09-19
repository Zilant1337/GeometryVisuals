module com.example.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive java.desktop;

    requires com.dlsc.formsfx;

    opens com.example.javafxapp to javafx.fxml;
    exports com.example.javafxapp;
}