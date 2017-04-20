/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.Sensors.SensorReadings;
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
public class CommsProtocolTest {
    
    public CommsProtocolTest() {
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
     * Test of createLoginConfirmationMessage method, of class CommsProtocol.
     */
    @Test
    public void testCreateLoginConfirmationMessage_UserLoginInfo() {
        System.out.println("createLoginConfirmationMessage");
        UserLoginInfo userInfo = new UserLoginInfo("login", "password", new Socket());
        byte[] expResult = new byte[29];
        expResult[0] = 1;
        expResult[1] = 0;
        expResult[2] = 0;
        expResult[3] = 0;
        expResult[4] = 1;
        expResult[5] = 5;
        expResult[6] = 8;
        expResult[7] = 0;
        expResult[8] = 0;
        expResult[9] = 0;
        expResult[10] = 5;
        expResult[11] = 108;
        expResult[12] = 111;
        expResult[13] = 103;
        expResult[14] = 105;
        expResult[15] = 110;
        expResult[16] = 9;
        expResult[17] = 0;
        expResult[18] = 0;
        expResult[19] = 0;
        expResult[20] = 8;
        expResult[21] = 112;
        expResult[22] = 97;
        expResult[23] = 115;
        expResult[24] = 115;
        expResult[25] = 119;
        expResult[26] = 111;
        expResult[27] = 114;
        expResult[28] = 100;
        byte[] result = CommsProtocol.createLoginConfirmationMessage(userInfo);
        assertArrayEquals(expResult, result);
    }
    
}
