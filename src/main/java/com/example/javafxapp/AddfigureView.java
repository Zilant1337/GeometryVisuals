package com.example.javafxapp;

import Geometry.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AddfigureView implements Initializable {
    public Canvas mainCanvas;
    public HelloController mainWindowController;
    @FXML
    public GridPane FigureAdditionForm;
    @FXML
    public Spinner NumberOfVerticesField;
    @FXML
    public ComboBox ShapeType;
    @FXML
    public TextField RadiusField;
    @FXML
    public Button AddFigureButton;
    @FXML
    public VBox MainAddFigureBox;
    @FXML
    public Button CloseButton;


    private List<TextField> xCoords;
    private HashMap<TextField, TextField> XYCoordPointGrid;
    private double radiusValue;
    private int numberOfPoints;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
    public void SetupAddFigureForm()
    {
        AddShapeTypesToShapeTypeComboBox(FXCollections.observableArrayList(new String[]{
                "Segment", "Polyline", "Circle", "Polygon",
                "Triangle", "Quadrilateral",
                "Rectangle", "Trapeze"}));
        NumberOfVerticesField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 18, 1, 1) {
        });
        NumberOfVerticesField.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                numberOfPoints = (Integer) NumberOfVerticesField.getValue();
                for (int i = xCoords.size()-1; i >= numberOfPoints; i--) {
                    xCoords.get(i).setDisable(true);
                    XYCoordPointGrid.get(xCoords.get(i)).setDisable(true);
                }
                for (int i = 0; i < numberOfPoints; i++) {
                    xCoords.get(i).setDisable(false);
                    XYCoordPointGrid.get(xCoords.get(i)).setDisable(false);
                }
            }
        });
        CreatePointFields();
    }
    private void AddShapeTypesToShapeTypeComboBox(ObservableList obsList){
        ShapeType.setItems(obsList);
    }

    private void CreatePointFields()
    {
        try {
            XYCoordPointGrid = new HashMap<TextField, TextField>();
            xCoords = new ArrayList<TextField>();
            CreateSixPointFields(2, 1);
            CreateSixPointFields(7, 7);
            CreateSixPointFields(12, 13);
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
    private void CreateSixPointFields(int gridColumnIndex, int countStartValue)
    {
        try {
            for (int index = 0; index < 6; ++index)
            {
                Text pointIndex = new Text();
                Text x = new Text();
                Text y = new Text();
                TextField xCoordinate = new TextField();
                TextField yCoordinate = new TextField();
                pointIndex.setText((index + countStartValue) + " point");
                x.setText("x:");
                y.setText("y:");
                xCoordinate.prefHeight(140);
                yCoordinate.prefHeight(140);
                xCoordinate.setDisable(true);
                yCoordinate.setDisable(true);
                FigureAdditionForm.add(pointIndex, gridColumnIndex, 2*index + 1, 2, 1);
                FigureAdditionForm.add(x, gridColumnIndex - 1, 2*index + 2, 1, 1);
                FigureAdditionForm.add(y, gridColumnIndex + 1, 2*index + 2, 1, 1);
                FigureAdditionForm.add(xCoordinate, gridColumnIndex, 2*index + 2, 1, 1);
                FigureAdditionForm.add(yCoordinate, gridColumnIndex + 2, 2*index + 2, 1, 1);
                xCoords.add(xCoordinate);
                XYCoordPointGrid.put(xCoordinate, yCoordinate);
            }
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
    @FXML
    private void ShapeType_SelectionChanged()
    {
        try {
            String str = (ShapeType.getValue()).toString();
            radiusValue = 0.0;
            RadiusField.setText(Double.toString(radiusValue));
            numberOfPoints = 1;
            NumberOfVerticesField.getValueFactory().setValue(numberOfPoints);
            RadiusField.setDisable(true);
            NumberOfVerticesField.setDisable(true);
            AddFigureButton.setDisable(false);
            for (TextField x: xCoords) DisablePoint(x, XYCoordPointGrid.get(x));
            int curPointIndex = 0;
            switch (str)
            {
                case "Polyline":
                case "Polygon":
                    NumberOfVerticesField.setDisable(false);
                    for (TextField x: xCoords) {
                        if (curPointIndex < numberOfPoints) EnablePoint(x, XYCoordPointGrid.get(x));
                        curPointIndex++;
                    }
                    break;
                case "Circle":
                    RadiusField.setDisable(false);

                    for (TextField x: xCoords) {
                        if (curPointIndex < numberOfPoints) EnablePoint(x, XYCoordPointGrid.get(x));
                        curPointIndex++;
                    }
                    break;
                case "Segment":
                    numberOfPoints = 2;
                    NumberOfVerticesField.getValueFactory().setValue(numberOfPoints);

                    for (TextField x: xCoords) {
                        if (curPointIndex < numberOfPoints) EnablePoint(x, XYCoordPointGrid.get(x));
                        curPointIndex++;
                    }
                    break;
                case "Triangle":
                    numberOfPoints = 3;
                    NumberOfVerticesField.getValueFactory().setValue(numberOfPoints);
                    for (TextField x: xCoords) {
                        if (curPointIndex < numberOfPoints) EnablePoint(x, XYCoordPointGrid.get(x));
                        curPointIndex++;
                    }
                    return;
                case "Quadrilateral":
                case "Rectangle":
                default:
                    numberOfPoints = 4;
                    NumberOfVerticesField.getValueFactory().setValue(numberOfPoints);
                    for (TextField x: xCoords) {
                        if (curPointIndex < numberOfPoints) EnablePoint(x, XYCoordPointGrid.get(x));
                        curPointIndex++;
                    }
                    break;
            }

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

    private Segment DrawSegment(double[] start, double[] end)
    {
        mainCanvas.getGraphicsContext2D().strokeLine(mainCanvas.getWidth()/2 + start[0],
                mainCanvas.getHeight()/2 - start[1],
                mainCanvas.getWidth()/2 + end[0],
                mainCanvas.getHeight()/2 - end[1]);
        return new Segment(new Point2D(start), new Point2D(end));
    }

    private Circle DrawCircle(double[] center)
    {
        mainCanvas.getGraphicsContext2D().strokeOval(mainCanvas.getWidth()/2 + center[0] - radiusValue,
                mainCanvas.getHeight()/2 - center[1] - radiusValue,
                2*radiusValue, 2*radiusValue);
        return new Circle(new Point2D(center), radiusValue);
    }
    private Geometry.Polyline DrawPolyline()
    {
        Point2D[] points = FormPointCollection();
        for (int i = 0; i < numberOfPoints - 1; i++){
            DrawSegment(points[i].x, points[i+1].x);
        }
        return new Geometry.Polyline(points);
    }
    private NGon DrawPolygon()
    {
        Point2D[] points = FormPointCollection();
        DrawPolyline();
        DrawSegment(points[numberOfPoints-1].x, points[0].x);
        return new NGon(points);
    }
    private QGon DrawQuadrilateral()
    {
        DrawPolygon();
        return new QGon(FormPointCollection());
    }
    private TGon DrawTriangle()
    {
        DrawPolygon();
        return new TGon(FormPointCollection());
    }
    private Rectangle DrawRectangle()
    {
        DrawPolygon();
        return new Rectangle(FormPointCollection());
    }
    private Trapeze DrawTrapeze()
    {
        DrawPolygon();
        return new Trapeze(FormPointCollection());
    }
    private void DisablePoint(TextField x, TextField y)
    {
        x.setText("");
        y.setText("");
        x.setDisable(true);
        y.setDisable(true);
    }
    private void EnablePoint(TextField x, TextField y)
    {
        x.setText("0");
        y.setText("0");
        x.setDisable(false);
        y.setDisable(false);
    }
    private Point2D[] FormPointCollection()
    {
        Point2D[] pointCollection = new Point2D[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            TextField x = xCoords.get(i);
            pointCollection[i] = new Point2D(new double[]{Double.parseDouble(x.getText()),
                    Double.parseDouble(XYCoordPointGrid.get(x).getText())
            });
        }
        return pointCollection;
    }
    @FXML
    private void AddShape_Click()
    {
        try {
            if (ShapeType.getValue() == "")
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Error: Shape type haven't been chosen");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
            else
            {
                radiusValue = Double.parseDouble(RadiusField.getText());
                String str = (ShapeType.getValue()).toString();
                IShape newShape = null;
                switch (str)
                {
                    case "Polyline":
                        newShape = DrawPolyline();
                        break;
                    case "Polygon":
                        newShape = DrawPolygon();
                        break;
                    case "Circle":
                        newShape = DrawCircle(new double[]
                                { Double.parseDouble(xCoords.get(0).getText()),
                                        Double.parseDouble(XYCoordPointGrid.get(xCoords.get(0)).getText())
                                });
                        break;
                    case "Segment":
                        newShape = DrawSegment(new double[]
                                        {
                                                Double.parseDouble(xCoords.get(0).getText()),
                                                Double.parseDouble(XYCoordPointGrid.get(xCoords.get(0)).getText())
                                        },
                                new double[]
                                        {
                                                Double.parseDouble(xCoords.get(1).getText()),
                                                Double.parseDouble(XYCoordPointGrid.get(xCoords.get(1)).getText())
                                        });

                        break;
                    case "Triangle":
                        newShape = DrawTriangle();
                        break;
                    case "Quadrilateral":
                        newShape = DrawQuadrilateral();
                        break;
                    case "Rectangle":
                        newShape = DrawRectangle();
                        break;
                    case "Trapeze":
                        newShape = DrawTrapeze();
                        break;
                }
                mainWindowController.shapesList.add(newShape);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("Item successfully added");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
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
    @FXML
    private void CloseWindow(){
        Stage stage = (Stage)CloseButton.getScene().getWindow();
        stage.close();
    }
}
