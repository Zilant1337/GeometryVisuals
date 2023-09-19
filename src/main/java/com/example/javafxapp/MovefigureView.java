package com.example.javafxapp;

import Geometry.IShape;
import Geometry.Point2D;
import javafx.collections.FXCollections;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MovefigureView implements Initializable {
    public Canvas mainCanvas;
    public HelloController mainWindowController;
    @FXML
    public GridPane FigureMovementForm;
    @FXML
    public ComboBox MovementType;
    @FXML
    public ComboBox FigureToMove;
    @FXML
    public TextField ShiftXCoord;
    @FXML
    public TextField ShiftYCoord;
    @FXML
    public TextField RotationAngle;
    @FXML
    public Spinner AxisMirroringSpinner;
    @FXML
    public Button MoveFigureButton;
    @FXML
    public Button CloseButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    public void SetupMovementForm()
    {
        MovementType.setItems(FXCollections.observableArrayList(new String[]{
                "Shift", "Rotate", "Mirror to Axis"
        }));
        AxisMirroringSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                new ObservableListBase<String>() {
                    @Override
                    public String get(int index) {
                        if (index==0) return "x";
                        else return "y";
                    }

                    @Override
                    public int size() {
                        return 2;
                    }
                }
        ));
        AxisMirroringSpinner.getValueFactory().wrapAroundProperty().setValue(true);
        for (IShape shape : mainWindowController.shapesList){
            FigureToMove.getItems().add(shape.toString());
        }
    }
    @FXML
    private void MovementTypeChanged()
    {
        MovementTypeOrFigureChanged();
    }

    @FXML
    private void FigureToMoveChanged()
    {
        MovementTypeOrFigureChanged();
    }

    private void MovementTypeOrFigureChanged()
    {
        if (MovementType.getValue()!=null & FigureToMove.getValue()!=null){
            MoveFigureButton.setDisable(false);
            FigureMovementForm.getChildren().forEach(child -> {
                child.setVisible(true);
            });
            if (MovementType.getValue()=="Shift"){
                ShiftXCoord.setDisable(false);
                ShiftYCoord.setDisable(false);
                RotationAngle.setDisable(true);
                AxisMirroringSpinner.setDisable(true);
            }
            else if (MovementType.getValue()=="Rotate"){
                ShiftXCoord.setDisable(true);
                ShiftYCoord.setDisable(true);
                RotationAngle.setDisable(false);
                AxisMirroringSpinner.setDisable(true);
            }
            else if (MovementType.getValue()=="Mirror to Axis"){
                ShiftXCoord.setDisable(true);
                ShiftYCoord.setDisable(true);
                RotationAngle.setDisable(true);
                AxisMirroringSpinner.setDisable(false);
            }
        }
    }

    @FXML
    private void MoveFigure_Click(){
        String action = MovementType.getValue().toString();
        IShape figure = mainWindowController.shapesList.get(FigureToMove.getSelectionModel().getSelectedIndex());
        switch (action) {
            case "Shift":
                figure.shift(new Point2D(new double[]{
                        Double.parseDouble(ShiftXCoord.getText()),
                        Double.parseDouble(ShiftYCoord.getText())
                }));
                break;
            case "Rotate":
                figure.rot(Double.parseDouble(RotationAngle.getText()));
                break;
            case "Mirror to Axis":
                figure.symAxis(AxisMirroringSpinner.getValue() == "x"? 0: 1);
                break;
        }
        mainWindowController.RedrawMainCanvas();
        CloseWindow();
    }

    @FXML
    private void CloseWindow(){
        Stage stage = (Stage)CloseButton.getScene().getWindow();
        stage.close();
    }
}
