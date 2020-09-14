package com.Covid.Gui;

import com.Covid.datafetch.DataProviderService;
import com.Covid.datafetch.Model.CovidDataModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Launch extends Application {
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);
        primaryStage.show();

        Stage secoundaryStage = new Stage();
        secoundaryStage.initStyle(StageStyle.UNDECORATED);
        secoundaryStage.initOwner(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("/com/Covid/Gui/widget/widget.fxml"));
        Scene scene = new Scene(root);
        secoundaryStage.setScene(scene);
        secoundaryStage.show();

        //Make the plug in right-top
        Rectangle2D visualBound = Screen.getPrimary().getVisualBounds();
        secoundaryStage.setX(visualBound.getMaxX()-25-scene.getWidth());
        secoundaryStage.setY(visualBound.getMinY()+25);

        //Add support for drage and move the plug
        scene.setOnMousePressed(event -> {
            xOffset = secoundaryStage.getX() - event.getScreenX();
            yOffset = secoundaryStage.getY() - event.getScreenY();
        });

        scene.setOnMouseDragged(event -> {
            secoundaryStage.setX(event.getScreenX()+xOffset);
            secoundaryStage.setY(event.getScreenY()+yOffset);
        });

        //primaryStage.setScene(scene);
        //primaryStage.initStyle();
        //primaryStage.show();

        //CovidDataModel DataModel = new DataProviderService().getData("Israel");
        //System.out.println(DataModel);



    }

    public static void main(String[] args){
        launch(args);
    }
}
