/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.RoomController;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bartek
 */
public class SlaveConnectionTask implements Runnable
{
    private Socket connection;  
    private boolean isRunning;
    private final CommsEventHandler handler;
    DataOutputStream out;
    
    public SlaveConnectionTask(Socket socket)
    {
	connection = socket;
        handler = new CommsEventHandler();
        isRunning = true;
        try {
            out = new DataOutputStream(connection.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SlaveConnectionTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addCommsEventListener(ICommsEvent listener)
    {
        handler.addCommsEventListener(listener);
    }
    
    public void stop()
    {
        isRunning = false;
        if(connection != null && connection.isConnected())
        {
            try
            {
                connection.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(SlaveConnectionTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Socket getConnection()
    {
        return connection;
    }
    
    public boolean send(byte[] data, int bytesRead) throws IOException
    {
        boolean success = false;
        if(connection != null && connection.isConnected())
        {
            try 
            {
               out.write(data, 0, bytesRead);
               //connection.getOutputStream().write(data);
               success = true;
            }
            catch (IOException ex) 
            {
                RoomController.logger.log(Level.SEVERE, ex.getMessage());               
            }
        }
        return success;
    }
    
    @Override
    public void run()
    {
        while(isRunning)
        {
            if(connection.isConnected())
            {
                try
                {
                    if(connection.getInputStream().available() > 0)
                    {
                        byte[] buffer = new byte[connection.getInputStream().available()];

                        connection.getInputStream().read(buffer);
                        
                        handler.triggerEvent(buffer, connection);
                    }                 
                }
                catch (IOException ex)
                {
                    RoomController.logger.log(Level.WARNING, ex.getMessage());
                    try
                    {
                        connection.close();
                    }
                    catch (IOException ex1)
                    {
                        Logger.getLogger(SlaveConnectionTask.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    isRunning = false;
                }
            }
            
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(SlaveConnectionTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
