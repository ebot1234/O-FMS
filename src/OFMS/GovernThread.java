package OFMS;

import Game.RandomString;
import UI.New_UI;
import UI.UI_Layer;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class is responsible for managing matches by communicating to "Field And
 * Robots" to start and stop robots. As such, it contains a reference to the
 * current game UI. An instance of this class should be thought of as an actual
 * match - this is where a match starts, moves into autonomous, then to
 * teleoperated, etcetera.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class GovernThread extends Thread {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    /**
     * The sole instance of this class - this should be thought of as a new
     * match.
     */
    
    private static Random random = new Random();
    private static GovernThread _instance;
    /**
     * Holds a reference to the teams on the field.
     */
    private final FieldAndRobots field;
    /**
     * Holds a reference to the game UI.
     */
    private final New_UI gameUi;
    
    
    
    
    /**
     * Represents a match in the "no match currently underway" period.
     */
    public static final int NO_MATCH_UNDERWAY_MODE = 0;
    /**
     * Represents a match in the warmup period.
     */
    public static final int WARMUP_MODE = 1;
    /**
     * Represents a match in the Auto period.
     */
    public static final int AUTO_MODE = 2; //1
    /**
     * Represents a match in the teleoperated period.
     */
    public static final int TELE_MODE = 3;//2
    /**
     * The state of the current match, if any. Value should be one of the three
     * mode constants (NO_MATCH_UNDERWAY_MODE, AUTO_MODE, TELE_MODE).
     */
    private int matchMode = NO_MATCH_UNDERWAY_MODE;
    
    //How long the warmup period lests in milliseconds
    private double WarmUpTimeMillis = 2 * 1000;
    /**
     * How long the autonomous period lasts in milliseconds. Defaults to 10,000
     */
    private double autoTimeMillis = 10 * 1000;
    /**
     * How long the teleoperated period lasts in milliseconds. Defaults to
     * 120,000
     */
    private double teleTimeMillis = 140 * 1000;
    /**
     * Holds the elapsed mode time(as opposed to total time) of the current
     * match in milliseconds.
     */
    private int currMatchTimeMillis = 0;
    /**
     * Holds the elapsed time of the current match in milliseconds.
     */
    private int pseudoTimeMillis = 0;
    /**
     * Time, in seconds, that will be reported to the PLC to be displayed -
     * defaults to the autonomous period seconds.
     */
    private int PLC_timeSeconds = (int) autoTimeMillis / 1000;
    /**
     * Color to use to indicate a match is underway and in the autonomous
     * period.
     */
    private static final Color MATCH_UNDERWAY_AUTONOMOUS = Color.BLUE;
    /**
     * Color to use to indicate a match is underway and in the teleoperated
     * period.
     */
    private static final Color MATCH_UNDERWAY_TELEOP = Color.GREEN;
    /**
     * Color to use to indicate a match is underway and is in the endgame
     * period.
     */
    private static final Color MATCH_UNDERWAY_ENDGAME = Color.YELLOW;
    /**
     * Color to use to indicate a match has ended.
     */
    private static final Color MATCH_ENDED = Color.RED;
    private static final Color WARM_UP = Color.MAGENTA;
    /**
     * Indicates whether or not this is a new match - if this class has not yet
     * executed.
     */
    private boolean newMatch = true;
    /**
     * Local variable to determine if the match is currently underway or not.
     */
    private boolean matchRunning = false;
    /**
     * The kill switch. If set to true, this thread will attempt an orderly
     * shutdown.
     */
    private boolean kill = false;
    
    
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Creation And Instances">    
    /**
     * Creates a new Govern Thread instance bound to the indicated game UI and
     * field of teams.
     *
     * @param gameUi the FMS UI displaying match state and affording control
     * over the match.
     * @param field holds the field of teams and robot control.
     */
    private GovernThread(New_UI gameUi, FieldAndRobots field) {
        System.out.println("GovernThread Constructor");
        this.gameUi = gameUi;
        this.field = field;
        }

    /**
     * Creates a new instance bound to the indicated game UI and field of teams.
     * If the instance of this class is null, this method will attempt to
     * perform an orderly shutdown of the existing instance before overlaying it
     * with the new class.
     *
     * @param gameUi the FMS UI controlling the match.
     * @param field the team and robot information for each alliance.
     * @return a game instance bound to the input parameters.
     */
    public static GovernThread getNewInstance(New_UI gameUi,
            FieldAndRobots field) {
        if (_instance != null) {
            _instance.kill = true;
            System.out.println("Asked for a new instance of GT, had one already, "
                    + "killing the existing instance first...");
            while (_instance.isAlive()) {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GovernThread.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("...existing instance has died, creating new.");
        }
        _instance = new GovernThread(gameUi, field);
        return _instance;
    }

    /**
     * Gets the current instance of Govern Thread(aka the "match"). If the
     * instance is null, this will return null.
     *
     * @return the current instance, or null.
     */
    public static GovernThread getInstance() {
        if (_instance == null) {
            System.out.println("Asked for GovernThread instance before "
                    + "it was properly created; returning null!!");
        }
        return _instance;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Thread Start and Run Code">
    /**
     * Used to start the game - running thread. If called with a null game UI or
     * field this method will complain to the system output stream but otherwise
     * do nothing. If both of those classes are non-null, it will call
     * super.start and get the ball rolling.
     */
    @Override
    public synchronized void start() {
        if (gameUi == null) {
            System.out.println("Invalid GovernThread - No OFMS_UI!!");
        } else if (field == null) {
            System.out.println("Invalid GovernThread - No FieldAndRobots!!");
        } else {
            if (newMatch) {
                super.start();
            } else {
                System.out.println("************ERROR - THIS IS NOT A NEW MATCH**********");
            }
        }
        matchRunning = true;
    }

    /**
     * This method is called when the thread is told to start. This runs the
     * match and displays each period's status and time on the timer bar on the
     * UI. This will also respond to ESTOP requests.
     */
    @Override
    public void run() {
        //Warmup period
        newMatch = false;
        setModeAndStateForAllRobots(AUTO_MODE, false);
        System.out.println("Warmup Start");
        matchMode = WARMUP_MODE;
        playSound("MATCH_WARMUP.wav");//Play WarmUp sound
        UI_Layer.getInstance().changeProBarColor(WARM_UP);
        //Sets the Red Vault powerups to zero cubes
        FieldAndRobots.getInstance().redVault(FieldAndRobots.CubeNumbers.Cube_Zero, FieldAndRobots.PowerUps.Force);
        FieldAndRobots.getInstance().redVault(FieldAndRobots.CubeNumbers.Cube_Zero, FieldAndRobots.PowerUps.Boost);
        FieldAndRobots.getInstance().redVault(FieldAndRobots.CubeNumbers.Cube_Zero, FieldAndRobots.PowerUps.Levitate);
        //Sets the Blue vault power ups to zero cubes
        FieldAndRobots.getInstance().blueVault(FieldAndRobots.CubeNumbers.Cube_Zero, FieldAndRobots.PowerUps.Force);
        FieldAndRobots.getInstance().blueVault(FieldAndRobots.CubeNumbers.Cube_Zero, FieldAndRobots.PowerUps.Boost);
        FieldAndRobots.getInstance().blueVault(FieldAndRobots.CubeNumbers.Cube_Zero, FieldAndRobots.PowerUps.Levitate);
      while (!kill)
      {
          int newMatchTimeMillis = pseudoTimeMillis;
          if(!isWarmUp())
          {
              newMatchTimeMillis -= getWarmUpTimeMillis();
              
   
     }
          currMatchTimeMillis = (int) getModeTimeMillis() - newMatchTimeMillis;
            PLC_timeSeconds = currMatchTimeMillis / 1000;

            if (pseudoTimeMillis <= getTotalTimeMillis()) {
                UI_Layer.getInstance().updateProBar(((double) pseudoTimeMillis / getTotalTimeMillis()));
                UI_Layer.getInstance().setMatchTime(fixTime((currMatchTimeMillis / 1000) + ""));
                
            } else {
                stopMatch();
            }
      }
        newMatch = false;
        setModeAndStateForAllRobots(AUTO_MODE, true);
        System.out.println("Autonomous Start");
        matchMode = AUTO_MODE;
        playSound("CHARGE.wav"); // Play charge
        UI_Layer.getInstance().changeProBarColor(MATCH_UNDERWAY_AUTONOMOUS);

        while (!kill) {
            int newMatchTimeMillis = pseudoTimeMillis;
            if (!isAutonomous()) {
                newMatchTimeMillis -= getAutoTimeMillis();
            }

            currMatchTimeMillis = (int) getModeTimeMillis() - newMatchTimeMillis;
            PLC_timeSeconds = currMatchTimeMillis / 1000;

            if (pseudoTimeMillis <= getTotalTimeMillis()) {
                UI_Layer.getInstance().updateProBar(((double) pseudoTimeMillis / getTotalTimeMillis()));
                UI_Layer.getInstance().setMatchTime(fixTime((currMatchTimeMillis / 1000) + ""));
            } else {
                stopMatch();
            }

            if (pseudoTimeMillis < autoTimeMillis) {
            } else if (pseudoTimeMillis == autoTimeMillis) {
                System.out.println("Autonomous End");
                setModeAndStateForAllRobots(TELE_MODE, false);
                matchMode = NO_MATCH_UNDERWAY_MODE;
                System.out.println("Teleop Start");
                setModeAndStateForAllRobots(TELE_MODE, true);
                matchMode = TELE_MODE;
                playSound("3BELLS.wav"); // Play 3 bells
                UI_Layer.getInstance().changeProBarColor(MATCH_UNDERWAY_TELEOP);
            } else if (pseudoTimeMillis < autoTimeMillis + (teleTimeMillis * (3.0 / 4.0))) { // Find the non - end game time of the match
            } else if (pseudoTimeMillis == autoTimeMillis + (teleTimeMillis * (3.0 / 4.0))) {
                playSound("ENDGAME.wav"); // Play Endgame
                UI_Layer.getInstance().changeProBarColor(MATCH_UNDERWAY_ENDGAME);
            } else if (pseudoTimeMillis < getTotalTimeMillis()) { // Find the endgame time of the match
            } else if (pseudoTimeMillis == getTotalTimeMillis()) {
                System.out.println("Teleop End");
                setModeAndStateForAllRobots(TELE_MODE, false);
                matchMode = NO_MATCH_UNDERWAY_MODE;
                playSound("ENDMATCH.wav"); // Play end sound
                UI_Layer.getInstance().changeProBarColor(MATCH_ENDED);
                UI_Layer.getInstance().disableStopButton();
            } else {
            }

            //******************************************************************
            try {
                pseudoTimeMillis += 100;
                this.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(GovernThread.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Robot Control">
    /**
     * This stops the match and plays the emergency stop sound.
     */
    public void emergencyStopMatch() {
        System.out.println("Match Emergency Stopped!!!");
        stopMatch();
        playSound("FOGHORN.wav");
    }

    /**
     * This ends the match safely and sets all visible statuses to such.
     */
    public void stopMatch() {
        System.out.println("Match Ended");
        UI_Layer.getInstance().setResetButtonEnabled(true);
        disableAllBots();
        this.interrupt();
        kill = true;
        matchRunning = false;
    }

    /**
     * Sets all robots on all alliances to a disabled state.
     */
    public void disableAllBots() {
        setModeAndStateForAllRobots(TELE_MODE, false);
    }

    /**
     * Sets the state of all robots to teleoperated or autonomous. If the field
     * is null, this method does nothing.
     *
     * @param mode one of the TELEOP_MODE or AUTO_MODE constants from
     * GovernThread
     * @param enabled whether or not the specified mode should be on or off
     */
    public void setModeAndStateForAllRobots(int mode, boolean enabled) {
        if (field != null) {
            field.setAllRobotStates(mode, enabled);
        }
    }

    /**
     * Sets the bypassed state of all robots on all alliances. If the field is
     * null, this method does nothing.
     *
     * @param bypassState whether or not the robots should be bypassed.
     */
    public void setAllRobotsToBypassed(boolean bypassState) {
        if (field != null) {
            field.setBypassForAllRobots(bypassState);
        }
    }

    /**
     * Resets all robots on the field. If the field is null, this method does
     * nothing.
     */
    public void resetAllRobots() {
        if (field != null) {
            field.resetAllRobots();
        }
    }
    private static void getRandomItem(List<String> gameData) {
        //Size of the list is 5
       int index = random.nextInt(gameData.size());
       System.out.println("" + gameData.get(index));
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Match Getters">
    /**
     * Returns the current state of the current match as a String.
     *
     * @return String state of the match - "Disabled", "Autonomous", "Teleop" or
     * "Unknown State".
     */
    public String getMatchState() {
        if(matchMode == NO_MATCH_UNDERWAY_MODE) {
            return "Disabled";
        } 
        else if(matchMode == WARMUP_MODE){
            return "WarmUp";
        }
        else if (matchMode == AUTO_MODE) {
            return "Autonomous";
        } else if (matchMode == TELE_MODE) {
            return "Teleop";
        } else {
            return "Unknown State";
        }
    }
   
    
    

    /**
     * Determines if the match is in autonomous mode.
     *
     * @return True if the match is in autonomous.
     */
    public boolean isAutonomous() {
        return matchMode == AUTO_MODE;
    }
    // returns true if the match is in WarmUp mode and determines if the match is in warmup
    public boolean isWarmUp(){
        return matchMode == WARMUP_MODE;
        
       }
  
   
    /**
     * Determines whether a match is currently in progress.
     *
     * @return True if the match is currently running/in progress.
     */
    public boolean isMatchRunning() {
        return matchRunning;
    }

    /**
     * Determines if this is a new match.
     *
     * @return True if this is a new match.
     */
    public boolean isNewMatch() {
        return newMatch;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Timing">
    
    public void setWarmUpTime(int newWarmUpTime)
    {
        WarmUpTimeMillis = (newWarmUpTime * 1000);
    }
    /**
     * Sets the local time of the autonomous period, in seconds.
     *
     * @param newAutoTime Length of the autonomous period, in seconds.
     */
    public void setAutoTime(int newAutoTime) {
        autoTimeMillis = (newAutoTime * 1000);
    }

    /**
     * Sets the local time of the teleoperated period, in seconds.
     *
     * @param newTeleTime Length of the teleoperated period, in seconds.
     */
    public void setTeleTime(int newTeleTime) {
        teleTimeMillis = (newTeleTime * 1000);
    }

    /**
     * Determines the total match time.
     *
     * @return The sum of teleoperated time and autonomous time, in milli
     * seconds.
     */
    private int getTotalTimeMillis() {
        return (int) (WarmUpTimeMillis + teleTimeMillis + autoTimeMillis);
    }

    /**
     * Determines the time in the current game period.
     *
     * @return The time of autonomous, teleoperated, or the total time,
     * depending on the game state.
     */
    private int getModeTimeMillis() {
        if(matchMode == WARMUP_MODE){
            return (int)WarmUpTimeMillis;
        }
        if (matchMode == AUTO_MODE) {
            return (int) autoTimeMillis;
        } else if (matchMode == TELE_MODE) {
            return (int) teleTimeMillis;
        } else {
            return getTotalTimeMillis();
        }
    }

    /**
     * Determines the autonomous time left.
     *
     * @return The auton time left, in milli seconds.
     */
    private double getAutoTimeMillis() {
        return autoTimeMillis;
    }
    private double getWarmUpTimeMillis()
    {
        return WarmUpTimeMillis;
    }

    /**
     * Returns the time remaining in the current mode. Ex: if autonomous has ran
     * through 3 of 10 seconds, the result will be 7 seconds for the 7 remaining
     * seconds of the autonomous period instead of what is left in the combined
     * autonomous and teleoperated time.
     *
     * @return The remaining mode time, as a String.
     */
    public String get_PLC_Time() {
        return fixTime("" + PLC_timeSeconds);
    }

    /**
     * Corrects the string time for display purposes.
     *
     * @param time The String time to be fixed.
     * @return The String of fixed time.
     */
    private String fixTime(String time) {
        if (time.length() < 3) {
            return fixTime("0" + time);
        } else {
            return time;
        }
    }
    //</editor-fold>

    /**
     * Plays a sound file
     *
     * @param fileName The name of the file to play. Currently the sound files
     * must be located inside the project under src\OFMS\Sounds
     */
    public void playSound(final String fileName) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais;
            ais = AudioSystem.getAudioInputStream(
                    GovernThread.class.getResource("/OFMS/Sounds/" + fileName));
            clip.open(ais);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            System.err.println("My_Sound_Error: " + e.getMessage());
        }
    }
}