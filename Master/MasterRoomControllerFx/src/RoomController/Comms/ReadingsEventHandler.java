/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.Sensors.SensorReadings;
import java.util.ArrayList;

/**
 *
 * @author Bartek
 */
public class ReadingsEventHandler
{
    private final ArrayList<IReadingsEvent> listeners;
    
    public ReadingsEventHandler()
    {
        listeners = new ArrayList<>();
    }
    
    public void addReadingsEventListener(IReadingsEvent listener)
    {
        listeners.add(listener);
    }
    
    public void triggerEvent(SensorReadings readings)
    {
        if(listeners != null && !listeners.isEmpty())
        {
            for(IReadingsEvent event : listeners)
            {
                event.ReadingsReceived(readings);
            }
        }
    }
}
