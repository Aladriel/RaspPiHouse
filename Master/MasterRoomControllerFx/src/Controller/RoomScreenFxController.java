/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.MainScreenController;
import Controller.RoomChartController;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Bartek
 */
public class RoomScreenFxController implements Initializable {
    
    int roomRow;
    int roomColumn;
    private boolean roomInited = false;
    private MainScreenController screenController;
    private PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(10));
    private boolean isVoiceActive = false;
    
    @FXML private Label lblRoomTemp; 
    @FXML private Label lblRoomHum;   
    @FXML private Label lblRoomLight;
    @FXML private Label lblRoomFire; 
    @FXML private Label lblRoomMotion;   
    @FXML private Label lblRoomRoom;
    @FXML private Label backIcon;
    @FXML private Label speakerIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {          
        pause.setOnFinished(new EventHandler<ActionEvent>()
        {
            
            @Override
            public void handle(ActionEvent event) {
                if(!roomInited) {
                    lblRoomRoom.setText(screenController.getName(roomRow,roomColumn));
                    speakerIcon.getStyleClass().add("speakerOff");
                    roomInited = true;
                }
                setFire();
                setHumidity();
                setLight();               
                setMotion();
                setTemp();
                backIcon.toFront();
                pause.setDuration(Duration.seconds(5));
                pause.play();
            }
        });
        pause.play();
        

    }
    
    @FXML
    private void closeStageEvent(TouchEvent event) 
    {
        Stage stage = (Stage) backIcon.getScene().getWindow();
        pause.stop();
        if(isVoiceActive){
            isVoiceActive = !isVoiceActive; 
            setVoice();
            setSpeakerImage();
        }
        stage.hide();
    }
    
    @FXML void chartShowEvent(TouchEvent event) {
        try {
            javafx.scene.Parent root2;
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
            root2 = loader.load(getClass().getResource("charts/RoomChart.fxml").openStream());
            Stage roomScreen = new javafx.stage.Stage();
            RoomChartController chartController = loader.getController();
            chartController.setRoomRow(roomRow);
            chartController.setRoomColumn(roomColumn);
            roomScreen.setTitle("RoomChart");
            roomScreen.setScene(new javafx.scene.Scene(root2, 800, 480));
            roomScreen.show();
        } catch (IOException ex) {
            Logger.getLogger(RoomScreenFxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void voiceActivationEvent(TouchEvent event) 
    {
        isVoiceActive = !isVoiceActive; 
        setVoice();
        setSpeakerImage();
    }
    
    public void setController(MainScreenController main) {
        screenController = main;
    }
    
    private void setSpeakerImage()
    {  
        speakerIcon.getStyleClass().clear();
        if(isVoiceActive) {
            speakerIcon.getStyleClass().add("speakerOn");
        } else {
            speakerIcon.getStyleClass().add("speakerOff");
        }
    }
    
    
     public void setTemp()
    {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    lblRoomTemp.setText(screenController.getTemp(roomRow, roomColumn));
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
                    lblRoomHum.setText(screenController.getHum(roomRow, roomColumn));
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
                    lblRoomLight.getStyleClass().clear();
                    ObservableList<String> style = screenController.getLight(roomRow,roomColumn);
                    lblRoomLight.getStyleClass().add(style.get(0));
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
                    lblRoomFire.getStyleClass().clear();
                    ObservableList<String> style = screenController.getFire(roomRow,roomColumn);
                    lblRoomFire.getStyleClass().add(style.get(0));
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
                    lblRoomMotion.getStyleClass().clear();
                    ObservableList<String> style = screenController.getMotion(roomRow,roomColumn);
                    lblRoomMotion.getStyleClass().add(style.get(0));
                }
            });  
    }
    
    public void setRoomRow(int row){
        this.roomRow = row;
    }
    
    public void setRoomColumn(int column){
        this.roomColumn = column;
    }
    
    public void setVoice() {
        screenController.setVoiceArray(isVoiceActive, roomRow, roomColumn);
    }

    public void stopAllTasks() {
        pause.stop();
    }
    
}
