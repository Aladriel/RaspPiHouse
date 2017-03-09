/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.StreamReading;
import java.util.ArrayList;

/**
 *
 * @author eyrmin
 */
public class StreamEventHandler
{
    private final ArrayList<IStreamEvent> listeners;
    
    public StreamEventHandler()
    {
        listeners = new ArrayList<>();
    }
    
    public void addStreamEventListener(IStreamEvent listener)
    {
        listeners.add(listener);
    }
    
    public void triggerEvent(StreamReading reading)
    {
        if(listeners != null && !listeners.isEmpty())
        {
            for(IStreamEvent event : listeners)
            {
                event.streamReceived(reading);
            }
        }
    }
}
