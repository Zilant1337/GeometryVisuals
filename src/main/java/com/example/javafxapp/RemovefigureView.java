package com.example.javafxapp;

import GeometryFigs.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RemovefigureView implements Initializable {

    public Canvas mainCanvas;
    public HelloController mainWindowController;
    @FXML
    public ComboBox FigureToRemove;
    @FXML
    public Button RemoveShapeButton;
    @FXML
    public Button CloseButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
    public void SetupFigureRemovalForm()
    {
        try {
            for (IShape shape : mainWindowController.shapesList) {
                FigureToRemove.getItems().add(shape.toString());
            }
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
    private void FigureToRemoveChanged() {
        try {
            RemoveShapeButton.setDisable(false);
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
    private void RemoveShape_Click(){
        try {
            if (mainWindowController.shapesList.isEmpty()) return;
            Integer figureToRemoveIndex = FigureToRemove.getSelectionModel().getSelectedIndex();
            mainWindowController.shapesList.remove(mainWindowController.shapesList.get(figureToRemoveIndex));
            RenewFigureToRemoveComboBox();
            mainWindowController.RedrawMainCanvas();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Удачно");
            alert.setHeaderText("Удачно");
            alert.setContentText("Фигура удачно убрана");
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
    private void RenewFigureToRemoveComboBox(){
        try{
            FigureToRemove.getItems().clear();
            for (IShape shape: mainWindowController.shapesList){
                FigureToRemove.getItems().add(shape.toString());
            }
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
        try {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
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
