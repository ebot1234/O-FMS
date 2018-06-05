package Communication;

import OFMS.FieldAndRobots;
import UI.Msg;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * This class receives packets (from driver stations) sent to port 1160 on the
 * FMS computer. These packets include robot connection status, robot mode, and
 * battery voltage.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class DSReceiver extends Thread {

    /**
     * Holds the socket used for communications.
     */
    private DatagramSocket sock;

    /**
     * DSReceiver Constructor - initializes the socket to the correct port.
     */
    public DSReceiver() {
        System.out.println("***DSReveiver Contructor");
        try {
            // Tells "sock" to look for packets only on socket 1160
            sock = new DatagramSocket(1160);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Couldn't setup DS Receiver socket");
        }
    }

    /**
     * This is called when this thread is told to start (via the start method).
     * This finds the team the packet came from and updates that teams
     * information and connection status. It also prints any received misformed
     * packets.
     */
    @Override
    public void run() {
        //All team updates filter through the FieldAndRobots
        FieldAndRobots FAR = FieldAndRobots.getInstance();

        // Creates a new byte array. This will store the packet data.
        byte[] data = new byte[50];
        // Creates a new DatagramPacket(aka UDP)
        DatagramPacket p = new DatagramPacket(data, data.length);
        while (!sock.isClosed()) {
            try {
                if (sock != null) {
                    sock.receive(p); // Program waits until a new packet is received.
                } else {
                    Msg.send("DS Receiver Socket is null...");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                // Finds the team number from the received packet
                String team1 = "" + Integer.parseInt((int) data[4] + "");
                String team2 = "" + Integer.parseInt((int) data[5] + "");
                if (team2.length() < 2) {
                    team2 = "0".concat(team2);
                }
                int team = Integer.parseInt(team1 + "".concat(team2 + ""));

                
                // Gets the robots battery voltage from the packet
                double batteryVolts = Double.parseDouble(
                        convertInt((int) data[40])
                        + "." + convertInt((int) data[41]));

                // Tells field and robots to update the teams information
                if (FAR != null) {
                    FAR.updateRobotStatus(team, batteryVolts,
                            isRobotCommAlive((int) data[2], false));
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Prints the entire packet that caused an error
                for (int i = 0; i < 50; i++) {
                    // 0xff is needed to prevent byte wrapping
                    System.out.print(i + ": " + Integer.toHexString(
                            data[i] & 0xff) + ", ");
                }
                System.out.println("");
            }
            // Resets length of packet in case the length differs
            p.setLength(data.length);
        } // End of the while loop
        Msg.send("***THE DS RECEIVER SOCKET HAS BEEN CLOSED***NO MORE DS RECEIVER***");
    }

    /**
     * Closes the receiving socket so it can be used later.
     */
    public void shutdownSocket() {
        if (sock != null) {
            sock.close();
        }
    }

    /**
     * This converts an integer into the corresponding hexadecimal
     * representation.
     *
     * @param n the integer to be converted
     * @return The integer value obtained from casting into a hex number
     */
    public static int convertInt(int n) {
        return Integer.parseInt(Integer.toHexString(n & 0xff));
    }

    /**
     * This converts an integer into the String value of its hexadecimal
     * representation.
     *
     * @param n the integer to be converted
     * @return The String value obtained from casting into a hex number
     */
    public static String convertString(int n) {
        // 0x7f is needed to prevent byte wrapping
        return Integer.toHexString(n & 0x7f);
    }

    /**
     * Tells if the driver station is connected to a robot or not.
     *
     * @param state the state of the robot.
     * @return whether or not the DS is connected to a robot
     */
    public static boolean isRobotCommAlive(int state, boolean print) {
        boolean alive = false;
        // Converts the robot state into a hex String
        String conState = convertString(state);
        if (print) {
            Msg.send("ConState: " + conState);
        }
        //If the DS has connection to a robot it will have one of these values
        // in it. 5 - Autonomous Disabled 7 - Autonomous Enabled 4 - Teleop
        // Disabled 6 - Teleop Enabled
        // ESTOPPED = 8 and/or 0
        if (conState.equals("8") || conState.equals("0") || conState.startsWith("7") || conState.startsWith("6")
                || conState.startsWith("5") || conState.startsWith("4")) {
            if (conState.endsWith("e")) {
                alive = true;
            } else if (conState.endsWith("0")) {
                alive = false; // THIS MIGHT BE A PROBLEM
            } else {
                //alive = true;
            }
        } else {
        }
        return alive;
    }
}
