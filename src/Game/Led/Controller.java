/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Led;

import Game.RandomString;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Ethen B.
 */
public class Controller {
    //IP address for the Rasberry Pi on the Field for the LEDs
    private final String Led_IP = "10.0.0.20";
    
    private InetAddress LedAddr;
    
    private DatagramSocket Led;
    private DatagramPacket len;
    
   int nearStripUniverse = 1;
   int farStripUniverse = 2;
   int packetTimeOut = 1;
   int numPixels = 150;
   int pixelDataOffset = 126;
   String componentId = "O!FMS";
  
   Strip nearStrip = new Strip();
   Strip farStrip = new Strip();
   public static RandomString gameData;
   

    private  void LED(){
    
    try {
            System.out.println("Made New Socket for the Leds");
            Led = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("PLC Error 1!");
            e.printStackTrace();
        }
        try {
            System.out.println("Made new address for the Leds...");
            LedAddr = InetAddress.getByName(Led_IP);
        } catch (IOException ex) {
            System.out.println("Led Error 2!");
            ex.printStackTrace();
        }
    }
    
    public void setMode(nearMode, farMode, Mode){
    
       nearStrip.currentMode = nearMode;
       nearStrip.counter = 0;
       farStrip.currentMode = farMode;
       farStrip.counter = 0;
    }
    
    public void getCurrentMode(Mode){
        
        if(nearStrip.currentMode == farStrip.currentMode)
        {
            setMode(nearMode, farMode,  Mode) == nearStrip.currentMode;
        }    
        else
        {
            Modes.offMode();
        }
    }
    Boolean isRed;
    public void setSidedness(){
        
        if(gameData.equals("LLL"))
        {
            nearStrip.isRed = nearIsRed;
            farStrip.isRed = farIsRed;
        } 
        if(gameData.equals("LRL"))
        {
            nearStrip.isRed = nearIsRed;
            farStrip.isRed = farIsRed;
        } 
        if(gameData.equals("RLR"))
        {
            nearStrip.isRed = nearIsRed;
            farStrip.isRed = farIsRed;
        } 
        if(gameData.equals("RRR"))
        {
            nearStrip.isRed = nearIsRed;
            farStrip.isRed = farIsRed;
        }
     }
    
    public void updateLeds()
    {
        nearStrip.updatePixels();
        farStrip.updatePixels();
        
        if(len = controller.packet()== 0)
        {
            len = createBlankPacket();
        }
        if(nearStrip.shouldSendPacket())
        {
            nearStrip.populatePacketPixels(packet[pixelDataOffset:]);
            Controller.sendPacket(farStripUniverse);
        }
        if(farStrip.shouldSendPacket())
        {
            farStrip.populatePacketPixels(packet[pixelDataOffset:]);
            Controller.sendPacket(farStripUniverse);
        }
    }
    public void createBlankPacket()
    {
        byte[] data = new byte[126+3*numPixels];
       
        //Preamble size
        data[0] = 0x00;
        data[1] = 0x10;
        
        //postamble size
        data[2] = 0x00;
        data[3] = 0x00;
        
        //ACN Packet Identifyer
        data[4] = 0x41;
        data[5] = 0x53;
        data[6] = 0x43;
        data[7] = 0x2d;
        data[8] = 0x45;
        data[9] = 0x31;
        data[10] = 0x2e;
        data[11] = 0x31;
        data[12] = 0x37;
        data[13] = 0x00;
        data[14] = 0x00;
        data[15] = 0x00;
        
        //Root PDU length and Flags
       int rootPduLength = data [-16];
        data[16] = (byte) (0x70 | rootPduLength);
        data[17] = (byte) (rootPduLength | 0xff);
        
        //E1.31 vector
        data[18] = 0x00;
        data[19] = 0x00;
        data[20] = 0x00;
        data[21] = 0x04;
        
       //Compentent Id
    
           
       
         
    }
    
}
