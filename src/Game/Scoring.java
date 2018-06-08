/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import OFMS.FieldAndRobots;
/**
 *
 * @author ellen.estep.one
 */
public class Scoring {
    int RedOwnershipScore;
    int BlueOwnershipScore;
    //Red and Blue total score
    int RedScore = 0;
    int BlueScore = 0;
    //Red Vault Score
    int RedVaultScore = 0;
    //Blue Vault Score
    int BlueVaultScore = 0;
    int RedTechFouls;
    int RedFouls;
    int BlueTechFouls;
    int BlueFouls;
    
    
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
        Cube_1, Cube_2, Cube_3, Cube_Zero, Force_PLAYED, Levitate_PLAYED, Boost_PLAYED;
    }
    
    public enum PowerUps
    {
        Force, Levitate, Boost;
    }
    public void RedBoost()
    {
         long Boost = System.currentTimeMillis() + 10000; // Current time + 10 seconds

        while(true){
            if(Boost <= System.currentTimeMillis()){
                
                int Redscore = RedVaultScore*2;
                Boost += 10000;
            }
                try{ 
                Thread.sleep(5);
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
                
                int Bluescore = BlueVaultScore*2;
                Boost += 10000;
            }
                try{ 
                Thread.sleep(5);
            }catch(Exception ex){
                
                System.err.println( ex.getMessage() );
            }
        }
     }
     public void redVault(Scoring.CubeNumbers numbers,Scoring.PowerUps powerups )
    {
        //the score for each cubes in the vault
        if ( numbers == Scoring.CubeNumbers.Cube_1)
        {
          int RedScore = RedVaultScore + 3;
        }
        if(numbers == Scoring.CubeNumbers.Cube_2)
        {
            int RedScore = RedVaultScore + 3;
        }
        if (numbers == Scoring.CubeNumbers.Cube_3)
        {
            int RedScore = RedVaultScore + 3;
        }
        if (numbers == Scoring.CubeNumbers.Force_PLAYED)
        {
            int RedScore = RedVaultScore*2;
        }
        if(numbers == Scoring.CubeNumbers.Boost_PLAYED)
        {
            RedBoost();
        }
        if(numbers == Scoring.CubeNumbers.Levitate_PLAYED)
        {
            int RedScore = RedVaultScore + 30;
        }
    }
    
    
    
    public void blueVault(Scoring.CubeNumbers numbers, Scoring.PowerUps powerups)
    {
        //Add the cube numbers for the Blue Vault
         if ( numbers == Scoring.CubeNumbers.Cube_1)
        {
            int score = BlueVaultScore+9;
        }
        if(numbers == Scoring.CubeNumbers.Cube_2)
        {
            int score = BlueVaultScore+9;
        }
        if (numbers == Scoring.CubeNumbers.Cube_3)
        {
            int score = BlueVaultScore+9;
        }
         if (numbers == Scoring.CubeNumbers.Force_PLAYED)
        {
            int BlueScore = BlueVaultScore*2;
        }
        if(numbers == Scoring.CubeNumbers.Boost_PLAYED)
        {
            BlueBoost();
        }
        if(numbers == Scoring.CubeNumbers.Levitate_PLAYED)
        {
            int BlueScore = BlueVaultScore + 30;
        }
    }
    
}
