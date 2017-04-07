/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rasppi.intelligenthouse.Comms;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 *
 * @author Bartek
 */
public class Tlv
{
    private byte tag;
    private int len;
    private byte[] value;
    
    public Tlv()
    {
    }
    
    public Tlv(byte tag, int len, byte[] value)
    {
        this.tag = tag;
        this.len = len;
        this.value = value;
    }
    
    public Tlv(byte tag, int len, byte value)
    {
        this.tag = tag;
        this.len = len;
        byte[] b = new byte[1];
        b[0] = value;
        this.value = b;
    }

    public byte[] build()
    {
        byte[] tlv = new byte[value.length + 5];
        ByteBuffer buffer = ByteBuffer.wrap(tlv);

        buffer.put(tag);
        buffer.putInt(len);
        buffer.put(value);

        return tlv;
    }
    
    public static ArrayList<Tlv> parse(byte[] data)
    {
        ArrayList<Tlv> tlvs = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.wrap(data);
        
        while(buffer.position() < data.length)
        {
            Tlv tlv = new Tlv();
            tlv.setTag(buffer.get());
            tlv.setLen(buffer.getInt());
            byte[] b = new byte[tlv.getLen()];
            buffer.get(b, 0, tlv.getLen());
            tlv.setValue(b);
            
            tlvs.add(tlv);
        }
        
        return tlvs;
    }

    /**
     * @return the tag
     */
    public byte getTag() 
    {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(byte tag) 
    {
        this.tag = tag;
    }

    /**
     * @return the len
     */
    public int getLen() 
    {
        return len;
    }

    /**
     * @param len the len to set
     */
    public void setLen(int len) 
    {
        this.len = len;
    }

    /**
     * @return the value
     */
    public byte[] getValue() 
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(byte[] value) 
    {
        this.value = value;
    }
}
