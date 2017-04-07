/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoomController.Comms;

/**
 *
 * @author Bartek
 */

import java.io.InputStream;

/**
 *
 * @author Bartek
 */
public interface ILoginEvent
{
    void loginInfoReceived(UserLoginInfo loginInfo);
}
