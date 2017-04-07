/*
 * To change this license header, choose License -+Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController;

import MasterRoomControllerFx.MainScreenController;
import RoomController.Sensors.SensorsManager;
import RoomController.Comms.CommsManager;
import RoomController.Comms.CommsProtocol;
import RoomController.Comms.ILoginEvent;
import RoomController.Comms.IReadingsEvent;
import RoomController.Comms.UserLoginInfo;
import RoomController.Sensors.SensorReading;
import RoomController.Sensors.SensorReadings;
import RoomController.Sensors.SensorType;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Bartek
 */
public class RoomController implements IReadingsEvent, ILoginEvent
{
    final String LOGGER_NAME = "RCLogger";
    final String LOG_PATH = "log.log";
    
    private ConfigManager confManager;
    private CommsManager commsManager;
    private SensorsManager sensorsManager;
    private MainScreenController screenController;
    private Thread mainThread;
    private boolean isRunning;
    private Clock clock;
    private VoiceStream voiceStream;
    private String[] roomNames = new String[6];
    
    
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
            
            if(confManager.LoadConfig(sensorsManager, this))
            {
                logger.setLevel(confManager.getLogLevel());
                commsManager = new CommsManager(confManager.getMasterPort());
                commsManager.addReadingsEventListener(this);
                commsManager.addLoginEventListener(this);
                commsManager.start();
		
		mainThread = new Thread(new Runnable()
		{
		    @Override
		    public void run()
		    {
			isRunning = true;
			
			while(isRunning)
			{
			    // check own sensors and update the values on the UI if they changed
			   //processReadings(sensorsManager.getReadings(), 1);

			    try
			    {
				// check the readings every 3 seconds
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
		});
                
                mainThread.start();
                
                clock = new Clock();
                Thread clockThread = new Thread(clock);
                clockThread.start();
                
                voiceStream = new VoiceStream();
                if(voiceStream.isLineAvailable()) {
                    Thread voiceStreamThread = new Thread(voiceStream);
                    voiceStreamThread.start();
                }
                success = true;
                
            }
        }

        return success;
    }
    
    public void setScreenController(MainScreenController controller)
    {
	screenController = controller;
    }
    
    public void stopAllTasks()
    {
	isRunning = false;
        
        if(mainThread != null)
            mainThread.interrupt(); // interrupt to break out of the sleep()
        if(commsManager != null)
            commsManager.stop();
        if(clock != null)
            clock.stop();
        if(voiceStream != null)
            voiceStream.stop();
    }
   
    
    private void processReadings(SensorReadings readings, int deviceId)
    {
        if(readings == null)
            return;
        
        if(readings.getCount() > 0)
        {
	    for(SensorReading reading : readings.getReadings())
	    {
		switch(reading.getType())
		{    
		    case Light:
		    {
			processLightReading(reading, deviceId);
			break;
		    }
		    case Humidity:
		    {
			processHumidityReading(reading, deviceId);
			break;
		    }
		    case Thermo:
		    {
			processThermoReading(reading, deviceId);
			break;
		    }
                    case Smoke:
		    {
			processSmokeReading(reading, deviceId);
			break;
		    }
                    case Motion:
		    {
			processMotionReading(reading, deviceId);
			break;
		    }
		}
	    }
        }
    }
    
    
    
    private void processLightReading(SensorReading reading, int deviceId)
    {
        try
        { 
            if(reading.getType() == SensorType.Light)
            {
                if(deviceId < 4)
                    screenController.setLight(reading.getValue(), 0, deviceId - 1);
                else
                    screenController.setLight(reading.getValue(), 1, deviceId - 4);
            }
        }
        catch(NullPointerException e)
        {
        }
    }
    
    private void processSmokeReading(SensorReading reading, int deviceId)
    {
        try
        { 
            if(reading.getType() == SensorType.Smoke)
            {
                if(deviceId < 4)
                    screenController.setFire(reading.getValue(), 0, deviceId-1);
                else
                    screenController.setFire(reading.getValue(), 1, deviceId - 4);
            }
        }
        catch(NullPointerException e)
        {
        }
    }
    
    
        
    private void processMotionReading(SensorReading reading, int deviceId)
    {
        try
        { 
            if(reading.getType() == SensorType.Motion)
            {
                if(deviceId < 4)
                    screenController.setMotion(reading.getValue(), 0, deviceId-1);
                else
                    screenController.setMotion(reading.getValue(), 1, deviceId - 4);
                //if(screenController.isAlertOn()) sendEmailNotification();
            }
        }
        catch(NullPointerException e)
        {
        }
    }
    
    
    private void processThermoReading(SensorReading reading, int deviceId)
    {
        try
        {
            if(reading.getType() == SensorType.Thermo)
            {
                if(deviceId < 4)
                    screenController.setTemp(reading.getValue(), 0, deviceId - 1);
                else
                    screenController.setTemp(reading.getValue(), 1, deviceId - 4);
            }
        }
        catch(NullPointerException e)
        {
        }
    }
	
    private void processHumidityReading(SensorReading reading, int deviceId)
    {
        try
        {
            if(reading.getType() == SensorType.Humidity)
            {
                if(deviceId < 4)
                    screenController.setHumidity(reading.getValue(), 0, deviceId - 1);
                else
                    screenController.setHumidity(reading.getValue(), 1, deviceId - 4);
            }
        }
        catch(NullPointerException e)
        {
        }
    }

    @Override
    public void ReadingsReceived(SensorReadings readings) 
    {
        processReadings(readings, readings.getDeviceId());
    }
    
    @Override
    public void loginInfoReceived(UserLoginInfo loginInfo) {
         //decode hash
        if(readUserFromXML(loginInfo)) {          
            byte[] message = CommsProtocol.createLoginConfirmationMessage(loginInfo);
            commsManager.sendToMobile(message,loginInfo.getConnection());
        } else {
            loginInfo.setLogin("a");
            loginInfo.setPassword("a");
            byte[] message = CommsProtocol.createLoginConfirmationMessage(loginInfo);
            commsManager.sendToMobile(message,loginInfo.getConnection());
        }
    }
    
    private boolean readUserFromXML(UserLoginInfo loginInfo) {
        final String CONFIG_PATH = "users.xml";
        boolean userFound = false;
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try 
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(CONFIG_PATH);          
            Element root = doc.getDocumentElement();
            
            NodeList userNodes = doc.getElementsByTagName("user");
            for(int i = 0; i < userNodes.getLength(); i++)
            {
                Node nNode = userNodes.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;
                    String login = eElement.getElementsByTagName("login").item(0).getTextContent();
                    if(login.equals(loginInfo.getLogin())) {
                        String password = eElement.getElementsByTagName("password").item(0).getTextContent();
                        if(password.equals(loginInfo.getPassword())) {
                            int[] privileges = new int[6];
                            int count = 0;
                            int len = eElement.getElementsByTagName("privileges").item(0).getChildNodes().getLength();
                            for(int j = 0;j<len;j++){
                                String content = eElement.getElementsByTagName("privileges").item(0).getChildNodes().item(j).getTextContent();                               
                                if(isInteger(content)) {                                  
                                    privileges[count] = Integer.parseInt(content);
                                    count++;
                                }
                            }
                            loginInfo.setPrivileges(privileges);
                            return true;
                        }
                    }
                }
            }                      
        } catch(Exception e)
        {
            RoomController.logger.log(Level.SEVERE, e.getMessage());
        }
        return userFound;
    }
    
    private static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
    
    private void sendEmailNotification() 
    {
        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.mail.yahoo.com");
		props.put("mail.smtp.port", "465");
                props.put("mail.smtp.ssl.enable", "true");
                props.put("mail.debug", "true");
                props.put("mail.debug.auth", "true");
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("bzielnik@yahoo.com", "pwd");
			}
		  });

        try {
          Message msg = new MimeMessage(session);
          msg.setFrom(new InternetAddress("bzielnik@yahoo.com", "Example.com Admin"));
          msg.addRecipient(Message.RecipientType.TO,
                           new InternetAddress("Bartek@o2.pl", "Mr. User"));
          msg.setSubject("House alert");
          msg.setText("Motion was detected in your house");
          Transport.send(msg);
        } catch (AddressException e) {
          // ...
        } catch (MessagingException e) {
          // ...
        } catch (UnsupportedEncodingException e) {
          // ...
        }
    }

    public void setRoomName(String value, int i)
    {
        try { 
            roomNames[i] = value;
        } catch(NullPointerException e) {
        }
            
    }
    
    public void setScreenNames()
    {
        int count = 0;
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                screenController.setRoomName(roomNames[count], i, j);
                count++;
            }
        }
    }

    
    public class Clock implements Runnable
    {
        private boolean isRunning;
        
        public void stop()
        {
            isRunning = false;
        }
        
        @Override
        public void run() 
        {
            isRunning = true;
            
            while(isRunning)
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run() 
                    {
                        screenController.setTime();
                    }
                });
                
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public class VoiceStream implements Runnable{
        boolean outVoice = false;
        AudioFormat format = getAudioFormat();
        byte tmpBuff[];
        int bytesRead;
        int toDeviceId;
        TargetDataLine mic;
        DataOutputStream out;
        private boolean isRunning = false;
        private boolean streamOpen = true;
        private boolean lineAvailable = false;


        private AudioFormat getAudioFormat() {
            float sampleRate = 16000.0F;
            int sampleSizeBits = 16;
            int channels = 1;
            boolean signed = true;
            boolean bigEndian = false;

            return new AudioFormat(sampleRate, sampleSizeBits, channels, signed, bigEndian);
        }
        
        public boolean isLineAvailable() {
            return lineAvailable;
        }
        
        public VoiceStream(){
            try {
                DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class,format);
                if(AudioSystem.isLineSupported(micInfo)) {
                    mic = (TargetDataLine) AudioSystem.getLine(micInfo);               
                    mic.open(format);
                    System.out.println("Mic open.");
                    tmpBuff = new byte[mic.getBufferSize()/5];     
                    lineAvailable = true;
                }
            } catch (LineUnavailableException ex) {
                Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void setOutVoice(){
            boolean[][] tmpArray = screenController.getVoiceArray();
            for(int i=0;i<2;i++){
                for(int j=0;j<3;j++){
                    if(tmpArray[i][j]) {
                        outVoice = true;
                        if(i==0)
                            toDeviceId = j+1;
                        else
                            toDeviceId = j+4;
                        if(streamOpen){
                            byte[] message = CommsProtocol.startVoiceStreamMessage();                        
                            commsManager.send(message, toDeviceId, 3);
                            streamOpen = false;
                        }
                        break;
                    }
                }
            }
        }
        
        public void stop()
        {
            mic.close();
            isRunning = false;
        }
        
        @Override
        public void run(){
            mic.start();
            isRunning = true;
            while(isRunning){
                while(outVoice) {
                    //System.out.println("Reading from mic.");
                    bytesRead = mic.read(tmpBuff,0,tmpBuff.length);
                    if (bytesRead > 0){
                        commsManager.send(tmpBuff,toDeviceId,bytesRead);
                    } 
                    outVoice = false; 
                    setOutVoice();
                }
                if(!streamOpen) {
                    byte[] message = CommsProtocol.stopVoiceStreamMessage();
                    streamOpen = true;
                    commsManager.send(message, toDeviceId, 3);
                }
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run() 
                    {
                        setOutVoice();
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
