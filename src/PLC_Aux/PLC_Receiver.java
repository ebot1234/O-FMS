package PLC_Aux;

import OFMS.FieldAndRobots;
import OFMS.GovernThread;
import OFMS.Main;
import UI.AD_Test_Panel;
import UI.Msg;
import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Receives packet information(such as ESTOP status) from the PLC.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class PLC_Receiver extends Thread {

    /**
     * Holds the socket used for communication.
     */
    private DatagramSocket sock;
    /**
     * Holds a local copy, from Field And Robots, of the integer value for the
     * Red and Blue alliances. This is used later for accessing a specific
     * alliance.
     */
    private final static int RED = FieldAndRobots.RED, BLUE = FieldAndRobots.BLUE;
    /**
     * Holds a local copy, from Field And Robots, of the integer value for
     * station One, Two, and Three. This is used later for accessing a specific
     * station.
     */
    private final static int ONE = FieldAndRobots.ONE, TWO = FieldAndRobots.TWO, THREE = FieldAndRobots.THREE;
    /**
     * The string data received from the PLC.
     */
    private String dataStr;
    /**
     * Holds the value for if the field, as a whole, has been ESTOPPED.
     */
    private static boolean field_ESTOPPED = false;

    /**
     * Creates an instance and sets up the socket on port 7000.
     */
    public PLC_Receiver() {
        System.out.println("PLC Receiver Constructor");
        try {
            sock = new DatagramSocket(7000);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Couldn't setup PLC Receiver socket");
        }
    }

    /**
     * Has the field itself been ESTOPPED?
     *
     * @return True if the field is ESTOPPED.
     */
    public static boolean isFieldESTOPPED() {
        return field_ESTOPPED;
    }

    /**
     * Resets the field ESTOPPED to false.
     */
    public static void resetFieldESTOPPED() {
        field_ESTOPPED = false;
    }

    /**
     * Method called when you call "Thread.start();" - Receives packets from the
     * PLC and determines which robot the ESTOP(or the entire field).
     */
    @Override
    public void run() {
        // Sets up a byte array for the incoming packet
        byte[] data = new byte[6];
        // Sets up a Datagram Packet(aka UDP) to receive PLC data.
        DatagramPacket p = new DatagramPacket(data, data.length);
        while ((!sock.isClosed())) {
            try {
                // Waits until a packet is received before moving on...
                sock.receive(p);
                // Takes in the data from the packet and puts it into a string
                dataStr = new String(data, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                // Takes in the data from the packet and puts it into a string
                dataStr = new String(data, "UTF-8"); // COULD THIS BE A PROBLEM?
                Msg.send("Recieved PLC String: " + dataStr);

                AD_Test_Panel.getInstance().updateAll_ES_Indic(false);
                if (dataStr.substring(0, 6).equals("111111")) {
                    AD_Test_Panel.getInstance().updateFieldES(true);
                } else {
                    Msg.send("Updating robot ESTOP");
                    // If Red One requested an ESTOP then do it
                    if (dataStr.substring(0, 1).equals("1")) {
                        AD_Test_Panel.getInstance().updateES_Indic(RED, ONE, true);
                    }
                    // If Red Two requested an ESTOP then do it
                    if (dataStr.substring(1, 2).equals("1")) {
                        AD_Test_Panel.getInstance().updateES_Indic(RED, TWO, true);
                    }
                    // If Red Three requested an ESTOP then do it
                    if (dataStr.substring(2, 3).equals("1")) {
                        Msg.send("Updating R3 ESTOP");
                        AD_Test_Panel.getInstance().updateES_Indic(RED, THREE, true);
                    }


                    // If Blue One requested an ESTOP then do it
                    if (dataStr.substring(3, 4).equals("1")) {
                        AD_Test_Panel.getInstance().updateES_Indic(BLUE, ONE, true);
                    }
                    // If Blue Two requested an ESTOP then do it
                    if (dataStr.substring(4, 5).equals("1")) {
                        AD_Test_Panel.getInstance().updateES_Indic(BLUE, TWO, true);
                    }
                    // If Blue Three requested an ESTOP then do it
                    if (dataStr.substring(5, 6).equals("1")) {
                        AD_Test_Panel.getInstance().updateES_Indic(BLUE, THREE, true);
                    }
                }

                /**
                 * If the entire field has been ESTOPPED and this is during the
                 * match the stop the match and ESTOP every robot.
                 */
                if (dataStr.substring(0, 6).equals("111111")) {// && GovernThread.getInstance().isMatchRunning()) {
                    System.out.println("Full Field ESTOP");

                    // Stop the match
                    Main.layer.stopMatch();

                    // ESTOP all of the Red robots
                    FieldAndRobots.getInstance().actOnRobot(RED, ONE,
                            FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    FieldAndRobots.getInstance().actOnRobot(RED, TWO,
                            FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    FieldAndRobots.getInstance().actOnRobot(RED, THREE,
                            FieldAndRobots.SpecialState.ESTOP_ROBOT);

                    // ESTOP all of the Blue robots
                    FieldAndRobots.getInstance().actOnRobot(BLUE, ONE,
                            FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    FieldAndRobots.getInstance().actOnRobot(BLUE, TWO,
                            FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    FieldAndRobots.getInstance().actOnRobot(BLUE, THREE,
                            FieldAndRobots.SpecialState.ESTOP_ROBOT);

                    // Yes, the field has been ESTOPPED
                    field_ESTOPPED = true;
                } else { // The entire field has not been ESTOPPED
                    System.out.println("***Individual Team ESTOP");

                    /**
                     * If the match is running, then check which station
                     * requested an ESTOP.
                     */
                    //if (GovernThread.getInstance().isMatchRunning()) {
                    //AD_Test_Panel.getInstance().updateAll_ES_Indic(false);
                    // If Red One requested an ESTOP then do it
                    if (dataStr.substring(0, 1).equals("1")) {
                        FieldAndRobots.getInstance().actOnRobot(RED, ONE,
                                FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    }
                    // If Red Two requested an ESTOP then do it
                    if (dataStr.substring(1, 2).equals("1")) {
                        FieldAndRobots.getInstance().actOnRobot(RED, TWO,
                                FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    }
                    // If Red Three requested an ESTOP then do it
                    if (dataStr.substring(2, 3).equals("1")) {
                        FieldAndRobots.getInstance().actOnRobot(RED, THREE,
                                FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    }


                    // If Blue One requested an ESTOP then do it
                    if (dataStr.substring(3, 4).equals("1")) {
                        FieldAndRobots.getInstance().actOnRobot(BLUE, ONE,
                                FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    }
                    // If Blue Two requested an ESTOP then do it
                    if (dataStr.substring(4, 5).equals("1")) {
                        FieldAndRobots.getInstance().actOnRobot(BLUE, TWO,
                                FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    }
                    // If Blue Three requested an ESTOP then do it
                    if (dataStr.substring(5, 6).equals("1")) {
                        FieldAndRobots.getInstance().actOnRobot(BLUE, THREE,
                                FieldAndRobots.SpecialState.ESTOP_ROBOT);
                    }
                    // }
                }
                //If the Red Force becomes active
                if(dataStr.substring(0, 1).equals("RF1"))
                {
                    FieldAndRobots.getInstance().redVault
        (FieldAndRobots.CubeNumbers.Cube_1, FieldAndRobots.PowerUps.Force);
                }
                if (dataStr.substring(2,3).equals("RF2"))
                {
                    FieldAndRobots.getInstance().redVault
        (FieldAndRobots.CubeNumbers.Cube_2, FieldAndRobots.PowerUps.Force);
                }
                if(dataStr.substring(4,5).equals("RF3"))
                {
                    FieldAndRobots.getInstance().redVault
        (FieldAndRobots.CubeNumbers.Cube_3, FieldAndRobots.PowerUps.Force);
                }
                if(dataStr.substring(6,7).equals("RF"))
                {
                    FieldAndRobots.getInstance().redVault
        (FieldAndRobots.CubeNumbers.PLAYED, FieldAndRobots.PowerUps.Force);
                }
               /*
               * Third Byte = System Status Code(blue then red)
               */
            if (isBitSet(data[2], 0)) {
                 Main.getESTOP_Panel().setBlueIOStatus(Color.GREEN);
               } else {
                   Main.getESTOP_Panel().setBlueIOStatus(Color.RED);
               }
               if (isBitSet(data[2], 1)) {
                   Main.getESTOP_Panel().setRedIOStatus(Color.GREEN);
               } else {
                    Main.getESTOP_Panel().setRedIOStatus(Color.RED);
               }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Reset packet length
            p.setLength(data.length);
        }
        Msg.send("***THE PLC RECEIVER SOCKET HAS BEEN CLOSED***NO MORE PLC RECEIVER***");
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
     * Finds the status, on or off, of the specified bit in the byte.
     *
     * @param data The byte you wish to look through.
     * @param bitPos The position of the bit in the byte(1 through 8).
     * @return True if that bit is 'on'.
     */
    public static boolean isBitSet(byte data, int bitPos) {
        int bitPosition = bitPos % 8;  // Position of this bit in a byte

        return (data >> bitPosition & 1) == 1;
    }
}
