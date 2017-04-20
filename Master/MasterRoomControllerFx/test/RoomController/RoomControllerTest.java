/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController;

import MasterRoomControllerFx.MainScreenController;
import RoomController.Comms.UserLoginInfo;
import RoomController.Sensors.SensorReadings;
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
public class RoomControllerTest {
    
    public RoomControllerTest() {
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

    /**
     * Test of Initialise method, of class RoomController.
     */
    @Test
    public void testInitialise() {
        System.out.println("Initialise");
        RoomController instance = new RoomController();
        boolean expResult = true;
        boolean result = instance.Initialise();
        assertEquals(expResult, result);
    }
    
}
