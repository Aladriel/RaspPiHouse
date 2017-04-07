/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController;

import RoomController.Sensors.HumiditySensor;
import RoomController.Sensors.LightSensor;
import RoomController.Sensors.MotionSensor;
import RoomController.Sensors.Sensor;
import RoomController.Sensors.SensorInterface;
import RoomController.Sensors.SensorType;
import RoomController.Sensors.SensorsManager;
import RoomController.Sensors.SmokeSensor;
import RoomController.Sensors.ThermoSensor;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author eyrmin
 */
public class ConfigManager
{
    final int MASTER_PORT = 12001;
    final String CONFIG_PATH = "config.xml";
    
    // Elements
    final String CONFIG_ELEM_DEVICE = "Device";
    final String CONFIG_ELEM_SENSORS = "Sensors";
    final String CONFIG_ELEM_SENSOR = "Sensor";
    
    // Attributes
    final String CONFIG_ATTR_TYPE = "type";
    final String CONFIG_ATTR_MASTER_IP = "masterIp";
    final String CONFIG_ATTR_ID = "id";
    final String CONFIG_ATTR_LOG_LEVEL = "log_level";
    final String CONFIG_ATTR_PIN = "pin";
    final String CONFIG_ATTR_INTERFACE = "interface";
    final String CONFIG_ATTR_ONE_WIRE_DEVICE_ID = "oneWireDeviceId";
    
    private String masterIp;
    private Level logLevel;
    private byte deviceId;
    
    public String getMasterIp()
    {
        return masterIp;
    }
    
    public int getMasterPort()
    {
        return MASTER_PORT;
    }
    
    public Level getLogLevel()
    {
        return logLevel;
    }
    
    public byte getDeviceId()
    {
        return deviceId;
    }
    
    public ConfigManager()
    {
        masterIp = "";
        logLevel = Level.CONFIG;
    }
    
    public boolean LoadConfig(SensorsManager sensorManager)
    {
        boolean success = false;
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try 
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(CONFIG_PATH);
            
            Element root = doc.getDocumentElement();
            
            Element deviceElem = (Element)root.getElementsByTagName(CONFIG_ELEM_DEVICE).item(0);
            if(deviceElem.getAttribute(CONFIG_ATTR_TYPE).equalsIgnoreCase("slave"))
                masterIp = deviceElem.getAttribute(CONFIG_ATTR_MASTER_IP);
            deviceId = Byte.parseByte(deviceElem.getAttribute(CONFIG_ATTR_ID));
            logLevel = Level.parse(deviceElem.getAttribute(CONFIG_ATTR_LOG_LEVEL));
            
            Node sensors = root.getElementsByTagName(CONFIG_ELEM_SENSORS).item(0);
            NodeList nodes = sensors.getChildNodes();
            
            for(int i = 0; i < nodes.getLength(); i++)
            {
                if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE)
                {
                    Element sensorElem = (Element)nodes.item(i);
                    SensorType type = SensorType.valueOf(sensorElem.getAttribute(CONFIG_ATTR_TYPE));
                    Sensor sensor = null;

                    switch(type)
                    {
                        case Humidity:
                            sensor = new HumiditySensor(Integer.parseInt(sensorElem.getAttribute(CONFIG_ATTR_PIN)));
                            break;
                        case Light:
                            sensor = new LightSensor(Integer.parseInt(sensorElem.getAttribute(CONFIG_ATTR_PIN)));
                            break;
                        case Thermo:
                            sensor = new ThermoSensor(Integer.parseInt(sensorElem.getAttribute(CONFIG_ATTR_PIN)));
                            break;
                        case Smoke:
                            sensor = new SmokeSensor(Integer.parseInt(sensorElem.getAttribute(CONFIG_ATTR_PIN)));
                            break;
                        case Motion:
                            sensor = new MotionSensor(Integer.parseInt(sensorElem.getAttribute(CONFIG_ATTR_PIN)));
                            break;
                    }

                    if(sensor != null)
                    {
                        if(!sensorElem.getAttribute(CONFIG_ATTR_INTERFACE).equalsIgnoreCase(""))
                            sensor.setInterface(SensorInterface.valueOf(sensorElem.getAttribute(CONFIG_ATTR_INTERFACE)));
                        if(!sensorElem.getAttribute(CONFIG_ATTR_ONE_WIRE_DEVICE_ID).equalsIgnoreCase(""))
                            sensor.setOneWireDeviceId(sensorElem.getAttribute(CONFIG_ATTR_ONE_WIRE_DEVICE_ID));

                        sensorManager.addSensor(sensor);
                    }
                }
            }
            
            success = true;
        }
        catch(Exception e)
        {
            RoomController.logger.log(Level.SEVERE, e.getMessage());
        }
        
        return success;
    }
}
