/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Sensors;

import java.util.ArrayList;

/**
 *
 * @author eyrmin
 */
public class SensorReadings
{
    private ArrayList<SensorReading> readings;
    
    public SensorReadings()
    {
        readings = new ArrayList<>();
    }
    
    public ArrayList<SensorReading> getReadings()
    {
        return readings;
    }
    
    public int getCount()
    {
        return readings.size();
    }
    
    public void addReading(SensorType type, float value)
    {
        SensorReading reading = new SensorReading(type, value);
        readings.add(reading);
    }
}
