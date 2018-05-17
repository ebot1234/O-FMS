package UI;

import OFMS.Main;
import javax.swing.JTextArea;

/**
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class Msg {

    public static void send(String message) {
        System.out.println("Panel Message: " + message);
        JTextArea temp = Main.getInstance().getCurrentMsgBox();
        if (temp != null) {
            //temp.setForeground(Color.red);
            temp.append(message + "\n\n");
        }
    }
}
