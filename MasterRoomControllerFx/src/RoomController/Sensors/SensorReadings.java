/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Sensors;

import java.util.ArrayList;

/**
 *
 * @author Bartek
 */
public class SensorReadings
{
    private ArrayList<SensorReading> readings;
    private int deviceId;
    
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

    /**
     * @return the deviceId
     */
    public int getDeviceId() 
    {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(int deviceId) 
    {
        this.deviceId = deviceId;
    }
}
