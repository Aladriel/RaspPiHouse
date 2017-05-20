/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import static RoomController.Comms.CommsProtocol.TAG_MSG_STREAM_LENGTH;
import RoomController.Sensors.SensorReadings;
import RoomController.Sensors.SensorType;
import RoomController.StreamReading;
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
     * Test of processStream method, of class CommsProtocol.
     */
    @Test
    public void testProcessStream() {
        System.out.println("processStream");
        byte b[] = {89, 0, 0, 0};
        byte[] message = (new Tlv(TAG_MSG_STREAM_LENGTH, 4, b)).build();
        StreamReading expResult = new StreamReading();
        expResult.setBytesRead(89);
        StreamReading result = CommsProtocol.processStream(message);
        assertEquals(expResult.getBytesRead(), result.getBytesRead());
    }

    /**
     * Test of createReadingsMessage method, of class CommsProtocol.
     */
    @Test
    public void testCreateReadingsMessage() {
        System.out.println("createReadingsMessage");
        SensorReadings readings = new SensorReadings();
        readings.addReading(SensorType.Humidity, 1.34f);
        byte deviceId = 0;
        byte[] expResult = new byte[27];
        expResult[0] = 1;
        expResult[1] = 0;
        expResult[2] = 0;
        expResult[3] = 0;
        expResult[4] = 1;
        expResult[5] = 1;
        expResult[6] = 2;
        expResult[7] = 0;
        expResult[8] = 0;
        expResult[9] = 0;
        expResult[10] = 1;
        expResult[11] = 0;
        expResult[12] = 3;
        expResult[13] = 0;
        expResult[14] = 0;
        expResult[15] = 0;
        expResult[16] = 1;
        expResult[17] = 2;
        expResult[18] = 4;
        expResult[19] = 0;
        expResult[20] = 0;
        expResult[21] = 0;
        expResult[22] = 4;
        expResult[23] = 49;
        expResult[24] = 46;
        expResult[25] = 51;
        expResult[26] = 52;
        byte[] result = CommsProtocol.createReadingsMessage(readings, deviceId);
        assertArrayEquals(expResult, result);
    }
    
}
