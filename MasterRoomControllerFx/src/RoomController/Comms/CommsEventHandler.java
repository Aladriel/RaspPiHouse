/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Bartek
 */
public class CommsEventHandler
{
    private final ArrayList<ICommsEvent> listeners;
    
    public CommsEventHandler()
    {
        listeners = new ArrayList<>();
    }
    
    public void addCommsEventListener(ICommsEvent listener)
    {
        listeners.add(listener);
    }
    
    public void triggerEvent(byte[] buffer, Socket connection)
    {
        if(listeners != null && !listeners.isEmpty())
        {
            for(ICommsEvent event : listeners)
            {
                event.messageReceived(buffer, connection);
            }
        }
    }
}
