package OFMS;

import Communication.DSReceiver;
import PLC_Aux.PLC_Receiver;
import PLC_Aux.PLC_Sender;
import Real_Time_Scoring.GameData;
import UI.ESTOP_Panel;
import UI.Msg;
import UI.New_UI;
import UI.UI_Layer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import splashscreen.FMS_SplashScreen;


/**
 * This is the main class where everything runs from.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class Main {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    /**
     * Holds a reference to the UI Layer - this layer acts as a so called
     * 'middle man' between the current UI and the background code.
     */
    public static UI_Layer layer;
    /**
     * Thread for periodically checking field readiness and setting UI control
     * values accordingly.
     */
    public static Timer updateUI_Thread = new Timer();
    /**
     * Stored reference to the single driver station receiver class responsible
     * for all communications between this FMS and the driver stations.
     */
    private final DSReceiver ds;
    /**
     * Reference to the PLC Receiver responsible for accepting ESTOP commands.
     */
    private final PLC_Receiver plcReceiver;
    /**
     * Reference to the standard UI for controlling robots and the match.
     */
    public static New_UI newUI;
    /**
     * The full UI responsible for the entire event management system.
     */
    private static full_UI_Tester full_test;
    /**
     * The simple UI to run simple scrimmages with no advanced features.
     */
    private static tester simple_test;
    /**
     * Variable holding whether simple UI or full UI is enabled.
     */
    private static boolean simpleMode = true;
    //Adds the class of game Data
    public static GameData gameData;
    /**
     * A local reference of the Main instance.
     */
    private static Main _instance;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor and Main">
    /**
     * Main method for OFMS.
     *
     * @param args
     */
    public static void main(String[] args) {
        getInstance();
    }

     
    
    /**
     * Gets the current "instance" of Main.
     *
     * @return The current instance.
     */
    public static Main getInstance() {
        //System.out.println("********************Main get instance");
        if (_instance == null) {
            System.out.println("***ERROR - MAIN NULL");
            _instance = new Main();
            System.out.println("***Main created new instance");
        }
        return _instance;
    }

    /**
     * Main Constructor - creates O!FMS
     */
    private Main() {
        _instance = this;
        
        FMS_SplashScreen splash = new FMS_SplashScreen();
        splash.splashTheScreen(splash);

        // Instantiate UI_Layer
        layer = UI_Layer.getInstance();

        // Instantiate Receivers
        ds = new DSReceiver();
        plcReceiver = new PLC_Receiver();
        // Instantiate the Game Data thingy
        gameData = new GameData();

        // Instantiate Full and Simple UIs then bring up the simple UI
        full_test = new full_UI_Tester();
        full_test.setUpIndicators();
        full_test.setVisible(true);

        //Instantiate new FieldAndRobots
        FieldAndRobots field = FieldAndRobots.getNewDefaultInstance();

        // Create govern thread
        newUI = full_test.getNewUI();
        if (newUI != null) {
            //We don't do anything with the game thread except start it up.
            GovernThread game = GovernThread.getNewInstance(newUI, field);
            System.out.println("Correctly have newUI in Main");
        } else {
            GovernThread game = GovernThread.getNewInstance(New_UI.getInstance(), field);
            System.out.println("********ERROR - NewUI IS NULL; GETTING INSTANCE\n\n\n\n");
        }

        // Create PLC Sender
        PLC_Sender.getInstance();
                
        // Starts the DS and PLC Receivers
        startUpReceivers();

        // Start FMS Updates for each team
        FieldAndRobots.getInstance().startFMSUpdatesForAllTeams();
        
        

        //Create array of place-holder team numbers to initialize UI

        String[][] teams = {{"0001", "0002", "0003"}, {"0004", "0005", "0006"}};
        layer.setBlueRedNumbers(teams);

        /**
         * Create a timer to to check if field is ready every 500 milliseconds;
         * if field is ready, update lights and ESTOP panels.
         */
        updateUI_Thread.schedule(new TimerTask() {
            @Override
            public void run() {
                layer.checkIfFieldReady();
                if (!isSimpleMode()) {
                    PLC_Sender.getInstance().updatePLC_Lights(false);
                    getESTOP_Panel().updatePanel();
                }
            }
        }, 0, 500);
    }
    //</editor-fold>

    /**
     * Start the DS and PLC Receivers.
     */
    private void startUpReceivers() {
        // Start the driver station receiver
        ds.start();

        // Start PLC Receiver
        plcReceiver.start();
    }
    
    public void shutdownAllNetworkComms() {
        Msg.send("Shutting Down All Network Communication...");
        ds.shutdownSocket();
        plcReceiver.shutdownSocket();
    }

    /**
     * Gets the box to send status messages to.
     *
     * @return JTextField for the message box.
     */
    public JTextArea getCurrentMsgBox() {
        if (simpleMode) {
            if (simple_test != null) {
                return simple_test.getNewUI().getMessagesTextArea();
            }
            System.out.println("ERROR - NULL SIMPLE TEST in MAIN");
            return null;
        } else {
            if (full_test != null) {
                return full_test.getFullUI().getNew_UI().getMessagesTextArea();
            }
            System.out.println("ERROR - NULL FULL TEST in MAIN");
            return null;
        }
    }

    /**
     * Gets the box to send status messages to.
     *
     * @return JTextField for the message box.
     */
    public JFrame getMainFrame() {
        if (simpleMode) {
            if (simple_test != null) {
                return (JFrame) simple_test;
            }
            System.out.println("ERROR 2 - NULL SIMPLE TEST in MAIN");
            return null;
        } else {
            if (full_test != null) {
                return (JFrame) full_test;
            }
            System.out.println("ERROR 2 - NULL FULL TEST in MAIN");
            return null;
        }
    }

    /**
     * Tells if the UI in simple mode or real mode.
     *
     * @return True if we're in simple mode.
     */
    public static boolean isSimpleMode() {
        return simpleMode;
    }

    /**
     * Gets an ESTOP UI panel.
     *
     * @return An ESTOP UI panel.
     */
    public static ESTOP_Panel getESTOP_Panel() {
        return full_test.getFullUI().getESTOP_Panel();
    }

    /**
     * This method switches between the full and simple UI.
     */
    public static void switchViews() {
        if (simpleMode) {
            // Store current team numbers for later use
            String[][] teams = layer.getBlueRedNumbers();

            /**
             * Remove simple UI and bring up full UI.
             */
            simple_test.setVisible(false);
            full_test.setUpIndicators(); // Essentially, makes the UI active
            full_test.setVisible(true);

            // Reset the match
            layer.resetCommand();
            //tell PLC to update lights
            layer.forceLightUpdate();
            // set new layer using stored teams
            layer.setBlueRedNumbers(teams);

            // Update team numbers to PLC
            PLC_Sender.getInstance().updatePLC_TeamNum(true);

            // Not in simple mode
            simpleMode = true;
        } else { // we're not in simple mode...
            // Store current team numbers for later use
            String[][] teams = layer.getBlueRedNumbers();

            /**
             * Remove full UI and bring up simple UI.
             */
            full_test.setVisible(false);
            simple_test.setUpIndicators(); // Essentially, make the UI active
            simple_test.setVisible(true);

            // reset the match
            layer.resetCommand();
            // tell PLC to update lights
            layer.forceLightUpdate();
            // set new layer using stored teams
            layer.setBlueRedNumbers(teams);

            // Update team numbers to PLC
            PLC_Sender.getInstance().updatePLC_TeamNum(true);

            // Now in simple mode
            simpleMode = true;
            
            
        }
    }
}
