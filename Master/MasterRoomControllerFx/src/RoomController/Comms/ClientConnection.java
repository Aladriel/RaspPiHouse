/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bartek
 */
public class ClientConnection
{
    private SlaveConnectionTask client;
    private int deviceId;
    private String deviceType;
    
    public ClientConnection(SlaveConnectionTask connection)
    {
        client = connection;
    }
    
    public ClientConnection(SlaveConnectionTask connection, int devId)
    {
        client = connection;
        deviceId = devId;
        deviceType = "Slave";
    }
    public ClientConnection(SlaveConnectionTask connection, int devId, String devType)
    {
        client = connection;
        deviceId = devId;
        deviceType = devType;
    }
    
    public void send(byte[] message, int bytesRead) {
        try {
            client.send(message, bytesRead);
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendToMobile(byte[] message) {
            client.sendToMobile(message);
    }
    
    public boolean isLinkedToConnection(Socket connection)
    {
        if(client.getConnection() == connection)
            return true;
        else
            return false;
    }

    /**
     * @return the deviceId
     */
    public int getDeviceId() 
    {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(int deviceId) 
    {
        this.deviceId = deviceId;
    }
    
    public void close()
    {
        client.stop();
    }

    /**
     * @return the deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }


}
