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
        try {
            MovementType.setItems(FXCollections.observableArrayList(new String[]{
                    "Shift", "Rotate", "Mirror to Axis"
            }));
            AxisMirroringSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                    new ObservableListBase<String>() {
                        @Override
                        public String get(int index) {
                            if (index == 0) return "x";
                            else return "y";
                        }

                        @Override
                        public int size() {
                            return 2;
                        }
                    }
            ));
            AxisMirroringSpinner.getValueFactory().wrapAroundProperty().setValue(true);
            for (IShape shape : mainWindowController.shapesList) {
                FigureToMove.getItems().add(shape.toString());
            }
        }
        catch (Exception ex){
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
    @FXML
    private void MovementTypeChanged(){
        MovementTypeOrFigureChanged();
    }

    @FXML
    private void FigureToMoveChanged(){
        MovementTypeOrFigureChanged();
    }

    private void MovementTypeOrFigureChanged()
    {
        try {
            if (MovementType.getValue() != null & FigureToMove.getValue() != null) {
                MoveFigureButton.setDisable(false);
                FigureMovementForm.getChildren().forEach(child -> {
                    child.setVisible(true);
                });
                if (MovementType.getValue() == "Shift") {
                    ShiftXCoord.setDisable(false);
                    ShiftYCoord.setDisable(false);
                    ShiftXCoord.setText("0");
                    ShiftYCoord.setText("0");
                    RotationAngle.setDisable(true);
                    AxisMirroringSpinner.setDisable(true);
                } else if (MovementType.getValue() == "Rotate") {
                    ShiftXCoord.setDisable(true);
                    ShiftYCoord.setDisable(true);
                    ShiftXCoord.setText("");
                    ShiftYCoord.setText("");
                    RotationAngle.setDisable(false);
                    AxisMirroringSpinner.setDisable(true);
                } else if (MovementType.getValue() == "Mirror to Axis") {
                    ShiftXCoord.setDisable(true);
                    ShiftYCoord.setDisable(true);
                    ShiftXCoord.setText("");
                    ShiftYCoord.setText("");
                    RotationAngle.setDisable(true);
                    AxisMirroringSpinner.setDisable(false);
                }
            }
        }
        catch (Exception ex){
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

    @FXML
    private void MoveFigure_Click(){
        try {
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
                    figure.symAxis(AxisMirroringSpinner.getValue() == "x" ? 0 : 1);
                    break;
            }
            mainWindowController.RedrawMainCanvas();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Shape successfully transformed");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            CloseWindow();
        }
        catch (Exception ex){
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

    @FXML
    private void CloseWindow(){
        try {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        }
        catch (Exception ex){
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
}
