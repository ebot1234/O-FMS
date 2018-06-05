/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Real_Time_Scoring;
import UI.New_UI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

 
/**
 *
 * @author Ethen
 * For the generation of the scale and switch values before the start of the match
 */
public class GameData {
    private static Random random = new Random();
    
    public static void main(String[] args)
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
    //Incharge of getting the random item from the list of vaules
    private static void getRandomItem(List<String> gameData) {
        //Size of the list is 5
       int index = random.nextInt(gameData.size());
       System.out.println("" + gameData.get(index));
    }
    
}
