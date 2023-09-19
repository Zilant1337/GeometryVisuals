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
import java.util.List;
import java.util.regex.Pattern;
import javax.imageio.*;
import javafx.embed.swing.SwingFXUtils;

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
        try {
            MainCanvas.getGraphicsContext2D().clearRect(0, 0, MainCanvas.getWidth(), MainCanvas.getHeight());
            DrawMainAxis();
            DrawShapes();
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
    private void DrawMainAxis()
    {
        try {
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
    private void DrawShapes()
    {
        try {
            for (int i = 0; i < shapesList.size(); i++) {
                if (redColoredShapesIndices[0] == i || redColoredShapesIndices[1] == i) {
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
                if (redColoredShapesIndices[0] == i | redColoredShapesIndices[1] == i) {
                    MainCanvas.getGraphicsContext2D().setStroke(Color.BLACK);
                    MainCanvas.getGraphicsContext2D().setFill(Color.BLACK);
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
    private Segment DrawSegment(double[] start, double[] end)
    {
        try {
            MainCanvas.getGraphicsContext2D().strokeLine(MainCanvas.getWidth() / 2 + start[0],
                    MainCanvas.getHeight() / 2 - start[1],
                    MainCanvas.getWidth() / 2 + end[0],
                    MainCanvas.getHeight() / 2 - end[1]);
            return new Segment(new Point2D(start), new Point2D(end));
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
        return null;
    }
    private void DrawCircle(double[] center, double R)
    {
        try {
            MainCanvas.getGraphicsContext2D().strokeOval(MainCanvas.getWidth() / 2 + center[0] - R,
                    MainCanvas.getHeight() / 2 - center[1] - R,
                    2 * R, 2 * R);
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
    private void DrawPolyline(Point2D[] points)
    {
        try {
            for (int i = 0; i < points.length - 1; i++) {
                DrawSegment(points[i].x, points[i + 1].x);
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
    private void DrawPolygon(Point2D[] points)
    {
        try {
            DrawPolyline(points);
            DrawSegment(points[points.length - 1].x, points[0].x);
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
    private void Clear_Click() {
        try {
            MainCanvas.getGraphicsContext2D().clearRect(0, 0,
                    MainCanvas.getWidth(),
                    MainCanvas.getHeight());
            shapesList.clear();
            RedrawMainCanvas();
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
    private void Perimeter_Click() {
        try {
            double P = 0;
            for (IShape shape : shapesList)
                P += shape.length();
            PerimeterOrArea.setText("Perimeter: " + String.valueOf(P));
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
    private void Area_Click() {
        try {
            double S = 0;
            for (IShape shape : shapesList)
                S += shape.square();
            PerimeterOrArea.setText("Area: " + String.valueOf(S));
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
    public void ShapeArea_Click(ActionEvent actionEvent) {
        CreateShapeStatPopup("area");
    }
    public void ShapePerimeter_Click(ActionEvent actionEvent) {
        CreateShapeStatPopup("perimeter");
    }
    public void CreateShapeStatPopup(String popupName){
        try {
            VBox root = new VBox();
            ComboBox shapesCBox = new ComboBox();
            Button calcArea = new Button("Calculate " + popupName);
            calcArea.setMaxSize(200, 20);
            Button cancel = new Button("Cancel");
            cancel.setMaxSize(200, 20);
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
                if (popupName == "perimeter")
                    PerimeterOrArea.setText("Perimeter: " + shapesList.get(shapesCBox.getSelectionModel().getSelectedIndex()).length());
                else if (popupName == "area")
                    PerimeterOrArea.setText("Area: " + shapesList.get(shapesCBox.getSelectionModel().getSelectedIndex()).square());
                ((Stage) calcArea.getScene().getWindow()).close();
            });
            cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                ((Stage) cancel.getScene().getWindow()).close();
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
    public void ShowSaveFileDialog(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/src/main/presets"));
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            StringBuilder sb = new StringBuilder();
            for (IShape shape : shapesList) {
                sb.append(shape.toString().split(": ")[0] + "\n");
                sb.append(shape.toString().split(": ")[1]);
                sb.append("\n");
            }
            File file = fileChooser.showSaveDialog(MainCanvas.getScene().getWindow());
            if (file != null) {
                try {
                    Files.write(file.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Success");
                    alert.setContentText("Preset successfully exported");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
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
    public void ShowUploadFileDialog(ActionEvent actionEvent) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/src/main/presets"));
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(MainCanvas.getScene().getWindow());
            if(file != null){
                try {
                    List<String> s = Files.readAllLines(file.toPath());
                    String[] specialSymbols = { "[", "{", "(", ")", "}", "]", "Center:", "Radius:"};
                    String params = "";
                    shapesList.clear();
                    for (int i = 0; i < s.size() - 1; i+=2) {
                        params = s.get(i+1);
                        for (String sym : specialSymbols)
                            params = String.join("", params.split(Pattern.quote(sym)));
                        String[] points = params.split(";");
                        Point2D[] point2DCollection = new Point2D[points.length];
                        if (s.get(i).equals("Circle")){
                            Point2D p = new Point2D(new double[]{Double.parseDouble(points[0].split(",")[0]),
                                    Double.parseDouble(points[0].split(",")[1])
                            });
                            double r = Double.parseDouble(points[1]);
                            Circle circle = new Circle(p, r);
                            shapesList.add(circle);
                        }
                        else {
                            if (s.get(i).equals("Segment")) {
                                Point2D p1 = new Point2D(new double[]{Double.parseDouble(points[0].split(",")[0]),
                                        Double.parseDouble(points[0].split(",")[1])
                                });
                                Point2D p2 = new Point2D(new double[]{Double.parseDouble(points[1].split(",")[0]),
                                        Double.parseDouble(points[1].split(",")[1])
                                });
                                Segment segment = new Segment(p1, p2);
                                shapesList.add(segment);
                            }
                            else {
                                for (int j = 0; j < points.length; j++){
                                    point2DCollection[j] = new Point2D(new double[]{Double.parseDouble(points[j].split(",")[0]),
                                            Double.parseDouble(points[j].split(",")[1])
                                    });
                                }
                                if (s.get(i).equals("Polyline")) {
                                    shapesList.add(new Polyline(point2DCollection));
                                }
                                else if (s.get(i).equals("Polygon")) {
                                    shapesList.add(new NGon(point2DCollection));
                                }
                                else if (s.get(i).equals("Triangle")) {
                                    shapesList.add(new TGon(point2DCollection));
                                }
                                else if (s.get(i).equals("Quadrilateral")) {
                                    shapesList.add(new QGon(point2DCollection));
                                }
                                else if (s.get(i).equals("Rectangle")) {
                                    shapesList.add(new Rectangle(point2DCollection));
                                }
                                else if (s.get(i).equals("Trapeze")) {
                                    shapesList.add(new Trapeze(point2DCollection));
                                }
                            }
                        }
                    }
                    RedrawMainCanvas();
                } catch (IOException e) {
                    throw new RuntimeException(e);
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
    public void ShowSaveImageDialog(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/src/main/screenshots"));
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(MainCanvas.getScene().getWindow());
            WritableImage writableImage = new WritableImage((int) MainCanvas.getWidth(), (int) MainCanvas.getHeight());
            MainCanvas.snapshot(null, writableImage);
            System.out.println(writableImage);
            System.out.println(writableImage.getPixelWriter());
            System.out.println(writableImage.getPixelWriter().getPixelFormat());
            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null),
                            "png", file);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Success");
                    alert.setContentText("Image successfully saved");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Error!");
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
}