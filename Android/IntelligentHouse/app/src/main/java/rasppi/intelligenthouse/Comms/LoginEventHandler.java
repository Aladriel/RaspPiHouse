/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rasppi.intelligenthouse.Comms;

import java.util.ArrayList;

/**
 *
 * @author eyrmin
 */
public class LoginEventHandler
{
    private final ArrayList<ILoginEvent> listeners;
    
    public LoginEventHandler()
    {
        listeners = new ArrayList<>();
    }
    
    public void addLoginEventListener(ILoginEvent listener)
    {
        listeners.add(listener);
    }
    
    public void triggerEvent(byte[] buffer)
    {
        if(listeners != null && !listeners.isEmpty())
        {
            for(ILoginEvent event : listeners)
            {
                event.loginInfoReceived(buffer);
            }
        }
    }
}
