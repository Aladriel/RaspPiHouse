/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Sensors;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author eyrmin
 */
public class SensorsManager
{
    private ArrayList<Sensor> sensors;
    
    public SensorsManager()
    {
        sensors = new ArrayList<>();
    }
    
    public void addSensor(Sensor sensor)
    {
        sensors.add(sensor);
    }
    
    public SensorReadings getReadings()
    {
        SensorReadings readings = new SensorReadings();
        
        for(Sensor s : sensors)
        {
            try
            {
                readings.addReading(s.getType(), s.getReading());
            }
            catch (ValueNotChangedException ex)
            {
            }
            catch(SensorNotAvailableException ex)
            {
                RoomController.RoomController.logger.log(Level.WARNING, "Sensor {0} is not available", s.toString());
            }
        }
        
        return readings;
    }
    
}
