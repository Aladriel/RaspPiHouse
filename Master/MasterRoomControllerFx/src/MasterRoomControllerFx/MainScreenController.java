/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MasterRoomControllerFx;

import MasterRoomControllerFx.rooms.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;

/**
 *
 * @author Bartek
 */
public class MainScreenController implements Initializable
{
    private final int ROOM_ROWS = 2;
    private final int ROOM_COLUMNS = 3;
    
    @FXML public GridPane roompanel1;
    @FXML private GridPane roompanel2;
    @FXML private GridPane roompanel3;
    @FXML private GridPane roompanel4;
    @FXML private GridPane roompanel5;
    @FXML private GridPane roompanel6;
    
    @FXML private Label lblTemp11;
    @FXML private Label lblTemp12;
    @FXML private Label lblTemp13;
    @FXML private Label lblTemp21;
    @FXML private Label lblTemp22;
    @FXML private Label lblTemp23;
    
    @FXML private Label lblHum11;
    @FXML private Label lblHum12;
    @FXML private Label lblHum13;
    @FXML private Label lblHum21;
    @FXML private Label lblHum22;
    @FXML private Label lblHum23;
    
    @FXML private Label lblTime;
    @FXML private Label lblDate;
    
    @FXML private Label lblLight11;
    @FXML private Label lblLight12;
    @FXML private Label lblLight13;
    @FXML private Label lblLight21;
    @FXML private Label lblLight22;
    @FXML private Label lblLight23;
    
    @FXML private Label lblFire11;
    @FXML private Label lblFire12;
    @FXML private Label lblFire13;
    @FXML private Label lblFire21;
    @FXML private Label lblFire22;
    @FXML private Label lblFire23;
    
    @FXML private Label lblMotion11;
    @FXML private Label lblMotion12;
    @FXML private Label lblMotion13;
    @FXML private Label lblMotion21;
    @FXML private Label lblMotion22;
    @FXML private Label lblMotion23;
    
    @FXML private Label lblRoom11;
    @FXML private Label lblRoom12;
    @FXML private Label lblRoom13;
    @FXML private Label lblRoom21;
    @FXML private Label lblRoom22;
    @FXML private Label lblRoom23;
    
    
    @FXML private Label alertIcon;
    
    
    private Label[][] tempArray;
    private Label[][] humArray;
    private Label[][] lightArray;
    private Label[][] fireArray;
    private Label[][] motionArray;
    private Label[][] roomArray;
    
    
    private boolean isAlertActive = false;
    private boolean alertOn = false;
    private boolean csvFlag = false;
    private boolean[][] voiceCaptureArray = new boolean[ROOM_ROWS][ROOM_COLUMNS];
    
    private BufferedImage image;
    
    public BufferedImage getImage() {
        return image;
    }
    
    private RoomScreenFxController rsController;
    private javafx.stage.Stage roomScreen;
    
    
    @FXML
    private void DisplayRoom1Event(MouseEvent event) 
    {
        try
             {
                javafx.scene.Parent root2;
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                root2 = loader.load(getClass().getResource("rooms/RoomScreenFx.fxml").openStream());
                rsController = loader.getController();  
                rsController.setController(this);
                rsController.setRoomRow(0);
                rsController.setRoomColumn(0);
                roomScreen = new javafx.stage.Stage();
                roomScreen.setTitle("RoomScreen");
                roomScreen.setScene(new javafx.scene.Scene(root2, 800, 480));
                roomScreen.show();       

                 //hide this current window (if this is whant you want
                 //((Scene)event.getSource()).getWindow().hide();
             }
             catch (IOException ex)
             {
                 Logger.getLogger(MasterRoomControllerFx.class.getName()).log(Level.SEVERE, null, ex);
             }
    }

