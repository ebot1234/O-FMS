package UI;

import java.awt.Color;

/**
 * Class used to hold the various colors we use in other places.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class ColorPallate {

    /**
     * Color to use when indicating a team is in a ready state.
     */
    public static final Color READY = new Color(0, 204, 0);
    /**
     * Color to use when indicating a team is not ready.
     */
    public static final Color NOT_READY = Color.GRAY;
    /**
     * Color to use when indicating a team has been bypassed.
     */
    public static final Color BYPASSED = Color.ORANGE;
    /**
     * Color to use when indicating a team has been ESTOPPED.
     */
    public static final Color ESTOPPED_COM = Color.WHITE;
    /**
     * Color to use when indicating an ESTOP.
     */
    public static final Color ESTOPPED_AMB = Color.ORANGE;
    /**
     * Alex's color gray
     */
    public static final Color ES_GRAY = Color.GRAY;
    /**
     * Color to use when indicating a stations team number is being edited.
     */
    public static final Color EDITING = new Color(255, 153, 153);
}
