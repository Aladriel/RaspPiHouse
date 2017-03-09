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
public interface IReadingsEvent
{
    void ReadingsReceived(SensorReadings readings);
}
