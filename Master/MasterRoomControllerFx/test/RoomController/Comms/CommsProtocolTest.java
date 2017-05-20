/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import static RoomController.Comms.CommsProtocol.TAG_MSG_BLIND_VALUE;
import static RoomController.Comms.CommsProtocol.TAG_MSG_SENDER_ID;
import static RoomController.Comms.CommsProtocol.TAG_MSG_SENDER_LOGIN;
import static RoomController.Comms.CommsProtocol.TAG_MSG_TO_DEVICE_ID;
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
    
    @Test
    public void testProcessLightStateMessage()
    {
        byte b[] = {0, 0, 0, 72};
        Tlv tlv = new Tlv(TAG_MSG_TO_DEVICE_ID, 4, b);
        int[] result = CommsProtocol.processLightStateMessage(tlv.build());
        assertEquals(72, result[0]);
    }
    
    @Test
    public void testProcessBlindStateMessage()
    {
        byte b[] = {0, 0, 0, 34};
        Tlv tlv = new Tlv(TAG_MSG_TO_DEVICE_ID, 4, b);
        int[] result = CommsProtocol.processBlindStateMessage(tlv.build());
        assertEquals(34, result[0]);
    }
           
    @Test
    public void testProcessMessage()
    {
        byte b[] = {1, 0, 0, 0};
        Tlv tlv = new Tlv(TAG_MSG_SENDER_ID, 4, b);
        SensorReadings result = CommsProtocol.processMessage(tlv.build());
        assertEquals(1, result.getDeviceId());
    }
    
    @Test
    public void testProcessLoginMessage()
    {
        byte b[] = {0x6c,0x6f, 0x67, 0x69, 0x6e};
        Tlv tlv = new Tlv(TAG_MSG_SENDER_LOGIN, 5, b);
        UserLoginInfo result = CommsProtocol.processLoginMessage(tlv.build());
        assertEquals("login", result.getLogin());
    }
    
    @Test
    public void testCreateLoginConfirmationMessage()
    {
        UserLoginInfo user = new UserLoginInfo("login", "password", new Socket());
        user.setPrivileges(new int[]{12, 74});
        String roomNames[] = {"Kitchen", "Lounge"};
        byte result[] = CommsProtocol.createLoginConfirmationMessage(user, roomNames);
        byte[] expResult = new byte[65];
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
        expResult[29] = 10;
        expResult[30] = 0;
        expResult[31] = 0;
        expResult[32] = 0;
        expResult[33] = 8;
        expResult[34] = 0;
        expResult[35] = 0;
        expResult[36] = 0;
        expResult[37] = 12;
        expResult[38] = 0;
        expResult[39] = 0;
        expResult[40] = 0;
        expResult[41] = 74;
        expResult[42] = 11;
        expResult[43] = 0;
        expResult[44] = 0;
        expResult[45] = 0;
        expResult[46] = 7;
        expResult[47] = 75;
        expResult[48] = 105;
        expResult[49] = 116;
        expResult[50] = 99;
        expResult[51] = 104;
        expResult[52] = 101;
        expResult[53] = 110;
        expResult[54] = 11;
        expResult[55] = 0;
        expResult[56] = 0;
        expResult[57] = 0;
        expResult[58] = 6;
        expResult[59] = 76;
        expResult[60] = 111;
        expResult[61] = 117;
        expResult[62] = 110;
        expResult[63] = 103;
        expResult[64] = 101;
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testStartVoiceStreamMessage()
    {
        byte result[] = CommsProtocol.startVoiceStreamMessage();
        byte[] expResult = new byte[18];
        expResult[0] = 1;
        expResult[1] = 0;
        expResult[2] = 0;
        expResult[3] = 0;
        expResult[4] = 1;
        expResult[5] = 3;
        expResult[6] = 6;
        expResult[7] = 0;
        expResult[8] = 0;
        expResult[9] = 0;
        expResult[10] = 1;
        expResult[11] = 0;
        expResult[12] = 7;
        expResult[13] = 0;
        expResult[14] = 0;
        expResult[15] = 0;
        expResult[16] = 1;
        expResult[17] = 1;
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testStopVoiceStreamMessage()
    {
        byte result[] = CommsProtocol.startVoiceStreamMessage();
        byte[] expResult = new byte[18];
        expResult[0] = 1;
        expResult[1] = 0;
        expResult[2] = 0;
        expResult[3] = 0;
        expResult[4] = 1;
        expResult[5] = 3;
        expResult[6] = 6;
        expResult[7] = 0;
        expResult[8] = 0;
        expResult[9] = 0;
        expResult[10] = 1;
        expResult[11] = 0;
        expResult[12] = 7;
        expResult[13] = 0;
        expResult[14] = 0;
        expResult[15] = 0;
        expResult[16] = 1;
        expResult[17] = 1;
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testGetMessageType()
    {
        byte b = TAG_MSG_BLIND_VALUE;
        Tlv tlv = new Tlv(b, 1, b);
        assertEquals(TAG_MSG_BLIND_VALUE, CommsProtocol.getMessageType(tlv.build()));
    }
          
}
