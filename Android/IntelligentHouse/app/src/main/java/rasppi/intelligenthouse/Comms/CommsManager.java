/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rasppi.intelligenthouse.Comms;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;

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
    private LoginEventHandler loginHandler;
    private ReadingsEventHandler readingsHandler;
    
    public CommsManager(String masterIp, int masterPort)
    {
        this.masterIp = masterIp;
        this.masterPort = masterPort;
        connection = new Socket();
        backgroundTask = null;
        thread = null;
        loginHandler = new LoginEventHandler();
        readingsHandler = new ReadingsEventHandler();
        
        try
        {
            connection.setKeepAlive(true);
            connection.setReuseAddress(true);
        }
        catch (SocketException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void addLoginEventListener(ILoginEvent listener)
    {
        loginHandler.addLoginEventListener(listener);
    }

    public void addReadingEventListener(IReadingsEvent listener)
    {
        readingsHandler.addReadingsEventListener(listener);
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
                ex.printStackTrace();
                start();
                if(backgroundTask.connectToMaster() == false)
                    reinitSocket();
            }
        }
        else
        {
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
            ex.printStackTrace();
        }
    }
    
    public InputStream getInputStream() {
        return backgroundTask.getInputStream();
    }

    @Override
    public void messageReceived(byte[] buffer)
    {
        switch (CommsProtocol.getMessageType(buffer)){
            case CommsProtocol.MSG_TYPE_LOGIN_INFO:
                loginHandler.triggerEvent(buffer);
                break;
            case CommsProtocol.MSG_TYPE_READING_DATA:
                readingsHandler.triggerEvent(buffer);
                break;
        }

    }
}
