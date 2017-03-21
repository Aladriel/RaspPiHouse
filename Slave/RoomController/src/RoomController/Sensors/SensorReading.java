/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Sensors;

/**
 *
 * @author eyrmin
 */
public class SensorReading
{
    private SensorType type;
    private float value;
    
    public SensorType getSensorType()
    {
        return type;
    }
    
    public float getValue()
    {
        return value;
    }
    
    public SensorReading(SensorType type, float value)
    {
        this.type = type;
        this.value = value;
    }
}
