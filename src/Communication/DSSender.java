import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import 
/**
 * This class sends packets of information to a driver station.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class DSSender {

    /**
     * An 'instance' of this class - there can be only one. This is that one.
     */
    private static DSSender _instance;
    /**
     * Stored reference to the socket used for communication.
     */
    private DatagramSocket dsock;

    /**
     * Gets a instance of DSSender.
     *
     * @return an instance of DSSender
     */
    public static DSSender getInstance() {
        if (_instance == null) {
            _instance = new DSSender();
            System.out.println("DSSender Constructed");
        }
        return _instance;
    }

    /**
     * DSSender constructor - initializes the socket.
     */
    private DSSender() {
        try {
            dsock = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a byte representation of the position of the teams driver
     * station.
     *
     * @param station The integral representation of the station
     * @return A byte representation of the station
     */
    public static byte byteForStation(int station) {
        switch (station) {
            case 1:
                return 0x31; // Station 1
            case 2:
                return 0x32; // Station 2
            case 3:
                return 0x33; // Station 3
        }
        return 0x31; // Defaults to station 1
    }

    /**
     * Returns a byte representation of the alliance the team(more correctly,
     * the Driver Station) is on.
     *
     * @param alliance The integral representation of the alliance. 1 = Red
     * alliance while 2 = Blue alliance
     * @return A byte representation of the alliance
     */
    public static byte byteForAlliance(int alliance) {
        switch (alliance) {
            case 1:
                return 0x52; // Red Alliance
            case 2:
                return 0x42; // Blue Alliance
        }
        return 0x52; // Defaults to Red Alliance
    }

    /**
     * Updates the Driver Station by creating and sending a packet with the new
     * information.
     *
     * @param team The team object. This is used to find information about the
     * team.
     * @param robotState The state of the robot(autonomous or tele-operated, plus
     * whether it is enabled or disabled.
     * @param allianceColor The byte representing the color of the Driver
     * Stations alliance
     * @param station The byte representing the station the Driver Station is
     * connected to
     * @throws SocketException If there's a problem
     */
    public void updateTeam(Team team, byte robotState, byte allianceColor,
            byte station) throws SocketException {
        sendPacket(buildPacket(team.getInetAddress(), robotState, allianceColor,
                station));
    }

    /**
     * In charge of sending a UDP packet to a Driver Station.
     *
     * @param p The packet to be sent
     */
    private void sendPacket(DatagramPacket p) {
        try {
            dsock.send(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a packet for the DS based off of inputted information.
     *
     * @param addr The address of the Driver Station(where the packet should be
     * sent)
     * @param robotState The state the robot should be set to
     * @param allianceColor The alliance color the Driver Station should be set
     * to
     * @param station The alliance station the Driver Station should be set to
     * @return The built Datagram Packet
     * @throws SocketException If something goes wrong
     */
    private DatagramPacket buildPacket(InetAddress addr, byte robotState,
            byte allianceColor, byte station) throws SocketException {
        // Create array
        byte[] data = new byte[74];

        // Fill array with blank information
        for (int i = 0; i < 74; i++) {
            data[i] = 0;
        }

        // Packet number
        data[0] = (byte) 98; //9818
        data[1] = (byte) 18; //4342

        // Robot state
        data[2] = robotState;

        // Alliance Station
        data[3] = allianceColor; // Color
        data[4] = station; // Station number

        // FMS version
        data[18] = 0;
        data[19] = 6;
        data[20] = 2;
        data[21] = 5;
        data[22] = 0;
        data[23] = 8;
        data[24] = 4;
        data[25] = 6;

        // CRC 32(Checksum)
        CRC32 check = new CRC32();
        check.update(data);
        byte[] crc = ByteBuffer.allocate(4).putInt((int) check.getValue()).array();

        // CRC hash
        data[70] = crc[0];
        data[71] = crc[1];
        data[72] = crc[2];
        data[73] = crc[3];

        // 1121 is the port used by the DS to recieve FMS packets
        return new DatagramPacket(data, data.length, addr, 1121);
    }
}

