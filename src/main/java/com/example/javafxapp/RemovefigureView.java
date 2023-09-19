package com.example.javafxapp;

import Geometry.IShape;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
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
        for (IShape shape : mainWindowController.shapesList){
            FigureToRemove.getItems().add(shape.toString());
        }
    }
    @FXML
    private void FigureToRemoveChanged() { RemoveShapeButton.setDisable(false); }
    @FXML
    private void RemoveShape_Click(){
        if (mainWindowController.shapesList.isEmpty()) return;
        Integer figureToRemoveIndex = FigureToRemove.getSelectionModel().getSelectedIndex();
        mainWindowController.shapesList.remove(mainWindowController.shapesList.get(figureToRemoveIndex));
        RenewFigureToRemoveComboBox();
        mainWindowController.RedrawMainCanvas();
        CloseWindow();
    }
    private void RenewFigureToRemoveComboBox(){
        FigureToRemove.getItems().clear();
        for (IShape shape: mainWindowController.shapesList){
            FigureToRemove.getItems().add(shape.toString());
        }
    }

    @FXML
    private void CloseWindow(){
        Stage stage = (Stage)CloseButton.getScene().getWindow();
        stage.close();
    }
}
