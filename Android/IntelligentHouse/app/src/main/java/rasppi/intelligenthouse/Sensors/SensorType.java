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
public enum SensorType
{
    None(0),
    Thermo(1),
    Humidity(2),
    Light(3), 
    Smoke(4), 
    Motion(5);
    
    
    private final int value;
    
    private SensorType(int val)
    {
        value = val;
    }
    
    public int toValue()
    {
        return value;
    }
    
    public static SensorType fromByte(byte b)
    {
        switch(b)
        {
            case 1:
                return Thermo;
            case 2:
                return Humidity;
            case 3:
                return Light;
            case 4:
                return Smoke;
            case 5:
                return Motion;            
            default:
                return None;       
        }
    }
}
