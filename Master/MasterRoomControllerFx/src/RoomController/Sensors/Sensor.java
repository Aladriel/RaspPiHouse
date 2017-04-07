/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Sensors;

/**
 *
 * @author Bartek
 */
public abstract class Sensor implements ISensor
{
    protected final String ONE_WIRE_PATH = "/sys/bus/w1/devices/";
    protected final String ONE_WIRE_BUS_MASTER_PATH = ONE_WIRE_PATH + "w1_bus_master1";
    protected final String ONE_WIRE_SLAVES_LIST_FILE = ONE_WIRE_PATH + "w1_master_slaves";
    
    protected SensorType type;
    protected int gpioPin;
    protected float lastReading;
    protected SensorInterface iface;
    protected String oneWireDeviceId;
    
    public SensorType getType()
    {
        return type;
    }
    
    public int getGpioPin()
    {
        return gpioPin;
    }
    
    public float getLastReading()
    {
        return lastReading;
    }
    
    public SensorInterface getInterface()
    {
        return iface;
    }
    
    public String getOneWireDeviceId()
    {
        return oneWireDeviceId;
    }
    
    public void setOneWireDeviceId(String id)
    {
        oneWireDeviceId = id;
    }
    
    public void setInterface(SensorInterface iface)
    {
        this.iface = iface;
    }
}
