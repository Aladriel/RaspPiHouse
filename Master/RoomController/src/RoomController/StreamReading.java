/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController;

/**
 *
 * @author Bartek
 */
public class StreamReading {
    private byte[] buffer = new byte[4000];
    private int bytesRead=0;

    /**
     * @return the buffer
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * @param buffer the buffer to set
     */
    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    /**
     * @return the bytesRead
     */
    public int getBytesRead() {
        return bytesRead;
    }

    /**
     * @param bytesRead the bytesRead to set
     */
    public void setBytesRead(int bytesRead) {
        this.bytesRead = bytesRead;
    }
    
}
