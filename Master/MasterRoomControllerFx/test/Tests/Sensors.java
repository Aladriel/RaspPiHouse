/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import RoomController.Sensors.HumiditySensor;
import RoomController.Sensors.LightSensor;
import RoomController.Sensors.MotionSensor;
import RoomController.Sensors.SensorNotAvailableException;
import RoomController.Sensors.SmokeSensor;
import RoomController.Sensors.ThermoSensor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mic
 */
public class Sensors {
    
    public Sensors() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void humiditySensor()
    {
        HumiditySensor s = new HumiditySensor(0);
        try {
            s.getReading();
        } catch (SensorNotAvailableException ex) {
            fail();
        }
    }
    
    @Test
    public void lightSensor()
    {
        LightSensor s = new LightSensor(0);
        try {
            s.getReading();
        } catch (SensorNotAvailableException ex) {
            fail();
        }
    }
    
    @Test
    public void motionSensor()
    {
        MotionSensor s = new MotionSensor(0);
        try {
            s.getReading();
        } catch (SensorNotAvailableException ex) {
            fail();
        }
    }
    
    @Test
    public void smokeSensor()
    {
        SmokeSensor s = new SmokeSensor(0);
        try {
            s.getReading();
        } catch (SensorNotAvailableException ex) {
            fail();
        }
    }
    
    @Test
    public void thermoSensor()
    {
        ThermoSensor s = new ThermoSensor(0);
        try {
            s.getReading();
        } catch (SensorNotAvailableException ex) {
            fail();
        }
    }
    
}
