package splashscreen;

import java.awt.*;
import javax.swing.*;

public class FMS_SplashScreen extends JWindow {

//    private static JProgressBar progressBar = new JProgressBar();
//    private static FMS_SplashScreen execute;
    private static int count;

    public FMS_SplashScreen() {
        Container container = getContentPane();
        container.setLayout(null);

        LogoPanel a = new LogoPanel();
        a.setBounds(0, 0, (int) a.getLogoWidth(), (int) a.getLogoHeight());
        container.add(a);

        /*
         progressBar.setMaximum(50);
         progressBar.setBounds(55, 180, 250, 15);
         container.add(progressBar);
         */
        //setSize(400, 200);
        setSize(a.getLogoWidth(), a.getLogoHeight());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            System.out.println("Success");
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void splashTheScreen(final FMS_SplashScreen daSplash) {
        boolean kill = false;
//        timer1 = new Timer();
        final double ORIG_TIME_MILLIS = System.currentTimeMillis();
        while (!kill) {
                //count++;
            //progressBar.setValue(count);
            if ((System.currentTimeMillis() - ORIG_TIME_MILLIS) > 1000) { //count == 30
                //timer1.stop();
                daSplash.setVisible(false);
                kill = true;
                //load the rest of your application
            }
        }
    }
}
//
//        ActionListener al = new ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            }
//        };
//        timer1 = new Timer(50, al);
//        timer1.start();
//
//    public static void main(String args[]) {
//        execute = new FMS_SplashScreen();
//    }
//
//new ImageIcon(getClass().getResource("/splashscreen/O!FMS DE Logo (Custom).png"));
/*
 JPanel panel = new JPanel();
 panel.setBorder(new javax.swing.border.EtchedBorder());
 panel.setBackground(new Color(255,255,255));
 panel.setBounds(10,10,348,150);
 panel.setLayout(null);
 container.add(panel);  
 //"Hello World!");
 //label.setFont(new Font("Verdana",Font.BOLD,14));
 //label.setBounds(85,25,280,30);
 //"Hello");
 //label.setIcon(icon);
 //label.setFont(new Font("Verdana",Font.BOLD,14));
 */
/*
 ImageIcon icon = createImageIcon("/splashscreen/O!FMS DE Logo (Custom).png", "Raha");

 JLabel label = new JLabel(icon);
 label.setBounds(25, 40, icon.getIconWidth(), icon.getIconHeight());
 container.add(label);
 */
//a.setBorder(new javax.swing.border.AbstractBorder() {});//EtchedBorder());
//a.setBackground(new Color(255, 255, 255));
//a.setBounds(0, 0, 350, 100); // Good!
//a.setBounds(a.getBounds());
//a.setLayout(null); //BAD
        //a.setVisible(true); // Don't matter
