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
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 *
 * @author Ethen B.
 */
public class Controller {
    //IP address for the Rasberry Pi on the Field for the LEDs
    private final String Led_IP = "10.0.0.20";
    
    private InetAddress LedAddr;
    private final static int DMX_DATA_POSITION = 126; 
 
    
    private DatagramSocket Led;
    private DatagramPacket len;
    
   int nearStripUniverse = 1;
   int farStripUniverse = 2;
   int packetTimeOut = 1;
   int numPixels = 150;
   int pixelDataOffset = 126;
  // String componentId = "O!FMS";
  
   Strip nearStrip = new Strip();
   Strip farStrip = new Strip();
   public static RandomString gameData;
   
 
    private int[] pointIndices;
   

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
    public Controller pointIndices(){return null;
}
    
    
  
    public void createBlankPacket(int universeNumber, int[] pointIndices)
            
    {
         super.equals(DMX_DATA_POSITION + pointIndices.length * 3); 
         this.pointIndices = pointIndices; 
        byte[] data = new byte[126+3*numPixels];
       
        int flagLength;
    
    // Preamble size
    data[0] = (byte) 0x00;
    data[1] = (byte) 0x10;
    // Post-amble size
    data[0] = (byte) 0x00;
    data[1] = (byte) 0x10;
    // ACN Packet Identifier
    data[4] = (byte) 0x41;
    data[5] = (byte) 0x53;
    data[6] = (byte) 0x43;
    data[7] = (byte) 0x2d;
    data[8] = (byte) 0x45;
    data[9] = (byte) 0x31;
    data[10] = (byte) 0x2e;
    data[11] = (byte) 0x31;
    data[12] = (byte) 0x37;
    data[13] = (byte) 0x00;
    data[14] = (byte) 0x00;
    data[15] = (byte) 0x00;
    // Flags and length
    flagLength = 0x00007000 | ((data.length - 16) & 0x0fffffff);
    data[16] = (byte) ((flagLength >> 8) & 0xff);
    data[17] = (byte) (flagLength & 0xff);
    // RLP 1.31 Protocol PDU Identifier
    data[18] = (byte) 0x00;
    data[19] = (byte) 0x00;
    data[20] = (byte) 0x00;
    data[21] = (byte) 0x04;
    // Sender's CID
    for (int i = 22; i < 38; ++i) {
        data[i] = (byte) i;
    }
    // Flags and length
    flagLength = 0x00007000 | ((data.length - 38) & 0x0fffffff);
    data[38] = (byte) ((flagLength >> 8) & 0xff);
    data[39] = (byte) (flagLength & 0xff);
    // DMP Protocol PDU Identifier
    data[40] = (byte) 0x00;
    data[41] = (byte) 0x00;
    data[42] = (byte) 0x00;
    data[43] = (byte) 0x02;
    // Source name
    data[44] = 'O';
    data[45] = '!'; 
    data[46] = 'F';
    data[47] = 'M';
    data[48] = 'S';
    for (int i = 48; i < 108; ++i) {
        data[i] = 0;
    }
    // Priority
    data[108] = 100;
    // Reserved
    data[109] = 0x00;
    data[110] = 0x00;
    // Sequence Number
    data[111] = 0x00;
    // Options
    data[112] = 0x00;
    // Universe number
    // 113-114 are done in setUniverseNumber()
    
    // Flags and length
    flagLength = 0x00007000 | ((data.length - 115) & 0x0fffffff);
    data[115] = (byte) ((flagLength >> 8) & 0xff);
    data[116] = (byte) (flagLength & 0xff);
    // DMP Set Property Message PDU
    data[116] = (byte) 0x02;
    // Address Type & Data Type
    data[117] = (byte) 0xa1;
    // First Property Address
    data[119] = 0x00;
    data[120] = 0x00;
    // Address Increment
    data[121] = 0x00;
    data[122] = 0x01;
    // Property value count
    int numProperties = 1 + this.pointIndices.length * 3;
    data[123] = (byte) ((numProperties >> 8) & 0xff);
    data[124] = (byte) (numProperties & 0xff);
    // DMX Start 
    data[125] = 0x00;
    
  }  

}
