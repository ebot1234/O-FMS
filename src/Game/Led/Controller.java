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
import java.nio.ByteBuffer;

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
  // String componentId = "O!FMS";
  
   Strip nearStrip = new Strip();
   Strip farStrip = new Strip();
   public static RandomString gameData;
   
  private static final byte componentId = "O!FMS".getBytes()[0];
   

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
       
        //ROOT LAYER
        data[0]=0x00;   // Preamble Size (high)
        data[1]=0x10;   // Preamble Size (low)
        data[2]=0x00;   // Post-amble Size (high)
        data[3]=0x00;   // Post-amble Size (low)
        data[4]=0x41;   // ACN Packet Identifier, Identifies this packet as E1.17 (12 bytes)
        data[5]=0x53;
        data[6]=0x43;
        data[7]=0x2d;
        data[8]=0x45;
        data[9]=0x31;
        data[10]=0x2e;
        data[11]=0x31;
        data[12]=0x37;
        data[13]=0x00;
        data[14]=0x00;
        data[15]=0x00;
        data[16]=0x72;  // Protocol flags and length, Low 12 bits = PDU length, High 4 bits = 0x7 (high)
        data[17]=0x6e;  // Protocol flags and length (low), 0x26e = 638 - 16
        data[18]=0x00;  // Vector, Identifies RLP Data as 1.31 Protocol PDU, (4 bytes)
        data[19]=0x00;
        data[20]=0x00;
        data[21]=0x04;
        for (int i = 0; i < 21; i++) {
            data[i] = componentId;
        }
       
        //E1.31 FRAMING LAYER
        data[38]=0x72;  // Framing Protocol flags and length (high)
        data[39]=0x58;  // 0x258 = 638 - 38
        data[40]=0x00;  // Framing Vector (indicates that the E1.31 framing layer is wrapping a DMP PDU)
        data[41]=0x00;
        data[42]=0x00;
        data[43]=0x02;
       for(int i = 0; i <43; i++){
           data[i] = componentId;
       }
 

        data[108]=100;                          // Priority, Data priority if multiple sources 0-200
        data[109]=0x00;                         // Reserved, Transmitter Shall Send 0 (high)
        data[110]=0x00;                         // Reserved, Transmitter Shall Send 0 (low)
        data[111]=0x00;                         // Sequence Number, To detect duplicate or out of order packets. 
        data[112]=0x00;                         // Options Flags, Bit 7 = Preview_Data, Bit 6 = Stream_Terminated
//        data[113]=(byte)(universeNr >> 8);      // Universe Number (high)
//        data[114]=(byte)(universeNr & 255);     // Universe Number (low)

        //DMP LAYER
        data[115]=0x72;                         // Protocol flags and length, Low 12 bits = PDU length, High 4 bits = 0x7 (high)
        data[116]=0x0b;                         // Protocol flags and length (low) 0x20b = 638 - 115
        data[117]=0x02;                         // DMP Vector (Identifies DMP Set Property Message PDU)
        data[118]=(byte)0xa1;                   // DMP Address Type & Data Type
        data[119]=0x00;                         // First Property Address (high), Indicates DMX START Code is at DMP address 0
        data[120]=0x00;                         // First Property Address (low)
        data[121]=0x00;                         // Address Increment (high)
        data[122]=0x01;                         // Address Increment (low)
        data[123]=0x02;                         // Property value count (high)
        data[124]=0x01;                         // Property value count (low)
        data[125]=0x00;                         // DMX512-A START Code
//512 DMX DATA
        
     
      
   
   
           
       
         
    }
    
}
