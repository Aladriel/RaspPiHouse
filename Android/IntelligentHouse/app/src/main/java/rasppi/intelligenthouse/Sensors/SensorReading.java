/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rasppi.intelligenthouse.Sensors;

/**
 *
 * @author Bartek
 */
public class SensorReading
{
    private SensorType type;
    private float value;
    
    public SensorReading(SensorType type, float value)
    {
        this.type = type;
        this.value = value;
    }
    
    public SensorReading()
    {
        type = SensorType.None;
        value = 0;
    }
    
    public SensorType getType()
    {
        return type;
    }
    
    public float getValue()
    {
        return value;
    }
    
     /**
     * @param type the type to set
     */
    public void setType(SensorType type) 
    {
        this.type = type;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) 
    {
        this.value = value;
    }
}