    @FXML
    private void DisplayRoom2Event(MouseEvent event) 
    {
        try
             {
                 javafx.scene.Parent root2;
                 javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                 root2 = loader.load(getClass().getResource("rooms/RoomScreenFx.fxml").openStream());
                 rsController = loader.getController();  
                 rsController.setController(this);
                 rsController.setRoomRow(0);
                 rsController.setRoomColumn(1);
                 roomScreen = new javafx.stage.Stage();
                 roomScreen.setTitle("RoomScreen");
                 roomScreen.setScene(new javafx.scene.Scene(root2, 800, 480));
                 roomScreen.show();

                 //hide this current window (if this is whant you want
                 //((Scene)event.getSource()).getWindow().hide();
             }
             catch (IOException ex)
             {
                 Logger.getLogger(MasterRoomControllerFx.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    @FXML
    private void DisplayRoom3Event(MouseEvent event) 
    {
        try
             {
                 javafx.scene.Parent root2;
                 javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                 root2 = loader.load(getClass().getResource("rooms/RoomScreenFx.fxml").openStream());
                 rsController = loader.getController();  
                 rsController.setController(this);
                 rsController.setRoomRow(0);
                 rsController.setRoomColumn(2);
                 roomScreen = new javafx.stage.Stage();
                 roomScreen.setTitle("RoomScreen");
                 roomScreen.setScene(new javafx.scene.Scene(root2, 800, 480));
                 roomScreen.show();

                 //hide this current window (if this is whant you want
                 //((Scene)event.getSource()).getWindow().hide();
             }
             catch (IOException ex)
             {
                 Logger.getLogger(MasterRoomControllerFx.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    @FXML
    private void DisplayRoom4Event(MouseEvent event) 
    {
        try
             {
                 javafx.scene.Parent root2;
                 javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                 root2 = loader.load(getClass().getResource("rooms/RoomScreenFx.fxml").openStream());
                 rsController = loader.getController();  
                 rsController.setController(this);
                 rsController.setRoomRow(1);
                 rsController.setRoomColumn(0);
                 roomScreen = new javafx.stage.Stage();
                 roomScreen.setTitle("RoomScreen");
                roomScreen.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    @Override
                    public void handle(WindowEvent event)
                    {
                        if(rsController != null) {
                            rsController.stopAllTasks();
                            Platform.exit();
                        }
                    }
                });   
                 roomScreen.setScene(new javafx.scene.Scene(root2, 800, 480));
                 roomScreen.show();

                 //hide this current window (if this is whant you want
                 //((Scene)event.getSource()).getWindow().hide();
             }
             catch (IOException ex)
             {
                 Logger.getLogger(MasterRoomControllerFx.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    @FXML
    private void DisplayRoom5Event(MouseEvent event) 
    {
        try
             {
                 javafx.scene.Parent root2;
                 javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                 root2 = loader.load(getClass().getResource("rooms/RoomScreenFx.fxml").openStream());
                 rsController = loader.getController();  
                 rsController.setController(this);
                 rsController.setRoomRow(1);
                 rsController.setRoomColumn(1);
                 roomScreen = new javafx.stage.Stage();
                 roomScreen.setTitle("RoomScreen");
                 roomScreen.setScene(new javafx.scene.Scene(root2, 800, 480));
                 roomScreen.show();

                 //hide this current window (if this is whant you want
                 //((Scene)event.getSource()).getWindow().hide();
             }
             catch (IOException ex)
             {
                 Logger.getLogger(MasterRoomControllerFx.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    @FXML
    private void DisplayRoom6Event(MouseEvent event) 
    {
        try
             {
                 javafx.scene.Parent root2;
                 javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                 root2 = loader.load(getClass().getResource("rooms/RoomScreenFx.fxml").openStream());
                 rsController = loader.getController();  
                 rsController.setController(this);
                 rsController.setRoomRow(1);
                 rsController.setRoomColumn(2);
                 roomScreen = new javafx.stage.Stage();
                 roomScreen.setTitle("RoomScreen");
                 roomScreen.setScene(new javafx.scene.Scene(root2, 800, 480));
                 roomScreen.show();

                 //hide this current window (if this is whant you want
                 //((Scene)event.getSource()).getWindow().hide();
             }
             catch (IOException ex)
             {
                 Logger.getLogger(MasterRoomControllerFx.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    @FXML
    private void handleAlertEvent(MouseEvent event) 
    {
        isAlertActive = !isAlertActive;  
        setAlertImage();
    }
    
    
    private void setAlertImage()
    {
        if(isAlertActive)
        {
            Image image = new Image(getClass().getResourceAsStream("graphics/alert.png"));
            alertIcon.setGraphic(new ImageView(image));   
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Alert information");
            String s ="House alert will be active in 3 minutes";
            alert.setContentText(s);
            alert.show();
            PauseTransition pause = new PauseTransition(javafx.util.Duration.minutes(3));
            pause.setOnFinished(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event) {
                    alertOn = true;
                }
            });
        }
        else
        {
            Image image = new Image(getClass().getResourceAsStream("graphics/alertoff.png"));
            alertIcon.setGraphic(new ImageView(image));
            alertOn = false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	tempArray = new Label[ROOM_ROWS][ROOM_COLUMNS];
	humArray = new Label[ROOM_ROWS][ROOM_COLUMNS];
        lightArray = new Label[ROOM_ROWS][ROOM_COLUMNS];
        fireArray = new Label[ROOM_ROWS][ROOM_COLUMNS];
        motionArray = new Label[ROOM_ROWS][ROOM_COLUMNS];
        roomArray = new Label[ROOM_ROWS][ROOM_COLUMNS];
        

	tempArray[0][0] = lblTemp11;
	tempArray[0][1] = lblTemp12;
	tempArray[0][2] = lblTemp13;
	tempArray[1][0] = lblTemp21;
	tempArray[1][1] = lblTemp22;
	tempArray[1][2] = lblTemp23;
	
	humArray[0][0] = lblHum11;
	humArray[0][1] = lblHum12;
	humArray[0][2] = lblHum13;
	humArray[1][0] = lblHum21;
	humArray[1][1] = lblHum22;
	humArray[1][2] = lblHum23;
        
        lightArray[0][0] = lblLight11;
	lightArray[0][1] = lblLight12;
	lightArray[0][2] = lblLight13;
	lightArray[1][0] = lblLight21;
	lightArray[1][1] = lblLight22;
	lightArray[1][2] = lblLight23;
        
        fireArray[0][0] = lblFire11;
	fireArray[0][1] = lblFire12;
	fireArray[0][2] = lblFire13;
	fireArray[1][0] = lblFire21;
	fireArray[1][1] = lblFire22;
	fireArray[1][2] = lblFire23;
        
        motionArray[0][0] = lblMotion11;
	motionArray[0][1] = lblMotion12;
	motionArray[0][2] = lblMotion13;
	motionArray[1][0] = lblMotion21; 
	motionArray[1][1] = lblMotion22;
	motionArray[1][2] = lblMotion23;
        
        roomArray[0][0] = lblRoom11;
	roomArray[0][1] = lblRoom12;
	roomArray[0][2] = lblRoom13;
	roomArray[1][0] = lblRoom21; 
	roomArray[1][1] = lblRoom22;
	roomArray[1][2] = lblRoom23;
        for(int i=0;i<ROOM_ROWS;i++){
            for(int j=0;j<ROOM_COLUMNS;j++){
                voiceCaptureArray[i][j] = false;
            }
        }
        
        setAlertImage();
        initPanel();
    } 
    
    private void lines() throws FileNotFoundException {
            final String folderPath = "C:\\Users\\Bartek\\Desktop\\Projekt\\MasterRoomControllerFx\\src";

    long totalLineCount = 0;
    final List<File> folderList = new LinkedList<>();
    folderList.add(new File(folderPath));
    while (!folderList.isEmpty()) {
        final File folder = folderList.remove(0);
        if (folder.isDirectory() && folder.exists()) {
            System.out.println("Scanning " + folder.getName());
            final File[] fileList = folder.listFiles();
            for (final File file : fileList) {
                if (file.isDirectory()) {
                    folderList.add(file);
                } else if (file.getName().endsWith(".java")
                        || file.getName().endsWith(".sql")) {
                    long lineCount = 0;
                    final Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        scanner.nextLine();
                        lineCount++;
                    }
                    totalLineCount += lineCount;
                    final String lineCountString;
                    if (lineCount > 99999) {
                        lineCountString = "" + lineCount;
                    } else {
                        final String temp = ("     " + lineCount);
                        lineCountString = temp.substring(temp.length() - 5);
                    }
                    System.out.println(lineCountString + " lines in " + file.getName());
                }
            }
        }
    }
    System.out.println("Scan Complete: " + totalLineCount + " lines total");
    }



    public boolean isAlertOn() {
        return alertOn;
    }
    
    public void initPanel()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run() 
            {
                for(int i = 0; i < ROOM_ROWS; i++) {
                    for(int j = 0; j < ROOM_COLUMNS; j++) {
                        lightArray[i][j].getStyleClass().add("lightIconOff");
                        fireArray[i][j].getStyleClass().add("fireIconOff");
                        motionArray[i][j].getStyleClass().add("motionIconOff");
                    }
                }
            }
        }); 
    }
    
    public void setRoomName(String name, int i, int j)
    {
        if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    roomArray[i][j].setText(name);
                }
            });
        }
    }
    
    public void setTime()
    {
        ZonedDateTime zdt = ZonedDateTime.now();
        String time = String.format("%1$02d:%2$02d", zdt.getHour(), zdt.getMinute());
        lblTime.setText(time);
        String date =zdt.getDayOfWeek()+", "+ Integer.toString(zdt.getDayOfMonth())+" " + zdt.getMonth() +" "+ Integer.toString(zdt.getYear());
        lblDate.setText(date); 
        if(zdt.getHour() == 0 && zdt.getMinute() == 0 && zdt.getSecond() < 5) {
            for(int i = 0; i < ROOM_ROWS; i++) {
                for(int j = 0; j < ROOM_COLUMNS; j++) {                   
                    try {
                        Path pathTemp = Paths.get("tempHistory"+i+j+".csv");
                        Path pathHum = Paths.get("humHistory"+i+j+".csv");
                        Files.deleteIfExists(pathTemp);
                        Files.deleteIfExists(pathHum);
                    } catch (IOException ex) {
                        Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
       }      
    }
    
    private void writeToCSV(float tmp, String fileName) {
        try {          
            ZonedDateTime zdt = ZonedDateTime.now();
            if(zdt.getSecond() < 5 && zdt.getMinute() == 0) { //reading every 3s
                FileWriter pw = new FileWriter(fileName+".csv", true);
                pw.append(String.valueOf(tmp));
                pw.append(";");     
                String time = String.format("%1$02d:%2$02d", zdt.getHour(), zdt.getMinute());
                pw.append(time);
                pw.append("\n");
                pw.flush();
                pw.close();
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void setTemp(float tmp, int i, int j)
    {
	if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    tempArray[i][j].setText(String.format("%.1f °C", tmp));
                    String fileName = "tempHistory"+i+j;
                    writeToCSV(tmp, fileName);
                }
            });
        }    
    }
    
    public void setHumidity(float tmp, int i, int j)
    {
	if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    humArray[i][j].setText(String.format("%.0f%%", tmp));
                    String fileName = "humHistory"+i+j;
                    writeToCSV(tmp, fileName);
                }
            });
        }
    }
    
    public void setLight(float tmp, int i, int j)
    {
	if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    lightArray[i][j].getStyleClass().clear();
                    if(tmp == 1)
                    {
                        lightArray[i][j].getStyleClass().add("lightIcon");
                    } else {
                        lightArray[i][j].getStyleClass().add("lightIconOff");
                    }
                }
            });
        }    
    }
    
    public void setFire(float tmp, int i, int j)
    {
	if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    fireArray[i][j].getStyleClass().clear();
                    if(tmp == 1)
                    {
                        fireArray[i][j].getStyleClass().add("fireIcon");
                    } else {
                        fireArray[i][j].getStyleClass().add("fireIconOff");
                    }
                }
            });
        }    
    }
    public void setMotion(float tmp, int i, int j)
    {
	if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() 
                {
                    motionArray[i][j].getStyleClass().clear();
                    if(tmp == 1)
                    {
                        motionArray[i][j].getStyleClass().add("motionIcon");
                    } else {
                        motionArray[i][j].getStyleClass().add("motionIconOff");
                    }
                }
            });
        }    
    }
    
    public void setVoiceArray(boolean tmp, int i, int j){
        if(i < ROOM_ROWS && j < ROOM_COLUMNS) {
            voiceCaptureArray[i][j] = tmp;
        }
    }
    
    public boolean[][] getVoiceArray(){
        return voiceCaptureArray;
    }
    
    public String getName(int i, int j) 
    {
        if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            return roomArray[i][j].getText();
        } else return null;
    }    
    
    public String getTemp(int i, int j) 
    {
        if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            return tempArray[i][j].getText();
        } else return null;
    }   
        
    public String getHum(int i, int j) 
    {
        if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            return humArray[i][j].getText();
        } else return null;
    }   
            
    public ObservableList<String> getLight(int i, int j) 
    {
        if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            return lightArray[i][j].getStyleClass();
        } else return null;
    }   
                
    public ObservableList<String> getFire(int i, int j) 
    {
        if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            return fireArray[i][j].getStyleClass();
        } else return null;
    }   
    
    public ObservableList<String> getMotion(int i, int j) 
    {
        if(i < ROOM_ROWS && j < ROOM_COLUMNS)
        {
            return motionArray[i][j].getStyleClass();
        } else return null;
    }
    

}
