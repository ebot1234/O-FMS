import Communication.DSSender;
import UI.UI_Layer;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a team and its robot within the system. Holds various robot state
 * indicators and values.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Ethen B.-Remastered the FMS
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class Team extends Thread {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    /**
     * The base byte value recognized by the FRC driver station as indicating a
     * robot is in disabled tele-operated mode.
     */
    private static final byte ROBOT_TELEOP_DISABLED = 0x43;
    /**
     * The base byte value recognized by the FRC driver station as indicating a
     * robot is in disabled autonomous mode.
     */
    private static final byte ROBOT_AUTON_DISABLED = 0x53;
    /**
     * The byte value recognized by the FRC driver station as indicating a robot
     * is in an enabled state, when added to the base byte value for either
     * teleoperated or autonomous mode.
     */
    private static final byte ROBOT_ENABLED = 0x20;
    /**
     * The base byte value recognized by the FRC driver station as indicating a
     * robot is Emergency Stopped.
     */
    private static final byte ROBOT_ESTOPPED = 0;
    /**
     * Indicates if the robot should be in autonomous or teleoperated mode and
     * whether it's enabled.
     */
    private byte robotDesiredState = ROBOT_TELEOP_DISABLED;
    /**
     * The address of this team's Driver Station on the field network.
     */
    private InetAddress addr;
    /**
     * Stores this robot's alliance in a form understood by the FRC driver
     * station. Holds the transformed (by DSSender) value of the alliance number
     * (which is an integer).
     */
    private final byte alliance;
    /**
     * Stores this robot's station in a form understood by the FRC driver
     * station. Holds the transformed (by DSSender) value of the station number
     * (which is an integer).
     */
    private final byte station;
    /**
     * Stores the integer value of this robot's alliance number.
     */
    private final int allianceInt;
    /**
     * Stores the integer value of this robot's station number.
     */
    private final int stationInt;
    /**
     * Stores the team number of this team.
     */
    private int teamNum;
    /**
     * Stores the maximum time that O!FMS can go without receiving a DS packet
     * and still consider the team (Driver Station) connected.
     */
    private static final double MAX_PACKET_TRIP_TIME_MILLIS = 1000;
    /**
     * Stores the offset of time between the latest packet time (as recorded by
     * this computers system clock) and the previous packets received time.
     */
    private double diffInPacketTimeMillis = MAX_PACKET_TRIP_TIME_MILLIS;
    /**
     * Stores the time the last packet(from this team) was received by the field
     * management system.
     */
    private double lastPacketTimeMillis = System.currentTimeMillis() - MAX_PACKET_TRIP_TIME_MILLIS;
    /**
     * Indicates whether or not the team is connected to FMS.
     */
    private boolean fmsCommAlive = false;
    /**
     * Indicates whether or not the robot is connected to the teams Driver
     * Station.
     */
    private boolean robotCommAlive = false;
    /**
     * Holds the current battery voltage of this team's robot.
     */
    private double batteryVoltage = 0.0;
    /**
     * Indicates the readiness of this team, for competition, based on the state
     * of communications and if bypassed.
     */
    private boolean teamReady = false;
    /**
     * Indicates whether this team is currently bypassed.
     */
    private boolean teamBypassed = false;
    /**
     * Temporary value held if the robot is ESTOPPED. Once a robot is ESTOPPED
     * it cannot be un-ESTOPPED until an O!FMS reset.
     */
    private boolean ESTOPPED = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor and Thread Run">
    /**
     * Creates a new instance with the provided information.
     *
     * @param number The number of the team
     * @param alliance The alliance the team is on. For standard match
     * configurations, FieldAndRobots.RED and FieldAndRobots.BLUE may be used
     * (and should be) to specify this value.
     * @param station The station the team is on
     */
    public Team(int number, int alliance, int station) {
        System.out.println("Team Constructor");
        teamNum = number;
        allianceInt = alliance;
        stationInt = station;
        this.alliance = DSSender.byteForAlliance(alliance);
        this.station = DSSender.byteForStation(station);

        setTeamNumber(number);
    }

    /**
     * Tells this instance to start sending status updates to FMS and the teams
     * DS.
     */
    public void startFMSUpdates() {
        this.start();
    }

    /**
     * Reads internal state variables and sends updates to the game UI and to
     * the driver station when active.
     */
    @Override
    public void run() {
        while (true) {
            updateSelfWithTimes();
            /**
             * Update the game UI based off of local information.
             */
            UI_Layer gameUi = UI_Layer.getInstance();
            if (gameUi != null) {
                gameUi.setCommStatus(allianceInt, stationInt,
                        fmsCommAlive, robotCommAlive, teamBypassed, ESTOPPED);
                gameUi.setBatteryVisual(allianceInt, stationInt,
                        batteryVoltage);
            }
            try {
                //Update the driver station
                DSSender.getInstance()
                        .updateTeam(this, getRobotState(), alliance, station);
            } catch (SocketException ex) {
                Logger.getLogger(Team.class.getName()).log(Level.SEVERE, null, ex);
            }
            /**
             * Waits half a second between updates.
             */
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Team.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Updaters">    
    /**
     * This method will set the current battery voltage and communications
     * status indicator. A call to this method is assumed to only occur when a
     * packet of data is received from the associated robot, and this method
     * will therefore update the stored last packet received value. This helps
     * determine when connectivity is gained or lost.
     *
     * @param batteryVolts The robot's current battery voltage.
     * @param robCommAlive If the DS is connected to (and communicating with)
     * this team's robot.
     */
    public void updateFromReceived(double batteryVolts, boolean robCommAlive) {
        lastPacketTimeMillis = System.currentTimeMillis();
        batteryVoltage = batteryVolts;
        robotCommAlive = robCommAlive;
    }

    /**
     * This method updates the communication statuses by checking against the
     * last known time of a received packet by the driver station.
     */
    private void updateSelfWithTimes() {
        diffInPacketTimeMillis = System.currentTimeMillis() - lastPacketTimeMillis;
        if (diffInPacketTimeMillis >= MAX_PACKET_TRIP_TIME_MILLIS) {
            fmsCommAlive = false;
            robotCommAlive = false;
        } else {
            fmsCommAlive = true;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Robot State, Bypass, and ESTOP">
    /**
     * Sets the state of this team's robot.
     *
     * @param mode The mode the robot should be in. Must be
     * GovernThread.AUTO_MODE or GovernThread.TELE_MODE. Any other values will
     * result in the robot's state being set to the default of tele-operated.
     * @param enabled If the robot should be enabled or not.
     */
    public void setRobotState(int mode, boolean enabled) {
        byte out = ROBOT_TELEOP_DISABLED;

        if (mode == GovernThread.AUTO_MODE) {
            out = ROBOT_AUTON_DISABLED;
        } else if (mode == GovernThread.TELE_MODE) {
            out = ROBOT_TELEOP_DISABLED;
        }
        if (enabled) {
            out += ROBOT_ENABLED;
        }
        robotDesiredState = out;
    }

    /**
     * Gets the state the robot is set to run at.
     *
     * @return The byte value the robot should set to.
     */
    public byte getRobotState() {
        byte desiredState = robotDesiredState;

        if (ESTOPPED) {
            return ROBOT_ESTOPPED;
        } else {
            if (!isBypassed() && isReady()) { //both ARE required, isReady is for status, isBypassed is for state
                return desiredState;
            } else {
                return ROBOT_TELEOP_DISABLED;
            }
        }
    }

    /**
     * Reads and returns the current state of the bypassed indicator(in code).
     *
     * @return True if this team is bypassed.
     */
    public synchronized boolean isBypassed() {
        return teamBypassed;
    }

    /**
     * Sets the value of the bypassed indicator(in code).
     *
     * @param isBypassed True if this team should be bypassed.
     */
    public synchronized void setBypassed(boolean isBypassed) {
        teamBypassed = isBypassed;
    }

    /**
     * Emergency Stops this teams robot. Cannot be reset without calling the
     * reset method.
     */
    public void ESTOP() {
        ESTOPPED = true;
    }

    /**
     * Determines if the team is Emergency Stopped.
     *
     * @return True if the team is ESTOPPED.
     */
    public boolean isESTOPPED() {
        return ESTOPPED;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Reset Team and Zero Battery">
    /**
     * Resets the teams values to default values.
     */
    public void resetTeam() {
        zeroBatteryVoltage();

        diffInPacketTimeMillis = MAX_PACKET_TRIP_TIME_MILLIS;
        lastPacketTimeMillis = System.currentTimeMillis() - MAX_PACKET_TRIP_TIME_MILLIS;

        fmsCommAlive = false;
        robotCommAlive = false;
        setBypassed(false);
        teamReady = false;
        ESTOPPED = false;
    }

    /**
     * Zeros out the stored battery voltage value for this team.
     */
    public void zeroBatteryVoltage() {
        batteryVoltage = 0;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Team Number Control">
    /**
     * This method sets the team number, and also constructs and sets the
     * network address of this instance. The network address is constructed in
     * the form of 10.te.am.5, where 'te' is the first two digits of the team
     * number and 'am' is the last two digits. If the team number is less than
     * 1000 then zeros are prefixed before address construction.
     *
     * @param number The new team number.
     */
    public final void setTeamNumber(double number) {
        teamNum = (int) number;
        String num = "" + number / 100;
        String[] nums = num.split("\\.");
        String first = nums[0];
        String last = nums[1];
        if (nums[1].length() == 1) {
            last = nums[1] + "0";
        }
        String teamIP = "10." + first + "." + last + ".5";
        try {
            addr = InetAddress.getByName(teamIP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the integer team number.
     *
     * @return An integer value of the team number.
     */
    public int getTeamNumber() {
        return teamNum;
    }

    /**
     * Gets the address of the driver station for this team on the FMS network.
     * Note this value is never directly set, but is instead always constructed
     * from the team number.
     *
     * @return The inet address of this team's driver station
     */
    public InetAddress getInetAddress() {
        return addr;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Readiness Getters">
    /**
     * Determines if this robot is ready and returns the result. A robot is
     * deemed ready if it is in a bypassed state, or(if not bypassed) if both
     * FMS and robot communications are alive.
     *
     * @return True if this team is ready for competition.
     */
    public synchronized boolean isReady() {
        teamReady = false;
        if (!teamBypassed) {
            teamReady = fmsCommAlive && robotCommAlive;
        } else {
            teamReady = true;
        }
        return teamReady;
    }
    //</editor-fold>
}
