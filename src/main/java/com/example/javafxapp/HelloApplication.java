package com.example.javafxapp;

import GeometryFigs.*;
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
            stage.setTitle("Фигуры!");
            stage.setScene(mainScene);
            stage.setResizable(false);
            stage.show();

            mainGrid = (GridPane) fxmlLoader.getNamespace().get("MainFormGrid");
            controller.redColoredShapesIndices = new int[2];
            controller.redColoredShapesIndices[0]=-1;
            controller.redColoredShapesIndices[1]=-1;
            controller.CreateAxis();
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Ошибка: " + ex.getMessage());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Нажат ОК");
                }
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }



}