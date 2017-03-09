/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

import RoomController.Sensors.SensorReading;
import RoomController.Sensors.SensorReadings;
import RoomController.Sensors.SensorType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;

/**
 *
 * @author Bartek
 */

public abstract class CommsProtocol
{
    public static final byte TAG_MSG_TYPE = 0x01;
    public static final byte TAG_MSG_SENDER_ID = 0x02;
    public static final byte TAG_MSG_SENSOR_TYPE = 0x03;
    public static final byte TAG_MSG_SENSOR_READING = 0x04;
    public static final byte TAG_MSG_REQUEST_TYPE = 0x05;   
    public static final byte TAG_MSG_STREAM_TYPE = 0x06;
    public static final byte TAG_MSG_STREAM_ACTION_TYPE = 0x07;
    
    public static final byte MSG_TYPE_READING_DATA = 0x01;
    public static final byte MSG_TYPE_MASTER_REQUEST = 0x02;
    public static final byte MSG_TYPE_MASTER_STREAM = 0x03;
    
    public static final byte STREAM_TYPE_VOICE = 0x00;
    public static final byte STREAM_TYPE_VIDEO = 0x01;
    

    public static final byte STREAM_ACTION_START = 0x01;
    public static final byte STREAM_ACTION_STOP = 0x00;
    
    public static SensorReadings processMessage(byte[] message)
    {
        SensorReadings readings = new SensorReadings();
        SensorReading r = null;
        
        for(Tlv t : Tlv.parse(message))
        {
            switch(t.getTag())
            {
                case TAG_MSG_TYPE:
                {
                    break;
                }
                case TAG_MSG_SENDER_ID:
                    readings.setDeviceId((int)t.getValue()[0]);
                    break;
                case TAG_MSG_SENSOR_TYPE:
                    r = new SensorReading();
                    r.setType(SensorType.fromByte(t.getValue()[0]));
                    break;
                case TAG_MSG_SENSOR_READING:
                    r.setValue(Float.parseFloat(new String(t.getValue())));
                    readings.addReading(r.getType(), r.getValue());
                    break;
            }
        }
        
        return readings;
    }
    
    public static byte[] startVoiceStreamMessage()
    {
        byte[] msg = null;
        
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            
            try
            {
                s.write(buildTLV(TAG_MSG_TYPE, 1, MSG_TYPE_MASTER_STREAM));
                s.write(buildTLV(TAG_MSG_STREAM_TYPE, 1, STREAM_TYPE_VOICE));
                s.write(buildTLV(TAG_MSG_STREAM_ACTION_TYPE, 1, STREAM_ACTION_START));
            }
            catch (IOException ex)
            {
                RoomController.RoomController.logger.log(Level.WARNING, ex.getMessage());
                return null;
            }

            msg = s.toByteArray();

        
        return msg;
    }
    
    public static byte[] stopVoiceStreamMessage()
    {
        byte[] msg = null;
        
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            
            try
            {
                s.write(buildTLV(TAG_MSG_TYPE, 1, MSG_TYPE_MASTER_STREAM));
                s.write(buildTLV(TAG_MSG_STREAM_TYPE, 1, STREAM_TYPE_VOICE));
                s.write(buildTLV(TAG_MSG_STREAM_ACTION_TYPE, 1, STREAM_ACTION_STOP));
            }
            catch (IOException ex)
            {
                RoomController.RoomController.logger.log(Level.WARNING, ex.getMessage());
                return null;
            }

            msg = s.toByteArray();

        
        return msg;
    }
    
    
    
    
    private static byte[] buildTLV(byte tag, int len, byte value)
    {
        byte[] b = new byte[1];
        b[0] = value;
        
        return buildTLV(tag, len, b);
    }
    
    private static byte[] buildTLV(byte tag, int len, byte[] value)
    {
        byte[] tlv = new byte[value.length + 5];
        ByteBuffer buffer = ByteBuffer.wrap(tlv);
        
        buffer.put(tag);
        buffer.putInt(len);
        buffer.put(value);
        
        return tlv;
    }
}
