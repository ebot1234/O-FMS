package OFMS;
import Game.Seesaw;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Game.Scoring;

/**
 * This class represents an FRC playing field and all the teams (robots) upon
 * that field. This class also acts as a filter to manage the field of robots.
 *
 * For standard FRC usage, one can call the getNewDefaultInstance method to
 * create two alliances with three teams each. In this context, the RED and BLUE
 * constants may be used to identify each alliance, and the ONE TWO and THREE
 * constants identify the teams on each. However, for events or configurations
 * with more than three alliances or a number of teams other than three, the
 * getNewInstance(int,int):FieldAndRobots method may be used.
 *
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class FieldAndRobots {

    public static PowerUps powerups;

    //<editor-fold defaultstate="collapsed" desc="Variables">
    /**
     * Holds the alliances and their teams. Default size is two alliances,
     * represented by the first array, each with three teams.
     */
    public Team[][] teams;
            
     
    /**
     * Used to identify the red alliance in a default instance.
     */
    public static final int RED = 0;
    /**
     * Used to identify the blue alliance in a default instance.
     */
    public static final int BLUE = 1;
    /**
     * Used to identify the first team in an alliance.
     */
    public static final int ONE = 0;
    /**
     * Used to identify the second team in an alliance.
     */
    public static final int TWO = 1;
    /**
     * Used to identify the third team in an alliance.
     */
    public static final int THREE = 2;
    /**
     * The sole instance of this class.
     */
    private static FieldAndRobots _instance;
    //Red Vault Score
    int RedVaultScore = 0;
    //Blue Vault Score
    int BlueVaultScore = 0;
    int RedScore = 0;
    int BlueScore = 0;
    int RedOwnershipScore = 0;
    int BlueOwnershipScore = 0;
    
    
    Random random = new Random();
    
    
   
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Creation And Instancing FAR">
    /**
     * Creates an instance with the indicated number of alliances, where each
     * alliance has the indicated number of teams. All teams are initialized
     * with a sequential team number, with the first team on the first alliance
     * being assigned a team number of "0", the next "1", and so on.
     *
     * @param allianceCount the number of alliances to create
     * @param teamsPerAlliance the number of teams per alliance
     */
    private FieldAndRobots(int allianceCount, int teamsPerAlliance) {
        teams = new Team[allianceCount][teamsPerAlliance];
        System.out.println("FieldAndRobots Contructor");
        System.out.println("Initializing Teams...");
        int teamId = 0;
        for (int allianceNbr = 0; allianceNbr < allianceCount; allianceNbr++) {
            for (int team = 0; team < teamsPerAlliance; team++) {
                teams[allianceNbr][team] = new Team(teamId, allianceNbr, team);
                System.out.println("Alliance " + allianceNbr + " Team " + team
                        + " Initialized");
                teamId++;
            }
        }
    }

    /**
     * Use this method to create and obtain a new instance with a variant number
     * of alliances and/or teams (2 alliances with 3 teams each is the
     * standard). This method will overwrite/destroy any existing instance, so
     * don't use it more than once within a given application without taking
     * that into account.
     *
     * @param allianceCount the number of alliances to create
     * @param teamsPerAlliance the number of teams per alliance
     * @return the created instance
     */
    public static FieldAndRobots getNewInstance(int allianceCount,
            int teamsPerAlliance) {
        if (_instance != null) {
            System.out.println("Asked for a new instance of FAR, had one already, "
                    + "throwing it away.");
        }
        if (allianceCount == 0) {
            System.out.println("Defaults triggered: using 2 alliances with "
                    + "three teams each.");
            allianceCount = 2;
            teamsPerAlliance = 3;
        }
        _instance = new FieldAndRobots(allianceCount, teamsPerAlliance);
        return _instance;
    }

    /**
     * Use this method to create and obtain a new instance with the standard
     * number of alliances and teams per alliance (2 alliances, 3 teams each).
     *
     * @return an instance with 2 alliances each with 3 teams
     */
    public static FieldAndRobots getNewDefaultInstance() {
        return getNewInstance(0, 0);
    }

    /**
     * Use this to get the current instance of FieldAndRobots.
     *
     * @return The instance of FieldAndRobots
     */
    public static FieldAndRobots getInstance() {
        if (_instance != null) {
            return _instance;
        }
        return getNewDefaultInstance();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Control All Teams">
    /**
     * Starts FMS updates for all teams managed by and known to this instance.
     */
    public void startFMSUpdatesForAllTeams() {
        for (Team[] alliance : teams) {
            for (Team team : alliance) {
                if (team != null) {
                    team.startFMSUpdates();
                }
            }
        }
        System.out.println("Finished Starting Team FMS Updates...");
    }

    /**
     * Sets the mode and enabled state for all teams managed by and known to
     * this instance.
     *
     * @param mode GovernThread.AUTO_MODE or GovernThread.TELE_MODE.
     * @param enabled whether or not all robots should be set to an enabled
     * state.
     */
    public void setAllRobotStates(int mode, boolean enabled) {
        for (Team[] alliance : teams) {
            for (Team team : alliance) {
                if (team != null) {
                    team.setRobotState(mode, enabled);
                }
            }
        }
    }
    public void setGameData(){
         //For the generation of the game data
    {
        List<String> gameData = new ArrayList<>();
        //List of the Scale and Switch Vaules
        gameData.add("LLL");
        gameData.add("RRR");
        gameData.add("LRL");
        gameData.add("RLR");
        for(int i = 0; i<1; i++){
        getRandomItem(gameData);
    }
    }
    
    } 
    public void getRandomItem(List<String> gameData) {
        //Size of the list is 5
       int index = random.nextInt(gameData.size());
       System.out.println("" + gameData.get(index));
    }
    /**
     * Sets the bypassed state for all robots managed by and known to this
     * instance.
     *
     * @param bypassState whether or not all robots should be set to a bypassed
     * state.
     */
    public void setBypassForAllRobots(boolean bypassState) {
        for (Team[] alliance : teams) {
            for (Team team : alliance) {
                if (team != null) {
                    team.setBypassed(bypassState);
                }
            }
        }
        System.out.println("Bypassing in FAR done...");
    }

    /**
     * Resets all teams managed by and known to this instance.
     */
    public void resetAllRobots() {
        for (Team[] alliance : teams) {
            for (Team team : alliance) {
                if (team != null) {
                    team.resetTeam();
                }
            }
        }
        System.out.println("Resetting in FAR done...");
    }

    /**
     * Get's the read and blue team numbers that Field And Robots has.
     *
     * @return An two dimensional String array of Red robots and Blue robots in
     * the format ((R1, R2, R3), (B1, B2, B3)).
     */
    public String[][] getRedBlueNumbers() {
        String[] red = {Main.layer.fixNumTeam(teams[0][0].getTeamNumber() + "", "Red 1 - FAR"),
            Main.layer.fixNumTeam(teams[0][1].getTeamNumber() + "", "Red 2 - FAR"),
            Main.layer.fixNumTeam(teams[0][2].getTeamNumber() + "", "Red 3 - FAR")};
        String[] blue = {Main.layer.fixNumTeam(teams[1][0].getTeamNumber() + "", "Blue 1 - FAR"),
            Main.layer.fixNumTeam(teams[1][1].getTeamNumber() + "", "Blue 2 - FAR"),
            Main.layer.fixNumTeam(teams[1][2].getTeamNumber() + "", "Blue 3 - FAR")};
        String[][] robots = {red, blue};
        return robots;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Control Specific Teams">
    /**
     * Sets the team number (for the FMS's use) of the team in the specified
     * alliance at the specified station or position. If an alliance or a
     * station number are specified which exceed the number of alliances or
     * teams per alliance, or if the team at the indicated location is null,
     * this method will do nothing.
     *
     * @param alliance The alliance type. If using a default instance, you can
     * (and should) use the RED and BLUE constants in this class to identify the
     * alliance.
     * @param stationNumber The station number of the team. If using a default
     * instance, you can (and should) use the ONE, TWO and THREE constants in
     * this class to identify the station number.
     * @param teamNumber the team number associated with the team on the
     * indicated alliance at the specified station.
     */
    public void setTeamNumber(int alliance, int stationNumber, int teamNumber) {
        if (verify(alliance, stationNumber)) {
            teams[alliance][stationNumber].setTeamNumber(teamNumber);
        } else {
            System.out.println("SetTeamNumber Error!");
        }
    }

    /**
     * Updates the battery voltage and current status of robot communications
     * for a given team. If the team number is not found among the teams managed
     * by this instance, this method does nothing.
     *
     * @param teamNumber the team number whose status should be updated.
     * @param batteryVoltage the current battery voltage of the team's robot
     * @param isRobotCommunicating whether or not the team's robot is currently
     * communicating with the system.
     */
    public void updateRobotStatus(int teamNumber, double batteryVoltage,
            boolean isRobotCommunicating) {
        //Look for the team; if we find it, update it and break out
        for (Team[] alliance : teams) {
            for (Team team : alliance) {
                if (team.getTeamNumber() == teamNumber) {
                    team.updateFromReceived(batteryVoltage, isRobotCommunicating);
                    return;
                }
            }
        }
    }

    /**
     * Checks to see if all robots on an alliance are ready for competition. If
     * a team is not ready it defaults to "not ready".
     *
     * @param allianceNumber the alliance number whose robots are to be queried.
     * @return false if any robot on the indicated alliance has a state other
     * than "ready".
     */
    public boolean isAllianceReady(int allianceNumber) {
        boolean allReady = true;
        if (allianceNumber < teams.length) {
            for (Team team : teams[allianceNumber]) {
                if (team != null) {
                    if (!team.isReady()) {
                        allReady = false;
                        break;
                    }
                }
            }

        }
        return allReady;
    }
    
   

    /**
     * A simple enumeration for all of the "Special States" we want to set a
     * robot to.
     */
    public enum SpecialState {

        ESTOP_ROBOT, ZERO_BATTERY, BYPASS, UNBYPASS;
    }
    
    public enum CubeNumbers
    {
        Cube_1, Cube_2, Cube_3, PLAYED, Cube_Zero, Force_PLAYED, Boost_PLAYED, Lev_PLAYED;
    }
    
    public enum PowerUps
    {
        Force_1, Levitate_2, Boost_2, Force_PLAYED, Boost_PLAYED, Lev_PLAYED, Force_2, Force_3, Levitate_1, Levitate_3, Boost_1, Boost_3;
    }
  
     public void RedOwnership(){
    long Ownership = System.currentTimeMillis() + 1000; // Current time + 1 second

        while(true){
            if(Ownership <= System.currentTimeMillis()){
                
                
                RedOwnershipScore++;
                Ownership += 1000;
            }
                try{ 
                Thread.sleep(5);
            }catch(Exception ex){
                
                System.err.println( ex.getMessage() );
            }
        }}
    public void BlueOwnership(){
    long Ownership = System.currentTimeMillis() + 1000; // Current time + 1 second

        while(true){
            if(Ownership <= System.currentTimeMillis()){
                
                BlueOwnershipScore++;
                Ownership += 1000;
            }
                try{ 
                Thread.sleep(5);
            }catch(Exception ex){
                
                System.err.println( ex.getMessage() );
            }
        }
    }
 
     public void RedBoost()
    {
         long Boost = System.currentTimeMillis() + 10000; // Current time + 10 seconds

        while(true){
            if(Boost <= System.currentTimeMillis()){
                
                 RedScore = RedVaultScore*2;
                Boost += 10000;
            }
                try{ 
                Thread.sleep(5); //Does this command for 10 seconds
            }catch(Exception ex){
                
                System.err.println( ex.getMessage() );
            }
        }
     }
     public void BlueBoost()
    {
         long Boost = System.currentTimeMillis() + 10000; // Current time + 10 seconds

        while(true){
            if(Boost <= System.currentTimeMillis()){
                
                BlueScore = BlueVaultScore*2;
                Boost += 10000;
            }
                try{ 
                Thread.sleep(10);//Does this command for 10 seconds
            }catch(Exception ex){
                
                System.err.println( ex.getMessage() );
            }
        }
     }
     public void redVaultForce(Scoring.PowerUps powerUps)
    {
        //Red force PowerUp
       
         if (Scoring.PowerUps.Force_1 == Scoring.PowerUps.Force_PLAYED)
        {
            RedScore = RedOwnershipScore*2;
        }
        
         if(Scoring.PowerUps.Force_2 == Scoring.PowerUps.Force_PLAYED)
         {
             RedScore = RedOwnershipScore*2;
             
         }
        if(Scoring.PowerUps.Force_3 == Scoring.PowerUps.Force_PLAYED)
        {
            RedScore = RedOwnershipScore*2;
            
        }
    }
        public void redVaultBoost(Scoring.PowerUps powerUps)
        {
            if(Scoring.PowerUps.Boost_1 == Scoring.PowerUps.Boost_PLAYED)
            {
                RedBoost();
            }
            if(Scoring.PowerUps.Boost_2 == Scoring.PowerUps.Boost_PLAYED)
            {
            
            }
            if(Scoring.PowerUps.Boost_1 == Scoring.PowerUps.Boost_PLAYED)
            {
                
            }
        }
        public void redVaultLev(Scoring.PowerUps powerUps)
        {
            if(Scoring.PowerUps.Levitate_1 == Scoring.PowerUps.Lev_PLAYED)
            {
            
            }
            if(Scoring.PowerUps.Levitate_2 == Scoring.PowerUps.Lev_PLAYED)
            {
            
            }
            if(Scoring.PowerUps.Levitate_3 == Scoring.PowerUps.Levitate_3)
            {
                
            }
        }

     
            
        
       /* //Red Boost PowerUp
        if(Scoring.CubeNumbers.Cube_1 == Scoring.CubeNumbers.Boost_PLAYED)
        {
           
            RedBoost();
        }
        if(Scoring.CubeNumbers.Cube_2 == Scoring.CubeNumbers.Boost_PLAYED)
        {
           
            RedBoost();
        }
        if(Scoring.CubeNumbers.Cube_3 == Scoring.CubeNumbers.Boost_PLAYED)
        {
            
            RedBoost();
        }
        //Red Levitate PowerUp
        if(Scoring.CubeNumbers.Cube_1 == Scoring.CubeNumbers.Lev_PLAYED)
        {
            RedScore = RedVaultScore+0;
        }
        if(Scoring.CubeNumbers.Cube_2 == Scoring.CubeNumbers.Lev_PLAYED)
        {
            RedScore = RedVaultScore+0;
        }
        if(Scoring.CubeNumbers.Cube_3 == Scoring.CubeNumbers.Lev_PLAYED)
        {
            RedScore = RedVaultScore+30;
        }
    }
    
    
    
    public void blueVault(Scoring.CubeNumbers numbers, Scoring.PowerUps powerups)
    {
        //For the Blue PowerUps when they are played
        //Blue force PowerUp 
         if (Scoring.CubeNumbers.Cube_1 == Scoring.CubeNumbers.Force_PLAYED)
        {
             BlueScore = BlueOwnershipScore*2;
        }
         if(Scoring.CubeNumbers.Cube_2 == Scoring.CubeNumbers.Force_PLAYED)
         {
             BlueScore = BlueOwnershipScore*2;
         }
        if(Scoring.CubeNumbers.Cube_3 == Scoring.CubeNumbers.Force_PLAYED)
        {
            BlueScore = BlueOwnershipScore*2;
        }
        
        //Blue Boost PowerUp
        if(Scoring.CubeNumbers.Cube_1 == Scoring.CubeNumbers.Boost_PLAYED)
        {
            BlueScore = BlueVaultScore+3;
            BlueBoost();
        }
        if(Scoring.CubeNumbers.Cube_2 == Scoring.CubeNumbers.Boost_PLAYED)
        {
            BlueScore = BlueVaultScore+3;
            BlueBoost();
        }
        if(Scoring.CubeNumbers.Cube_3 == Scoring.CubeNumbers.Boost_PLAYED)
        {
            BlueScore  = BlueVaultScore+3;
            BlueBoost();
        }
        //Blues Levitate PowerUp
        if(Scoring.CubeNumbers.Cube_1 == Scoring.CubeNumbers.Lev_PLAYED)
        {
            BlueScore = BlueVaultScore+0;
        }
        if(Scoring.CubeNumbers.Cube_2 == Scoring.CubeNumbers.Lev_PLAYED)
        {
            BlueScore = BlueVaultScore+0;
        }
        if(Scoring.CubeNumbers.Cube_3 == Scoring.CubeNumbers.Lev_PLAYED)
        {
            BlueScore = BlueVaultScore+30;
        }
    }
    
   
        
    
    
       
        
    
    /**
     * Sets a specific robot to a specific "Special State".
     *
     * @param alliance The alliance the team is on.
     * @param stationNumber The station the team is in.
     * @param state The "Special State" the robot should be set to.
     */
    public void actOnRobot(int alliance, int stationNumber, SpecialState state) {
        if (verify(alliance, stationNumber)) {
            if (state == SpecialState.ESTOP_ROBOT) {
                teams[alliance][stationNumber].ESTOP();
            } else if (state == SpecialState.ZERO_BATTERY) {
                teams[alliance][stationNumber].zeroBatteryVoltage();
            } else if (state == SpecialState.BYPASS) {
                teams[alliance][stationNumber].setBypassed(true);
            } else if (state == SpecialState.UNBYPASS) {
                teams[alliance][stationNumber].setBypassed(false);
            } else {
                System.out.println("INCORRECT STATE(in actOnRobot): NOTHING DONE");
            }
        } else {
            System.out.println("ActOnRobot Verification Error!");
        }
    }
   
    
    /**
     * Verify that the specific team is not null.
     *
     * @param alliance The alliance the team is on.
     * @param station The station the team is specifically on.
     * @return
     */
    private boolean verify(int alliance, int station) {
        return alliance < teams.length
                && station < teams[alliance].length
                && teams[alliance][station] != null;
    }
    //</editor-fold>

    /**
     * Checks to see if any teams have ESTOPPED their robots before the match
     * has started.
     *
     * @return True if no robots are ESTOPPED.
     */
    public boolean areRobotsNotESTOPPED() {
        for (Team[] alliance : teams) {
            for (Team team : alliance) {
                if (team.isESTOPPED()) {
                    return false;
                }
            }
        }
        return true;
    }
}