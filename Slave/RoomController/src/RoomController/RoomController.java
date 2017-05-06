/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController;

import RoomController.Sensors.SensorsManager;
import RoomController.Comms.CommsManager;
import RoomController.Comms.CommsProtocol;
import RoomController.Comms.IControlEvent;
import RoomController.Comms.IStreamEvent;
import RoomController.Sensors.SensorReading;
import RoomController.Sensors.SensorReadings;
import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author eyrmin
 */
public class RoomController implements IStreamEvent, IControlEvent
{
    final String LOGGER_NAME = "RCLogger";
    final String LOG_PATH = "log.log";
    
    private ConfigManager confManager;
    private CommsManager commsManager;
    private SensorsManager sensorsManager;
    
    private IncomingSoundListener isl;
    Thread thread;

    InputStream inputStream;
    
    public static Logger logger;
    
    public RoomController()
    {
        logger = Logger.getLogger(LOGGER_NAME);
    }
    
    public boolean Initialise()
    {
        boolean success = false;
        
        if(logger != null)
        {
            try
            {
                logger.addHandler(new FileHandler(LOG_PATH, true));
            }
            catch (IOException | SecurityException ex)
            {
                return false;
            }
            
            confManager = new ConfigManager();
            sensorsManager = new SensorsManager();
            
            if(confManager.LoadConfig(sensorsManager))
            {
                logger.setLevel(confManager.getLogLevel());
                commsManager = new CommsManager(confManager.getMasterIp(), confManager.getMasterPort());
                commsManager.addStreamEventListener(this);
                commsManager.addControlEventListener(this);
                commsManager.start();
                
                isl = new IncomingSoundListener();
                thread = new Thread(isl);
                //thread.start();
                success = true;
            }
        }

        return success;
    }
    
    public void runIdle() throws IOException
    {
        while(true)
        {
            // check the sensors readings and if any values changed, send the reading to the master unit
            processReadings(sensorsManager.getReadings());
            
            try
            {
                // check the readings every 30 seconds
                Thread.sleep(3000);
            }
            catch (InterruptedException ex)
            {
                logger.log(Level.WARNING, ex.getMessage());
            }
            catch(Exception e)
            {
                break;
            }
        }
        commsManager.stop();
    }
    
    private void processReadings(SensorReadings readings) throws IOException
    {
        if(readings == null)
            return;
        
        if(readings.getCount() > 0)
        {
            ArrayList<SensorReading> sensorReadings = readings.getReadings();
            byte[] message = CommsProtocol.createReadingsMessage(readings, confManager.getDeviceId());
            commsManager.sendToMaster(message);
        }
    }

    @Override
    public void streamReceived(byte[] message) {
        byte[] streamInfo = CommsProtocol.processStream(message);
        if(streamInfo[0] == 0) {
            if(!thread.isAlive())
                thread.start();
        } else {
            if(!thread.isInterrupted())
                thread.interrupt();
        }
 
    }

    @Override
    public void controlMessageReceived(byte[] controlInfo) {
        switch(CommsProtocol.getMessageType(controlInfo)){
            case CommsProtocol.MSG_TYPE_SET_LIGHT:
                String lightState = CommsProtocol.processLightStateMessage(controlInfo);
                processLightState(lightState);
            case CommsProtocol.MSG_TYPE_SET_BLIND:
                String blindState = CommsProtocol.processLightStateMessage(controlInfo);
                processBlindState(blindState);
        }
    }

    private void processLightState(String lightState) {
        ProcessBuilder pb ;
        
        if(lightState.equals("0.0"))
         {
                pb = new ProcessBuilder("python", "/home/pi/Projects/Python/set_on_light.py", lightState);
         }  
        else
        {
                pb = new ProcessBuilder("python", "/home/pi/Projects/Python/set_off_light.py", lightState);
        }/*   ProcessBuilder pb = new ProcessBuilder();      
        List<String> command = new ArrayList<>();
		command.add("gpio");
		command.add("-g");
		command.add("write");
		command.add("18");
                command.add("0");
            pb = new ProcessBuilder(command);*/
        try
        {
           // System.out.println(lightState);             
            Process p = pb.start();
        }
        catch (IOException ex)
        {
            logger.log(Level.WARNING, ex.getMessage());
        }
    }
    
    private void processBlindState(String lightState) {       
        ProcessBuilder pb ;
        
        if(lightState.equals("0.0"))
         {
                pb = new ProcessBuilder("python", "/home/pi/Projects/Python/set_on_blind.py", lightState);
         }  
        else
        {
                pb = new ProcessBuilder("python", "/home/pi/Projects/Python/set_off_blind.py", lightState);
        }
        try
        {
           // System.out.println(lightState);             
            Process p = pb.start();
        }
        catch (IOException ex)
        {
            logger.log(Level.WARNING, ex.getMessage());
        }
    }
    

       class IncomingSoundListener implements Runnable{
           
            private SourceDataLine speaker;
            private AudioFormat format = getAudioFormat();
            private boolean isRunning = false;
            
            public IncomingSoundListener() {
                InitializeSpeaker();
            }
            
            private AudioFormat getAudioFormat(){
                float sampleRate = 16000.0F;
                int sampleSizeBits = 16;
                int channels = 1;
                boolean signed = true;
                boolean bigEndian = false;

                return new AudioFormat(sampleRate, sampleSizeBits, channels, signed, bigEndian);
            }
    
            private void InitializeSpeaker() {      
                try {
                    DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class,format);
                    speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
                    speaker.open(format);
                    speaker.start();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
            @Override
            public void run(){
            try{
                isRunning = true;
                while(isRunning) {
                    inputStream = commsManager.getInputStream();
                    byte[] data = new byte[256];
                    inputStream.read(data);
                    ByteArrayInputStream bais = new ByteArrayInputStream(data);
                    AudioInputStream ais = new AudioInputStream(bais,format,data.length);
                    int bytesRead = 0;
                    if((bytesRead = ais.read(data)) != -1){
                        System.out.println("Writing to audio output.");
                        speaker.write(data,0,bytesRead);
                    }
                    ais.close();
                    bais.close();
                }
               speaker.drain();
               speaker.close();
               System.out.println("Stopped listening to incoming audio.");
            }catch(Exception ex){
                Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
