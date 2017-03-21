/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Bartek
 */
public class ScreenSaverFxController implements Initializable
{
    @FXML
    private ImageView imgView;
    
    private final String imagesPath = "/img/";
    private List<String> images;
    PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(5));

    public void initialize(URL location, ResourceBundle bundle) 
    { 
        loadImages();
        imgView.setImage(loadRandomImages());
        pause.setOnFinished(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                loadImages();
                imgView.setImage(loadRandomImages());
                pause.setDuration(Duration.seconds(5));
                pause.play();
            }
        });
        pause.play();

    }
    
    
    private Image loadRandomImages() 
    {
        int countImages = images.size();
        int imageNumber = (int) (Math.random() * countImages);

        String image = images.get(imageNumber);
        return new Image("file:img//" + image);
    }

    private void loadImages() 
    {
        if(images == null) 
        {
            images = new ArrayList<String>();
        }
        else 
        {
            images.clear();
        }
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File directory = new File(s + imagesPath);

        File[] files = directory.listFiles();
        for(File f : files) 
        {
            if(f.isFile()) 
            {
                images.add(f.getName());
            }
        }
    }
    
        
           
            
}
