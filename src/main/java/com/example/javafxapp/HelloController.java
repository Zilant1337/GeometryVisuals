package com.example.javafxapp;

import Geometry.*;
import Geometry.Circle;
import Geometry.Polyline;
import Geometry.Rectangle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class HelloController {
    public HelloApplication mainApplicationScript;
    private GridPane currentGrid;

    @FXML
    private GridPane MainFormGrid;
    @FXML
    private GridPane FigureAdditionForm;
    @FXML
    private GridPane FigureRemovalForm;
    @FXML
    private GridPane FigureMovementForm;
    @FXML
    private GridPane IntersectionForm;
    @FXML
    private Canvas MainCanvas;
    @FXML
    private Text PerimeterOrArea;
    @FXML
    private ComboBox ShapeType;
    @FXML
    private Spinner NumberOfVerticesField;
    @FXML
    private TextField RadiusField;
    @FXML
    private Button AddFigureButton;
    @FXML
    private Button RemoveShapeButton;
    @FXML
    private ComboBox FigureToRemove;
    @FXML
    private ComboBox FigureToIntersect1;
    @FXML
    private ComboBox FigureToIntersect2;
    @FXML
    private ComboBox FigureToMove;
    @FXML
    private Button ShapesIntersectionButton;

    private ArrayList<IShape> shapesList;
    private List<TextField> xCoords;
    private HashMap<TextField, TextField> XYCoordPointGrid;
    private double radiusValue;
    private int numberOfPoints;
    private int[] redColoredShapesIndices;

    public void CreateAxis()
    {
        try
        {
            shapesList = new ArrayList<IShape>();
            RedrawMainCanvas();
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
    public void SwitchGridTo(GridPane newGrid)
    {
        try {
            if (currentGrid != null) currentGrid.setVisible(false);
            currentGrid = newGrid;
            newGrid.setVisible(true);
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
    protected void OpenMainForm(){ SwitchGridTo(MainFormGrid); }
    @FXML
    protected void OpenFigureAdditionForm(){ SwitchGridTo(FigureAdditionForm); }
    @FXML
    protected void OpenFigureRemovalForm(){ SwitchGridTo(FigureRemovalForm);}
    @FXML
    protected void OpenIntersectionForm(){ SwitchGridTo(IntersectionForm);}
    @FXML
    protected void OpenFigureMovementForm(){ SwitchGridTo(FigureMovementForm);}

    public void StartSetup()
    {
        try {
            SetupAddFigureForm();
            SetupIntersectionForm();
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
    private void SetupAddFigureForm()
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
    private void SetupIntersectionForm()
    {
        redColoredShapesIndices = new int[2]; redColoredShapesIndices[0]=-1; redColoredShapesIndices[1]=-1;
        FigureToIntersect1.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (FigureToIntersect2.getValue()!=null) ShapesIntersectionButton.setDisable(false);
                redColoredShapesIndices[0] = FigureToIntersect1.getSelectionModel().getSelectedIndex();
                RedrawMainCanvas();
            }
        });
        FigureToIntersect2.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (FigureToIntersect1.getValue()!=null) ShapesIntersectionButton.setDisable(false);
                redColoredShapesIndices[1] = FigureToIntersect2.getSelectionModel().getSelectedIndex();
                RedrawMainCanvas();
            }
        });
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
    private void AddShapeTypesToShapeTypeComboBox(ObservableList obsList){
        ShapeType.setItems(obsList);
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
                AddItemToShapeListComboBoxes(newShape.toString());
                shapesList.add(newShape);

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
    private void AddItemToShapeListComboBoxes(String value)
    {
        try {
            FigureToRemove.getItems().add(value);
            FigureToIntersect1.getItems().add(value);
            FigureToIntersect2.getItems().add(value);
            FigureToMove.getItems().add(value);
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
    private void RedrawMainCanvas()
    {
        MainCanvas.getGraphicsContext2D().clearRect(0,0, MainCanvas.getWidth(), MainCanvas.getHeight());
        DrawMainAxis();
        DrawShapes();
    }
    private void DrawMainAxis()
    {
        MainCanvas.getGraphicsContext2D().setStroke(Color.DARKGRAY);
        MainCanvas.getGraphicsContext2D().setFill(Color.DARKGRAY);
        MainCanvas.getGraphicsContext2D().setLineWidth(2);
        MainCanvas.getGraphicsContext2D().strokeLine(MainCanvas.getWidth() * 1 / 50.0,
                MainCanvas.getHeight() * 1.0 / 2.0,
                MainCanvas.getWidth() * 49.0 / 50.0,
                MainCanvas.getHeight() * 1.0 / 2.0);
        MainCanvas.getGraphicsContext2D().strokeLine(MainCanvas.getWidth() * 1 / 2.0,
                MainCanvas.getHeight() * 1.0 / 50.0,
                MainCanvas.getWidth() * 1.0 / 2.0,
                MainCanvas.getHeight() * 49.0 / 50.0);
        MainCanvas.getGraphicsContext2D().setStroke(Color.BLACK);
        MainCanvas.getGraphicsContext2D().setFill(Color.BLACK);
    }
    private void DrawShapes()
    {
        for (int i = 0; i < shapesList.size(); i++){
            if (redColoredShapesIndices[0]==i || redColoredShapesIndices[1]==i) {
                MainCanvas.getGraphicsContext2D().setStroke(Color.RED);
                MainCanvas.getGraphicsContext2D().setFill(Color.RED);
            }
            if (shapesList.get(i) instanceof NGon)
                DrawPolygon(((NGon) shapesList.get(i)).getP());
            else if (shapesList.get(i) instanceof Polyline)
                DrawPolyline(((Polyline) shapesList.get(i)).getP());
            else if (shapesList.get(i) instanceof Segment)
                DrawSegment(((Segment) shapesList.get(i)).getStart().x, ((Segment) shapesList.get(i)).getFinish().x);
            else if (shapesList.get(i) instanceof Circle)
                DrawCircle(((Circle) shapesList.get(i)).getP().x, ((Circle) shapesList.get(i)).getR());
            if (redColoredShapesIndices[0]==i | redColoredShapesIndices[1]==i) {
                MainCanvas.getGraphicsContext2D().setStroke(Color.BLACK);
                MainCanvas.getGraphicsContext2D().setFill(Color.BLACK);
            }
        }
    }
    private Segment DrawSegment(double[] start, double[] end)
    {
        MainCanvas.getGraphicsContext2D().strokeLine(MainCanvas.getWidth()/2 + start[0],
                MainCanvas.getHeight()/2 - start[1],
                MainCanvas.getWidth()/2 + end[0],
                MainCanvas.getHeight()/2 - end[1]);
        return new Segment(new Point2D(start), new Point2D(end));
    }
    private Circle DrawCircle(double[] center)
    {
        MainCanvas.getGraphicsContext2D().strokeOval(MainCanvas.getWidth()/2 + center[0] - radiusValue,
                                                    MainCanvas.getHeight()/2 - center[1] - radiusValue,
                                                        2*radiusValue, 2*radiusValue);
        return new Circle(new Point2D(center), radiusValue);
    }
    private void DrawCircle(double[] center, double R)
    {
        MainCanvas.getGraphicsContext2D().strokeOval(MainCanvas.getWidth()/2 + center[0] - R,
                MainCanvas.getHeight()/2 - center[1] - R,
                2*R, 2*R);
    }
    private Geometry.Polyline DrawPolyline()
    {
        Point2D[] points = FormPointCollection();
        for (int i = 0; i < numberOfPoints - 1; i++){
            DrawSegment(points[i].x, points[i+1].x);
        }
        return new Geometry.Polyline(points);
    }
    private void DrawPolyline(Point2D[] points)
    {
        for (int i = 0; i < numberOfPoints - 1; i++){
            DrawSegment(points[i].x, points[i+1].x);
        }
    }
    private NGon DrawPolygon()
    {
        Point2D[] points = FormPointCollection();
        DrawPolyline();
        DrawSegment(points[numberOfPoints-1].x, points[0].x);
        return new NGon(points);
    }
    private void DrawPolygon(Point2D[] points)
    {
        DrawPolyline(points);
        DrawSegment(points[numberOfPoints-1].x, points[0].x);
    }
    private TGon DrawTriangle()
    {
        DrawPolygon();
        return new TGon(FormPointCollection());
    }
    private QGon DrawQuadrilateral()
    {
        DrawPolygon();
        return new QGon(FormPointCollection());
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
    private void Clear_Click() {
        MainCanvas.getGraphicsContext2D().clearRect(0,0,
                                                    MainCanvas.getWidth(),
                                                    MainCanvas.getHeight());
        EmptyShapeComboBoxes();
        shapesList.clear();
        RedrawMainCanvas();
    }
    private void EmptyShapeComboBoxes(){
        FigureToRemove.getItems().clear();
        FigureToIntersect1.getItems().clear();
        FigureToIntersect2.getItems().clear();
        FigureToMove.getItems().clear();
    }
    @FXML
    private void Perimeter_Click() {
        double P = 0;
        for (IShape shape : shapesList)
            P += shape.length();
        PerimeterOrArea.setText("Area: " + String.valueOf(P));
    }
    @FXML
    private void Area_Click() {
        double S = 0;
        for (IShape shape : shapesList)
            S += shape.square();
        PerimeterOrArea.setText("Perimeter: " + String.valueOf(S));
    }

    @FXML
    private void FigureToRemoveChanged() { RemoveShapeButton.setDisable(false); }
    @FXML
    private void RemoveShape_Click(){
        if (shapesList.isEmpty()) return;
        Integer figureToRemoveIndex = FigureToRemove.getSelectionModel().getSelectedIndex();
        shapesList.remove(shapesList.get(figureToRemoveIndex));
        EmptyShapeComboBoxes();
        shapesList.forEach(item -> AddItemToShapeListComboBoxes(item.toString()));
        RedrawMainCanvas();
    }

    @FXML
    private void IntersectShapes_Click()
    {
        if (shapesList.get(FigureToIntersect1.getSelectionModel().getSelectedIndex()).cross(
                shapesList.get(FigureToIntersect2.getSelectionModel().getSelectedIndex())))
            PerimeterOrArea.setText("Intersection: intersect");
        else
            PerimeterOrArea.setText("Intersection: don't intersect");
        SwitchGridTo(MainFormGrid);
    }
}