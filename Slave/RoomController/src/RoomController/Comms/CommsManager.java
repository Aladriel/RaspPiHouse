/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.StreamReading;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

/**
 *
 * @author eyrmin
 */
public class CommsManager implements ICommsEvent
{
    private Socket connection;
    private String masterIp;
    private int masterPort;
    private CommsTask backgroundTask;
    private Thread thread;
    private StreamEventHandler streamHandler;
    private ControlMessageEventHandler controlHandler;
    
    public CommsManager(String masterIp, int masterPort)
    {
        this.masterIp = masterIp;
        this.masterPort = masterPort;
        connection = new Socket();
        backgroundTask = null;
        thread = null;
        streamHandler = new StreamEventHandler();
        controlHandler = new ControlMessageEventHandler();
        
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
        streamHandler.addStreamEventListener(listener);
    }
    public void addControlEventListener(IControlEvent listener)
    {
        controlHandler.addControlEventListener(listener);
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
    
    public InputStream getInputStream() {
        return backgroundTask.getInputStream();
    }

    @Override
    public void messageReceived(byte[] buffer) {
        switch(CommsProtocol.getMessageType(buffer)){
            case CommsProtocol.MSG_TYPE_SET_LIGHT:
                controlHandler.triggerEvent(buffer);
                break;
            case CommsProtocol.MSG_TYPE_SET_BLIND:
                controlHandler.triggerEvent(buffer);
                break;
            case CommsProtocol.MSG_TYPE_MASTER_STREAM:
                streamHandler.triggerEvent(buffer);
                break;
        }
        
    }
}
