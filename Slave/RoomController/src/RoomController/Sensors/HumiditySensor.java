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
public class HumiditySensor extends Sensor
{
    public HumiditySensor(int pin)
    {
        type = SensorType.Humidity;
        gpioPin = pin;
        lastReading = 0;
        //oneWireDeviceId = null;
    }
    
    @Override
    public float getReading() throws  SensorNotAvailableException
    {
        float value = 0;
        ProcessBuilder pb = new ProcessBuilder("python", "/home/pi/Projects/Python/get_humidity_from_dht22.py", "22");
        try
        {
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    //System.out.println("value is : " + in.readLine());
            value = Float.parseFloat(in.readLine());
        }
        catch (IOException ex)
        {
            Logger.getLogger(HumiditySensor.class.getName()).log(Level.SEVERE, null, ex);
            throw new SensorNotAvailableException();
        }
        return value;
    }
    
}
