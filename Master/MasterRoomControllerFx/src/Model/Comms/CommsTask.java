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
    private final ReadingsEventHandler handler;
    
    public boolean getIsRunning()
    {
        return isRunning;
    }
    
    public void addReadingsEventListener(IReadingsEvent listener)
    {
        handler.addReadingsEventListener(listener);
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
        
        handler = new ReadingsEventHandler();

        isRunning = false;
    }
    
    public void send(byte[] message, int deviceId, int bytesRead)
    {
        clients.send(message, deviceId, bytesRead);
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
        SensorReadings readings = CommsProtocol.processMessage(buffer);
        clients.setDeviceId(connection, readings.getDeviceId());
        handler.triggerEvent(readings);
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
                clients.add(new ClientConnection(task));
		Thread thread = new Thread(task);
		thread.start();
	    }
	    catch(IOException e)
	    {
	    }
        }
    }
}
