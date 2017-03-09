/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Bartek
 */
public class ClientConnections
{
    private ArrayList<ClientConnection> clients;
    
    public ClientConnections()
    {
        clients = new ArrayList<>();
    }
    
    public void add(ClientConnection client)
    {
        boolean isFound = false;
        
        for(ClientConnection c : clients)
        {
            if(c.getDeviceId() == client.getDeviceId())
            {
                isFound = true;
                break;
            }
        }
        
        if(isFound == false)
            clients.add(client);
    }
    
    public void send(byte[] message, int deviceId, int bytesRead){      
        for(ClientConnection c : clients)
        {
            if(c.getDeviceId() == deviceId){
                c.send(message, bytesRead);
            }
        }
        

    }
    
    public void setDeviceId(Socket connection, int deviceId)
    {
        for(ClientConnection c : clients)
        {
            if(c.isLinkedToConnection(connection) == true)
                c.setDeviceId(deviceId);
        }
    }
    
    public void closeAll()
    {
        for(ClientConnection c : clients)
            c.close();
    }
}
