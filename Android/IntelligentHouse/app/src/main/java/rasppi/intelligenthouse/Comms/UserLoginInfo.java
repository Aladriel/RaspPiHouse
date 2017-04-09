/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rasppi.intelligenthouse.Comms;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author Bartek
 */
public class UserLoginInfo implements Serializable {
    
    private String login;
    private String password;
    private String[] roomNames = new String[6];
    private int[] privileges;


    private Socket connection;

    public UserLoginInfo() {
    }
    

    public UserLoginInfo(String login, String password, int[] privileges) {
        this.login = login;
        this.password = password;
        this.privileges = privileges;
    }
    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getPrivileges() {
        return privileges;
    }

    public void setPrivileges(int[] privileges) {
        this.privileges = privileges;
    }

    public String[] getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(String[] roomNames) {
        this.roomNames = roomNames;
    }
}
