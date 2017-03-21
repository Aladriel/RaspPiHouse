/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eyrmin
 */
public class Main
{
    public static void main(String[] args) throws IOException 
    {
	RoomController controller = new RoomController();
        if(controller.Initialise())
            controller.runIdle();
    }
}
