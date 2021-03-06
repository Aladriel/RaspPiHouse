/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rasppi.intelligenthouse.Sensors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 *
 * @author Bartek
 */
public class ThermoSensor extends Sensor
{
    //private final String ONE_WIRE_FAMILY_ID = "28-";
    //private final String ONE_WIRE_READING_FILE = "/w1_slave";
    
    public ThermoSensor(int pin)
    {
        type = SensorType.Thermo;
        gpioPin = pin;
        lastReading = 0;
    }
    
    @Override
    public float getReading() throws SensorNotAvailableException
    {
        float value = 0;
        ProcessBuilder pb = new ProcessBuilder("python", "/home/pi/Projects/Python/get_temp_from_dht22.py", "22");
        try
        {
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    //System.out.println("value is : " + in.readLine());
            value = Float.parseFloat(in.readLine());
        }
        catch (IOException ex)
        {
            Logger.getLogger(ThermoSensor.class.getName()).log(Level.SEVERE, null, ex);
            throw new SensorNotAvailableException();
        }
        return value;
    }
        

    
}
