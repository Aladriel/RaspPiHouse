/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rasppi.intelligenthouse.Comms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import rasppi.intelligenthouse.Sensors.SensorReading;
import rasppi.intelligenthouse.Sensors.SensorReadings;
import rasppi.intelligenthouse.Sensors.SensorType;

/**
 *
 * @author eyrmin
 */

public abstract class CommsProtocol
{
    public static final byte TAG_MSG_TYPE = 0x01;
    public static final byte TAG_MSG_SENDER_ID = 0x02;
    public static final byte TAG_MSG_SENSOR_TYPE = 0x03;
    public static final byte TAG_MSG_SENSOR_READING = 0x04;
    public static final byte TAG_MSG_REQUEST_TYPE = 0x05;
    public static final byte TAG_MSG_SENDER_LOGIN = 0x08;
    public static final byte TAG_MSG_SENDER_PASSWORD = 0x09;
    public static final byte TAG_MSG_SENDER_PRIVILEGES = 0x0A;
    public static final byte TAG_MSG_ROOM_NAME = 0x0B;



    
    public static final byte TAG_MSG_STREAM_TYPE = 0x06;
    public static final byte TAG_MSG_STREAM_ACTION_TYPE = 0x07;
    
    public static final byte MSG_TYPE_READING_DATA = 0x01;
    public static final byte MSG_TYPE_MASTER_REQUEST = 0x02;
    public static final byte MSG_TYPE_MASTER_STREAM = 0x03;
    public static final byte MSG_TYPE_LOGIN_REQUEST = 0x04;
    public static final byte MSG_TYPE_LOGIN_INFO = 0x05;

    
    public static final byte STREAM_TYPE_VOICE = 0x01;
    public static final byte STREAM_TYPE_VIDEO = 0x02;
    
   // public static final byte STREAM_ACTION_STOP = 0x00;


    public static byte[] createLoginRequestMessage(String login, String password){
        byte[] msg = null;
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            s.write(buildTLV(TAG_MSG_TYPE, 1, MSG_TYPE_LOGIN_REQUEST));
            byte[] log = login.getBytes(StandardCharsets.UTF_8);
            s.write(buildTLV(TAG_MSG_SENDER_LOGIN, log.length, log));
            byte[] pwd = password.getBytes(StandardCharsets.UTF_8);
            s.write(buildTLV(TAG_MSG_SENDER_PASSWORD, pwd.length, pwd));

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        msg = s.toByteArray();
        return  msg;
    }

    public static UserLoginInfo processLoginMessage(byte[] message)
    {
        UserLoginInfo user = new UserLoginInfo();
        String[] roomNames = new String[6];
        int roomCount=0;

        for(Tlv t : Tlv.parse(message))
        {
            switch(t.getTag())
            {
                case TAG_MSG_TYPE:
                {
                    break;
                }
                case TAG_MSG_SENDER_LOGIN:
                    user.setLogin(new String(t.getValue(),StandardCharsets.UTF_8));
                    break;
                case TAG_MSG_SENDER_PASSWORD:
                    user.setPassword(new String(t.getValue(),StandardCharsets.UTF_8));
                    break;
                case TAG_MSG_SENDER_PRIVILEGES:
                    IntBuffer intBuf =
                            ByteBuffer.wrap(t.getValue())
                                    .order(ByteOrder.BIG_ENDIAN)
                                    .asIntBuffer();
                    int[] array = new int[intBuf.remaining()];
                    intBuf.get(array);
                    user.setPrivileges(array);
                    break;
                case TAG_MSG_ROOM_NAME:
                    roomNames[roomCount] = new String(t.getValue(),StandardCharsets.UTF_8);
                    roomCount++;
                    break;
            }
        }
        user.setRoomNames(roomNames);

        return user;
    }

    public static SensorReadings processReadingMessage(byte[] message)
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

    /*
    public static byte[] createReadingsMessage(SensorReadings readings, byte deviceId)
    {
        byte[] msg = null;
        
        if(readings.getCount() > 0)
        {
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            
            try
            {
                s.write(buildTLV(TAG_MSG_TYPE, 1, MSG_TYPE_READING_DATA));
                s.write(buildTLV(TAG_MSG_SENDER_ID, 1, deviceId));
                
                for(SensorReading r : readings.getReadings())
                {
                    String value = Float.toString(r.getValue());
                    s.write(buildTLV(TAG_MSG_SENSOR_TYPE, 1, (byte)r.getSensorType().toValue()));
                    s.write(buildTLV(TAG_MSG_SENSOR_READING, (byte)value.length(), value.getBytes()));
                }
            }
            catch (IOException ex)
            {
                RoomController.RoomController.logger.log(Level.WARNING, ex.getMessage());
                return null;
            }

            msg = s.toByteArray();
        }
        
        return msg;
    }
    */
    public static byte getMessageType(byte[] message) {
        byte type = 0;
        for(Tlv t : Tlv.parse(message))
        {
            type = t.getValue()[0];
            break;
        }
        return type;
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
