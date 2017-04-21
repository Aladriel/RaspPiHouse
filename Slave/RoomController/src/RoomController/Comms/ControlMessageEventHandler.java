/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author eyrmin
 */
public class ControlMessageEventHandler
{
    private final ArrayList<IControlEvent> listeners;
    
    public ControlMessageEventHandler()
    {
        listeners = new ArrayList<>();
    }
    
    public void addControlEventListener(IControlEvent listener)
    {
        listeners.add(listener);
    }
    
    public void triggerEvent(byte[] buffer)
    {
        if(listeners != null && !listeners.isEmpty())
        {
            for(IControlEvent event : listeners)
            {
                event.controlMessageReceived(buffer);
            }
        }
    }
}
