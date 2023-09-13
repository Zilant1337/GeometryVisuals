package Geometry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.canvas.*;

public class HelloApplication {

    private Scene mainScene;
    private Stage mainStage;
    private GridPane mainGrid;
    private Canvas mainCanvas;
    private GridPane currentGrid;
    private Point2D canvasCenter;
    private Shape itemToRemove;
    private Shape itemToTransform;
    private Shape itemToIntersect1;
    private Shape itemToIntersect2;
    private double rotationAngle;
    private double[] positionShiftVector;
    private int numberOfPoints;
    private double radiusValue;




/*
    private void Clear_Click(object sender, RoutedEventArgs e)
    {
        try {
            if (shapesMap.Count != 0)
            {
                foreach (UIElement key in shapesMap.Keys)
                mainCanvas.getGraphicsContext2D().Remove(key);
                shapesMap.Clear();
                ShapesListComboBox.Items.Clear();
                MovableShapesListComboBox.Items.Clear();
                ShapesToIntersectListComboBox.Items.Clear();
                ShapesToIntersect2ListComboBox.Items.Clear();
                MessageBox.Show("Canvas cleared.", "Success", MessageBoxButton.OK, MessageBoxImage.Asterisk);
            }
            else
            {
                MessageBox.Show("Error: No shapes found.", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
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

    private void Perimeter_Click(object sender, RoutedEventArgs e)
    {
        try {
            if (shapesMap.Count != 0)
            {
                double num1 = 0.0;
                foreach (Shape key in shapesMap.Keys)
                num1 += shapesMap[key].length();
                PerimeterOrArea.Text = "Perimeter:";
                PerimeterOrAreaField.Text = num1.ToString();
                PerimeterOrArea.setVisible(true);
                PerimeterOrAreaField.setVisible(true);
                int num2 = (int)MessageBox.Show("Perimeter has been calculated.\nCheck the right corner.", "Success", MessageBoxButton.OK, MessageBoxImage.Asterisk);
            }
            else
            {
                int num = (int)MessageBox.Show("Error: No shapes to measure.", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
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

    private void Area_Click(object sender, RoutedEventArgs e)
    {
        try {
            if (shapesMap.Count != 0)
            {
                double num1 = 0.0;
                foreach (Shape key in shapesMap.Keys)
                num1 += shapesMap[key].square();
                PerimeterOrArea.Text = "Area:";
                PerimeterOrAreaField.Text = num1.ToString();
                PerimeterOrArea.setVisible(true);
                PerimeterOrAreaField.setVisible(true);
                int num2 = (int)MessageBox.Show("Area has been calculated.\nCheck the right corner.", "Success", MessageBoxButton.OK, MessageBoxImage.Asterisk);
            }
            else
            {
                int num = (int)MessageBox.Show("Error: No shapes to measure.", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
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





    private void InsertItemToShapeListComboBoxes(string value, int index)
    {
        try {
            ShapesListComboBox.Items.Insert(index, value);
            ShapesToIntersectListComboBox.Items.Insert(index, value);
            ShapesToIntersect2ListComboBox.Items.Insert(index, value);
            MovableShapesListComboBox.Items.Insert(index, value);
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







    private void PointsUp_Click(object sender, RoutedEventArgs e)
    {
        try {
            if (numberOfPoints < 18)
            {
                PointsNumber.Text = (++numberOfPoints).ToString();
                if (numberOfPoints < 7)
                    EnablePoint(FirstToSixthPointsGrid, (numberOfPoints - 1) % 6 * 5);
                else if (numberOfPoints < 13)
                    EnablePoint(SeventhToTwlelvthPointsGrid, (numberOfPoints - 1) % 6 * 5);
                else
                    EnablePoint(ThirteenthToEighteenthPointsGrid, (numberOfPoints - 1) % 6 * 5);
            }
            else
            {
                MessageBox.Show("Error: Can't go higher", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
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

    private void PointsDown_Click(object sender, RoutedEventArgs e)
    {
        try {
            if (numberOfPoints > 1)
            {
                PointsNumber.Text = (--numberOfPoints).ToString();
                for (int index = 25; index > -1 && index > 5 * numberOfPoints - 65; index -= 5)
                    DisablePoint(ThirteenthToEighteenthPointsGrid, index);
                for (int index = 25; index > -1 && index > 5 * numberOfPoints - 35; index -= 5)
                    DisablePoint(SeventhToTwlelvthPointsGrid, index);
                for (int index = 25; index > -1 && index > 5 * numberOfPoints - 5; index -= 5)
                    DisablePoint(FirstToSixthPointsGrid, index);
            }
            else
            {
                MessageBox.Show("Error: Can't go lower", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
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

    private void ShapeRadius_TextChanged(object sender, TextChangedEventArgs e)
    {
        try {
            if (!ShapeRadius.IsEnabled)
                return;
            if (double.TryParse(ShapeRadius.Text, out radiusValue) & radiusValue >= 0.0)
                ShapeRadius.Text = radiusValue.ToString();
            else if (ShapeRadius.Text != "")
            {
                MessageBox.Show("Error: Inappropriate radius value", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                radiusValue = 0.0;
                ShapeRadius.Text = radiusValue.ToString();
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

    private void ShapesListComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        try
        {
            int i = ShapesListComboBox.SelectedIndex, j = 0;
            foreach (Shape sh in shapesMap.Keys.ToArray().ToList())
            {
                if (i == j)
                {
                    itemToRemove = sh; break;
                }
                j++;
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

    private void Remove_Click(object sender, RoutedEventArgs e)
    {
        try {
            if (ShapesListComboBox.Text == "")
                MessageBox.Show("Error: Shape to remove haven't been chosen", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
            else {
                shapesMap.Remove(itemToRemove);
                mainCanvas.getGraphicsContext2D().Remove(itemToRemove);
                MovableShapesListComboBox.Items.RemoveAt(ShapesListComboBox.SelectedIndex);
                ShapesToIntersectListComboBox.Items.RemoveAt(ShapesListComboBox.SelectedIndex);
                ShapesToIntersect2ListComboBox.Items.RemoveAt(ShapesListComboBox.SelectedIndex);
                ShapesListComboBox.Items.Remove(ShapesListComboBox.SelectedItem);
                MessageBox.Show("Item successfully removed", "Success", MessageBoxButton.OK, MessageBoxImage.Asterisk);
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

    private void XPositionShiftValue_TextChanged(object sender, TextChangedEventArgs e)
    {
        try
        {
            double posX = 0;
            if (!XPositionShiftValue.IsEnabled)
                return;
            else if (!Double.TryParse(XPositionShiftValue.Text, out posX) && XPositionShiftValue.Text != "" && XPositionShiftValue.Text != "-")
            {
                MessageBox.Show("Error: Inappropriate movement vector coordinate value", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                positionShiftVector[1] = 0;
                XPositionShiftValue.Text = positionShiftVector[1].ToString();
            }
            positionShiftVector[0] = posX;
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

    private void YPositionShiftValue_TextChanged(object sender, TextChangedEventArgs e)
    {
        try {
            double posY = 0;
            if (!YPositionShiftValue.IsEnabled)
                return;
            else if (!Double.TryParse(YPositionShiftValue.Text, out posY) && YPositionShiftValue.Text != "" && YPositionShiftValue.Text != "-")
            {
                MessageBox.Show("Error: Inappropriate movement vector coordinate value", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                positionShiftVector[0] = 0;
                YPositionShiftValue.Text = positionShiftVector[0].ToString();
            }
            positionShiftVector[1] = posY;
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

    private void ShapeRotationValue_TextChanged(object sender, TextChangedEventArgs e)
    {
        try
        {
            if (!ShapeRotationValue.IsEnabled)
                return;
            else if (!Double.TryParse(ShapeRotationValue.Text, out rotationAngle) && ShapeRotationValue.Text != "" && ShapeRotationValue.Text != "-")
            {
                MessageBox.Show("Error: Inappropriate rotation angle value", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                rotationAngle = 0.0;
                ShapeRotationValue.Text = rotationAngle.ToString();
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

    private void SwitchAxisButtonUp_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            AxisToReflectShapeOver.Text = AxisToReflectShapeOver.Text == "x" ? "y" : "x";
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

    private void SwitchAxisButtonDown_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            AxisToReflectShapeOver.Text = AxisToReflectShapeOver.Text == "x" ? "y" : "x";
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

    private void MovementTypeComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        try {
            string str = ((ContentControl)MovementTypeComboBox.SelectedItem).Content.ToString();
            if (str == "") return;
            XPositionShiftValue.IsEnabled = false;
            YPositionShiftValue.IsEnabled = false;
            ShapeRotationValue.IsEnabled = false;
            SwitchAxisButtonDown.IsEnabled = false;
            SwitchAxisButtonUp.IsEnabled = false;
            rotationAngle = 0; positionShiftVector[0] = 0; positionShiftVector[1] = 0;
            XPositionShiftValue.Text = "0"; YPositionShiftValue.Text = "0";
            ShapeRotationValue.Text = "0"; AxisToReflectShapeOver.Text = "x";
            if (str == "Position")
            {
                XPositionShiftValue.IsEnabled = true;
                YPositionShiftValue.IsEnabled = true;
            }
            else if (str == "Rotation")
                ShapeRotationValue.IsEnabled = true;
            else if (str == "Axis Reflection")
            {
                SwitchAxisButtonDown.IsEnabled = true;
                SwitchAxisButtonUp.IsEnabled = true;
            }
            if (string.IsNullOrEmpty(MovableShapesListComboBox.Text)) return;
            MovementValuesGrid.setVisible(true);

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

    private void MovableShapesListComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        try {
            int i = MovableShapesListComboBox.SelectedIndex, j = 0;
            foreach (Shape sh in shapesMap.Keys.ToArray().ToList())
            {
                if (i == j)
                {
                    itemToTransform = sh;
                    break;
                }
                j++;
            }
            if (string.IsNullOrEmpty(MovementTypeComboBox.Text)) return;
            MovementValuesGrid.setVisible(true);

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

    private void Move_Click(object sender, RoutedEventArgs e)
    {
        try {
            if (string.IsNullOrEmpty(MovementTypeComboBox.Text) || string.IsNullOrEmpty(MovableShapesListComboBox.Text))
            {
                MessageBox.Show("Error: Shape to move have not been chosen", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                return;
            }

            IShape ishape = shapesMap[itemToTransform];
            Shape shape = itemToTransform;
            int index = MovableShapesListComboBox.SelectedIndex;
            shapesMap.Remove(itemToTransform);
            mainCanvas.getGraphicsContext2D().Remove(itemToTransform);
            ShapesListComboBox.Items.RemoveAt(index);
            ShapesToIntersectListComboBox.Items.RemoveAt(index);
            ShapesToIntersect2ListComboBox.Items.RemoveAt(index);
            MovableShapesListComboBox.Items.Remove(MovableShapesListComboBox.SelectedItem);

            if (MovementTypeComboBox.Text == "Position")
            {
                if (XPositionShiftValue.Text == "-" || YPositionShiftValue.Text == "-")
                {
                    MessageBox.Show("Error: Inappropriate coordinate value", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                    return;
                }
                ishape.shift(new Point2D(positionShiftVector));

                if (shape.GetType() == typeof(Line))
                {
                    Line l = (shape as Line);
                    l.setStartX(l.X1 + positionShiftVector[0];
                    l.setEndX(l.X2 + positionShiftVector[0];
                    l.setStartY(l.Y1 - positionShiftVector[1];
                    l.setEndY(l.Y2 - positionShiftVector[1];
                }
                else if (shape.GetType() == typeof(Ellipse))
                {
                    Ellipse el = (shape as Ellipse);
                    el.Margin = new Thickness(el.Margin.Left + positionShiftVector[0], el.Margin.Top - positionShiftVector[1], 0, 0);
                }
                else if (shape.GetType() == typeof(System.Windows.Shapes.Polyline))
                {
                    System.Windows.Shapes.Polyline pl = (shape as System.Windows.Shapes.Polyline);
                    PointCollection pc = new PointCollection();
                    for (int i = 0; i < pl.Points.Count; i++)
                    {
                        pc.Add(pl.Points[i]); pc[i] = new Point2D(pc[i].X + positionShiftVector[0], pc[i].Y - positionShiftVector[1]);
                    }
                    pl.Points = pc;
                }
                else if (shape.GetType() == typeof(Polygon))
                {
                    Polygon pg = (shape as Polygon);
                    PointCollection pc = new PointCollection();
                    for (int i = 0; i < pg.Points.Count; i++)
                    {
                        pc.Add(pg.Points[i]); pc[i] = new Point2D(pc[i].X + positionShiftVector[0], pc[i].Y - positionShiftVector[1]);
                    }
                    pg.Points = pc;
                }
            }
            else if (MovementTypeComboBox.Text == "Rotation")
            {
                if (ShapeRotationValue.Text == "-")
                {
                    MessageBox.Show("Error: Inappropriate coordinate value", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                    return;
                }
                ishape = ishape.rot(rotationAngle);

                if (shape.GetType() == typeof(Line))
                {
                    Line l = (shape as Line);
                    Point2D p1 = new Point2D();
                    Point2D p2 = new Point2D();
                    p1.setX(new double[]{ l.X1 - canvasCenter.X, l.Y1 - canvasCenter.Y });
                    p2.setX(new double[] { l.X2 - canvasCenter.X, l.Y2 - canvasCenter.Y });
                    p1.rot(-rotationAngle); p2.rot(-rotationAngle);
                    l.setStartX(canvasCenter.X + p1.getX()[0];
                    l.setEndX(canvasCenter.X + p2.getX()[0];
                    l.setStartY(canvasCenter.Y + p1.getX()[1];
                    l.setEndY(canvasCenter.Y + p2.getX()[1];
                }
                else if (shape.GetType() == typeof(System.Windows.Shapes.Polyline))
                {
                    System.Windows.Shapes.Polyline pl = (shape as System.Windows.Shapes.Polyline);
                    PointCollection pc = new PointCollection();
                    for (int i = 0; i < pl.Points.Count; i++)
                    {
                        Point2D p1 = new Point2D(new double[] { pl.Points[i].X - canvasCenter.X, pl.Points[i].Y - canvasCenter.Y });
                        p1.rot(-rotationAngle);
                        pc.Add(pl.Points[i]); pc[i] = new Point2D(p1.x[0] + canvasCenter.X, p1.x[1] + canvasCenter.Y);
                    }
                    pl.Points = pc;
                }
                else if (shape.GetType() == typeof(Polygon))
                {
                    System.Windows.Shapes.Polygon pg = (shape as System.Windows.Shapes.Polygon);
                    PointCollection pc = new PointCollection();
                    for (int i = 0; i < pg.Points.Count; i++)
                    {
                        Point2D p1 = new Point2D(new double[] { pg.Points[i].X - canvasCenter.X, pg.Points[i].Y - canvasCenter.Y });
                        p1.rot(-rotationAngle);
                        pc.Add(pg.Points[i]); pc[i] = new Point2D(p1.x[0] + canvasCenter.X, p1.x[1] + canvasCenter.Y);
                    }
                    pg.Points = pc;
                }
                else if (shape.GetType() == typeof(Ellipse))
                {
                    Ellipse el = (shape as Ellipse);
                    Point2D center = new Point2D(new double[] { el.Margin.Left + el.mainStage.getWidth()/2 - canvasCenter.X,
                            canvasCenter.Y - el.Margin.Top - el.mainStage.getHeight()/2 });
                    center.rot(rotationAngle);
                    el.Margin = new Thickness(canvasCenter.X + center.x[0] - el.mainStage.getWidth()/2, canvasCenter.Y - center.x[1] - el.mainStage.getHeight()/2, 0.0, 0.0);
                }
            }
            else if (MovementTypeComboBox.Text == "Axis Reflection")
            {
                int axis = AxisToReflectShapeOver.Text == "x" ? 0 : 1;
                ishape.symAxis(axis);
                if (shape.GetType() == typeof(Line))
                {
                    Line l = (shape as Line);
                    l.setStartX(axis == 0 ? l.X1 : 2 * canvasCenter.X - l.X1;
                    l.setEndX(axis == 0 ? l.X2 : 2 * canvasCenter.X - l.X2;
                    l.setStartY(axis == 0 ? 2 * canvasCenter.Y - l.Y1 : l.Y1;
                    l.setEndY(axis == 0 ? 2 * canvasCenter.Y - l.Y2 : l.Y2;
                }
                else if (shape.GetType() == typeof(Ellipse))
                {
                    Ellipse el = (shape as Ellipse);
                    if (axis==0)
                        el.Margin = new Thickness(el.Margin.Left, 2 * canvasCenter.Y - el.Margin.Top - el.mainStage.getHeight(), 0.0, 0.0);
                    else if (axis==1)
                        el.Margin = new Thickness(2 * canvasCenter.X - el.Margin.Left - el.mainStage.getWidth(), el.Margin.Top , 0.0, 0.0);
                }
                else if (shape.GetType() == typeof(System.Windows.Shapes.Polyline))
                {
                    System.Windows.Shapes.Polyline pl = (shape as System.Windows.Shapes.Polyline);
                    PointCollection pc = new PointCollection();
                    for (int i = 0; i < pl.Points.Count; i++)
                    {
                        pc.Add(pl.Points[i]);
                        if (axis == 0)
                            pc[i] = new Point2D(pc[i].X, 2 * canvasCenter.Y - pc[i].Y);
                        else if (axis == 1)
                            pc[i] = new Point2D(2 * canvasCenter.X - pc[i].X, pc[i].Y);
                    }
                    pl.Points = pc;
                }
                else if (shape.GetType() == typeof(Polygon))
                {
                    Polygon pg = (shape as Polygon);
                    PointCollection pc = new PointCollection();
                    for (int i = 0; i < pg.Points.Count; i++)
                    {
                        pc.Add(pg.Points[i]);
                        if (axis == 0)
                            pc[i] = new Point2D(pc[i].X, 2 * canvasCenter.Y - pc[i].Y);
                        else if (axis == 1)
                            pc[i] = new Point2D(2 * canvasCenter.X - pc[i].X, pc[i].Y);
                    }
                    pg.Points = pc;
                }
            }
            shapesMap.Add(shape, ishape);
            mainCanvas.getGraphicsContext2D().Insert(index, shape);
            InsertItemToShapeListComboBoxes(ishape, index);
            MessageBox.Show("Item successfully transformed", "Success", MessageBoxButton.OK, MessageBoxImage.Asterisk);

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

    private void InsertItemToShapeListComboBoxes(IShape ishape, int index)
    {
        if (ishape.GetType() == typeof(Circle)) InsertItemToShapeListComboBoxes((ishape as Circle).toString(), index);
        if (ishape.GetType() == typeof(Segment)) InsertItemToShapeListComboBoxes((ishape as Segment).toString(), index);
        if (ishape.GetType() == typeof(NGon)) InsertItemToShapeListComboBoxes((ishape as NGon).toString(), index);
        if (ishape.GetType() == typeof(GeometryClasses.Polyline)) InsertItemToShapeListComboBoxes((ishape as GeometryClasses.Polyline).toString(), index);
        if (ishape.GetType() == typeof(GeometryClasses.Rectangle)) InsertItemToShapeListComboBoxes((ishape as GeometryClasses.Rectangle).toString(), index);
        if (ishape.GetType() == typeof(QGon)) InsertItemToShapeListComboBoxes((ishape as QGon).toString(), index);
        if (ishape.GetType() == typeof(TGon)) InsertItemToShapeListComboBoxes((ishape as TGon).toString(), index);
        if (ishape.GetType() == typeof(Trapeze)) InsertItemToShapeListComboBoxes((ishape as Trapeze).toString(), index);
    }

    private void ShapesToIntersectListComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        try {
            int i = ShapesToIntersectListComboBox.SelectedIndex, j = 0;
            if (i == ShapesToIntersect2ListComboBox.SelectedIndex)
            {
                ShapesToIntersectListComboBox.SelectedItem = null;
                return;
            }
            if (itemToIntersect1!=null) itemToIntersect1.setStroke(Brushes.Black;
            foreach (Shape sh in shapesMap.Keys.ToArray().ToList())
            {
                if (i == j)
                {
                    sh.setStroke(Brushes.Red;
                    itemToIntersect1 = sh; break;
                }
                else if (sh != itemToIntersect2) sh.setStroke(Brushes.Black;
                j++;
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

    private void ShapesToIntersect2ListComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        try
        {
            int i = ShapesToIntersect2ListComboBox.SelectedIndex, j = 0;
            if (i == ShapesToIntersectListComboBox.SelectedIndex)
            {
                ShapesToIntersect2ListComboBox.SelectedItem = null;
                return;
            }
            if (itemToIntersect2 != null) itemToIntersect2.setStroke(Brushes.Black;
            foreach (Shape sh in shapesMap.Keys.ToArray().ToList())
            {
                if (i == j)
                {
                    sh.setStroke(Brushes.Red;
                    itemToIntersect2 = sh; break;
                }
                else if (sh != itemToIntersect1) sh.setStroke(Brushes.Black;
                j++;
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

    private void Intersect_Click(object sender, RoutedEventArgs e)
    {
        try {
            if (string.IsNullOrEmpty(ShapesToIntersectListComboBox.Text) || string.IsNullOrEmpty(ShapesToIntersect2ListComboBox.Text))
            {
                MessageBox.Show("Error: Choice of shapes for intersection has not been complete.", "Error", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                return;
            }
            PerimeterOrArea.Text = "Intersection:";
            if (shapesMap[itemToIntersect1].cross(shapesMap[itemToIntersect2]))
                PerimeterOrAreaField.Text = "Intersect.";
            else
                PerimeterOrAreaField.Text = "Don't intersect.";
            MessageBox.Show("Items successfully checked on intersection", "Success", MessageBoxButton.OK, MessageBoxImage.Asterisk);
            OpenAnotherGrid(mainGrid);
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
    }*/
}
