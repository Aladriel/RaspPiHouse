package Tests;

import rasppi.intelligenthouse.Comms.CommsManager;
import rasppi.intelligenthouse.Comms.CommsProtocol;
import rasppi.intelligenthouse.Comms.ILoginEvent;
import rasppi.intelligenthouse.Comms.IReadingsEvent;
import rasppi.intelligenthouse.Comms.UserLoginInfo;
import rasppi.intelligenthouse.Sensors.SensorReading;
import rasppi.intelligenthouse.Sensors.SensorReadings;
import rasppi.intelligenthouse.Sensors.SensorType;

import static org.junit.Assert.*;
import static rasppi.intelligenthouse.Comms.CommsProtocol.processLoginMessage;

public class IntegrationTests {

    public final static int PORT = 12001;
    public final static String IP = "192.168.1.18";
    public final static String LOGIN = "mail@mail.com";
    public final static String PASSWORD = "pass";

    @Test
    public void login() throws Exception {
        boolean result = true;

        boolean w = false;

        CommsManager cm = new CommsManager(IP, PORT);
        cm.addLoginEventListener(new ILoginEvent() {
            @Override
            public void loginInfoReceived(byte[] loginInfo) {
                UserLoginInfo uii = CommsProtocol.processLoginMessage(loginInfo);
                if (uii == null)
                {
                    result = false;
                }
                if (uii.getLogin().equals(LOGIN) == false || uii.getPassword().equals(PASSWORD))
                {
                    result = false;
                }

                w = true;
            }
        });
        cm.sendToMaster(CommsProtocol.createLoginRequestMessage(LOGIN, PASSWORD));

        while (!w);

        assertEquals(true, result);
    }

    @Test
    public void lightOn() throws Exception {
        boolean result = true;

        boolean w = false;

        CommsManager cm = new CommsManager(IP, PORT);
        cm.addLoginEventListener(new ILoginEvent() {
            @Override
            public void loginInfoReceived(byte[] loginInfo) {
                UserLoginInfo uii = CommsProtocol.processLoginMessage(loginInfo);
                if (uii == null)
                {
                    result = false;
                }
                if (uii.getLogin().equals(LOGIN) == false || uii.getPassword().equals(PASSWORD))
                {
                    result = false;
                }

                w = true;
            }
        });
        cm.addReadingEventListener(new IReadingsEvent() {
            @Override
            public void ReadingsReceived(byte[] readings) {
                SensorReadings sr = CommsProtocol.processReadingMessage(readings);
                for (SensorReading s : sr.getReadings())
                {
                    if (s.getType() == SensorType.Light && s.getValue() == 1)
                    {
                        result = true;
                    }
                }
            }
        });
        cm.sendToMaster(CommsProtocol.createLoginRequestMessage(LOGIN, PASSWORD));

        while (!w);

        result = false;
        w = false;
        cm.sendToMaster(CommsProtocol.createLightStateMessage(1, 0));

        while (!w);

        assertEquals(true, result);
    }

    @Test
    public void lightOff() throws Exception {
        boolean result = true;

        boolean w = false;

        CommsManager cm = new CommsManager(IP, PORT);
        cm.addLoginEventListener(new ILoginEvent() {
            @Override
            public void loginInfoReceived(byte[] loginInfo) {
                UserLoginInfo uii = CommsProtocol.processLoginMessage(loginInfo);
                if (uii == null)
                {
                    result = false;
                }
                if (uii.getLogin().equals(LOGIN) == false || uii.getPassword().equals(PASSWORD))
                {
                    result = false;
                }

                w = true;
            }
        });
        cm.addReadingEventListener(new IReadingsEvent() {
            @Override
            public void ReadingsReceived(byte[] readings) {
                SensorReadings sr = CommsProtocol.processReadingMessage(readings);
                for (SensorReading s : sr.getReadings())
                {
                    if (s.getType() == SensorType.Light && s.getValue() == 0)
                    {
                        result = true;
                    }
                }
            }
        });
        cm.sendToMaster(CommsProtocol.createLoginRequestMessage(LOGIN, PASSWORD));

        while (!w);

        result = false;
        w = false;
        cm.sendToMaster(CommsProtocol.createLightStateMessage(0, 0));

        while (!w);

        assertEquals(true, result);
    }

    @Test
    public void lightWithoutPrivileges() throws Exception {
        boolean result = true;

        boolean w = false;

        CommsManager cm = new CommsManager(IP, PORT);
        cm.addLoginEventListener(new ILoginEvent() {
            @Override
            public void loginInfoReceived(byte[] loginInfo) {
                UserLoginInfo uii = CommsProtocol.processLoginMessage(loginInfo);
                if (uii == null)
                {
                    result = false;
                }
                if (uii.getLogin().equals(LOGIN) == false || uii.getPassword().equals(PASSWORD))
                {
                    result = false;
                }
                if (uii.getPrivileges()[0] != 0)
                {
                    result = false;
                }

                w = true;
            }
        });
        cm.sendToMaster(CommsProtocol.createLoginRequestMessage(LOGIN, PASSWORD));

        while (!w);

        assertEquals(true, result);
    }

    @Test
    public void blindOn() throws Exception {
        boolean result = true;

        boolean w = false;

        CommsManager cm = new CommsManager(IP, PORT);
        cm.addLoginEventListener(new ILoginEvent() {
            @Override
            public void loginInfoReceived(byte[] loginInfo) {
                UserLoginInfo uii = CommsProtocol.processLoginMessage(loginInfo);
                if (uii == null)
                {
                    result = false;
                }
                if (uii.getLogin().equals(LOGIN) == false || uii.getPassword().equals(PASSWORD))
                {
                    result = false;
                }

                w = true;
            }
        });
        cm.sendToMaster(CommsProtocol.createLoginRequestMessage(LOGIN, PASSWORD));

        while (!w);

        assertEquals(true, result);
    }

    @Test
    public void blindOff() throws Exception {
        boolean result = true;

        boolean w = false;

        CommsManager cm = new CommsManager(IP, PORT);
        cm.addLoginEventListener(new ILoginEvent() {
            @Override
            public void loginInfoReceived(byte[] loginInfo) {
                UserLoginInfo uii = CommsProtocol.processLoginMessage(loginInfo);
                if (uii == null)
                {
                    result = false;
                }
                if (uii.getLogin().equals(LOGIN) == false || uii.getPassword().equals(PASSWORD))
                {
                    result = false;
                }

                w = true;
            }
        });
        cm.sendToMaster(CommsProtocol.createLoginRequestMessage(LOGIN, PASSWORD));

        while (!w);

        assertEquals(true, result);
    }

    @Test
    public void blindWithoutPrivileges() throws Exception {
        boolean result = true;

        boolean w = false;

        CommsManager cm = new CommsManager(IP, PORT);
        cm.addLoginEventListener(new ILoginEvent() {
            @Override
            public void loginInfoReceived(byte[] loginInfo) {
                UserLoginInfo uii = CommsProtocol.processLoginMessage(loginInfo);
                if (uii == null)
                {
                    result = false;
                }
                if (uii.getLogin().equals(LOGIN) == false || uii.getPassword().equals(PASSWORD))
                {
                    result = false;
                }
                if (uii.getPrivileges()[0] != 0)
                {
                    result = false;
                }

                w = true;
            }
        });
        cm.sendToMaster(CommsProtocol.createLoginRequestMessage(LOGIN, PASSWORD));

        while (!w);

        assertEquals(true, result);
    }
}