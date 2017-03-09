/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterRoomControllerFx.rooms;

import MasterRoomControllerFx.MainScreenController;
import MasterRoomControllerFx.rooms.charts.RoomChartController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Bartek
 */
public class RoomScreenFx3Controller implements Initializable {
    
    private int id = 3;
    private boolean roomInited = false;
    private MainScreenController screenController;
    private PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(10));
    
    @FXML private Label lblTemp; 
    @FXML private Label lblHum;   
    @FXML private Label lblLight;
    @FXML private Label lblFire; 
    @FXML private Label lblMotion;   
    @FXML private Label lblRoom;
    @FXML private Label backIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        backIcon.toFront();
        pause.setOnFinished(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                if(!roomInited) {
                    lblRoom.setText(screenController.getName(0,2));
                    roomInited = true;
                }
                setFire();
                setHumidity();
                setLight();
                setMotion();
                setTemp();
                pause.setDuration(Duration.seconds(5));
                pause.play();
            }
        });
        pause.play();
        

    }
    
    @FXML
    private void closeStageEvent(MouseEvent event) 
    {
        Stage stage = (Stage) backIcon.getScene().getWindow();
        stage.hide();
    }
    
    @FXML void chartShowEvent(MouseEvent event) {
        try {
            javafx.scene.Parent root2;
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
            root2 = loader.load(getClass().getResource("charts/RoomChart1.fxml").openStream());
            Stage roomScreen = new javafx.stage.Stage();
            RoomChartController chartController = loader.getController();
            chartController.setRoomRow(0);
            chartController.setRoomColumn(2);
            roomScreen.setTitle("RoomChart");
            roomScreen.setScene(new javafx.scene.Scene(root2, 800, 480));
            roomScreen.show();
        } catch (IOException ex) {
            Logger.getLogger(RoomScreenFx2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setController(MainScreenController main) {
        screenController = main;
    }
    
    
     public void setTemp()
    {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    lblTemp.setText(screenController.getTemp(0, 2));
                }
            });  
    }
    
    public void setHumidity()
    {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    lblHum.setText(screenController.getHum(0, 2));
                }
            });
    }
    
    public void setLight()
    {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    lblLight.getStyleClass().clear();
                    ObservableList<String> style = screenController.getLight(0,2);
                    lblLight.getStyleClass().add(style.get(0));
                }
            });   
    }
    
    public void setFire()
    {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    lblFire.getStyleClass().clear();
                    ObservableList<String> style = screenController.getFire(0,2);
                    lblFire.getStyleClass().add(style.get(0));
                }
            });  
    }
    public void setMotion()
    {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    lblMotion.getStyleClass().clear();
                    ObservableList<String> style = screenController.getMotion(0,2);
                    lblMotion.getStyleClass().add(style.get(0));
                }
            });  
    }
    
}
