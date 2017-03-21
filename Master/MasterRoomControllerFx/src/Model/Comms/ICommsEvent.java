/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import java.net.Socket;

/**
 *
 * @author Bartek
 */
public interface ICommsEvent
{
    void messageReceived(byte[] buffer, Socket connection);
}
