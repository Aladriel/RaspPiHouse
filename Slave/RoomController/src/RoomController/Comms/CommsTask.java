/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.RoomController;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eyrmin
 */
public class CommsTask implements Runnable
{
    private Socket connection;
    private final CommsEventHandler handler;
    private boolean isRunning;
    private String masterIp;
    private int masterPort;
    InputStream inputStream;
    public boolean getIsRunning()
    {
        return isRunning;
    }
    
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public void addCommsEventListener(ICommsEvent listener)
    {
        handler.addCommsEventListener(listener);
    }
    
    public CommsTask(Socket connection, String masterIp, int masterPort)
    {
        this.connection = connection;
        this.masterIp = masterIp;
        this.masterPort = masterPort;
        handler = new CommsEventHandler();
        isRunning = false;
    }
    
    public boolean connectToMaster() throws IllegalStateException
    {
        boolean connected = false;
        
        if(isRunning)
        {
            if(connection.isConnected() == false)
            {
                try
                {
                    connection.connect(new InetSocketAddress(masterIp, masterPort), 3000);
                    connected = true;
                }
                catch (IOException ex)
                {
                    RoomController.logger.log(Level.SEVERE, ex.getMessage());
                }
            }
        }
        else
            throw new IllegalStateException("Thread is not running");
        
        return connected;
    }
    
    public boolean send(byte[] data) throws IOException
    {
        boolean success = false;
        if(connection != null && connection.isConnected())
        {
            try 
            {
               connection.getOutputStream().write(data);
               success = true;
            }
            catch (IOException ex) 
            {
                RoomController.logger.log(Level.SEVERE, ex.getMessage());               
            }
        }
        return success;
    }
    
    public void stop()
    {
        isRunning = false;
        
        if(connection != null && !connection.isClosed())
        {
            try 
            {
                connection.close();
            }
            catch (IOException ex) 
            {
                RoomController.logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }
    
    @Override
    public void run()
    {
        isRunning = true;
        
        while(isRunning)
        {
            if(connection.isConnected())
            {
                try
                {
                    if(connection.getInputStream().available() > 0)
                    {
                        //byte[] buffer = new byte[connection.getInputStream().available()];
                        inputStream = connection.getInputStream();
                        byte[] buffer = new byte[inputStream.available()];
                        handler.triggerEvent(buffer);
                    }
                }
                catch (IOException ex)
                {
                    RoomController.logger.log(Level.WARNING, ex.getMessage());
                }
            }
            
            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException ex)
            {
                RoomController.logger.log(Level.WARNING, ex.getMessage());
            }
        }
    }
    

    
}
