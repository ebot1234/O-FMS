/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Scoring;

import java.util.Timer;
import java.util.TimerTask;

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
     //Scale sensors as a boolean value
     boolean scaleSensor1;
     boolean scaleSensor2;
     //Red Switch sensors
     boolean redSwitch1;
     boolean redSwitch2;
     //Blue Switch sensors
     boolean blueSwitch1;
     boolean blueSwitch2;
     
     
    
     Timer startTime;
     Timer endTime;
     Timer currentTime;
     
     //sets the Randomization of the scale or switch with the Scoring Table if true is red
     public void setRandomisation()
     {
         seesaw = nearIsRed; 
         
     }
     
    public void neitherAlliance()
    {
        neitherAlliance = redAlliance & blueAlliance;
    }
     //this does something? maybe sets the side of the scale and switch
    public void setSeesaw(boolean seesaw) {
        this.seesaw = seesaw;
        neitherAlliance();

        //need to add the current state of the powerups
        //if the scale sensors are reading that red has control
       if (scaleSensor1 = true)
       {
	ownedBy = redAlliance;
        } 
       if (scaleSensor2 = false)
       {
           ownedBy = redAlliance;
       }
       //tells the FMS if the scale is controlled by blue
       if(scaleSensor1 = false)
       {
           ownedBy = blueAlliance;
       }
       //Red Switch sensor 
       if(redSwitch1 = true)
       {
           ownedBy = redAlliance;
       }
       if ()
      
    }
    
    
}