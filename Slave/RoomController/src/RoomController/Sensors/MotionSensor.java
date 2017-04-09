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
public class MotionSensor extends Sensor
{
    public MotionSensor(int pin)
    {
        type = SensorType.Motion;
        gpioPin = pin;
        lastReading = 0;
    }
    
    @Override
    public float getReading() throws  SensorNotAvailableException
    {
        float v = 0;
        ProcessBuilder pb = new ProcessBuilder("python", "/home/pi/Projects/Python/get_humidity_from_dht22.py", "22");
        try
        {
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            if(in.readLine().charAt(0) == '0')
                v = 1;
            else
                v = 0;
        }
        catch (IOException ex)
        {
            Logger.getLogger(MotionSensor.class.getName()).log(Level.SEVERE, null, ex);
            throw new SensorNotAvailableException();
        }
        return v;
    }
    
}
