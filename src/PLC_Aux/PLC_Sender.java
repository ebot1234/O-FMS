package PLC_Aux;

import OFMS.FieldAndRobots;
import OFMS.GovernThread;
import OFMS.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * This class sends time information (via a datagram socket) to a PLC.
 *
 * Complete System developed by:
 *@author Ethen Brandenburg - Team 1080
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class PLC_Sender {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    /**
     * This is the socket used for communications.
     */
    private DatagramSocket dsock;
    /**
     * Hard-coded IP of the PLC.
     */
    private final String PLC_IP = "10.0.0.7";
    /**
     * The address, by name, of the PLC_IP IP address.
     */
    private InetAddress addr;
    /**
     * DS Light Statuses - Off, On, and Blinking. We can set the standard speed
     * of blinking here between slow(S), medium(M), and fast(F).
     */
    private static final byte LIGHT_OFF = "0".getBytes()[0], LIGHT_ON = "1".getBytes()[0], LIGHT_BLINKING = "S".getBytes()[0];
    /**
     * Field Light Statuses - Off, On, and Blinking. We can set the standard
     * speed of blinking here between slow(S), medium(M), and fast(F).
     */
    private static final byte FIELD_OFF = "0".getBytes()[0], FIELD_ON = "1".getBytes()[0], FIELD_BLINKING = "S".getBytes()[0];
    /**
     * The 'mode' sent to the PLC so that it can understand what it should parse
     * for.
     */
    private static final byte LIGHT_MODE = "L".getBytes()[0], TEAM_NUM_MODE = "N".getBytes()[0], TIME_MODE = "T".getBytes()[0], VIEW_MODE = "V".getBytes()[0];
    /**
     * A byte representation of zero - also used as the 'clear' or 'blank' byte.
     */
    private static final byte BYTE_ZERO = "0".getBytes()[0], BYTE_CLEAR = BYTE_ZERO;
    //2018 Year-Specfic Bytes
    private static final byte ZERO_CUBES = "0cb".getBytes()[0];
    //Adds the byte allocation for the 1 force cube and so on
    private static final byte RED_FORCE_1_CUBE = "RF1".getBytes()[0], RED_FORCE_2_CUBE = "RF2".getBytes()[0],RED_FORCE_3_CUBE = "RF3".getBytes()[0], RED_FORCE_BUTTON = "RF".getBytes()[0];
    //Adds the bytes for the Red levitiate 
    private static final byte RED_LEV_1_CUBE = "RLev1".getBytes()[0], RED_LEV_2_CUBE = "RLev2".getBytes()[0], RED_LEV_3_CUBE = "RLev3".getBytes()[0], RED_LEV_BUTTON = "RLEV".getBytes()[0];
   //Adds the bytes for the Red Boost
    private static final byte RED_BOOST_1_CUBE = "RB1".getBytes()[0], RED_BOOST_2_CUBE = "RB2".getBytes()[0], RED_BOOST_3_CUBE = "RB3".getBytes()[0], RED_BOOST_BUTTON = "RB".getBytes()[0];
    private static final byte BLUE_FORCE_1_CUBE = "BF1".getBytes()[0], BLUE_FORCE_2_CUBE = "BF2".getBytes()[0],BLUE_FORCE_3_CUBE = "BF3".getBytes()[0], BLUE_FORCE_BUTTON = "BF".getBytes()[0];
    //Adds the bytes for the Red levitiate 
    private static final byte BLUE_LEV_1_CUBE = "BLev1".getBytes()[0], BLUE_LEV_2_CUBE = "BLev2".getBytes()[0], BLUE_LEV_3_CUBE = "BLev3".getBytes()[0], BLUE_LEV_BUTTON = "BLEV".getBytes()[0];
   //Adds the bytes for the Red Boost
    private static final byte BLUE_BOOST_1_CUBE = "BB1".getBytes()[0], BLUE_BOOST_2_CUBE = "BB2".getBytes()[0], BLUE_BOOST_3_CUBE = "BB3".getBytes()[0], BLUE_BOOST_BUTTON = "BB".getBytes()[0];
    private static final byte RED_FORCE_RELAY1 = "RFR1".getBytes()[0], RED_FORCE_RELAY2 = "RFR2".getBytes()[0], RED_FORCE_RELAY3 = "RFR3".getBytes()[0];
    private static final byte RED_BOOST_RELAY1 = "RBR1".getBytes()[0], RED_BOOST_RELAY2 = "RBR2".getBytes()[0], RED_BOOST_RELAY3 = "RBR3".getBytes()[0];
    private static final byte RED_LEV_RELAY1 = "RLR1".getBytes()[0], RED_LEV_RELAY2 = "RLR2".getBytes()[0], RED_LEV_RELAY3 = "RLR3".getBytes()[0];
    private static final byte BLUE_FORCE_RELAY1 = "BFR1".getBytes()[0], BLUE_FORCE_RELAY2 = "BFR2".getBytes()[0], BLUE_FORCE_RELAY3 = "BFR3".getBytes()[0];
    private static final byte BLUE_BOOST_RELAY1 = "BBR1".getBytes()[0], BLUE_BOOST_RELAY2 = "BBR2".getBytes()[0], BLUE_BOOST_RELAY3 = "BBR3".getBytes()[0];
    private static final byte BLUE_LEV_RELAY1 = "BLR1".getBytes()[0], BLUE_LEV_RELAY2 = "BLR2".getBytes()[0], BLUE_LEV_RELAY3 = "BLR3".getBytes()[0];
    


    //Scale byte
    private static final byte SCALE_SENSOR1 = "SS1".getBytes()[0], SCALE_SENSOR2 = "SS2".getBytes()[0];
    //Red side Switch
    private static final byte RED_SWITCH_SENSOR1 = "RSS1".getBytes()[0], RED_SWITCH_SENSOR2 = "RSS2".getBytes()[0];
    //Blue side Switch
    private static final byte BLUE_SWITCH_SENSOR2 = "BSS2".getBytes()[0], BLUE_SWITCH_SENSOR1 = "BSS1".getBytes()[0];
    private static final byte NeitherAlliance = "NA".getBytes()[0];
    //Scale and Switch relays for the lights
    private static final byte SCALE_RELAY1 = "SR1".getBytes()[0], SCALE_RELAY2 = "SR2".getBytes()[0], SCALE_RELAY3 = "SR3".getBytes()[0], SCALE_RELAY4 = "SR4".getBytes()[0], SCALE_RELAY5 = "SR5".getBytes()[0], SCALE_RELAY6 = "SR6".getBytes()[0];
    private static final byte RED_SWITCH_RELAY1 = "RSR1".getBytes()[0], RED_SWITCH_RELAY2 = "RSR2".getBytes()[0], RED_SWITCH_RELAY3 = "RSR3".getBytes()[0], RED_SWITCH_RELAY_4 = "RSR4".getBytes()[0], RED_SWITCH_RELAY_5 = "RSR5".getBytes()[0],RED_SWITCH_RELAY_6 = "RSR6".getBytes()[0];
    private static final byte BLUE_SWITCH_RELAY1 = "BSR1".getBytes()[0], BLUE_SWITCH_RELAY2 = "BSR2".getBytes()[0], BLUE_SWITCH_RELAY3 = "BSR3".getBytes()[0], BLUE_SWITCH_RELAY_4 = "BSR4".getBytes()[0], BLUE_SWITCH_RELAY_5 = "BSR5".getBytes()[0], BLUE_SWITCH_RELAY_6 = "BSR6".getBytes()[0];
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
     * The time left in the mode of the match(ie auton or tele) represented by a
     * String.
     */
    private static String matchTime = "0010";
    
   
    /**
     * The previous packets for team number, lights, time, and ViewMarq - these
     * are used later to compare packets and ensure you wont over-send a packet.
     */
    private DatagramPacket prevTeamNumPacket = null, prevLightsPacket = null, prevTimePacket = null, prevVM_Packet = null, prevRedVaultPacket = null, prevBlueVaultPacket = null, prevBlueSwitchLightsPacket = null, prevRedSwitchLightsPacket = null, prevScaleLightsPacket = null;
    /**
     * An 'instance' of this class - there can be only one. This is that one.
     */
    private static PLC_Sender _instance;
    //</editor-fold>

    /**
     * Creates and fires up an instance, performing an initial update.
     */
    private PLC_Sender() {
        //Start up
        try {
            System.out.println("Made new socket for PLC...");
            dsock = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("PLC Error 1!");
            e.printStackTrace();
        }
        try {
            System.out.println("Made new address for PLC...");
            addr = InetAddress.getByName(PLC_IP);
        } catch (UnknownHostException ex) {
            System.out.println("PLC Error 2!");
            ex.printStackTrace();
        }
        //Send Starter Packets
        updatePLC_TeamNum(true);
        updatePLC_Time(true);
    }

    public static PLC_Sender getInstance() {
        if (_instance == null) {
            _instance = new PLC_Sender();
        }
        return _instance;
    }

    //<editor-fold defaultstate="collapsed" desc="Updater Methods">
    /**
     * Builds a packet of time information and sends it to the PLC.
     *
     * @param forceSend
     */
   
    public final void updateViewMarq(boolean forceSend) {
        System.out.println("PLC Update ViewMarq - Custom");
        try {
            DatagramPacket viewmarqPacket = buildViewMarqPacket();
            if (forceSend) {
                System.out.println("FORCE SENDING 1 - PLC Viewmarq");
                dsock.send(viewmarqPacket);
            } else if (!Arrays.equals(viewmarqPacket.getData(), prevVM_Packet.getData())) {
                dsock.send(viewmarqPacket);
            } else {
            }
            prevVM_Packet = viewmarqPacket;
            //System.out.println("SentPacket");
        } catch (IOException e) {
            System.out.println("PLC Error 1 - ViewMarq!");
        }
    }

    public final void updatePLC_TeamNum(boolean forceSend) {
        System.out.println("PLC Update Team Num");
        try {
            DatagramPacket teamPacket = buildTeamNumberPacket();
            if (forceSend) {
                System.out.println("FORCE SENDING 2 - PLC Team Num");
                dsock.send(teamPacket);
            } else if (!Arrays.equals(teamPacket.getData(), prevTeamNumPacket.getData())) {
                dsock.send(teamPacket);
            } else {
            }
            //System.out.println("SentPacket");
            prevTeamNumPacket = teamPacket;
        } catch (IOException e) {
            System.out.println("PLC Error 2 - Team Num!");
        }
    }

    public final void updatePLC_Lights(boolean forceSend) {
        try {
            DatagramPacket lightsPacket = buildLightModePacket();
            if (forceSend) {
                System.out.println("FORCE SENDING 3 - PLC Lights");
                dsock.send(lightsPacket);
            } else if (!Arrays.equals(lightsPacket.getData(), prevLightsPacket.getData())) {
                dsock.send(lightsPacket);
            } else {
            }
            prevLightsPacket = lightsPacket;
            //System.out.println("SentPacket");
        } catch (IOException e) {
            System.out.println("PLC Error 3 - Lights!\n");
        }
    }
  
    public final void updateRedSwitchLights(boolean forceSend){
        try{
            DatagramPacket RedSwitchLightsPacket = buildRedSwitchLightsPacket();
            if(forceSend){
                System.out.println("Force Sending - PLC Red Switch Lights");
                dsock.send(RedSwitchLightsPacket);
            } else if (!Arrays.equals(RedSwitchLightsPacket.getData(), prevRedSwitchLightsPacket.getData())){
                dsock.send(RedSwitchLightsPacket);
            } else {
                 } prevRedSwitchLightsPacket = RedSwitchLightsPacket;
        }  catch(IOException e){
                          System.out.println("PLC Error 5 - RED SWITCH LIGHTS!\n");
                         }    }
    
    public final void updateBlueSwitchLights(boolean forceSend){
        try{
            DatagramPacket BlueSwitchLightsPacket = buildBlueSwitchLightsPacket();
            if (forceSend){
                System.out.println("Force Sending PLC Blue Switch Lights");
                dsock.send(BlueSwitchLightsPacket);
            } else if (!Arrays.equals(BlueSwitchLightsPacket.getData(), prevBlueSwitchLightsPacket.getData())){
                dsock.send(BlueSwitchLightsPacket);
            } else{
            } prevBlueSwitchLightsPacket =  BlueSwitchLightsPacket;
        }catch(IOException e){
            System.out.println("PLC Error 6 - BLUE SWITCH LIGHTS\n");
        }
    }
    public final void updateScaleLights(boolean forceSend){
        try{
            DatagramPacket ScaleLightsPacket = buildScaleLightsPacket();
            if (forceSend){
                System.out.println("Force Sending PLC Scale Lights");
                dsock.send(ScaleLightsPacket);
            } else if (!Arrays.equals(ScaleLightsPacket.getData(), prevScaleLightsPacket.getData())){
                dsock.send(ScaleLightsPacket);
            } else{
            } prevScaleLightsPacket =  ScaleLightsPacket;
        }catch(IOException e){
            System.out.println("PLC Error 6 - SCALE LIGHTS\n");
        }
    }
    public final void updatePLC_Time(boolean forceSend) {
        //System.out.println("PLC Update Time");
        try {
            DatagramPacket timePacket = buildTimeModePacket();
            if (forceSend) {
                System.out.println("FORCE SENDING 4 - PLC Time");
                dsock.send(timePacket);
            } else if (!Arrays.equals(timePacket.getData(), prevTimePacket.getData())) {
                dsock.send(timePacket);
            } else {
            }
            prevTimePacket = timePacket;
        } catch (IOException e) {
            System.out.println("PLC Error 4 - Time!");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Packet Building Methods">
    private DatagramPacket buildTimeModePacket() throws SocketException {
        //System.out.println("Building packet for PLC...");

        byte[] data = new byte[9];

        for (int i = 0; i < 9; i++) {
            data[i] = BYTE_ZERO;
        }

        if (GovernThread.getInstance() != null) {
            matchTime = checkAndFixNum(GovernThread.getInstance().get_PLC_Time(), 4);
        } else {
        }

        System.out.println(matchTime);

        /*
         * Mode Byte
         */
        data[0] = TIME_MODE;

        /*
         * Match Time
         */
        data[1] = matchTime.substring(0, 1).getBytes()[0];
        data[2] = matchTime.substring(1, 2).getBytes()[0];
        data[3] = matchTime.substring(2, 3).getBytes()[0];
        data[4] = matchTime.substring(3, 4).getBytes()[0];

        CRC32 check = new CRC32();
        check.update(data);
        byte[] crc = ByteBuffer.allocate(4).putInt((int) check.getValue()).array();

        /*
         * CRC hash
         */
        data[5] = crc[0];
        data[6] = crc[1];
        data[7] = crc[2];
        data[8] = crc[3];

        return new DatagramPacket(data, data.length, addr, 5000);
    }

    /**
     * From a time value, builds a packet to send to a PLC.
     *
     * @param time presumed to be three digits. Must be at least three.
     * @return a packet filled with the time value.
     * @throws SocketException if things go horribly awry.
     */
    private DatagramPacket buildLightModePacket() throws SocketException {
        //System.out.println("Building packet for PLC...");

        byte[] data = new byte[25];

        for (int i = 0; i < 25; i++) {
            data[i] = BYTE_ZERO;
        }

        boolean matchRunning;
        if (GovernThread.getInstance() != null) {
            matchRunning = GovernThread.getInstance().isMatchRunning();
        } else {
            matchRunning = false;
        }
        FieldAndRobots FAR;
        if (FieldAndRobots.getInstance() != null) {
            FAR = FieldAndRobots.getInstance();

            /*
             * Mode Byte
             */
            data[0] = LIGHT_MODE;

            /*
             * Red Alliance Lights
             */
            data[1] = FAR.teams[RED][ONE].isReady()
                    ? (matchRunning ? LIGHT_ON : LIGHT_OFF) : LIGHT_BLINKING; //R1
            data[2] = FAR.teams[RED][TWO].isReady()
                    ? (matchRunning ? LIGHT_ON : LIGHT_OFF) : LIGHT_BLINKING; //R2
            data[3] = FAR.teams[RED][THREE].isReady()
                    ? (matchRunning ? LIGHT_ON : LIGHT_OFF) : LIGHT_BLINKING; //R3

            data[4] = BYTE_CLEAR; //Empty byte for fun

            /*
             * Red ESTOP Lights
             */
            data[5] = FAR.teams[RED][ONE].isESTOPPED() ? LIGHT_ON : LIGHT_OFF;
            data[6] = FAR.teams[RED][TWO].isESTOPPED() ? LIGHT_ON : LIGHT_OFF;
            data[7] = FAR.teams[RED][THREE].isESTOPPED() ? LIGHT_ON : LIGHT_OFF;

            data[8] = BYTE_CLEAR; //Empty byte for fun

            /*
             * Blue Alliance Lights
             */
            data[9] = FAR.teams[BLUE][ONE].isReady()
                    ? (matchRunning ? LIGHT_ON : LIGHT_OFF) : LIGHT_BLINKING; //B1
            data[10] = FAR.teams[BLUE][TWO].isReady()
                    ? (matchRunning ? LIGHT_ON : LIGHT_OFF) : LIGHT_BLINKING; //B2
            data[11] = FAR.teams[BLUE][THREE].isReady()
                    ? (matchRunning ? LIGHT_ON : LIGHT_OFF) : LIGHT_BLINKING; //B3

            data[12] = BYTE_CLEAR; //Empty byte for fun

            /*
             * Blue ESTOP Lights
             */
            data[13] = FAR.teams[BLUE][ONE].isESTOPPED() ? LIGHT_ON : LIGHT_OFF;
            data[14] = FAR.teams[BLUE][TWO].isESTOPPED() ? LIGHT_ON : LIGHT_OFF;
            data[15] = FAR.teams[BLUE][THREE].isESTOPPED() ? LIGHT_ON : LIGHT_OFF;

            data[16] = BYTE_CLEAR;
            
           
            
        } else {
            System.out.println("PACKET SEND ERROR #1");
        }
          
        
        if (Main.layer != null) {
            boolean redReady = Main.layer.isRedReady();
            boolean blueReady = Main.layer.isBlueReady();
            boolean allEstopped = PLC_Receiver.isFieldESTOPPED();

            /**
             * Alliance Lights
             */
            data[17] = redReady ? FIELD_OFF : FIELD_ON; //Red Alliance
            data[19] = blueReady ? FIELD_OFF : FIELD_ON; //Blue Alliance
            data[18] = (redReady && blueReady)
                    ? (matchRunning ? FIELD_ON : FIELD_BLINKING) : FIELD_OFF; //Main/green light
            data[20] = allEstopped ? FIELD_ON : FIELD_OFF; // Horn
        } else {
        }

        CRC32 check = new CRC32();
        check.update(data);
        byte[] crc = ByteBuffer.allocate(4).putInt((int) check.getValue()).array();

        // CRC hash
        data[21] = crc[0];
        data[22] = crc[1];
        data[23] = crc[2];
        data[24] = crc[3];

        return new DatagramPacket(data, data.length, addr, 5000);
    }

    private DatagramPacket buildTeamNumberPacket() {
        byte[] data = new byte[30];

        for (int i = 0; i < 30; i++) {
            data[i] = BYTE_ZERO;
        }

        FieldAndRobots FAR;
        if (FieldAndRobots.getInstance() != null) {
            FAR = FieldAndRobots.getInstance();

            /*
             * Team Number Mode Byte
             */
            data[0] = TEAM_NUM_MODE;
            /**
             * Red Alliance Team Numbers
             */
            String r1 = checkAndFixNum("" + FAR.teams[RED][ONE].getTeamNumber(), 4);
            data[1] = r1.substring(0, 1).getBytes()[0];
            data[2] = r1.substring(1, 2).getBytes()[0];
            data[3] = r1.substring(2, 3).getBytes()[0];
            data[4] = r1.substring(3, 4).getBytes()[0];

            String r2 = checkAndFixNum("" + FAR.teams[RED][TWO].getTeamNumber(), 4);
            data[5] = r2.substring(0, 1).getBytes()[0];
            data[6] = r2.substring(1, 2).getBytes()[0];
            data[7] = r2.substring(2, 3).getBytes()[0];
            data[8] = r2.substring(3, 4).getBytes()[0];

            String r3 = checkAndFixNum("" + FAR.teams[RED][THREE].getTeamNumber(), 4);
            data[9] = r3.substring(0, 1).getBytes()[0];
            data[10] = r3.substring(1, 2).getBytes()[0];
            data[11] = r3.substring(2, 3).getBytes()[0];
            data[12] = r3.substring(3, 4).getBytes()[0];

            data[13] = BYTE_CLEAR;

            /**
             * Blue Alliance Team Numbers
             */
            String b1 = checkAndFixNum("" + FAR.teams[BLUE][ONE].getTeamNumber(), 4);
            data[14] = b1.substring(0, 1).getBytes()[0];
            data[15] = b1.substring(1, 2).getBytes()[0];
            data[16] = b1.substring(2, 3).getBytes()[0];
            data[17] = b1.substring(3, 4).getBytes()[0];

            String b2 = checkAndFixNum("" + FAR.teams[BLUE][TWO].getTeamNumber(), 4);
            data[18] = b2.substring(0, 1).getBytes()[0];
            data[19] = b2.substring(1, 2).getBytes()[0];
            data[20] = b2.substring(2, 3).getBytes()[0];
            data[21] = b2.substring(3, 4).getBytes()[0];

            String b3 = checkAndFixNum("" + FAR.teams[BLUE][THREE].getTeamNumber(), 4);
            data[22] = b3.substring(0, 1).getBytes()[0];
            data[23] = b3.substring(1, 2).getBytes()[0];
            data[24] = b3.substring(2, 3).getBytes()[0];
            data[25] = b3.substring(3, 4).getBytes()[0];
        } else {
            System.out.println("PACKET SEND ERROR");
        }

        CRC32 check = new CRC32();
        check.update(data);
        byte[] crc = ByteBuffer.allocate(4).putInt((int) check.getValue()).array();

        // CRC hash
        data[26] = crc[0];
        data[27] = crc[1];
        data[28] = crc[2];
        data[29] = crc[3];

        return new DatagramPacket(data, data.length, addr, 5000);
    }

  
   
     private DatagramPacket buildScaleLightsPacket(){
         byte[] data = new byte[11];
         for(int i = 0; i < 11; i++){
             data[i] = BYTE_ZERO;
         }
         boolean matchRunning;
        if (GovernThread.getInstance() != null) {
            matchRunning = GovernThread.getInstance().isMatchRunning();
        } else {
            matchRunning = false;
        }
        FieldAndRobots FAR;
        if (FieldAndRobots.getInstance() != null) {
            FAR = FieldAndRobots.getInstance();
            
            data[0] = SCALE_RELAY1;
            data[1] = SCALE_RELAY2;
            data[2] = SCALE_RELAY3;
            data[3] = SCALE_RELAY4;
            data[4] = SCALE_RELAY5;
            data[5] = SCALE_RELAY6;
        }else {
            System.out.println("SCALE LIGHTS PACKET SEND ERROR");
        }
        CRC32 check = new CRC32();
        check.update(data);
        byte[] crc = ByteBuffer.allocate(4).putInt((int) check.getValue()).array();

        // CRC hash
        data[6] = crc[0];
        data[8] = crc[1];
        data[9] = crc[2];
        data[10] = crc[3];

        return new DatagramPacket(data, data.length, addr, 5000);
     }
    private DatagramPacket buildRedSwitchLightsPacket(){
        byte[] data = new byte[11];
         for(int i = 0; i < 11; i++){
             data[i] = BYTE_ZERO;
         }
         boolean matchRunning;
        if (GovernThread.getInstance() != null) {
            matchRunning = GovernThread.getInstance().isMatchRunning();
        } else {
            matchRunning = false;
        }
        FieldAndRobots FAR;
        if (FieldAndRobots.getInstance() != null) {
            FAR = FieldAndRobots.getInstance();
            
            data[0] = RED_SWITCH_RELAY1;
            data[1] = RED_SWITCH_RELAY2;
            data[2] = RED_SWITCH_RELAY3;
            data[3] = RED_SWITCH_RELAY_4;
            data[4] = RED_SWITCH_RELAY_5;
            data[5] = RED_SWITCH_RELAY_6;
        }else {
            System.out.println("SCALE LIGHTS PACKET SEND ERROR");
        }
        CRC32 check = new CRC32();
        check.update(data);
        byte[] crc = ByteBuffer.allocate(4).putInt((int) check.getValue()).array();

        // CRC hash
        data[6] = crc[0];
        data[8] = crc[1];
        data[9] = crc[2];
        data[10] = crc[3];

        return new DatagramPacket(data, data.length, addr, 5000);
     }
    private DatagramPacket buildBlueSwitchLightsPacket(){
        byte[] data = new byte[11];
         for(int i = 0; i < 11; i++){
             data[i] = BYTE_ZERO;
         }
         boolean matchRunning;
        if (GovernThread.getInstance() != null) {
            matchRunning = GovernThread.getInstance().isMatchRunning();
        } else {
            matchRunning = false;
        }
        FieldAndRobots FAR;
        if (FieldAndRobots.getInstance() != null) {
            FAR = FieldAndRobots.getInstance();
            
            data[0] = BLUE_SWITCH_RELAY1;
            data[1] = BLUE_SWITCH_RELAY2;
            data[2] = BLUE_SWITCH_RELAY3;
            data[3] = BLUE_SWITCH_RELAY_4;
            data[4] = BLUE_SWITCH_RELAY_5;
            data[5] = BLUE_SWITCH_RELAY_6;
        }else {
            System.out.println("SCALE LIGHTS PACKET SEND ERROR");
        }
        CRC32 check = new CRC32();
        check.update(data);
        byte[] crc = ByteBuffer.allocate(4).putInt((int) check.getValue()).array();

        // CRC hash
        data[6] = crc[0];
        data[8] = crc[1];
        data[9] = crc[2];
        data[10] = crc[3];

        return new DatagramPacket(data, data.length, addr, 5000);
     }
    
     
    private DatagramPacket buildViewMarqPacket() {
        byte[] data = new byte[20];

        for (int i = 0; i < 20; i++) {
            data[i] = BYTE_ZERO;
        }

        if (1 == 1) {

            String message = "<ID 1><T>Hello World!</T>";
            /*
             * Team Number Mode Byte
             */
            data[0] = VIEW_MODE;
            /**
             * Red Alliance Team Numbers
             */
            data[1] = message.substring(0, 1).getBytes()[0];
            data[2] = message.substring(1, 2).getBytes()[0];
            data[3] = message.substring(2, 3).getBytes()[0];
            data[4] = message.substring(3, 4).getBytes()[0];
            data[5] = message.substring(4, 5).getBytes()[0];
            data[6] = message.substring(5, 6).getBytes()[0];
            data[7] = message.substring(6, 7).getBytes()[0];
            data[8] = message.substring(7, 8).getBytes()[0];
            data[9] = message.substring(8, 9).getBytes()[0];
            data[10] = message.substring(10, 11).getBytes()[0];
            data[11] = message.substring(11, 12).getBytes()[0];
            data[12] = message.substring(12, 13).getBytes()[0];
            data[13] = message.substring(13, 14).getBytes()[0];
            data[14] = message.substring(14, 15).getBytes()[0];
            data[15] = message.substring(15, 16).getBytes()[0];
            data[13] = message.substring(16, 17).getBytes()[0];
            data[14] = message.substring(17, 18).getBytes()[0];
            data[15] = message.substring(18, 19).getBytes()[0];
            data[15] = message.substring(19, 20).getBytes()[0];

        } else {
            System.out.println("ViewMarq PACKET SEND ERROR");
        }

        return new DatagramPacket(data, data.length, addr, 5000);
    }
    //</editor-fold>

    /**
     * Prefixes a zero to any input value if less than three characters.
     *
     * @param initTime the value to fix.
     * @return the fixed value.
     */
    private String checkAndFixNum(String initTime, int length) {
        if (initTime.length() < length) {
            initTime = "0".concat(initTime);
            return checkAndFixNum(initTime, length);
        }
        return initTime;
    }
}
