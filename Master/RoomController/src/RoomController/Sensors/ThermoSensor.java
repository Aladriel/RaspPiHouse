/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Sensors;

import RoomController.RoomController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 *
 * @author eyrmin
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
        //oneWireDeviceId = null;
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
        
        /*float value = 0;
        
        switch(iface)
        {
            case OneWire:
            {
                value = getOneWireReading();
                
                if(value == lastReading)
                    throw new ValueNotChangedException();
                
                lastReading = value;
                break;
            }
            default:
                break;
        }
        
        return value;
    }
    
    private float getOneWireReading() throws SensorNotAvailableException
    {
        float value = 0;
        
        if(oneWireDeviceId != null)
        {
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(ONE_WIRE_PATH + oneWireDeviceId + ONE_WIRE_READING_FILE));
                String line = reader.readLine();
  
                if(line.contains("YES"))
                {
                    line = reader.readLine();
                    value = Float.valueOf(line.substring(line.indexOf("t=") + 2)) / 1000; 
                }
                else
                    throw new SensorNotAvailableException();
            }
            catch (IOException ex)
            {
                RoomController.logger.log(Level.SEVERE, ex.getMessage());
                throw new SensorNotAvailableException();
            }
        }
        else
            throw new SensorNotAvailableException();
        
        return value;
    }
    
    /*private ArrayList<String> getOneWireSensorPaths()
    {
        ArrayList<String> paths = new ArrayList<>();
        
        try
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(ONE_WIRE_SLAVES_LIST_FILE)))
            {
                String line;
                
                while((line = reader.readLine()) != null)
                    paths.add(line);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(ThermoSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return paths;
    }*/
    
}
