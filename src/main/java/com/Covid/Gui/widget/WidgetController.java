package com.Covid.Gui.widget;

import com.Covid.Config.ConfigModel;
import com.Covid.Config.ConfigurationService;
import com.Covid.datafetch.DataProviderService;
import com.Covid.datafetch.Model.CountryData;
import com.Covid.datafetch.Model.CovidDataModel;
import com.Covid.datafetch.Model.GlobalData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WidgetController implements Initializable {
    private ScheduledExecutorService executorService;
    private ConfigModel configModel;

    @FXML
    public AnchorPane rootPane;
    @FXML
    public Text textGlobalReport,textCountryReport,textCountryCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            configModel = new ConfigurationService().getConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeSchedular();
        initializeContaxtManu();
        textCountryCode.setText(configModel.getCountryCode());
    }

    private void initializeSchedular() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::loadData,0,
                configModel.getRefreshIntervalInSecounds(),
                TimeUnit.SECONDS);
    }
    private void initializeContaxtManu(){
        //Exit the program
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(event -> {
            System.exit(0);
        });

        // Refresh data
        MenuItem refreshItem = new MenuItem("Refresh");
        refreshItem.setOnAction(event -> {
            executorService.schedule(this::loadData,0,TimeUnit.SECONDS);
        });

        final ContextMenu contextMenu = new ContextMenu(exitItem,refreshItem);
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED,event -> {
            if (event.isSecondaryButtonDown()){
                contextMenu.show(rootPane,event.getScreenX(),event.getScreenY());
            }else {
                if (contextMenu.isShowing()){
                    contextMenu.hide();
                }
            }
        });

    }

    private void loadData(){
        DataProviderService dataProviderService = new DataProviderService();
        CovidDataModel covidDataModel = dataProviderService.getData(configModel.getCountryName());

        Platform.runLater(()->{
            inflateData(covidDataModel);
        });
   }

    private void inflateData(CovidDataModel covidDataModel){
        //Insert the global data
        GlobalData globalData = covidDataModel.getGlobalData();
        textGlobalReport.setText(getDataFormat(globalData.getCases(),globalData.getRecovered(),globalData.getDeaths()));

        //Insert the country data
        CountryData countryData= covidDataModel.getCountryData();
        textCountryReport.setText(getDataFormat(countryData.getCases(),countryData.getRecovered(),countryData.getDeaths()));

        //put size to scene
        readJustStage(textCountryCode.getScene().getWindow());
    }

    private void readJustStage(Window stage){
        stage.sizeToScene();

        Rectangle2D visualBound = Screen.getPrimary().getVisualBounds();
        stage.setX(visualBound.getMaxX()-25-textCountryCode.getScene().getWidth());
        stage.setY(visualBound.getMinY()+25);
    }

    private String getDataFormat(long totalCases, long recoveredCases , long totalDeaths){
        return String.format(
                "Cases: %-8d | Recovered: %-6d | Deaths: %-6d ",
                totalCases,recoveredCases,totalDeaths);
    }

}
