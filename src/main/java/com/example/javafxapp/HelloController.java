package com.example.javafxapp;

import Geometry.*;
import Geometry.Circle;
import Geometry.Polyline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;



public class HelloController {
    public HelloApplication mainApplicationScript;
    @FXML
    private Canvas MainCanvas;
    @FXML
    public Text PerimeterOrArea;

    public ArrayList<IShape> shapesList;
    public int[] redColoredShapesIndices;

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
    public void SwitchGridTo(String newView)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AddfigureView.class.getResource(newView));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            if (newView=="addfigure-view.fxml"){
                AddfigureView afController = fxmlLoader.getController();
                afController.mainCanvas = MainCanvas;
                afController.mainWindowController = this;
                afController.SetupAddFigureForm();
            }
            else if (newView=="movefigure-view.fxml"){
                MovefigureView mfController = fxmlLoader.getController();
                mfController.mainCanvas = MainCanvas;
                mfController.mainWindowController = this;
                mfController.SetupMovementForm();
            }
            else if (newView=="removefigure-view.fxml"){
                RemovefigureView rmfController = fxmlLoader.getController();
                rmfController.mainCanvas = MainCanvas;
                rmfController.mainWindowController = this;
                rmfController.SetupFigureRemovalForm();
            }
            else if (newView=="intersectfigure-view.fxml"){
                IntersectfigureView ifController = fxmlLoader.getController();
                ifController.mainCanvas = MainCanvas;
                ifController.mainWindowController = this;
                ifController.SetupIntersectionForm();
            }
            Stage stage = new Stage();
            stage.setTitle(newView.split("-")[0].toUpperCase());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
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
    protected void OpenFigureAdditionForm(){ SwitchGridTo("addfigure-view.fxml"); }
    @FXML
    protected void OpenFigureRemovalForm(){ SwitchGridTo("removefigure-view.fxml");}
    @FXML
    protected void OpenIntersectionForm(){ SwitchGridTo("intersectfigure-view.fxml");}
    @FXML
    protected void OpenFigureMovementForm(){ SwitchGridTo("movefigure-view.fxml");}
    public void RedrawMainCanvas()
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
    private void DrawCircle(double[] center, double R)
    {
        MainCanvas.getGraphicsContext2D().strokeOval(MainCanvas.getWidth()/2 + center[0] - R,
                MainCanvas.getHeight()/2 - center[1] - R,
                2*R, 2*R);
    }
    private void DrawPolyline(Point2D[] points)
    {
        for (int i = 0; i < points.length - 1; i++){
            DrawSegment(points[i].x, points[i+1].x);
        }
    }
    private void DrawPolygon(Point2D[] points)
    {
        DrawPolyline(points);
        DrawSegment(points[points.length-1].x, points[0].x);
    }
    @FXML
    private void Clear_Click() {
        MainCanvas.getGraphicsContext2D().clearRect(0,0,
                                                    MainCanvas.getWidth(),
                                                    MainCanvas.getHeight());
        shapesList.clear();
        RedrawMainCanvas();
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
    public void ShapeArea_Click(ActionEvent actionEvent) {
        CreateShapeStatPopup("area");
    }
    public void ShapePerimeter_Click(ActionEvent actionEvent) {
        CreateShapeStatPopup("perimeter");
    }
    public void CreateShapeStatPopup(String popupName){
        VBox root = new VBox();
        ComboBox shapesCBox = new ComboBox();
        Button calcArea = new Button("Calculate " + popupName); calcArea.setMaxSize(200, 20);
        Button cancel = new Button("Cancel"); cancel.setMaxSize(200, 20);
        for (IShape shape : shapesList)
            shapesCBox.getItems().add(shape.toString());
        shapesCBox.setMaxSize(200, 20);
        shapesCBox.valueProperty().addListener(new ChangeListener() {
               @Override
               public void changed(ObservableValue observableValue, Object o, Object t1) {
                   calcArea.setDisable(false);
               }
        });
        calcArea.setDisable(true);
        calcArea.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (popupName=="perimeter")
                PerimeterOrArea.setText("Perimeter: " + shapesList.get(shapesCBox.getSelectionModel().getSelectedIndex()).length());
            else if (popupName=="area")
                PerimeterOrArea.setText("Area: " + shapesList.get(shapesCBox.getSelectionModel().getSelectedIndex()).square());
            ((Stage)calcArea.getScene().getWindow()).close();
        });
        cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            ((Stage)cancel.getScene().getWindow()).close();
        });
        root.getChildren().add(shapesCBox);
        root.getChildren().add(calcArea);
        root.getChildren().add(cancel);

        Scene scene = new Scene(root, 200, 100);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void ShowSaveFileDialog(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/src/main/presets"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        StringBuilder sb = new StringBuilder();
        for (IShape shape : shapesList){
            sb.append(shape.toString().split(": ")[0] + "\n");
            sb.append(shape.toString().split(": ")[1]);
            sb.append("\n");
        }
        File file = fileChooser.showSaveDialog(MainCanvas.getScene().getWindow());
        if(file != null){
            try {
                Files.write( file.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void ShowUploadFileDialog(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/src/main/presets"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(MainCanvas.getScene().getWindow());
        if(file != null){
            try {
                String s = Files.readAllLines(file.toPath()).toString();
                System.out.println(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void ShowSaveImageDialog(ActionEvent actionEvent) {
        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/src/main/screenshots"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(MainCanvas.getScene().getWindow());
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int)MainCanvas.getWidth(), (int)MainCanvas.getHeight());
                MainCanvas.snapshot(null, writableImage);
                //RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error!");
            }
        }*/
    }
}