/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;


import PLC_Aux.PLC_Receiver;
import PLC_Aux.PLC_Sender;
/**
 *
 * @author Ethen B. 
 */
public class Scoring {
    
    private static Scoring _instance;

   
    private String dataStr;
    //Red and Blue Ownership Scores
    public int RedOwnershipScore;
    public int BlueOwnershipScore;
    //Red and Blue total score
    public int RedScore = 0;
    public int BlueScore = 0;
    //Red Vault Score
    public int RedVaultScore = 0;
    //Blue Vault Score
    public int BlueVaultScore = 0;
    public int RedTechFouls;
    public int RedFouls;
    public int BlueTechFouls;
    public int BlueFouls;
    
    
    public void RedScore(){
      RedScore =  RedOwnershipScore + RedVaultScore + RedTechFouls + RedFouls;
    }
    public void BlueScore(){
      BlueScore = BlueOwnershipScore + BlueVaultScore + BlueTechFouls + BlueFouls;
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
 
    
    //Power ups and Vault 
    
     public enum CubeNumbers
    {
        Cube_1, Cube_2, Cube_3, Cube_Zero, Lev_PLAYED, Force_PLAYED, Boost_PLAYED;
    }
    
    public enum PowerUps
    {
        Force_1, Levitate_2, Boost_2, Force_PLAYED, Boost_PLAYED, Lev_PLAYED, Force_2, Force_3, Levitate_1, Levitate_3, Boost_1, Boost_3;;
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
     public void redVault(Scoring.CubeNumbers numbers, Scoring.PowerUps powerUps)
    {
        //Red force PowerUp
       
         if (Scoring.CubeNumbers.Cube_1 == Scoring.CubeNumbers.Force_PLAYED)
        {
        }
         if(Scoring.CubeNumbers.Cube_2 == Scoring.CubeNumbers.Force_PLAYED)
         {
             RedScore = RedOwnershipScore*2;
             
         }
        if(Scoring.CubeNumbers.Cube_3 == Scoring.CubeNumbers.Force_PLAYED)
        {
            RedScore = RedOwnershipScore*2;
            
        }
     
            
        
        //Red Boost PowerUp
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
    
}
