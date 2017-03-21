/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.input.TouchEvent;

/**
 * FXML Controller class
 *
 * @author Bartek
 */
public class RoomChartController implements Initializable {
    @FXML Label backIcon;
    @FXML AreaChart areaChart;
    @FXML CategoryAxis xAxis;
    @FXML NumberAxis yAxis;

    int roomRow;
    int roomColumn;
    
    ArrayList<Float> temp = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<Float> hum = new ArrayList<>();
    
    private PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(10));

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pause.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                getTempData();
                getHumData();

                xAxis.setCategories(FXCollections.<String>observableArrayList
                (Arrays.asList("00:00","01:00","02:00","03:00",
                        "04:00","05:00","06:00","07:00","08:00",
                        "09:00","10:00","11:00","12:00","13:00",
                        "14:00","15:00","16:00","17:00","18:00",
                        "19:00","20:00","21:00","22:00","23:00","24:00")));      
                yAxis.setLowerBound(-20);
                yAxis.setUpperBound(100);
                yAxis.setTickUnit(0.5);
                areaChart.setTitle("Temperature and Humidity Monitoring");

                XYChart.Series<String,Number> seriesTemp= new XYChart.Series();
                seriesTemp.setName("Temperature (°C)");
                if(!temp.isEmpty()) {
                    for(int i = 0; i < temp.size(); i++){
                        seriesTemp.getData().add(new XYChart.Data<String,Number>(time.get(i), temp.get(i)));
                    }
                    areaChart.getData().add(seriesTemp);
                }


                XYChart.Series seriesHumidity = new XYChart.Series();
                seriesHumidity.setName("Humidity (%)");
                if(!hum.isEmpty()) {
                    for(int i = 0; i < hum.size(); i++){
                        seriesHumidity.getData().add(new XYChart.Data<String,Number>(time.get(i), hum.get(i)));
                    }
                    areaChart.getData().add(seriesHumidity);
                }           
            }
        });
        pause.play();
    }    
    
    @FXML
    private void closeStageEvent(TouchEvent event) 
    {
        Stage stage = (Stage) backIcon.getScene().getWindow();
        stage.hide();
    }
    
    public void getTempData() {
        try {
            File csvData = new File("tempHistory"+roomRow+roomColumn+".csv");
            if(csvData.exists()) {
                CSVParser parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.EXCEL.withDelimiter(';'));                      
                for (CSVRecord csvRecord : parser) {
                    for(int i = 0; i < csvRecord.size(); i++) {
                        if(i == 0) temp.add(Float.parseFloat(csvRecord.get(i)));
                        if(i == 1) time.add(csvRecord.get(i));
                    }                   
                }   
            }
        } catch (IOException ex) {
            Logger.getLogger(RoomChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getHumData() {
        try {
            File csvData = new File("humHistory"+roomRow+roomColumn+".csv");
            if(csvData.exists()) {
                CSVParser parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.EXCEL.withDelimiter(';'));                      
                for (CSVRecord csvRecord : parser) {
                    for(int i = 0; i < csvRecord.size()-1; i++) {
                        hum.add(Float.parseFloat(csvRecord.get(i)));
                    }                   
                }   
            }
        } catch (IOException ex) {
            Logger.getLogger(RoomChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setRoomRow(int row){
        this.roomRow = row;
    }
    
    public void setRoomColumn(int column){
        this.roomColumn = column;
    }
    
}
