/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import java.net.Socket;
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
public class ClientConnectionTest {
    
    public ClientConnectionTest() {
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
     * Test of isLinkedToConnection method, of class ClientConnection.
     */
    @Test
    public void testIsLinkedToConnection() {
        System.out.println("isLinkedToConnection");
        Socket connection = new Socket();
        ClientConnection instance = new ClientConnection(new SlaveConnectionTask(connection));
        boolean expResult = true;
        boolean result = instance.isLinkedToConnection(connection);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDeviceId method, of class ClientConnection.
     */
    @Test
    public void testGetDeviceId() {
        System.out.println("getDeviceId");
        Socket connection = new Socket();
        ClientConnection instance = new ClientConnection(new SlaveConnectionTask(connection));
        int expResult = 0;
        int result = instance.getDeviceId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDeviceType method, of class ClientConnection.
     */
    @Test
    public void testGetDeviceType() {
        System.out.println("getDeviceType");
        Socket connection = new Socket();
        ClientConnection instance = new ClientConnection(new SlaveConnectionTask(connection));
        String expResult = "Slave";
        String result = instance.getDeviceType();
        assertEquals(expResult, result);
    }
    
}
