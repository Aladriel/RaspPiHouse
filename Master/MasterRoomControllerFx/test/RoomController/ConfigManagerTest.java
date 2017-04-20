/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController;

import RoomController.Sensors.SensorsManager;
import java.util.logging.Level;
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
public class ConfigManagerTest {
    
    public ConfigManagerTest() {
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
     * Test of getMasterPort method, of class ConfigManager.
     */
    @Test
    public void testGetMasterPort() {
        System.out.println("getMasterPort");
        ConfigManager instance = new ConfigManager();
        int expResult = 12001;
        int result = instance.getMasterPort();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLogLevel method, of class ConfigManager.
     */
    @Test
    public void testGetLogLevel() {
        System.out.println("getLogLevel");
        ConfigManager instance = new ConfigManager();
        Level expResult = Level.CONFIG;
        Level result = instance.getLogLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDeviceId method, of class ConfigManager.
     */
    @Test
    public void testGetDeviceId() {
        System.out.println("getDeviceId");
        ConfigManager instance = new ConfigManager();
        byte expResult = 0;
        byte result = instance.getDeviceId();
        assertEquals(expResult, result);
    }

    /**
     * Test of LoadConfig method, of class ConfigManager.
     */
    @Test
    public void testLoadConfig() {
        System.out.println("LoadConfig");
        SensorsManager sensorManager = new SensorsManager();
        RoomController controller = new RoomController();
        ConfigManager instance = new ConfigManager();
        boolean expResult = true;
        boolean result = instance.LoadConfig(sensorManager, controller);
        assertEquals(expResult, result);
    }
    
}
