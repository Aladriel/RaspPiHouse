/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.Sensors.SensorReadings;
import java.net.Socket;

/**
 *
 * @author Bartek
 */
public class CommsManager implements IReadingsEvent, ILoginEvent
{
    private CommsTask backgroundTask;
    private Thread thread;
    private int port;
    private ReadingsEventHandler readingsHandler;
    private LoginEventHandler loginHandler;
    
    public CommsManager(int port)
    {
        backgroundTask = null;
        thread = null;
        this.port = port;
        readingsHandler = new ReadingsEventHandler();
        loginHandler = new LoginEventHandler();
    }
    
    public void send(byte[] message, int deviceId, int bytesRead){
        backgroundTask.send(message, deviceId, bytesRead);
    }
    
    public void sendToMobile(byte[] message){
        backgroundTask.sendToMobile(message);
    }
    public void sendToMobile(byte[] message, Socket connection){
        backgroundTask.sendToMobile(message, connection);
    }
    
    public void addReadingsEventListener(IReadingsEvent listener)
    {
        readingsHandler.addReadingsEventListener(listener);
    }
    public void addLoginEventListener(ILoginEvent listener)
    {
        loginHandler.addLoginEventListener(listener);
    }
    
    public void start()
    {
        if(backgroundTask == null)
        {
            backgroundTask = new CommsTask(port);
            backgroundTask.addLoginEventListener(this);
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
        readingsHandler.triggerEvent(readings);
    }

    @Override
    public void loginInfoReceived(UserLoginInfo loginInfo) {
        loginHandler.triggerEvent(loginInfo);
    }
}
