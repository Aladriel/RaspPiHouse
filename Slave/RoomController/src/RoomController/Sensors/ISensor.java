/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Sensors;

/**
 *
 * @author eyrmin
 */
public interface ISensor
{
    public float getReading() throws ValueNotChangedException, SensorNotAvailableException;
}
