/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Led;

import java.awt.Color;
import Game.Led.Colors;
import static Game.Led.Colors.blue;
import static Game.Led.Colors.green;
import static Game.Led.Colors.red;
import static Game.Led.Colors.white;
import Game.Led.Controller;
import OFMS.FieldAndRobots;
import java.util.List;
import PLC_Aux.PLC_Receiver;

/**
 *
 * @author Ethen B.
 */
public class Strip {
    
    Modes mode = new Modes();
     byte[] pixels;
    boolean isRed;
    String strip;
    byte[] oldPixels;
    int numPixels = 150;
   // private static final byte pixels = "pixels".getBytes()[0];
   // private static final byte oldpixels = "oldPixels".getBytes()[0];

    public Strip() {
        this.pixels = new byte[numPixels*3];
        this.oldPixels = new byte[numPixels*3];
    }
    
   
    
    public void updatePixels()
    {
       switch(strip){
        case "RedMode": 
             strip.updateSingleColorMode(red);
        
        case "GreenMode":
		strip.updateSingleColorMode(green);
        
	case "BlueMode":
		strip.updateSingleColorMode(blue);
	case "WhiteMode":
		strip.updateSingleColorMode(white);
	case "ChaseMode":
		strip.updateChaseMode();
	case "WarmupMode":
		strip.updateWarmupMode();
	case "Warmup2Mode":
		strip.updateWarmup2Mode();
	case "Warmup3Mode":
		strip.updateWarmup3Mode();
	case "Warmup4Mode":
		strip.updateWarmup4Mode();
	case "OwnedMode":
		strip.updateOwnedMode();
	case "NotOwnedMode":
		strip.updateNotOwnedMode();
	case "ForceMode":
		strip.updateForceMode();
	case "BoostMode":
		strip.updateBoostMode();
	case "RandomMode":
		strip.updateRandomMode();
	case "FadeMode":
		strip.updateFadeMode();
	case "GradientMode":
		strip.updateGradientMode();
	case "BlinkMode":
		strip.updateBlinkMode();
        
	default:
		strip.updateOffMode();
	}
      
    }
    public boolean shouldSendPacket(byte pixelData[])
    {
        for(int i=0;i<numPixels;i++)
        {
            byte[] pixel = new byte[9];
        if(pixel[i] == oldPixels)
        {
            return(true);
        }
        
        }
        return false;
        
    }
     
    public void populatePacketPixels(byte pixelData[])
    {
       for (int i = 0; i < 9; i++) {
           byte[] pixel = new byte[9];
            pixel[i] = 0;
            pixel[0] = pixelData[3*i];
            pixel[1] = pixelData[3*i+1];
            pixel[2] = pixelData[3*i+2];
        }
    }
   public boolean getColor(Colors){
	if (PLC_Aux.PLC_Recevier.dataStr.substring(1).equals("LLL")) 
        {
        } else {
            
        }
	return blue;
}
   public void getOppositeColor(Color) {
	if (strip.isRed) {
		return blue;
	}
	return red;
}
   public void getMidColor(Color){
	if strip.isRed {
		return purpleBlue
	}
        else{
            purpleRed;
        }}
}
    public void  getDimColor(Color) {
	if strip.isRed {
		return dimRed
	}
	return dimBlue
}
    public void getGradientStartOffset() int {
	if strip.isRed {
		return numPixels / 3;
	}
	return 2 * numPixels / 3;
}
    public void  updateOffMode() {
	for i := 0; i < numPixels; i++ {
		strip.pixels[i] = colors[black]
	}
}
    public void updateSingleColorMode(color color) {
	for i := 0; i < numPixels; i++ {
		strip.pixels[i] = colors[color]
	}
}
    public void updateChaseMode() {
	if strip.counter == int(black)*numPixels { // Ignore colors listed after white.
		strip.counter = 0
	}
	color := color(strip.counter / numPixels)
	pixelIndex := strip.counter % numPixels
	strip.pixels[pixelIndex] = colors[color]
}

public void updateWarmupMode() {
	endCounter := 250
	if strip.counter == 0 {
		// Show solid white to start.
		for i := 0; i < numPixels; i++ {
			strip.pixels[i] = colors[white]
		}
	} else if strip.counter <= endCounter {
		// Build to the alliance color from each side.
		numLitPixels := numPixels / 2 * strip.counter / endCounter
		for i := 0; i < numLitPixels; i++ {
			strip.pixels[i] = colors[strip.getColor()]
			strip.pixels[numPixels-i-1] = colors[strip.getColor()]
		}
	} else {
		// Prevent the counter from rolling over.
		strip.counter = endCounter
	}
}

public void updateWarmup2Mode() {
	startCounter := 100
	endCounter := 250
	if strip.counter < startCounter {
		// Show solid purple to start.
		for i := 0; i < numPixels; i++ {
			strip.pixels[i] = colors[purple]
		}
	} else if strip.counter <= endCounter {
		for i := 0; i < numPixels; i++ {
			strip.pixels[i] = getFadeColor(purple, strip.getColor(), strip.counter-startCounter,
				endCounter-startCounter)
		}
	} else {
		// Prevent the counter from rolling over.
		strip.counter = endCounter
	}
}

