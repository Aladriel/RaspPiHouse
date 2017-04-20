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
public class UserLoginInfoTest {
    
    public UserLoginInfoTest() {
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
     * Test of getLogin method, of class UserLoginInfo.
     */
    @Test
    public void testGetLogin() {
        System.out.println("getLogin");
        UserLoginInfo instance = new UserLoginInfo();
        String expResult = "";
        String result = instance.getLogin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class UserLoginInfo.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        UserLoginInfo instance = new UserLoginInfo();
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrivileges method, of class UserLoginInfo.
     */
    @Test
    public void testGetPrivileges() {
        System.out.println("getPrivileges");
        UserLoginInfo instance = new UserLoginInfo();
        int[] expResult = null;
        int[] result = instance.getPrivileges();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnection method, of class UserLoginInfo.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        UserLoginInfo instance = new UserLoginInfo();
        Socket expResult = null;
        Socket result = instance.getConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
