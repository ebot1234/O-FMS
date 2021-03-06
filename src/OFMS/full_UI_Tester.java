package OFMS;

import UI.BlueAlliance.BlueAlli;
import UI.BlueAlliance.Blue_UI_Block;
import UI.Full_UI;
import UI.Match_Control_UI;
import UI.New_UI;
import UI.RedAlliance.RedAlli;
import UI.RedAlliance.Red_UI_Block;
import java.awt.Toolkit;

/**
 * This is the full UI intended to have all features related to being such.
 *
 * Complete System developed by:
 *
 * @author Alex Bassett
 * @author Walton Robotics - Team 2974
 * @author Foundation code developed by Josh, Andrew Lobos, Team 225
 * @author Structural assistance and refactoring by Doug Neumann
 */
public class full_UI_Tester extends javax.swing.JFrame {

    /**
     * Creates a new full UI.
     */
    public full_UI_Tester() {
        initComponents();
        this.setTitle("(O!FMS) Open Field Managment System");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass()
                .getClassLoader().getResource("OFMS/LogoFiles/ofms logo.png")));
        this.setResizable(true);
        setUpIndicators();
    }

    /**
     * Sets the active components, such as buttons and text fields, to the UI
     * layer, thus making it available for interaction with the user.
     */
    public final void setUpIndicators() {
        System.out.println("Full UI - Set Up Indicators");

        //<editor-fold defaultstate="collapsed" desc="Set Team Indicators">
        BlueAlli tempB_Alli = full_UI1.getNew_UI().getAllisUI().getBlue();
        Blue_UI_Block tempB1 = tempB_Alli.getBlue1();
        Main.layer.setBlue1(tempB1.getTeamIndic(), tempB1.getBypass(), tempB1.getVoltIndic(),
                tempB1.getRob_Com(), tempB1.getDS_Com());

        Blue_UI_Block tempB2 = tempB_Alli.getBlue2();
        Main.layer.setBlue2(tempB2.getTeamIndic(), tempB2.getBypass(), tempB2.getVoltIndic(),
                tempB2.getRob_Com(), tempB2.getDS_Com());

        Blue_UI_Block tempB3 = tempB_Alli.getBlue3();
        Main.layer.setBlue3(tempB3.getTeamIndic(), tempB3.getBypass(), tempB3.getVoltIndic(),
                tempB3.getRob_Com(), tempB3.getDS_Com());


        RedAlli tempR_Alli = full_UI1.getNew_UI().getAllisUI().getRed();
        Red_UI_Block tempR1 = tempR_Alli.get1();
        Main.layer.setRed1(tempR1.getTeamIndic(), tempR1.getBypass(), tempR1.getVoltIndic(),
                tempR1.getRob_Com(), tempR1.getDS_Com());

        Red_UI_Block tempR2 = tempR_Alli.get2();
        Main.layer.setRed2(tempR2.getTeamIndic(), tempR2.getBypass(), tempR2.getVoltIndic(),
                tempR2.getRob_Com(), tempR2.getDS_Com());

        Red_UI_Block tempR3 = tempR_Alli.get3();
        Main.layer.setRed3(tempR3.getTeamIndic(), tempR3.getBypass(), tempR3.getVoltIndic(),
                tempR3.getRob_Com(), tempR3.getDS_Com());
        //</editor-fold>

        Match_Control_UI mcui = full_UI1.getMatchControl();

        Main.layer.setSideIndicators(full_UI1.getNew_UI().getRedIndicator(), full_UI1.getNew_UI().getBlueIndicator());
        Main.layer.setFullFieldReady(full_UI1.getNew_UI().getFullFieldIndic());
        Main.layer.setMatchButton(mcui.getMatchButton());
        Main.layer.setResetButton(mcui.getResetButton());
        Main.layer.setTimeSetters(mcui.getAutonField(), mcui.getTeleField());
        Main.layer.setMatchTimeField(full_UI1.getNew_UI().getFullFieldIndic());
        Main.layer.setStopButton(mcui.getStopMatchButton());
        Main.layer.setProgressBar(full_UI1.getNew_UI().getProgBar());
        Main.layer.setSwitchViewButton(mcui.getSwitchButton());
    }

    /**
     * Returns a reference to the current Full UI panel.
     *
     * @return The Full UI panel.
     */
    public Full_UI getFullUI() {
        return full_UI1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        full_UI1 = new UI.Full_UI();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(810, 775));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(full_UI1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(full_UI1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method shouldn't be called...
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(full_UI_Tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(full_UI_Tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(full_UI_Tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(full_UI_Tester.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("*****ERROR - Called Full Main");
                new full_UI_Tester().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private UI.Full_UI full_UI1;
    // End of variables declaration//GEN-END:variables

    New_UI getNewUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
