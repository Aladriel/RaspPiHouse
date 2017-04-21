/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.RoomController;
import RoomController.Sensors.SensorReadings;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bartek
 */
public class CommsTask implements Runnable, ICommsEvent
{
    private ServerSocket server;
    private boolean isRunning;
    private ClientConnections clients;
    private final ReadingsEventHandler readingsHandler;
    private final LoginEventHandler loginHandler;
    
    public boolean getIsRunning()
    {
        return isRunning;
    }
    
    public void addReadingsEventListener(IReadingsEvent listener)
    {
        readingsHandler.addReadingsEventListener(listener);
    }
    public void addLoginEventListener(ILoginEvent listener)
    {
        loginHandler.addLoginEventListener(listener);
    }
    
    public CommsTask(int port)
    {
        try
        {
            server = new ServerSocket(port);
            server.setReuseAddress(true);
            clients = new ClientConnections();
        }
        catch (IOException ex)
        {
            Logger.getLogger(CommsTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        readingsHandler = new ReadingsEventHandler();
        loginHandler = new LoginEventHandler();

        isRunning = false;
    }
    
    public void send(byte[] message, int deviceId, int bytesRead)
    {
        clients.send(message, deviceId, bytesRead);
    }
    public void sendToMobile(byte[] message)
    {
        clients.sendToMobile(message);
    }
    public void sendToMobile(byte[] message, Socket connection)
    {
        clients.sendToMobile(message, connection);
    }
    
    public void stop()
    {
        isRunning = false;
        
        if(server != null && !server.isClosed())
        {
            try 
            {
                server.close();
                clients.closeAll();
                
            }
            catch (IOException ex) 
            {
                RoomController.logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }
    
    @Override
    public void messageReceived(byte[] buffer, Socket connection)
    {
        switch(CommsProtocol.getMessageType(buffer)){
            case CommsProtocol.MSG_TYPE_READING_DATA:
                SensorReadings readings = CommsProtocol.processMessage(buffer);
                clients.setDeviceId(connection, readings.getDeviceId());
                clients.sendToMobile(buffer); //broadcast to all connected mobile devices
                readingsHandler.triggerEvent(readings);
                break;
            case CommsProtocol.MSG_TYPE_LOGIN_REQUEST:
                clients.setDeviceType(connection, "Mobile");
                UserLoginInfo userInfo = CommsProtocol.processLoginMessage(buffer);
                userInfo.setConnection(connection);
                loginHandler.triggerEvent(userInfo);
                break;
            case CommsProtocol.MSG_TYPE_SET_LIGHT:
                int[] lightInfo = CommsProtocol.processLightStateMessage(buffer);
                send(buffer, lightInfo[0], buffer.length);
                //display change in UI + send to other mobiles new state
        }

    }
    
    @Override
    public void run()
    {
        isRunning = true;
        
        while(isRunning)
        {
	    try
	    {
		SlaveConnectionTask task = new SlaveConnectionTask(server.accept());
                task.addCommsEventListener(this);
                clients.add(new ClientConnection(task),task.getConnection());
		Thread thread = new Thread(task);
		thread.start();
	    }
	    catch(IOException e)
	    {
	    }
        }
    }
}
