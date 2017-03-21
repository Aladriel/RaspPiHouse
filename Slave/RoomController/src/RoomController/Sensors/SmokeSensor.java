/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Sensors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bartek
 */
public class SmokeSensor extends Sensor
{
    public SmokeSensor(int pin)
    {
        type = SensorType.Smoke;
        gpioPin = pin;
        lastReading = 0;
    }
    
    @Override
    public float getReading() throws  SensorNotAvailableException
    {

        return 1;
    }  
}