public void updateWarmup3Mode() {
	startCounter := 50
	middleCounter := 225
	endCounter := 250
	if strip.counter < startCounter {
		// Show solid purple to start.
		for i := 0; i < numPixels; i++ {
			strip.pixels[i] = colors[purple]
		}
	} else if strip.counter < middleCounter {
		for i := 0; i < numPixels; i++ {
			strip.pixels[i] = getFadeColor(purple, strip.getMidColor(), strip.counter-startCounter,
				middleCounter-startCounter)
		}
	} else if strip.counter <= endCounter {
		for i := 0; i < numPixels; i++ {
			strip.pixels[i] = getFadeColor(strip.getMidColor(), strip.getColor(), strip.counter-middleCounter,
				endCounter-middleCounter)
		}
	} else {
		// Maintain the current value and prevent the counter from rolling over.
		strip.counter = endCounter
	}
}

public void updateWarmup4Mode() {
	middleCounter := 100
	for i := 0; i < numPixels; i++ {
		strip.pixels[numPixels-i-1] = getGradientColor(i+strip.counter+strip.getGradientStartOffset(), numPixels/2)
	}
	if strip.counter >= middleCounter {
		for i := 0; i < numPixels; i++ {
			if i < strip.counter-middleCounter {
				strip.pixels[i] = colors[strip.getColor()]
			}
		}
	}
}

public void updateOwnedMode() {
	speedDivisor := 30
	pixelSpacing := 4
	if strip.counter%speedDivisor != 0 {
		return
	}
	for i := 0; i < numPixels; i++ {
		if i%pixelSpacing == strip.counter/speedDivisor%pixelSpacing {
			strip.pixels[i] = colors[strip.getColor()]
		} else {
			strip.pixels[i] = colors[black]
		}
	}
}

public void updateNotOwnedMode() {
	for i := 0; i < numPixels; i++ {
		strip.pixels[i] = colors[strip.getDimColor()]
	}
}

public void updateForceMode() {
	speedDivisor := 30
	pixelSpacing := 7
	if strip.counter%speedDivisor != 0 {
		return
	}
	for i := 0; i < numPixels; i++ {
		switch (i + strip.counter/speedDivisor) % pixelSpacing {
		case 2:
			fallthrough
		case 4:
			strip.pixels[i] = colors[strip.getOppositeColor()]
		case 3:
			strip.pixels[i] = colors[strip.getDimColor()]
		default:
			strip.pixels[i] = colors[black]
		}
	}
}

public void updateBoostMode() {
	speedDivisor := 4
	pixelSpacing := 4
	if strip.counter%speedDivisor != 0 {
		return
	}
	for i := 0; i < numPixels; i++ {
		if i%pixelSpacing == strip.counter/speedDivisor%pixelSpacing {
			strip.pixels[i] = colors[strip.getColor()]
		} else {
			strip.pixels[i] = colors[black]
		}
	}
}

public void updateRandomMode() {
	if strip.counter%10 != 0 {
		return
	}
	for i := 0; i < numPixels; i++ {
		strip.pixels[i] = colors[color(rand.Intn(int(black)))] // Ignore colors listed after white.
	}
}

public void updateFadeMode() {
	fadeCycles := 40
	holdCycles := 10
	if strip.counter == 4*holdCycles+4*fadeCycles {
		strip.counter = 0
	}

	for i := 0; i < numPixels; i++ {
		if strip.counter < holdCycles {
			strip.pixels[i] = colors[black]
		} else if strip.counter < holdCycles+fadeCycles {
			strip.pixels[i] = getFadeColor(black, red, strip.counter-holdCycles, fadeCycles)
		} else if strip.counter < 2*holdCycles+fadeCycles {
			strip.pixels[i] = colors[red]
		} else if strip.counter < 2*holdCycles+2*fadeCycles {
			strip.pixels[i] = getFadeColor(red, black, strip.counter-2*holdCycles-fadeCycles, fadeCycles)
		} else if strip.counter < 3*holdCycles+2*fadeCycles {
			strip.pixels[i] = colors[black]
		} else if strip.counter < 3*holdCycles+3*fadeCycles {
			strip.pixels[i] = getFadeColor(black, blue, strip.counter-3*holdCycles-2*fadeCycles, fadeCycles)
		} else if strip.counter < 4*holdCycles+3*fadeCycles {
			strip.pixels[i] = colors[blue]
		} else if strip.counter < 4*holdCycles+4*fadeCycles {
			strip.pixels[i] = getFadeColor(blue, black, strip.counter-4*holdCycles-3*fadeCycles, fadeCycles)
		}
	}
}

fpublic void updateGradientMode() {
	for i := 0; i < numPixels; i++ {
		strip.pixels[numPixels-i-1] = getGradientColor(i+strip.counter, 75)
	}
}

public void updateBlinkMode() {
	divisor := 10
	for i := 0; i < numPixels; i++ {
		if strip.counter%divisor < divisor/2 {
			strip.pixels[i] = colors[white]
		} else {
			strip.pixels[i] = colors[black]
		}
	}

    
    
    
    }
	
    

