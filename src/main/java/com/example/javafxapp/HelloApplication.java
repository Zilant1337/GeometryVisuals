package com.example.javafxapp;

import Geometry.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {

    private GridPane mainGrid;
    private HelloController controller;


    @Override
    public void start(Stage stage) throws IOException {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene mainScene = new Scene(fxmlLoader.load(), 782, 560);
            controller = fxmlLoader.getController();
            controller.mainApplicationScript = this;
            stage.setTitle("Geometric Shapes");
            stage.setScene(mainScene);
            stage.setResizable(false);
            stage.show();

            mainGrid = (GridPane) fxmlLoader.getNamespace().get("MainFormGrid");
            controller.StartSetup();
            controller.SwitchGridTo(mainGrid);
            controller.CreateAxis();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Error: " + ex.getMessage());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }



}