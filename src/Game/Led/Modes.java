/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Led;

/**
 *
 * @author Ethen B.
 * This class contains all the modes for the led stuff
 */
public class Modes {
    
    String Mode;
    
    String offMode = Mode;
    String RedMode = Mode;
    String GreenMode = Mode;
    String BlueMode = Mode;
    String WhiteMode = Mode;
    String ChaseMode = Mode;
    String WarmupMode = Mode;
    String Warmup2Mode = Mode;
    String Warmup3Mode = Mode;
    String Warmup4Mode = Mode;
    String OwnedMode =Mode;
    String NotOwnedMode = Mode;
    String ForceMode = Mode;
    String BoostMode= Mode;
    String RandomMode = Mode;
    String FadeMode = Mode;
    String GradientMode =Mode;
    String BlinkMode = Mode;
    
    public void modeNames(){
        offMode = "Off";
        RedMode = "Red";
        GreenMode = "Green";
        BlueMode = "Blue";
        WhiteMode = "White";
        ChaseMode = "Chase";
        WarmupMode = "Warmup";
        Warmup2Mode = "Warmup Purple";
        Warmup3Mode = "Warmup Sneaky";
        Warmup4Mode = "Warmup Gradient";
        OwnedMode = "Owned";
        NotOwnedMode = "Not Owned";
        ForceMode = "Force";
        BoostMode = "Boost";
        RandomMode = "Random";
        FadeMode = "Fade";
        GradientMode = "Gradient";
        BlinkMode = "Blink";
    }
}
