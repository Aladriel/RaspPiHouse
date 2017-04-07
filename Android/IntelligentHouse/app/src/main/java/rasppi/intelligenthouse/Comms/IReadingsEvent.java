/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rasppi.intelligenthouse.Comms;

import rasppi.intelligenthouse.Sensors.SensorReadings;

/**
 *
 * @author Bartek
 */
public interface IReadingsEvent
{
    void ReadingsReceived(byte[] readings);
}
