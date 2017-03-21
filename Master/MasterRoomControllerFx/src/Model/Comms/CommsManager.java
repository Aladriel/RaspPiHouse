/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.Sensors.SensorReadings;

/**
 *
 * @author Bartek
 */
public class CommsManager implements IReadingsEvent
{
    private CommsTask backgroundTask;
    private Thread thread;
    private int port;
    private ReadingsEventHandler handler;
    
    public CommsManager(int port)
    {
        backgroundTask = null;
        thread = null;
        this.port = port;
        handler = new ReadingsEventHandler();
    }
    
    public void send(byte[] message, int deviceId, int bytesRead){
        backgroundTask.send(message, deviceId, bytesRead);
    }
    
    public void addReadingsEventListener(IReadingsEvent listener)
    {
        handler.addReadingsEventListener(listener);
    }
    
    public void start()
    {
        if(backgroundTask == null)
        {
            backgroundTask = new CommsTask(port);
            backgroundTask.addReadingsEventListener(this);
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
    


    @Override
    public void ReadingsReceived(SensorReadings readings) 
    {
        handler.triggerEvent(readings);
    }
}
