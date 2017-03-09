/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

/**
 *
 * @author Bartek
 */

import RoomController.StreamReading;

/**
 *
 * @author Bartek
 */
public interface IStreamEvent
{
    void streamReceived(StreamReading reading);
}
