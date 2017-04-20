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
public class SlaveConnectionTaskTest {
    
    public SlaveConnectionTaskTest() {
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
     * Test of getConnection method, of class SlaveConnectionTask.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        Socket expResult = new Socket();
        SlaveConnectionTask instance = new SlaveConnectionTask(expResult);
        Socket result = instance.getConnection();
        assertEquals(expResult, result);
    }

    /**
     * Test of send method, of class SlaveConnectionTask.
     */
    @Test
    public void testSend() throws Exception {
        System.out.println("send");
        int bytesRead = 0;
        byte[] data = new byte[1];
        data[0] = 0x41;
        SlaveConnectionTask instance = new SlaveConnectionTask(new Socket());
        boolean expResult = true;
        boolean result = instance.send(data, bytesRead);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendToMobile method, of class SlaveConnectionTask.
     */
    @Test
    public void testSendToMobile() {
        System.out.println("sendToMobile");
        byte[] data = new byte[1];
        data[0] = 0x41;
        SlaveConnectionTask instance = new SlaveConnectionTask(new Socket());
        boolean expResult = true;
        boolean result = instance.sendToMobile(data);
        assertEquals(expResult, result);
    }
    
}
