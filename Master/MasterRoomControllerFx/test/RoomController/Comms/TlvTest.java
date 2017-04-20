/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import java.util.ArrayList;
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
public class TlvTest {
    
    public TlvTest() {
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
     * Test of build method, of class Tlv.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        byte b = 1;
        Tlv instance = new Tlv(b, 4, b);
        byte[] expResult = new byte[6];
        expResult[0] = b;
        expResult[1] = 0;
        expResult[2] = 0;
        expResult[3] = 0;
        expResult[4] = 4;
        expResult[5] = b;
        byte[] result = instance.build();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getTag method, of class Tlv.
     */
    @Test
    public void testGetTag() {
        System.out.println("getTag");
        byte b = 1;
        Tlv instance = new Tlv(b, 4, b);
        byte expResult = b;
        byte result = instance.getTag();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLen method, of class Tlv.
     */
    @Test
    public void testGetLen() {
        System.out.println("getLen");
        byte b = 1;
        Tlv instance = new Tlv(b, 4, b);
        int expResult = 4;
        int result = instance.getLen();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class Tlv.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        byte b = 1;
        Tlv instance = new Tlv(b, 4, b);
        byte[] expResult = new byte[1];
        expResult[0] = b;
        byte[] result = instance.getValue();
        assertArrayEquals(expResult, result);
    }
    
}
