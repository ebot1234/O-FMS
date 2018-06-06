/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import OFMS.FieldAndRobots;
import java.util.Timer;



/**
 *
 * @author Ethen B.
 */
public class Seesaw {
    
     private int alliance;
     private int redAlliance;
     private int blueAlliance;
     boolean nearIsRed;
     private boolean seesaw;
     private int neitherAlliance;
     private int ownedBy;
     //Scale sensors 
     boolean scaleSensor1; //defult = true no score if false blue score
     boolean scaleSensor2;//if true red score
     //Red Switch sensors
     boolean redSwitch1;//if false blue scores
     boolean redSwitch2;//if true red scores
     //Blue Switch sensors
     boolean blueSwitch1;//same as above
     boolean blueSwitch2;//same as above
     int score;
    int BlueVaultScore = 0;
    int RedVaultScore = 0;
    int BlueOwnershipScore = 0; 
    int RedOwnershipscore = 0;
     Timer startTime;
     Timer endTime;
     Timer currentTime;
     
     
     public void RedOwnership(){
     long Ownership = System.currentTimeMillis() + 1000; // Current time + 1 second

        while(true){
            if(Ownership <= System.currentTimeMillis()){
                
                RedOwnershipscore++;
                Ownership += 1000;
            }
                try{ 
                Thread.sleep(5);
            }catch(Exception ex){
                
                System.err.println( ex.getMessage() );
            }
        }
     }
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
     //sets the Randomization of the scale or switch with the Scoring Table if true is red
     public void setRandomisation()
     {
         seesaw = nearIsRed; 
         
     }
     
    public void neitherAlliance()
    {
        neitherAlliance = redAlliance & blueAlliance;
    }
    
    public void RedScore() {
        // To keep track of the total red score
       
    }
    public void BlueScore() {
        // To keep track of the total blue score
       
    }
   
   
     //this does something? maybe sets the side of the scale and switch
    public void setSeesaw(boolean seesaw) {
        this.seesaw = seesaw;
        neitherAlliance();

        //need to add the current state of the powerups
        
       //scale stuff
      /* if(scaleSensor1 = false)
       {
           ownedBy = blueAlliance;
           BlueScore();
       }
       if (scaleSensor2 = true);
       {
           ownedBy = redAlliance;
           RedScore();
       }
       //Red Switch sensor input
       if(redSwitch1 = false)
       {
           ownedBy = blueAlliance;
           BlueScore();
       }
       if (redSwitch2 = true)
       {
           ownedBy = redAlliance;
           RedScore();
       }
       //Blue switch sensors input
       if (blueSwitch1 = false)
       {
           ownedBy = blueAlliance;
           BlueScore();
       }
       if(blueSwitch2 = true)
       {
           ownedBy = redAlliance;
           RedScore();
       }*/
      
    
    
    
    }}