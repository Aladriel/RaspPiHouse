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
 * @author eyrmin
 */
public class LightSensor extends Sensor
{
    public LightSensor(int pin)
    {
        type = SensorType.Light;
        gpioPin = pin;
        lastReading = 0;
        //oneWireDeviceId = null;
    }
    
    public float getReading() throws SensorNotAvailableException
    {
        float v = 0;
        ProcessBuilder pb = new ProcessBuilder("python", "/home/pi/Projects/Python/read_light.py", "17");
        try
        {
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    //System.out.println("value is : " + in.readLine());
            if(in.readLine().charAt(0) == '0')
                v = 1;
            else
                v = 0;
        }
        catch (IOException ex)
        {
            Logger.getLogger(LightSensor.class.getName()).log(Level.SEVERE, null, ex);
            throw new SensorNotAvailableException();
        }
        
        return v;
    }
    
}
