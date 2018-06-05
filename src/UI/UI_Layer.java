package UI;

import OFMS.FieldAndRobots;
import OFMS.GovernThread;
import OFMS.Main;
import PLC_Aux.PLC_Receiver;
import PLC_Aux.PLC_Sender;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 * Arguably one of the most important parts of the program, this class
 * represents the connection between the UI and the background code. As well as
 * representing it, this class also acts as a layer of abstraction so that it is
 * easy to connect multiple UIs to the background code. In the future this will
 * allow FMS to have easy integration into other UIs or even on a tablet/android
 * device.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class UI_Layer {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    //<editor-fold defaultstate="collapsed" desc="Readiness Indicators">
    /**
     * The indicator used to show if the entire field is ready to play a match
     * or not.
     */
    private static JTextField fullFieldReady;
    /**
     * The indicator used to show if the entire blue alliance is ready to play a
     * match(or not).
     */
    private static JLabel blueSideReady;
    /**
     * The indicator used to show if the entire red alliance is ready to play a
     * match(or not).
     */
    private static JLabel redSideReady;
    /**
     * Holds the previous state of the Blue alliance's readiness for
     * competition.
     */
    private static boolean prevBlueReady = false;
    /**
     * Holds the previous state of the Red alliance's readiness for competition.
     */
    private static boolean prevRedReady = false;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Time Related Elements">
    /**
     * This indicator is used as a visual representation of how far along the
     * match is.
     */
    private static JProgressBar matchProgressBar;
    /**
     * The indicator used to show the numerical remaining time in the match.
     */
    private static JTextField runningMatchTime;
    /**
     * Text field used to show and/or change the time in the autonomous period
     * of the match.
     */
    private static JTextField autonomousTime;
    /**
     * Text field used to show and/or change the time in the teleoperated period
     * of the match.
     */
    private static JTextField teleoperatedTime;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="UI Control Buttons">
    /**
     * Button used to switch between the simple and the full UI.
     */
    private static JButton switcherButton;
    /**
     * Button used solely to start a match.
     */
    private static JButton beginMatchButton;
    /**
     * Button used solely to stop a match.
     */
    private static JButton stopMatchButton;
    /**
     * Button used to reset the UI and field.
     */
    private static JButton resetButton;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Team Realted Elements">
    //<editor-fold defaultstate="collapsed" desc="Team Bypass Boxes">
    /**
     * Reference to the bypass checkbox for the team on the Blue alliance in
     * station 1.
     */
    private static JCheckBox blueTeamBypass1;
    /**
     * Reference to the bypass checkbox for the team on the Blue alliance in
     * station 2.
     */
    private static JCheckBox blueTeamBypass2;
    /**
     * Reference to the bypass checkbox for the team on the Blue alliance in
     * station 3.
     */
    private static JCheckBox blueTeamBypass3;
    /**
     * Reference to the bypass checkbox for the team on the Red alliance in
     * station 1.
     */
    private static JCheckBox redTeamBypass1;
    /**
     * Reference to the bypass checkbox for the team on the Red alliance in
     * station 2.
     */
    private static JCheckBox redTeamBypass2;
    /**
     * Reference to the bypass checkbox for the team on the Red alliance in
     * station 3.
     */
    private static JCheckBox redTeamBypass3;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Team Number Fields">
    /**
     * Holder of the team number of the team on the Blue alliance in station 1.
     */
    private static JTextField blueTeamNumber1;
    /**
     * Holder of the team number of the team on the Blue alliance in station 2.
     */
    private static JTextField blueTeamNumber2;
    /**
     * Holder of the team number of the team on the Blue alliance in station 3.
     */
    private static JTextField blueTeamNumber3;
    /**
     * Holder of the team number of the team on the Red alliance in station 1.
     */
    private static JTextField redTeamNumber1;
    /**
     * Holder of the team number of the team on the Red alliance in station 2.
     */
    private static JTextField redTeamNumber2;
    /**
     * Holder of the team number of the team on the Red alliance in station 3.
     */
    private static JTextField redTeamNumber3;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Team Voltage Fields"> 
    /**
     * Holder of the robot voltage for the team on the Blue alliance in station
     * 1.
     */
    private static JTextField voltageBlueTeam1;
    /**
     * Holder of the robot voltage for the team on the Blue alliance in station
     * 2.
     */
    private static JTextField voltageBlueTeam2;
    /**
     * Holder of the robot voltage for the team on the Blue alliance in station
     * 3.
     */
    private static JTextField voltageBlueTeam3;
    /**
     * Holder of the robot voltage for the team on the Red alliance in station
     * 1.
     */
    private static JTextField voltageRedTeam1;
    /**
     * Holder of the robot voltage for the team on the Red alliance in station
     * 2.
     */
    private static JTextField voltageRedTeam2;
    /**
     * Holder of the robot voltage for the team on the Red alliance in station
     * 3.
     */
    private static JTextField voltageRedTeam3;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Team Robot Comm Status"> 
    /**
     * Robot communication indicator for the team on the Blue alliance in
     * station 1.
     */
    private static JLabel robotComStatusBlueTeam1;
    /**
     * Robot communication indicator for the team on the Blue alliance in
     * station 2.
     */
    private static JLabel robotComStatusBlueTeam2;
    /**
     * Robot communication indicator for the team on the Blue alliance in
     * station 3.
     */
    private static JLabel robotComStatusBlueTeam3;
    /**
     * Robot communication indicator for the team on the Red alliance in station
     * 1.
     */
    private static JLabel robotComStatusRedTeam1;
    /**
     * Robot communication indicator for the team on the Red alliance in station
     * 2.
     */
    private static JLabel robotComStatusRedTeam2;
    /**
     * Robot communication indicator for the team on the Red alliance in station
     * 3.
     */
    private static JLabel robotComStatusRedTeam3;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Team Driver Station Comm Status">
    /**
     * Driver Station communication indicator for the team on the Blue alliance
     * in station 1.
     */
    private static JLabel dsComStatusBlueTeam1;
    /**
     * Driver Station communication indicator for the team on the Blue alliance
     * in station 2.
     */
    private static JLabel dsComStatusBlueTeam2;
    /**
     * Driver Station communication indicator for the team on the Blue alliance
     * in station 3.
     */
    private static JLabel dsComStatusBlueTeam3;
    /**
     * Driver Station communication indicator for the team on the Red alliance
     * in station 1.
     */
    private static JLabel dsComStatusRedTeam1;
    /**
     * Driver Station communication indicator for the team on the Red alliance
     * in station 2.
     */
    private static JLabel dsComStatusRedTeam2;
    /**
     * Driver Station communication indicator for the team on the Red alliance
     * in station 3.
     */
    private static JLabel dsComStatusRedTeam3;
    //</editor-fold>
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Colors">
    /**
     * Color to use when indicating a team is in a ready state.
     */
    private static final Color READY = ColorPallate.READY;
    /**
     * Color to use when indicating a team is not ready.
     */
    private static final Color NOT_READY = ColorPallate.NOT_READY;
    /**
     * Color to use when indicating a team has been bypassed.
     */
    private static final Color BYPASSED = ColorPallate.BYPASSED;
    /**
     * Color to use when indicating a team has been ESTOPPED.
     */
    private static final Color ESTOPPED = ColorPallate.ESTOPPED_COM;
    /**
     * Color to use when indicating a stations team number is being edited.
     */
    private static final Color EDITING = ColorPallate.EDITING;
    //</editor-fold>
    /**
     * An 'instance' of this class - there can be only one. This is that one.
     */
    private static UI_Layer _instance;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor and getInstance">
    /**
     * UI Layer's constructor - the first thing it does is to reset the match
     * with it's own defaults.
     */
    private UI_Layer() {
        System.out.println("UI_Layer Const");
        renewGameThread("10", "140");
    }

    /**
     * Gets a instance of UI_Layer.
     *
     * @return an instance of UI_Layer.
     */
    public static UI_Layer getInstance() {
        if (_instance == null) {
            _instance = new UI_Layer();
        }
        return _instance;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setup UI">
    /**
     * Essentially connects the given button to the O!FMS background code to act
     * as the "switch UI mode" button. It does so to enable a developer to be
     * able to use multiple UIs and just reset the indicator depending on which
     * UI they wish to use.
     *
     * @param switcher The button to act as the "switch UI mode" button.
     */
    public void setSwitchViewButton(JButton switcher) {
        if (switcherButton != null) {
            ActionListener[] listeners = switcherButton.getActionListeners();
            for (ActionListener lis : listeners) {
                switcherButton.removeActionListener(lis);
            }
        }
        switcherButton = null;
        switcherButton = switcher;
        ActionListener listen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Called Switch views from button. Event: " + e.toString());
                //System.out.println("Listener Count: " + ((JButton) e.getSource()).getActionListeners().length);
                Main.switchViews();
            }
        };
        switcherButton.addActionListener(listen);
    }

    /**
     * Essentially connects the given progress bar to the O!FMS background code.
     * It does so to enable a developer to be able to use multiple UIs and just
     * reset the indicator depending on which UI they wish to use.
     *
     * @param bar The progress bar to become the O!FMS progress bar.
     */
    public void setProgressBar(JProgressBar bar) {
        matchProgressBar = bar;
    }

    /**
     * Essentially connects the given test field to the O!FMS background code to
     * act as the 'field ready' indicator. It does so to enable a developer to
     * be able to use multiple UIs and just reset the indicator depending on
     * which UI they wish to use.
     *
     * @param fieldReady The text field indicator for if the field is ready to
     * run a match.
     */
    public void setFullFieldReady(JTextField fieldReady) {
        fullFieldReady = fieldReady;
    }

    /**
     * Essentially connects the given indicators(red side and blue side ready)
     * to the O!FMS background code. It does so to enable a developer to be able
     * to use multiple UIs and just reset the indicators depending on which UI
     * they wish to use.
     *
     * @param redIndicator The indicator to visually show that the Red alliance
     * is ready to play a match.
     * @param blueIndicator The indicator to visually show that the Blue
     * alliance is ready to play a match.
     */
    public void setSideIndicators(JLabel redIndicator, JLabel blueIndicator) {
        redSideReady = redIndicator;
        blueSideReady = blueIndicator;
    }

    /**
     * Essentially connects the given button to the O!FMS background code to act
     * as the fields "Stop Button". It does so to enable a developer to be able
     * to use multiple UIs and just reset the indicator depending on which UI
     * they wish to use.
     *
     * @param stop The button used to stop a match.
     */
    public void setStopButton(JButton stop) {
        if (stopMatchButton != null) {
            ActionListener[] listeners = stopMatchButton.getActionListeners();
            for (ActionListener lis : listeners) {
                stopMatchButton.removeActionListener(lis);
            }
        }
        stopMatchButton = stop;
        stopMatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMatch();
            }
        });
    }

    public void disableStopButton() {
        stopMatchButton.setEnabled(false);
    }

    /**
     * Method used to safely stop a match and return assorted buttons to their
     * default enabled state(whether that be enabled or disabled).
     */
    public void stopMatch() {
        GovernThread game = GovernThread.getInstance();
        if (game != null) {
            game.emergencyStopMatch();
        }
        beginMatchButton.setEnabled(false);
        resetButton.setEnabled(true);
        switcherButton.setEnabled(true);
        stopMatchButton.setEnabled(false);
    }

    /**
     * Essentially connects the given button to the O!FMS background code as the
     * match reset button. It does so to enable a developer to be able to use
     * multiple UIs and just reset the indicator depending on which UI they wish
     * to use.
     *
     * @param reset The button to become the reset button.
     */
    public void setResetButton(JButton reset) {
        if (resetButton != null) {
            ActionListener[] listeners = resetButton.getActionListeners();
            for (ActionListener lis : listeners) {
                resetButton.removeActionListener(lis);
            }
        }
        resetButton = reset;
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetCommand();
            }
        });
    }

    /**
     * Method used to reset fields and buttons to their default state after a
     * match has finished.
     */
    public void resetCommand() {
        beginMatchButton.setEnabled(false);
        switcherButton.setEnabled(true);
        stopMatchButton.setEnabled(false);

        autonomousTime.setEditable(true);
        teleoperatedTime.setEditable(true);

        resetProBar();

        renewGameThread(autonomousTime.getText(), teleoperatedTime.getText());

        SetAllBypassBoxesEnabled(true);

        SetAllBypassBoxesSelected(false);

        dsComStatusBlueTeam1.setBackground(NOT_READY);
        dsComStatusBlueTeam2.setBackground(NOT_READY);
        dsComStatusBlueTeam3.setBackground(NOT_READY);
        dsComStatusRedTeam1.setBackground(NOT_READY);
        dsComStatusRedTeam2.setBackground(NOT_READY);
        dsComStatusRedTeam3.setBackground(NOT_READY);

        robotComStatusBlueTeam1.setBackground(NOT_READY);
        robotComStatusBlueTeam2.setBackground(NOT_READY);
        robotComStatusBlueTeam3.setBackground(NOT_READY);
        robotComStatusRedTeam1.setBackground(NOT_READY);
        robotComStatusRedTeam2.setBackground(NOT_READY);
        robotComStatusRedTeam3.setBackground(NOT_READY);

        runningMatchTime.setText(UI_Layer.fixAutoTime(autonomousTime.getText()));

        PLC_Receiver.resetFieldESTOPPED();
        PLC_Sender.getInstance().updatePLC_Lights(true);

        SetAllTeamFieldsEditable(true);

        if (!Main.isSimpleMode()) {
            PLC_Sender.getInstance().updatePLC_Time(true);
        }
        System.out.println("Resetting Fields");
    }

    /**
     * Formats the String to be pretty when displayed by keeping the same number
     * of digits.
     *
     * @param time The String to be prettified.
     * @return The prettified String.
     */
    public static String fixAutoTime(String time) {
        if (time.length() < 3) {
            return fixAutoTime("0".concat(time));
        }
        return time;
    }

    /**
     * Method to get everything ready to play a new match. Resets the thread,
     * time, and robots. Also sets the tele and auto times to the previous
     * matches times.
     *
     * @param autoTime The desired autonomous time.
     * @param teleTime The desired teleop time.
     */
    public static void renewGameThread(String autoTime, String teleTime) {
        GovernThread game = GovernThread.getInstance();
        if (game != null) {
            game.stopMatch();
        }
        game = GovernThread.getNewInstance(Main.newUI,
                FieldAndRobots.getInstance());
        game.setAutoTime(Integer.parseInt(autoTime));
        game.setTeleTime(Integer.parseInt(teleTime));
        game.setAllRobotsToBypassed(false);
        game.resetAllRobots();
       
    }

    /**
     * Essentially connects the given button to the O!FMS background code as the
     * "Start Match" button. It does so to enable a developer to be able to use
     * multiple UIs and just reset the indicator depending on which UI they wish
     * to use.
     *
     * @param matchButton The button to become the "match start" button.
     */
    public void setMatchButton(JButton matchButton) {
        if (beginMatchButton != null) {
            ActionListener[] listeners = beginMatchButton.getActionListeners();
            for (ActionListener lis : listeners) {
                beginMatchButton.removeActionListener(lis);
            }
        }
        beginMatchButton = matchButton;
        beginMatchButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (FieldAndRobots.getInstance().areRobotsNotESTOPPED()) {
                    beginMatchButton.setEnabled(false);

                    resetButton.setEnabled(false);

                    autonomousTime.setEditable(false);
                    teleoperatedTime.setEditable(false);

                    switcherButton.setEnabled(false);

                    stopMatchButton.setEnabled(true);
                    PLC_Sender.getInstance().updatePLC_TeamNum(true);

                    SetAllTeamFieldsEditable(false);

                    GovernThread game = GovernThread.getInstance();
                    if (game != null) {
                        game.start();
                    }
                } else {
                    if (Main.getInstance().getMainFrame() != null) {
                        JOptionPane.showMessageDialog(Main.getInstance().getMainFrame(),
                                "Error - a robot is still ESTOPPED. You must Reset the match(to un-ESTOP them) before continuing.",
                                "Robot ESTOPPED Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Essentially connects the given test field to the O!FMS background code as
     * the new match time text field. It does so to enable a developer to be
     * able to use multiple UIs and just reset the indicator depending on which
     * UI they wish to use.
     *
     * @param time The text field to become the match countdown time field.
     */
    public void setMatchTimeField(JTextField time) {
        runningMatchTime = time;
    }

    /**
     * Essentially connects the given text fields to the O!FMS background code
     * as the autonomous and teleop time setter text fields. It does so to
     * enable a developer to be able to use multiple UIs and just reset the
     * indicator depending on which UI they wish to use.
     *
     * @param auton The text field to be set as the autonomous time text field.
     * @param tele The text field to be set as the teleop time text field.
     */
    public void setTimeSetters(JTextField auton, JTextField tele) {
        autonomousTime = auton;
        autonomousTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GovernThread game = GovernThread.getInstance();
                if (game != null) {
                    game.setAutoTime(Integer.parseInt(autonomousTime.getText()));
                } else {
                    System.out.println("GAME IS NULL");
                }
            }
        });
        autonomousTime.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                GovernThread game = GovernThread.getInstance();
                if (game != null) {
                    game.setAutoTime(Integer.parseInt(autonomousTime.getText()));
                } else {
                    System.out.println("GAME IS NULL");
                }
            }
        });

        teleoperatedTime = tele;
        teleoperatedTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GovernThread game = GovernThread.getInstance();
                if (game != null) {
                    game.setTeleTime(Integer.parseInt(teleoperatedTime.getText()));
                } else {
                    System.out.println("GAME IS NULL");
                }
            }
        });
        teleoperatedTime.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                GovernThread game = GovernThread.getInstance();
                if (game != null) {
                    game.setTeleTime(Integer.parseInt(teleoperatedTime.getText()));
                } else {
                    System.out.println("GAME IS NULL");
                }
            }
        });
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Team Setup">
    // <editor-fold defaultstate="collapsed" desc="Setup Blue Indicators">
    /**
     * Essentially connects the given UI elements to the background code that
     * O!FMS runs on for the team in the first station on the Blue alliance.
     *
     * @param num The team number field.
     * @param bypass The checkbox to bypass(aka disable) a robot.
     * @param voltage The text field to show the battery voltage of the
     * connected robot.
     * @param robCom The indicator used to show if the communication to the
     * robot is alive.
     * @param dsCom The indicator used to show if the communication to the DS is
     * alive.
     */
    public void setBlue1(JTextField num, JCheckBox bypass, JTextField voltage, JLabel robCom, JLabel dsCom) {
        blueTeamNumber1 = num;
        blueTeamNumber1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTeamInfo_Blue1();
            }
        });
        blueTeamNumber1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTeamInfo_Blue1();
            }
        });
        blueTeamNumber1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (blueTeamNumber1.isEditable()) {
                    blueTeamNumber1.setBackground(EDITING);
                }
            }
        });
        blueTeamBypass1 = bypass;
        blueTeamBypass1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color temp;
                if (blueTeamBypass1.isSelected()) {
                    temp = BYPASSED;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                            FieldAndRobots.ONE, FieldAndRobots.SpecialState.BYPASS);
                } else {
                    temp = NOT_READY;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                            FieldAndRobots.ONE, FieldAndRobots.SpecialState.UNBYPASS);
                }
                robotComStatusBlueTeam1.setBackground(temp);
                dsComStatusBlueTeam1.setBackground(temp);
            }
        });
        //
        voltageBlueTeam1 = voltage; // Initializing is good enough
        robotComStatusBlueTeam1 = robCom; // Initializing is good enough
        dsComStatusBlueTeam1 = dsCom; // Initializing is good enough
    }

    /**
     * Essentially connects the given UI elements to the background code that
     * O!FMS runs on for the team in the second station on the Blue alliance.
     *
     * @param num The team number field.
     * @param bypass The checkbox to bypass(aka disable) a robot.
     * @param voltage The text field to show the battery voltage of the
     * connected robot.
     * @param robCom The indicator used to show if the communication to the
     * robot is alive.
     * @param dsCom The indicator used to show if the communication to the DS is
     * alive.
     */
    public void setBlue2(JTextField num, JCheckBox bypass, JTextField voltage, JLabel robCom, JLabel dsCom) {
        blueTeamNumber2 = num;
        blueTeamNumber2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTeamInfo_Blue2();
            }
        });
        blueTeamNumber2.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTeamInfo_Blue2();
            }
        });
        blueTeamNumber2.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (blueTeamNumber2.isEditable()) {
                    blueTeamNumber2.setBackground(EDITING);
                }
            }
        });
        blueTeamBypass2 = bypass;
        blueTeamBypass2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color temp;
                if (blueTeamBypass2.isSelected()) {
                    temp = BYPASSED;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                            FieldAndRobots.TWO, FieldAndRobots.SpecialState.BYPASS);
                } else {
                    temp = NOT_READY;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                            FieldAndRobots.TWO, FieldAndRobots.SpecialState.UNBYPASS);
                }
                robotComStatusBlueTeam2.setBackground(temp);
                dsComStatusBlueTeam2.setBackground(temp);
            }
        });
        //
        voltageBlueTeam2 = voltage; // Initializing is good enough
        robotComStatusBlueTeam2 = robCom; // Initializing is good enough
        dsComStatusBlueTeam2 = dsCom; // Initializing is good enough
    }

    /**
     * Essentially connects the given UI elements to the background code that
     * O!FMS runs on for the team in the third station on the Blue alliance.
     *
     * @param num The team number field.
     * @param bypass The checkbox to bypass(aka disable) a robot.
     * @param voltage The text field to show the battery voltage of the
     * connected robot.
     * @param robCom The indicator used to show if the communication to the
     * robot is alive.
     * @param dsCom The indicator used to show if the communication to the DS is
     * alive.
     */
    public void setBlue3(JTextField num, JCheckBox bypass, JTextField voltage, JLabel robCom, JLabel dsCom) {
        blueTeamNumber3 = num;
        blueTeamNumber3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTeamInfo_Blue3();
            }
        });
        blueTeamNumber3.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTeamInfo_Blue3();
            }
        });
        blueTeamNumber3.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (blueTeamNumber3.isEditable()) {
                    blueTeamNumber3.setBackground(EDITING);
                }
            }
        });
        blueTeamBypass3 = bypass;
        blueTeamBypass3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color temp;
                if (blueTeamBypass3.isSelected()) {
                    temp = BYPASSED;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                            FieldAndRobots.THREE, FieldAndRobots.SpecialState.BYPASS);
                } else {
                    temp = NOT_READY;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                            FieldAndRobots.THREE, FieldAndRobots.SpecialState.UNBYPASS);
                }
                robotComStatusBlueTeam3.setBackground(temp);
                dsComStatusBlueTeam3.setBackground(temp);
            }
        });
        //
        voltageBlueTeam3 = voltage; // Initializing is good enough
        robotComStatusBlueTeam3 = robCom; // Initializing is good enough
        dsComStatusBlueTeam3 = dsCom; // Initializing is good enough
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setup Red Indicators">
    /**
     * Essentially connects the given UI elements to the background code that
     * O!FMS runs on for the team in the first station on the Red alliance.
     *
     * @param num The team number field.
     * @param bypass The checkbox to bypass(aka disable) a robot.
     * @param voltage The text field to show the battery voltage of the
     * connected robot.
     * @param robCom The indicator used to show if the communication to the
     * robot is alive.
     * @param dsCom The indicator used to show if the communication to the DS is
     * alive.
     */
    public void setRed1(JTextField num, JCheckBox bypass, JTextField voltage, JLabel robCom, JLabel dsCom) {
        redTeamNumber1 = num;
        redTeamNumber1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTeamInfo_Red1();
            }
        });
        redTeamNumber1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTeamInfo_Red1();
            }
        });
        redTeamNumber1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (redTeamNumber1.isEditable()) {
                    redTeamNumber1.setBackground(EDITING);
                }
            }
        });
        redTeamBypass1 = bypass;
        redTeamBypass1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color temp;
                if (redTeamBypass1.isSelected()) {
                    temp = BYPASSED;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                            FieldAndRobots.ONE, FieldAndRobots.SpecialState.BYPASS);
                } else {
                    temp = NOT_READY;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                            FieldAndRobots.ONE, FieldAndRobots.SpecialState.UNBYPASS);
                }
                robotComStatusRedTeam1.setBackground(temp);
                dsComStatusRedTeam1.setBackground(temp);
            }
        });
        //
        voltageRedTeam1 = voltage; // Initializing is good enough
        robotComStatusRedTeam1 = robCom; // Initializing is good enough
        dsComStatusRedTeam1 = dsCom; // Initializing is good enough
    }

    /**
     * Essentially connects the given UI elements to the background code that
     * O!FMS runs on for the team in the second station on the Red alliance.
     *
     * @param num The team number field.
     * @param bypass The checkbox to bypass(aka disable) a robot.
     * @param voltage The text field to show the battery voltage of the
     * connected robot.
     * @param robCom The indicator used to show if the communication to the
     * robot is alive.
     * @param dsCom The indicator used to show if the communication to the DS is
     * alive.
     */
    public void setRed2(JTextField num, JCheckBox bypass, JTextField voltage, JLabel robCom, JLabel dsCom) {
        redTeamNumber2 = num;
        redTeamNumber2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTeamInfo_Red2();
            }
        });
        redTeamNumber2.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTeamInfo_Red2();
            }
        });
        redTeamNumber2.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (redTeamNumber2.isEditable()) {
                    redTeamNumber2.setBackground(EDITING);
                }
            }
        });
        redTeamBypass2 = bypass;
        redTeamBypass2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color temp;
                if (redTeamBypass2.isSelected()) {
                    temp = BYPASSED;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                            FieldAndRobots.TWO, FieldAndRobots.SpecialState.BYPASS);
                } else {
                    temp = NOT_READY;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                            FieldAndRobots.TWO, FieldAndRobots.SpecialState.UNBYPASS);
                }
                robotComStatusRedTeam2.setBackground(temp);
                dsComStatusRedTeam2.setBackground(temp);
            }
        });
        //
        voltageRedTeam2 = voltage; // Initializing is good enough
        robotComStatusRedTeam2 = robCom; // Initializing is good enough
        dsComStatusRedTeam2 = dsCom; // Initializing is good enough
    }

    /**
     * Essentially connects the given UI elements to the background code that
     * O!FMS runs on for the team in the third station on the Red alliance.
     *
     * @param num The team number field.
     * @param bypass The checkbox to bypass(aka disable) a robot.
     * @param voltage The text field to show the battery voltage of the
     * connected robot.
     * @param robCom The indicator used to show if the communication to the
     * robot is alive.
     * @param dsCom The indicator used to show if the communication to the DS is
     * alive.
     */
    public void setRed3(JTextField num, JCheckBox bypass, JTextField voltage, JLabel robCom, JLabel dsCom) {
        redTeamNumber3 = num;
        redTeamNumber3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setTeamInfo_Red3();
            }
        });
        redTeamNumber3.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTeamInfo_Red3();
            }
        });
        redTeamNumber3.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (redTeamNumber3.isEditable()) {
                    redTeamNumber3.setBackground(EDITING);
                }
            }
        });
        redTeamBypass3 = bypass;
        redTeamBypass3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color temp;
                if (redTeamBypass3.isSelected()) {
                    temp = BYPASSED;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                            FieldAndRobots.THREE, FieldAndRobots.SpecialState.BYPASS);
                } else {
                    temp = NOT_READY;
                    FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                            FieldAndRobots.THREE, FieldAndRobots.SpecialState.UNBYPASS);
                }
                robotComStatusRedTeam3.setBackground(temp);
                dsComStatusRedTeam3.setBackground(temp);
            }
        });
        //
        voltageRedTeam3 = voltage; // Initializing is good enough
        robotComStatusRedTeam3 = robCom; // Initializing is good enough
        dsComStatusRedTeam3 = dsCom; // Initializing is good enough
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Team Info">
    /**
     * Updates team information for the red alliance, team #1, based on current
     * UI control values.
     */
    private void setTeamInfo_Red1() {
        redTeamNumber1.setText(fixNumTeam(redTeamNumber1.getText(), "Red 1"));

        redTeamNumber1.setBackground(Color.WHITE);

        FieldAndRobots.getInstance().setTeamNumber(FieldAndRobots.RED,
                FieldAndRobots.ONE, Integer.parseInt(redTeamNumber1.getText()));
        FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                FieldAndRobots.ONE, FieldAndRobots.SpecialState.ZERO_BATTERY);

        if (!Main.isSimpleMode()) {
            PLC_Sender.getInstance().updatePLC_TeamNum(true);
        }
    }

    /**
     * Updates team information for the red alliance, team #2, based on current
     * UI control values.
     */
    private void setTeamInfo_Red2() {
        redTeamNumber2.setText(fixNumTeam(redTeamNumber2.getText(), "Red 2"));

        redTeamNumber2.setBackground(Color.WHITE);

        FieldAndRobots.getInstance().setTeamNumber(FieldAndRobots.RED, FieldAndRobots.TWO,
                Integer.parseInt(redTeamNumber2.getText()));
        FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                FieldAndRobots.TWO, FieldAndRobots.SpecialState.ZERO_BATTERY);

        if (!Main.isSimpleMode()) {
            PLC_Sender.getInstance().updatePLC_TeamNum(true);
        }
    }

    /**
     * Updates team information for the red alliance, team #3, based on current
     * UI control values.
     */
    private void setTeamInfo_Red3() {
        redTeamNumber3.setText(fixNumTeam(redTeamNumber3.getText(), "Red 3"));

        redTeamNumber3.setBackground(Color.WHITE);

        FieldAndRobots.getInstance().setTeamNumber(FieldAndRobots.RED, FieldAndRobots.THREE,
                Integer.parseInt(redTeamNumber3.getText()));
        FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.RED,
                FieldAndRobots.THREE, FieldAndRobots.SpecialState.ZERO_BATTERY);

        if (!Main.isSimpleMode()) {
            PLC_Sender.getInstance().updatePLC_TeamNum(true);
        }
    }

    /**
     * Updates team information for the blue alliance, team #1, based on current
     * UI control values.
     */
    private void setTeamInfo_Blue1() {
        blueTeamNumber1.setText(fixNumTeam(blueTeamNumber1.getText(), "Blue 1"));

        blueTeamNumber1.setBackground(Color.WHITE);

        FieldAndRobots.getInstance().setTeamNumber(FieldAndRobots.BLUE, FieldAndRobots.ONE,
                Integer.parseInt(blueTeamNumber1.getText()));
        FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                FieldAndRobots.ONE, FieldAndRobots.SpecialState.ZERO_BATTERY);

        if (!Main.isSimpleMode()) {
            PLC_Sender.getInstance().updatePLC_TeamNum(true);
        }
    }

    /**
     * Updates team information for the blue alliance, team #2, based on current
     * UI control values.
     */
    private void setTeamInfo_Blue2() {
        blueTeamNumber2.setText(fixNumTeam(blueTeamNumber2.getText(), "Blue 2"));

        blueTeamNumber2.setBackground(Color.WHITE);

        FieldAndRobots.getInstance().setTeamNumber(FieldAndRobots.BLUE, FieldAndRobots.TWO,
                Integer.parseInt(blueTeamNumber2.getText()));
        FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                FieldAndRobots.TWO, FieldAndRobots.SpecialState.ZERO_BATTERY);

        if (!Main.isSimpleMode()) {
            PLC_Sender.getInstance().updatePLC_TeamNum(true);
        }
    }

    /**
     * Updates team information for the blue alliance, team #3, based on current
     * UI control values.
     */
    private void setTeamInfo_Blue3() {
        blueTeamNumber3.setText(fixNumTeam(blueTeamNumber3.getText(), "Blue 3"));

        blueTeamNumber3.setBackground(Color.WHITE);

        FieldAndRobots.getInstance().setTeamNumber(FieldAndRobots.BLUE, FieldAndRobots.THREE,
                Integer.parseInt(blueTeamNumber3.getText()));
        FieldAndRobots.getInstance().actOnRobot(FieldAndRobots.BLUE,
                FieldAndRobots.THREE, FieldAndRobots.SpecialState.ZERO_BATTERY);

        if (!Main.isSimpleMode()) {
            PLC_Sender.getInstance().updatePLC_TeamNum(true);
        }
    }

    /**
     * Takes a string, presumes it is a number, and prefixes "0" to the value if
     * it is less than four characters - intended to make the UI prettier and
     * prevent automatic resizing.
     *
     * @param input The string to modify.
     * @param team A debugging String used to identify which team caused an
     * error.
     * @return the original value prefixed with "0" if necessary.
     */
    public String fixNumTeam(String input, String team) {
        if (input.length() < 4) {
            //System.out.println("Shorter than 4 digits");
            return fixNumTeam("0".concat(input), team);
        } else if (input.length() > 4) {
            System.out.println("Err - Longer than 4");
            //JOptionPane.showMessageDialog(tester,
            //      team + " team number greater than 4 digits",
            //    "Team number error",
            //  JOptionPane.ERROR_MESSAGE);
            input = input.substring(0, 4);
        }
        return input;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get/Set Blue and Red Numbers">
    /**
     * Gets the Blue and Red team numbers from the current UI team number
     * fields.
     *
     * @return A two dimensional String array of the current blue then red team
     * numbers.
     */
    public String[][] getBlueRedNumbers() {
        String[] blue = {blueTeamNumber1.getText(), blueTeamNumber2.getText(), blueTeamNumber3.getText()};
        String[] red = {redTeamNumber1.getText(), redTeamNumber2.getText(), redTeamNumber3.getText()};

        String[][] robots = {blue, red};
        return robots;
    }

    /**
     * Sets the inputted team numbers to the UI and background code.
     *
     * @param teams The two dimensional String array of blue then red teams
     * following the structure ((B1, B2, B3),(R1, R2, R3)).
     */
    public void setBlueRedNumbers(String[][] teams) {
        blueTeamNumber1.setText(teams[0][0]);
        blueTeamNumber2.setText(teams[0][1]);
        blueTeamNumber3.setText(teams[0][2]);

        redTeamNumber1.setText(teams[1][0]);
        redTeamNumber2.setText(teams[1][1]);
        redTeamNumber3.setText(teams[1][2]);

        setTeamInfo_Red1();
        setTeamInfo_Red2();
        setTeamInfo_Red3();

        setTeamInfo_Blue1();
        setTeamInfo_Blue2();
        setTeamInfo_Blue3();
    }
    //</editor-fold>
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Progress Bar, Team Comm, and Battery">
    /**
     * Updates the progress bar value.
     *
     * @param percentTime the percentage of time (as measured against the total
     * match time) which has elapsed in the match.
     */
    public void updateProBar(double percentTime) {
        double total = matchProgressBar.getMaximum() - matchProgressBar.getMinimum();
        int calc = (int) (percentTime * total);
        matchProgressBar.setValue(calc);
    }

    /**
     * Resets the progress bar displayed value to its minimum.
     */
    private void resetProBar() {
        matchProgressBar.setValue(matchProgressBar.getMinimum());
    }

    /**
     * Changes the foreground color of the progress bar.
     *
     * @param color the color to use.
     */
    public void changeProBarColor(Color color) {
        matchProgressBar.setForeground(color);
    }

    /**
     * Displays the given values as status for the team specified by the given
     * alliance and station number.
     *
     * @param alliance The alliance of the robot whose indicators are to be
     * changed.
     * @param station The station position of the robot whose indicators are to
     * be changed.
     * @param isFMSAlive Whether or not the team's Driver Station to FMS
     * communications are alive.
     * @param isRobotAlive Whether or not the team's robot communications are
     * alive.
     * @param isTeamBypassed Whether or not the indicated team is bypassed.
     * @param isESTOPPED Whether or not the indicated team is ESTOPPED.
     */
    public void setCommStatus(int alliance, int station, boolean isFMSAlive,
            boolean isRobotAlive, boolean isTeamBypassed, boolean isESTOPPED) {
        //We default to not being ready.
        Color robCom = NOT_READY;
        Color dsCom = NOT_READY;
        //System.out.println("setting comm status...");
        if (isESTOPPED) {
            robCom = ESTOPPED;
            dsCom = ESTOPPED;
        } else if (!isTeamBypassed) {
            if (isRobotAlive) {
                robCom = READY;
            }
            if (isFMSAlive) {
                dsCom = READY;
            }
        } else if (isTeamBypassed) {
            robCom = BYPASSED;
            dsCom = BYPASSED;
        }

        //Decision off of the alliance and position so we set the right control
        // values.
        if (alliance == FieldAndRobots.RED) {
            if (station == FieldAndRobots.ONE) {
                robotComStatusRedTeam1.setBackground(robCom);
                dsComStatusRedTeam1.setBackground(dsCom);
            } else if (station == FieldAndRobots.TWO) {
                robotComStatusRedTeam2.setBackground(robCom);
                dsComStatusRedTeam2.setBackground(dsCom);
            } else if (station == FieldAndRobots.THREE) {
                robotComStatusRedTeam3.setBackground(robCom);
                dsComStatusRedTeam3.setBackground(dsCom);
            } else {
                System.out.println("ERROR - SET COM 1");
            }
        } else if (alliance == FieldAndRobots.BLUE) {
            if (station == FieldAndRobots.ONE) {
                robotComStatusBlueTeam1.setBackground(robCom);
                dsComStatusBlueTeam1.setBackground(dsCom);
            } else if (station == FieldAndRobots.TWO) {
                robotComStatusBlueTeam2.setBackground(robCom);
                dsComStatusBlueTeam2.setBackground(dsCom);
            } else if (station == FieldAndRobots.THREE) {
                robotComStatusBlueTeam3.setBackground(robCom);
                dsComStatusBlueTeam3.setBackground(dsCom);
            } else {
                System.out.println("ERROR - SET COM 2");
            }
        } else {
            System.out.println("ERROR - SET COM 3");
        }
    }

    /**
     * Sets the displayed battery voltage value for a given team.
     *
     * @param alliance the alliance of the team. MUST BE either
     * FieldAndRobots.RED or FieldAndRobots.BLUE for this method to do anything.
     * @param stationNumber the station number of the team. MUST BE either
     * FieldAndRobots.ONE, FieldAndRobots.TWO or FieldAndRobots.THREE in order
     * for this method to do anything.
     * @param voltage the voltage value to display.
     */
    public void setBatteryVisual(int alliance, int stationNumber, double voltage) {
        if (alliance == FieldAndRobots.RED) { //RED
            if (stationNumber == FieldAndRobots.ONE) {
                voltageRedTeam1.setText(fixBattery(voltage));
            } else if (stationNumber == FieldAndRobots.TWO) {
                voltageRedTeam2.setText(fixBattery(voltage));
            } else if (stationNumber == FieldAndRobots.THREE) {
                voltageRedTeam3.setText(fixBattery(voltage));
            }
        } else if (alliance == FieldAndRobots.BLUE) { //BLUE
            if (stationNumber == FieldAndRobots.ONE) {
                voltageBlueTeam1.setText(fixBattery(voltage));
            } else if (stationNumber == FieldAndRobots.TWO) {
                voltageBlueTeam2.setText(fixBattery(voltage));
            } else if (stationNumber == FieldAndRobots.THREE) {
                voltageBlueTeam3.setText(fixBattery(voltage));
            }
        }
    }

    /**
     * Internal helper method to fix up battery voltage values and format them
     * so they look pretty on the UI.
     *
     * @param voltage the value to beautify.
     * @return the pretty pretty string version of the input value.
     */
    private String fixBattery(double voltage) {
        String volt = "" + voltage;
        String[] volts = volt.split("\\.");
        String first = volts[0];
        String last = volts[1];
        if (volts[0].length() < 2) {
            first = "0" + volts[0];
        }
        if (volts[1].length() < 2) {
            last = volts[1] + "0";
        }
        return first + "." + last;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Enabled/Selected">
    /**
     * Makes all team number fields editable or not.
     *
     * @param editable Whether or not to make all of the boxes editable.
     */
    public void SetAllTeamFieldsEditable(boolean editable) {
        blueTeamNumber1.setEditable(editable);
        blueTeamNumber2.setEditable(editable);
        blueTeamNumber3.setEditable(editable);
        redTeamNumber1.setEditable(editable);
        redTeamNumber2.setEditable(editable);
        redTeamNumber3.setEditable(editable);
    }

    /**
     * Makes all bypass boxes selected or not.
     *
     * @param selected Whether or not to make all of the boxes selected.
     */
    public void SetAllBypassBoxesSelected(boolean selected) {
        redTeamBypass1.setSelected(selected);
        redTeamBypass2.setSelected(selected);
        redTeamBypass3.setSelected(selected);
        blueTeamBypass1.setSelected(selected);
        blueTeamBypass2.setSelected(selected);
        blueTeamBypass3.setSelected(selected);
    }

    /**
     * Makes all bypass boxes enabled or not.
     *
     * @param enabled Whether or not to make all of the boxes enabled.
     */
    public void SetAllBypassBoxesEnabled(boolean enabled) {
        redTeamBypass1.setEnabled(enabled);
        redTeamBypass2.setEnabled(enabled);
        redTeamBypass3.setEnabled(enabled);
        blueTeamBypass1.setEnabled(enabled);
        blueTeamBypass2.setEnabled(enabled);
        blueTeamBypass3.setEnabled(enabled);
    }

    /**
     * Sets the reset button enabled(able to interact with), or not.
     *
     * @param enabled If the reset button should be enabled.
     */
    public void setResetButtonEnabled(boolean enabled) {
        resetButton.setEnabled(enabled);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Check Field Readiness">
    /**
     * Determines if the field is ready, meaning are all teams on both the red
     * and blue alliances reporting readiness. Presumes use of a default
     * FieldAndRobots instance. Sets control states and values accordingly.
     */
    public void checkIfFieldReady() {
        boolean redReady = isRedReady();
        boolean blueReady = isBlueReady();
        if (redReady && blueReady) {
            fullFieldReady.setBackground(READY);
            GovernThread game = GovernThread.getInstance();
            if (game != null) {
                beginMatchButton.setEnabled(game.isNewMatch());
            } else {
                fullFieldReady.setBackground(NOT_READY);
                beginMatchButton.setEnabled(false);
            }
        } else {
            fullFieldReady.setBackground(NOT_READY);
            beginMatchButton.setEnabled(false);
        }
        if (!Main.isSimpleMode()) {
            if (redReady != prevRedReady || blueReady != prevBlueReady) {
                PLC_Sender.getInstance().updatePLC_Lights(true);
            }
        }
        prevRedReady = redReady;
        prevBlueReady = blueReady;
    }

    /**
     * Determines if the red alliance is ready and sets UI values and states
     * accordingly. Presumes use of a default FieldAndRobots instance.
     *
     * @return the readiness of all teams on the red alliance.
     */
    public boolean isRedReady() {
        if (FieldAndRobots.getInstance().isAllianceReady(FieldAndRobots.RED)) {
            redSideReady.setBackground(READY);
            return true;
        } else {
            redSideReady.setBackground(NOT_READY);
            return false;
        }
    }

    /**
     * Determines if the blue alliance is ready and sets UI values and states
     * accordingly. Presumes use of a default FieldAndRobots instance.
     *
     * @return the readiness of all teams on the blue alliance.
     */
    public boolean isBlueReady() {
        if (FieldAndRobots.getInstance().isAllianceReady(FieldAndRobots.BLUE)) {
            blueSideReady.setBackground(READY);
            return true;
        } else {
            blueSideReady.setBackground(NOT_READY);
            return false;
        }
    }
    //</editor-fold>

    /**
     * Sets the displayed time of the match to the FMS_UI and updates the PLC
     * time.
     *
     * @param text The time to be displayed.
     */
    public void setMatchTime(String text) {
        runningMatchTime.setText(text);
        if (!Main.isSimpleMode()) {
            PLC_Sender.getInstance().updatePLC_Time(true);
        }
    }

    /**
     * Pass-through method made to force the PLC_Sender to update all of the
     * light values(team stack lights and main field stack lights).
     */
    public static void forceLightUpdate() {
        PLC_Sender.getInstance().updatePLC_Lights(true);
    }
}
