/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.StreamReading;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

/**
 *
 * @author eyrmin
 */
public class CommsManager implements ICommsEvent, IStreamEvent
{
    private Socket connection;
    private String masterIp;
    private int masterPort;
    private CommsTask backgroundTask;
    private Thread thread;
    private StreamEventHandler handler;
    
    public CommsManager(String masterIp, int masterPort)
    {
        this.masterIp = masterIp;
        this.masterPort = masterPort;
        connection = new Socket();
        backgroundTask = null;
        thread = null;
        handler = new StreamEventHandler();
        
        try
        {
            connection.setKeepAlive(true);
            connection.setReuseAddress(true);
        }
        catch (SocketException ex)
        {
            RoomController.RoomController.logger.log(Level.SEVERE, ex.getMessage());
        }
    }
    
    public void addStreamEventListener(IStreamEvent listener)
    {
        handler.addStreamEventListener(listener);
    }
    
    public void start()
    {
        if(backgroundTask == null)
        {
            backgroundTask = new CommsTask(connection, masterIp, masterPort);
            backgroundTask.addCommsEventListener(this);
        }
        
        thread = new Thread(backgroundTask);
        thread.start();
    }
    
    public void stop()
    {
        if(backgroundTask != null && backgroundTask.getIsRunning())
        {
            backgroundTask.stop();
            backgroundTask = null;
            thread = null;
        }
    }
    
    public void connectToMaster()
    {
        if(connection.isConnected())
            return;
        
        if(backgroundTask != null)
        {
            try
            {
                if(backgroundTask.connectToMaster() == false)
                    reinitSocket();
            }
            catch(IllegalStateException ex)
            {
                RoomController.RoomController.logger.log(Level.WARNING, ex.getMessage());
                RoomController.RoomController.logger.log(Level.INFO, "Restarting comms task");
                start();
                if(backgroundTask.connectToMaster() == false)
                    reinitSocket();
            }
        }
        else
        {
            RoomController.RoomController.logger.log(Level.INFO, "Comms task not running. Starting up");
            start();
            if(backgroundTask.connectToMaster() == false)
                reinitSocket();
        }
    }
    
    public void sendToMaster(byte[] buffer) throws IOException
    {
        connectToMaster();
        if(backgroundTask.send(buffer) == false)
        {
            stop();
            reinitSocket();
            start();
        }
    }

    @Override
    public void messageReceived(byte[] buffer)
    {
        StreamReading reading = CommsProtocol.processStream(buffer);
        handler.triggerEvent(reading);
    }
    
    private void reinitSocket()
    {
        try
        {
            connection = new Socket();
            connection.setKeepAlive(true);
            connection.setReuseAddress(true);
        }
        catch (SocketException ex)
        {
            RoomController.RoomController.logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void streamReceived(StreamReading reading) {
        handler.triggerEvent(reading);
    }
}
