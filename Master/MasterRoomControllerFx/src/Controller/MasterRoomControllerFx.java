/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import RoomController.RoomController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Bartek
 */
public class MasterRoomControllerFx extends Application
{
    private RoomController controller;
    private ScreenSaverFxController ssaveController;
    private javafx.stage.Stage screenSaver;
    private Parent root;
    private MainScreenController screenController;
    PauseTransition pause = new PauseTransition(javafx.util.Duration.minutes(3));
    @Override
    public void start(Stage stage) throws Exception
    {
	controller = new RoomController();
        
	if(controller.Initialise())
	{
	    FXMLLoader loader = new FXMLLoader();
	    root = loader.load(getClass().getResource("MainScreen.fxml").openStream());
            screenController = (MainScreenController) loader.getController();
	    controller.setScreenController(loader.getController());
            controller.setScreenNames();

	    Scene scene = new Scene(root);
	    scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
	    stage.setScene(scene);
            stage.setResizable(false);
	    stage.setFullScreen(false);   
	    stage.setOnCloseRequest(new EventHandler<WindowEvent>()
	    {
		@Override
		public void handle(WindowEvent event)
		{
                    if(controller != null)
                        controller.stopAllTasks();
		    Platform.exit();
		}
	    });          
            //Temporary app closers
           stage.getScene().setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                event.consume();
                controller.stopAllTasks();
                Platform.exit();
               }                 
           }); 
                  
           stage.getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    event.consume();
                    pause.stop();
                }
               
           });
           
            stage.getScene().setOnSwipeLeft(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                if(controller != null)
                    controller.stopAllTasks();
                Platform.exit();
            }
           });
            
            pause.setOnFinished(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event) {
                    try
                    {
                        javafx.scene.Parent root2;
                        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                        root2 = loader.load(getClass().getResource("ScreenSaverFx.fxml").openStream());
                        ssaveController = loader.getController();
                        screenSaver = new javafx.stage.Stage();
                        screenSaver.setTitle("ScreenSaver");
                        screenSaver.setScene(new javafx.scene.Scene(root2, 800, 480));
                        screenSaver.getScene().setOnTouchReleased(new EventHandler<TouchEvent>() {
                            @Override
                            public void handle(TouchEvent event) {
                                pause.setDuration(javafx.util.Duration.minutes(3));
                                pause.play();
                                ((Scene)event.getSource()).getWindow().hide();
                            }
                        });
                        screenSaver.show();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(MasterRoomControllerFx.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });          
	   stage.show();
           pause.play();
           
           
	}
    }

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args)
    {
	launch(args);
    }
    
    
}
