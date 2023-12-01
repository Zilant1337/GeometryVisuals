package com.example.javafxapp;

import GeometryFigs.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IntersectfigureView implements Initializable {
    public Canvas mainCanvas;
    public HelloController mainWindowController;
    @FXML
    public ComboBox ShapeFilter;
    @FXML
    public ComboBox FigureToIntersect1;
    @FXML
    public ComboBox FigureToIntersect2;
    @FXML
    public Button ShapesIntersectionButton;
    @FXML
    public Button CloseButton;
    @FXML
    private ArrayList<Integer> filteredShapeIndices;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    public void SetupIntersectionForm()
    {
        try {
            filteredShapeIndices = new ArrayList<>();
            for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i).toString());
                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i).toString());
                filteredShapeIndices.add(i);
            }
            mainWindowController.redColoredShapesIndices = new int[2];
            mainWindowController.redColoredShapesIndices[0] = -1;
            mainWindowController.redColoredShapesIndices[1] = -1;
            ShapeFilter.setItems(FXCollections.observableArrayList(new String[]{
                    "Segment", "Polyline", "Circle", "Polygon",
                    "Triangle", "Quadrilateral",
                    "Rectangle", "Trapeze"}));
            ShapeFilter.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    ShapesIntersectionButton.setDisable(true);
                    FigureToIntersect1.getItems().clear();
                    FigureToIntersect2.getItems().clear();
                    filteredShapeIndices.clear();
                    if (ShapeFilter.getValue() == "Segment") {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            if (mainWindowController.shapesList.get(i) instanceof Segment) {
                                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i));
                                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i));
                                filteredShapeIndices.add(i);
                            }
                        }
                    } else if (ShapeFilter.getValue() == "Polyline") {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            if (mainWindowController.shapesList.get(i) instanceof Polyline) {
                                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i));
                                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i));
                                filteredShapeIndices.add(i);
                            }
                        }
                    } else if (ShapeFilter.getValue() == "Circle") {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            if (mainWindowController.shapesList.get(i) instanceof Circle) {
                                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i));
                                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i));
                                filteredShapeIndices.add(i);
                            }
                        }
                    } else if (ShapeFilter.getValue() == "Triangle") {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            if (mainWindowController.shapesList.get(i) instanceof TGon) {
                                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i));
                                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i));
                                filteredShapeIndices.add(i);
                            }
                        }
                    } else if (ShapeFilter.getValue() == "Rectangle") {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            if (mainWindowController.shapesList.get(i) instanceof Rectangle) {
                                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i));
                                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i));
                                filteredShapeIndices.add(i);
                            }
                        }
                    } else if (ShapeFilter.getValue() == "Trapeze") {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            if (mainWindowController.shapesList.get(i) instanceof Segment) {
                                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i));
                                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i));
                                filteredShapeIndices.add(i);
                            }
                        }
                    } else if (ShapeFilter.getValue() == "Quadrilateral") {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            if (!(mainWindowController.shapesList.get(i) instanceof Rectangle)
                                    & !(mainWindowController.shapesList.get(i) instanceof Trapeze)
                                    & mainWindowController.shapesList.get(i) instanceof QGon) {
                                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i));
                                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i));
                                filteredShapeIndices.add(i);
                            }
                        }
                    } else if (ShapeFilter.getValue() == "Polygon") {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            if (!(mainWindowController.shapesList.get(i) instanceof Rectangle)
                                    & !(mainWindowController.shapesList.get(i) instanceof Trapeze)
                                    & !(mainWindowController.shapesList.get(i) instanceof TGon)
                                    & !(mainWindowController.shapesList.get(i) instanceof QGon)
                                    & mainWindowController.shapesList.get(i) instanceof NGon) {
                                FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i));
                                FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i));
                                filteredShapeIndices.add(i);
                            }
                        }
                    } else {
                        for (int i = 0; i < mainWindowController.shapesList.size(); i++) {
                            FigureToIntersect1.getItems().add(mainWindowController.shapesList.get(i).toString());
                            FigureToIntersect2.getItems().add(mainWindowController.shapesList.get(i).toString());
                            filteredShapeIndices.add(i);
                        }
                    }
                }
            });
            FigureToIntersect1.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    if (FigureToIntersect1.getValue() != null && FigureToIntersect1.getValue() != "") {
                        if (FigureToIntersect2.getValue() != null && FigureToIntersect2.getValue() != "" &&
                                !FigureToIntersect1.getSelectionModel().getSelectedItem().toString().equals(
                                        FigureToIntersect2.getSelectionModel().getSelectedItem().toString())) {
                            ShapesIntersectionButton.setDisable(false);
                        } else
                            ShapesIntersectionButton.setDisable(true);
                        mainWindowController.redColoredShapesIndices[0] = filteredShapeIndices.get(FigureToIntersect1.getSelectionModel().getSelectedIndex());
                        mainWindowController.RedrawMainCanvas();
                    }
                }
            });
            FigureToIntersect2.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    if (FigureToIntersect2.getValue() != null && FigureToIntersect2.getValue() != "") {
                        if (FigureToIntersect1.getValue() != null && FigureToIntersect1.getValue() != "" &&
                                !FigureToIntersect1.getSelectionModel().getSelectedItem().toString().equals(
                                        FigureToIntersect2.getSelectionModel().getSelectedItem().toString())) {
                            ShapesIntersectionButton.setDisable(false);
                        } else
                            ShapesIntersectionButton.setDisable(true);
                        mainWindowController.redColoredShapesIndices[1] = filteredShapeIndices.get(FigureToIntersect2.getSelectionModel().getSelectedIndex());
                        mainWindowController.RedrawMainCanvas();
                    }
                }
            });

            mainWindowController.RedrawMainCanvas();
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
    @FXML
    private void IntersectShapes_Click()
    {
        try {
            if (FigureToIntersect1.getSelectionModel().getSelectedItem().toString().equals(
                    FigureToIntersect2.getSelectionModel().getSelectedItem().toString())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Ошибка: пересечение фигуры с самой собой запрещено");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Нажат ОК");
                    }
                });
                return;
            }
            if (mainWindowController.shapesList.get(filteredShapeIndices.get(FigureToIntersect1.getSelectionModel().getSelectedIndex())).cross(
                    mainWindowController.shapesList.get(filteredShapeIndices.get(FigureToIntersect2.getSelectionModel().getSelectedIndex()))))
                mainWindowController.PerimeterOrArea.setText("Пересечение: Пересекается");
            else
                mainWindowController.PerimeterOrArea.setText("Пересечение: Не пересекается");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Удачно");
            alert.setHeaderText("Удачно");
            alert.setContentText("Пересечение удачно проверено");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Нажат ОК");
                }
            });
            CloseWindow();
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

    @FXML
    private void CloseWindow(){
        try{
            Stage stage = (Stage)CloseButton.getScene().getWindow();
            stage.close();
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
}
